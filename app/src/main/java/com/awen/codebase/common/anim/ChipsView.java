package com.awen.codebase.common.anim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.awen.codebase.R;


public class ChipsView extends View {
	private Bitmap mIcon;
	private Context mContext;
	private float moveX = 0;
	private float moveY = 0;
	private float moveLineX = 0;
	private float moveLineY = 0;
	private int bgWidth;
	private int bgHeight;
	private Paint mPaint;
	private RectF mBgRect;
	private float posY;
	private float mDensity;
	private ValueAnimator cycleFrowardAnimator;
	private ValueAnimator cycleBackAnimator;
	private ValueAnimator delayAnimator;
	private DisplayMetrics dm;
	public ChipsView(Context context) {
		super(context);
		init(context);
		posY = 300*mDensity;
		moveLineY = posY;
		moveY = posY;
	}

	public ChipsView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
		moveY = 0;
	}


	public ChipsView(Context context, AttributeSet attrs) {
		super(context, attrs);
		moveY = 0;
		init(context);
	}
	
    private void init(Context context){
		this.mContext = context;
		dm = mContext.getResources().getDisplayMetrics();
		mDensity = dm.density;
		mPaint = new Paint();
		setFocusable(false);
		setFocusableInTouchMode(false);
		mIcon = BitmapFactory.decodeResource(getResources(), R.drawable.chips);
		forwardAnim();
		showTime();
    }

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		canvas.drawBitmap(mIcon, moveX, moveY, mPaint);
		canvas.drawBitmap(mIcon, moveX, posY, mPaint);
		canvas.drawBitmap(mIcon, posY/2, moveY, mPaint);
		Matrix matrix = new Matrix();
	    matrix.setScale(moveX/200, moveX/200,mIcon.getWidth()/2,mIcon.getWidth()/2);
	    matrix.postTranslate(posY*2/3,2*posY - 400);
		canvas.drawBitmap(mIcon, matrix, mPaint);
	}
   private void forwardAnim(){
		if (cycleFrowardAnimator == null) {
			cycleFrowardAnimator = ValueAnimator.ofFloat(0,300*mDensity);
			cycleFrowardAnimator.setDuration(3000);
			cycleFrowardAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
						@Override
						public void onAnimationUpdate(ValueAnimator animation) {
							float animationValue = (Float)animation.getAnimatedValue();
						    moveX = animationValue;
						    moveY = (float) ( posY + Math.sqrt((posY/2)*(posY/2)-(moveX-posY/2)*(moveX-posY/2)));
							invalidate();
						}
					});
			cycleFrowardAnimator.addListener(new AnimatorListenerAdapter() {
				@Override
				public void onAnimationEnd(Animator animation) {
					super.onAnimationEnd(animation);
					backTimeAnim();
				}
			});
		}
		cycleFrowardAnimator.setInterpolator(new LinearInterpolator());
		cycleFrowardAnimator.start();
   }
   private void backTimeAnim(){
		if (cycleBackAnimator == null) {
			cycleBackAnimator = ValueAnimator.ofFloat(posY,0);
			cycleBackAnimator.setDuration(3000);
			cycleBackAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
						@Override
						public void onAnimationUpdate(ValueAnimator animation) {
							float animationValue = (Float)animation.getAnimatedValue();
						    moveX = animationValue;
						    moveY = (float) ( posY - Math.sqrt((posY/2)*(posY/2)-(moveX-posY/2)*(moveX-posY/2)));
							invalidate();
						}
					});
			cycleBackAnimator.addListener(new AnimatorListenerAdapter() {
				@Override
				public void onAnimationEnd(Animator animation) {
					super.onAnimationEnd(animation);
					forwardAnim();
				}
			});
		}
		cycleBackAnimator.setInterpolator(new LinearInterpolator());
		cycleBackAnimator.start();
   }
   private void showTime(){
   	if (delayAnimator == null) {
   		delayAnimator = ValueAnimator.ofFloat(0,10);
   		delayAnimator.setDuration(12000);
   		delayAnimator.addListener(new AnimatorListenerAdapter() {
				@Override
				public void onAnimationEnd(Animator animation) {
					super.onAnimationEnd(animation);
					release();
				}
			});
		}
   	delayAnimator.start();
   }
   /**
    * 释放资源
    * @author AwenZeng  
    */
   public void release(){
	   setVisibility(View.GONE);
	   if(mIcon!=null){
		   mIcon.recycle();
		   mIcon = null;
	   }
   }
}
