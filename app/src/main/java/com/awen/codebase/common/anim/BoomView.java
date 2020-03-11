package com.awen.codebase.common.anim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.awen.codebase.R;


public class BoomView extends View {
	//爆炸效果资源图
	private Bitmap bmpBoom,boosBoom;

	//爆炸效果的位置坐标
	private int boomX, boomY;
	//爆炸动画播放当前的帧下标
	private int cureentFrameIndex;

	private int boosBoomFrameIndex;
	//爆炸效果的总帧数
	private int totleFrame;
	//每帧的宽高
	private int frameW, frameH;
	private int frameW1, frameH1;
	//是否播放完毕，优化处理
	public boolean playEnd;

	private Context mContext;
	private Paint mPaint;
	private ValueAnimator lineAnimator;
	private int duration = 1500;

	public BoomView(Context context) {
		super(context);
		init(context);
	}

	public BoomView(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public BoomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}

	private void init(Context context){
		mContext = context;
		bmpBoom = BitmapFactory.decodeResource(getResources(), R.drawable.boom);
		boosBoom = BitmapFactory.decodeResource(getResources(), R.drawable.boos_boom);
		boomX = 250;
		boomY = 800;
		totleFrame = 7;
		mPaint = new Paint();
		frameW = bmpBoom.getWidth() / totleFrame;
		frameH = bmpBoom.getHeight();

		frameW1 = boosBoom.getWidth()/5;
		frameH1 = boosBoom.getHeight();

		showView();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.save();
		canvas.clipRect(boomX, boomY, boomX + frameW, boomY + frameH );
		canvas.drawBitmap(bmpBoom, boomX - cureentFrameIndex * frameW, boomY, mPaint);
		canvas.restore();


		canvas.save();
		canvas.clipRect(boomX+100, boomY, boomX+100 + frameW, boomY + frameH );
		canvas.drawBitmap(bmpBoom, boomX+100 - cureentFrameIndex * frameW, boomY, mPaint);
		canvas.restore();

		canvas.save();
		canvas.clipRect(boomX+200, boomY, boomX+200 + frameW1, boomY + frameH1 );
		canvas.drawBitmap(boosBoom, boomX+200 - cureentFrameIndex * frameW1, boomY, mPaint);
		canvas.restore();


		canvas.save();
		canvas.clipRect(boomX+300, boomY, boomX+300 + frameW, boomY + frameH );
		canvas.drawBitmap(bmpBoom, boomX+300 - cureentFrameIndex * frameW, boomY, mPaint);
		canvas.restore();


		canvas.save();
		canvas.clipRect(boomX+400, boomY, boomX+400 + frameW, boomY + frameH );
		canvas.drawBitmap(bmpBoom, boomX+400 - cureentFrameIndex * frameW, boomY, mPaint);
		canvas.restore();


		canvas.save();
		canvas.clipRect(boomX+500, boomY, boomX+500 + frameW, boomY + frameH );
		canvas.drawBitmap(bmpBoom, boomX+500 - cureentFrameIndex * frameW, boomY, mPaint);
		canvas.restore();

	}


	public void logic() {
		if (cureentFrameIndex < totleFrame) {
			cureentFrameIndex++;
		} else {
			cureentFrameIndex = 0;
		}
	}

	private void showView() {
		if (lineAnimator == null) {
			lineAnimator = ValueAnimator.ofInt(0, 6);
			lineAnimator.setDuration(duration);
			lineAnimator.setRepeatCount(5);
			lineAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
						@Override
						public void onAnimationUpdate(ValueAnimator animation) {
							cureentFrameIndex = (Integer) animation.getAnimatedValue();
							if(cureentFrameIndex<=5){
								boosBoomFrameIndex = cureentFrameIndex;
							}
							invalidate();
						}
					});
			lineAnimator.addListener(new AnimatorListenerAdapter() {
				@Override
				public void onAnimationEnd(Animator animation) {
					super.onAnimationEnd(animation);
					release();
				}
			});
		}
		lineAnimator.setInterpolator(new LinearInterpolator());
		lineAnimator.start();
	}

	/**
	 * 释放资源
	 *
	 * @author AwenZeng
	 */
	public void release() {
		setVisibility(View.GONE);
		if (bmpBoom != null) {
			bmpBoom.recycle();
			bmpBoom = null;
		}
	}
}
