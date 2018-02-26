package com.awen.codebase.anim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.awen.codebase.R;


/**
 * 加载自定义View<p>
 * 简单描述：旋转icon,加载文字可以放在：icon右边和icon下面
 * 在xml中使用：<p>
 * 1.需要在xml文件的顶部添加这句话xmlns:loading="http://schemas.android.com/apk/res/com.boyaa.androidmarket"<p> 
 * 2.之后可以在空间中直接使用配置参数。例如：<p>
 * loading:showSmallLoadingView="true"<p>
   loading:iconTransparent="false"<p>
   loading:showText="false"<p>
   
 * @author awenzeng
 *
 */
public class LoadingView extends View {

	private Paint mPaint;
	private Matrix matrix;
	private Context mContext;
	private String loadingStr = "加载中";
	private String tempStr = "";
	private float textSize = 22;
	private int iconWidth;
	private int iconHeith;
	private int textColor = 0xFF0084DC;
	private int posImgLeft;
	private int posImgRight;
	private int posTextLeft;
	private int posTextRight;
	private int textShowPos;
	private int flyPosLeft;
	private int flyPosRight;
	private int mode = 1;
	private float degrees = 0; // 旋转角度
	private int x, y, z, a, b, c;
	private int alpha = 255;
	private int radius;
	private int textPosition = 0;
	private PaintFlagsDrawFilter pfd;
	private boolean isDraw = true;
	private boolean isShowFlyCardItem = false;
	private boolean isShowTextTips = true;
	private boolean isIconTransparent = true;
	private boolean isSmallLoadingView = false;
	private Bitmap fixedBitmap;// 不动图片
	private Bitmap rotateBitmap;// 旋转图片
	private Bitmap flySpadeBitmap;
	private Bitmap flyClubBitmap;
	private Resources res;
	/**
	 * True:  显示在右边<p>
	 * false：显示在下面
	 */
	private boolean isShowRightOrDown = true;
	public static final int TEXT_SHOW_RIGHT = 0;
	public static final int TEXT_SHOW_DOWN = 1;
	
	private static final int DRAW_INTERVAL = 30;
	private static final float ROTATE_SPEED = 0.5f;
	private long mLastDrawTime = -1;
	private ValueAnimator delayAnimator;
	
	private static final int ROTATE_CIRCLE_TIME = 1300;

	public LoadingView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initAttrs(context,attrs);
	}

	public LoadingView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initAttrs(context,attrs);
		
	}

	public LoadingView(Context context) {
		super(context);
		init(context);
	}
    private void initAttrs(Context context, AttributeSet attrs){
		TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.LoadingView);
		textSize = mTypedArray.getDimension(R.styleable.LoadingView_loadingTextSize, 22);
		isShowFlyCardItem = mTypedArray.getBoolean(R.styleable.LoadingView_showFlyCards, false);
		isShowTextTips = mTypedArray.getBoolean(R.styleable.LoadingView_showTextWord, true);
		isSmallLoadingView = mTypedArray.getBoolean(R.styleable.LoadingView_showSmallLoadingView, false);
		isIconTransparent = mTypedArray.getBoolean(R.styleable.LoadingView_iconTransparent, true);
		textPosition = mTypedArray.getInt(R.styleable.LoadingView_textPositon, TEXT_SHOW_DOWN);
		if(textPosition == TEXT_SHOW_DOWN){
			isShowRightOrDown = false;
		}else if(textPosition == TEXT_SHOW_RIGHT){
			isShowRightOrDown = true;
		}
		if(isIconTransparent){
			textColor = 0xFFCDD3DD;
		}else{
			textColor = 0xFF0084DC;
		}
		init(context);

		mTypedArray.recycle();
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    	super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		posImgLeft = getMeasuredWidth() / 2 - radius;
		posImgRight = getMeasuredHeight() / 2 - radius;
		posTextLeft = getMeasuredWidth()/ 2 - radius;
		posTextRight = getMeasuredHeight()/ 2 + radius + 35;
		mPaint.setTextSize(textSize);
		mPaint.setColor(textColor);
		if (loadingStr.length() == 0 || loadingStr.equals("")) {
			isShowTextTips = false;
		} else {
			if (!isShowRightOrDown) {
				textShowPos = posTextLeft;
				if (mPaint.measureText(loadingStr) > 2 * radius) {
					textShowPos = posTextLeft - ((int) mPaint.measureText(loadingStr)) / 2+ radius;
				} else {
					textShowPos = posTextLeft + (radius - (int) mPaint.measureText(loadingStr) / 2);
				}
			}else{
				posImgLeft = posImgLeft - ((int)mPaint.measureText(loadingStr) + radius + 20)/2;
			} 
		}
    }
    
	private void init(Context context) {
		this.mContext = context;
		res = mContext.getResources();
		mPaint = new Paint();
		matrix = new Matrix();
		textSize = 50;
		if(isSmallLoadingView){
			iconWidth = 44;
			textSize = 15;
		}else{
			iconWidth = 66;
			textSize = 22;
		}
		iconHeith = iconWidth;
		pfd = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG
				| Paint.FILTER_BITMAP_FLAG);
		if(isIconTransparent){
			fixedBitmap =  BitmapFactory.decodeResource(res, R.drawable.loading_fixed_img);
			rotateBitmap = BitmapFactory.decodeResource(res, R.drawable.loading_rotate_img);
		}else{
			fixedBitmap =  BitmapFactory.decodeResource(res, R.drawable.loading_fixed_deep_img);
			rotateBitmap = BitmapFactory.decodeResource(res, R.drawable.loading_rotate_deep_img);
		}
		flySpadeBitmap = BitmapFactory.decodeResource(res, R.drawable.loading_spade);
		flyClubBitmap = BitmapFactory.decodeResource(res, R.drawable.loading_club);
		radius = fixedBitmap.getWidth() / 2;
		mLastDrawTime = SystemClock.elapsedRealtime();
		showTime();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		calculate(SystemClock.elapsedRealtime());
		mPaint.reset();
		canvas.setDrawFilter(pfd);
		if (fixedBitmap != null&&!fixedBitmap.isRecycled()) {
			canvas.drawBitmap(fixedBitmap, posImgLeft, posImgRight, mPaint);
		}
		if (isShowTextTips) {
			mPaint.reset();
			mPaint.setTextSize(textSize);
			mPaint.setColor(textColor);
			if (mode == 0) {
				tempStr = loadingStr + ".";
			} else if (mode == 10) {
				tempStr = loadingStr + "..";
			} else if (mode == 20) {
				tempStr = loadingStr + "...";
			}
			if (isShowRightOrDown) {
				canvas.drawText(tempStr,
						posImgLeft + 2 * radius + 20,
						posImgRight + radius + 10, mPaint);
			} else {
				canvas.drawText(tempStr, textShowPos, posTextRight, mPaint);
			}

		}
		mPaint.reset();
		matrix.reset();
		matrix.postTranslate(posImgLeft, posImgRight);
		if (rotateBitmap != null&&!rotateBitmap.isRecycled()) {
			matrix.postRotate(degrees,
					posImgLeft + rotateBitmap.getWidth() / 2, posImgRight
							+ rotateBitmap.getHeight() / 2);
			canvas.drawBitmap(rotateBitmap, matrix, mPaint);
		}
		if (isShowFlyCardItem) {
			showFlyBitmap(canvas, flySpadeBitmap, posImgLeft, posImgRight
					- radius);
			showFlyBitmap(canvas, flyClubBitmap, posImgLeft + 2 * radius,
					posImgRight);
			showFlyBitmap3(canvas, flyClubBitmap, posImgLeft, posImgRight + 2
					* radius);
			showFlyBitmap3(canvas, flyClubBitmap, posImgLeft, posImgRight
					- radius);
			showFlyBitmap3(canvas, flySpadeBitmap, posImgLeft - radius,
					posImgRight + radius);
		}
	}
	
	public boolean calculate(long currentTimeMillis){
		long time = currentTimeMillis - mLastDrawTime;
		degrees += time * 360 / ROTATE_CIRCLE_TIME;
		if(degrees > 360) {
			degrees -= 360;
		}
		
		if (mode < 30){
			mode++;
		}else{
			mode = 0;
		}
		
		x -= 1;
		y -= 2;
		z -= 3;
		a -= 2;
		alpha -= 5;
		if (alpha < 0){
			alpha = 0;
		}else if (alpha == 0){
			alpha = 255;
			x = 0;
			y = 0;
			z = 0;
			a = 0;
		}
		long delay = DRAW_INTERVAL - time;
		if(delay < 0){
			delay = 0;
		}
		postInvalidateDelayed(delay);
		mLastDrawTime = currentTimeMillis;
		return true;
	}
	
	private void showFlyBitmap(Canvas canvas, Bitmap flyBitmap, int left,
                               int right) {
		matrix.reset();
		flyPosLeft = left + Math.abs(a);
		flyPosRight = right + a;
		matrix.postTranslate(flyPosLeft, flyPosRight);
		matrix.postRotate(degrees + 57, flyPosLeft + flySpadeBitmap.getWidth()
				/ 2, flyPosRight + flySpadeBitmap.getHeight() / 2);
		mPaint.setAlpha(alpha);
		if(flyBitmap != null&&!flyBitmap.isRecycled()){
			canvas.drawBitmap(flyBitmap, matrix, mPaint);
		}
		
	}

	private void showFlyBitmap1(Canvas canvas, Bitmap flyBitmap, int left,
                                int right) {
		matrix.reset();
		flyPosLeft = left + Math.abs(a);
		flyPosRight = right + a;
		matrix.postTranslate(flyPosLeft, flyPosRight);
		matrix.postRotate(degrees + 57, flyPosLeft + flySpadeBitmap.getWidth()
				/ 2, flyPosRight + flySpadeBitmap.getHeight() / 2);
		mPaint.setAlpha(alpha);
		if(flyBitmap != null&&!flyBitmap.isRecycled()){
			canvas.drawBitmap(flyBitmap, matrix, mPaint);
		}
	}

	private void showFlyBitmap2(Canvas canvas, Bitmap flyBitmap, int left,
                                int right) {
		matrix.reset();
		flyPosLeft = left + Math.abs(a);
		flyPosRight = right + a;
		matrix.postTranslate(flyPosLeft, flyPosRight);
		matrix.postRotate(degrees + 57, flyPosLeft + flySpadeBitmap.getWidth()
				/ 2, flyPosRight + flySpadeBitmap.getHeight() / 2);
		mPaint.setAlpha(alpha);
		if(flyBitmap != null&&!flyBitmap.isRecycled()){
			canvas.drawBitmap(flyBitmap, matrix, mPaint);
		}
	}

	private void showFlyBitmap3(Canvas canvas, Bitmap flyBitmap, int left,
                                int right) {
		matrix.reset();
		flyPosLeft = left + x;
		flyPosRight = right - x * x / 30;
		matrix.postTranslate(flyPosLeft, flyPosRight);
		matrix.postRotate(degrees + 34, flyPosLeft + flySpadeBitmap.getWidth()
				/ 2, flyPosRight + flySpadeBitmap.getHeight() / 2);
		mPaint.setAlpha(alpha);
		if(flyBitmap != null&&!flyBitmap.isRecycled()){
			canvas.drawBitmap(flyBitmap, matrix, mPaint);
		}
	}

	private void showFlyBitmap4(Canvas canvas, Bitmap flyBitmap, int left,
                                int right) {
		matrix.reset();
		flyPosLeft = left + x;
		flyPosRight = right - x * x / 30;
		matrix.postTranslate(flyPosLeft, flyPosRight);
		matrix.postRotate(degrees + 34, flyPosLeft + flySpadeBitmap.getWidth()
				/ 2, flyPosRight + flySpadeBitmap.getHeight() / 2);
		mPaint.setAlpha(alpha);
		if(flyBitmap != null&&!flyBitmap.isRecycled()){
			canvas.drawBitmap(flyBitmap, matrix, mPaint);
		}
	}

	private void showFlyBitmap5(Canvas canvas, Bitmap flyBitmap, int left,
                                int right) {
		matrix.reset();
		flyPosLeft = left + x;
		flyPosRight = right - x * x / 30;
		matrix.postTranslate(flyPosLeft, flyPosRight);
		matrix.postRotate(degrees + 34, flyPosLeft + flySpadeBitmap.getWidth()
				/ 2, flyPosRight + flySpadeBitmap.getHeight() / 2);
		mPaint.setAlpha(alpha);
		if(flyBitmap != null&&!flyBitmap.isRecycled()){
			canvas.drawBitmap(flyBitmap, matrix, mPaint);
		}
	}
    private void showTime(){
    	if (delayAnimator == null) {
    		delayAnimator = ValueAnimator.ofFloat(0,10);
    		delayAnimator.setDuration(5000);
    		delayAnimator.addListener(new AnimatorListenerAdapter() {
				@Override
				public void onAnimationEnd(Animator animation) {
					super.onAnimationEnd(animation);
					setVisibility(View.GONE);
					recycle();
				}
			});
		}
    	delayAnimator.start();
    }


	public float getTextSize() {
		return textSize;
	}

	public void setTextSize(float textSize) {
		this.textSize = textSize;
	}

	public int getTextColor() {
		return textColor;
	}

	public void setTextColor(int textColor) {
		this.textColor = textColor;
	}

	public int getTextPosition() {
		return textPosition;
	}

	public void setTextPosition(int textPosition) {
		this.textPosition = textPosition;
	}

	public boolean isIconTransparent() {
		return isIconTransparent;
	}

	public void setIconTransparent(boolean isIconTransparent) {
		this.isIconTransparent = isIconTransparent;
	}

	public Bitmap getFixedBitmap() {
		return fixedBitmap;
	}

	public void setFixedBitmap(Bitmap fixedBitmap) {
		this.fixedBitmap = fixedBitmap;
	}

	public Bitmap getRotateBitmap() {
		return rotateBitmap;
	}

	public void setRotateBitmap(Bitmap rotateBitmap) {
		this.rotateBitmap = rotateBitmap;
	}

	public Bitmap getFlySpadeBitmap() {
		return flySpadeBitmap;
	}

	public void setFlySpadeBitmap(Bitmap flySpadeBitmap) {
		this.flySpadeBitmap = flySpadeBitmap;
	}

	public Bitmap getFlyClubBitmap() {
		return flyClubBitmap;
	}

	public void setFlyClubBitmap(Bitmap flyClubBitmap) {
		this.flyClubBitmap = flyClubBitmap;
	}

	public String getLoadingStr() {
		return loadingStr;
	}

	public void setLoadingStr(String loadingStr) {
		this.loadingStr = loadingStr;
		mPaint.setTextSize(textSize);
	}
	/**
	 * 设置加载文字文字
	 * @param resId
	 */
	public void setText(int resId) {
		this.loadingStr = mContext.getString(resId);
		mPaint.setTextSize(textSize);
	}

	public boolean isShowFlyCardItem() {
		return isShowFlyCardItem;
	}

	public void setShowFlyCardItem(boolean isShowFlyCardItem) {
		this.isShowFlyCardItem = isShowFlyCardItem;
	}

	public boolean isShowTextTips() {
		return isShowTextTips;
	}

	public void setShowTextTips(boolean isShowTextTips) {
		this.isShowTextTips = isShowTextTips;
	}
	
	/**
	 * 释放掉用到的Bitmap和关闭线程
	 */
	private void recycle() {
		Log.i("good", "加载框释放");
		isDraw = false;
		if (fixedBitmap != null&&!fixedBitmap.isRecycled()) {
			fixedBitmap.recycle();
			fixedBitmap = null;
		}
		if (rotateBitmap != null&&!rotateBitmap.isRecycled()) {
			rotateBitmap.recycle();
			rotateBitmap = null;
		}
		if (flySpadeBitmap != null&&!flySpadeBitmap.isRecycled()) {
			flySpadeBitmap.recycle();
			flySpadeBitmap = null;
		}
		if (flyClubBitmap != null&&flyClubBitmap.isRecycled()) {
			flyClubBitmap.recycle();
			flyClubBitmap = null;
		}
	}
}