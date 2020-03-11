package com.awen.codebase.common.anim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.OvershootInterpolator;

import com.awen.codebase.R;


public class CoinsView extends View {
	private Context mContext;
	private Bitmap mIcon;
	private float mDensity;
	private float moveX = 0;
	private float moveY = 0;
	private float move1Y = 0;
	private float move2Y = 0;
	private float move3Y = 0;
	private float move4Y = 0;
	private float move5Y = 0;
	private Paint mPaint;
	private DisplayMetrics dm;
	private Resources res;
	private ValueAnimator ovalAnimator;
	private ValueAnimator ovalAnimator1;
	private ValueAnimator ovalAnimator2;
	private ValueAnimator ovalAnimator3;
	private ValueAnimator ovalAnimator4;
	private ValueAnimator ovalAnimator5;
    private final int INDEX = 13;
	public CoinsView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.mContext = context;
		mPaint = new Paint();
		dm = mContext.getResources().getDisplayMetrics();
		res = getResources();
		mDensity = dm.density;
		mIcon = BitmapFactory.decodeResource(getResources(), R.drawable.chips);
		startAnim();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		//for(int i =0 ;i<INDEX;i++){
		  canvas.drawBitmap(mIcon,5*mDensity*30 , moveY, mPaint);

		  canvas.drawBitmap(mIcon,6*mDensity*30 , move1Y, mPaint);
		  canvas.drawBitmap(mIcon,3*mDensity*30 , move2Y, mPaint);
		  canvas.drawBitmap(mIcon,8*mDensity*30 , move3Y, mPaint);
		 
	//	}
		
	}
	private void startAnim(){
		if (ovalAnimator == null) {
			ovalAnimator = ValueAnimator.ofFloat(0,500*mDensity);
			ovalAnimator.setDuration(3000);
			ovalAnimator.setRepeatCount(3);
			//ovalAnimator.setRepeatMode(ValueAnimator.REVERSE);
			ovalAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
						@Override
						public void onAnimationUpdate(ValueAnimator animation) {
							float animationValue = (Float)animation.getAnimatedValue();
						    moveY = animationValue;
							invalidate();
							setVisibility(View.VISIBLE);
						}
					});
			ovalAnimator.addListener(new AnimatorListenerAdapter() {
				@Override
				public void onAnimationEnd(Animator animation) {
					super.onAnimationEnd(animation);
					release();
				}
			});
		}
		ovalAnimator.setInterpolator(new OvershootInterpolator());
		ovalAnimator.start();
		if (ovalAnimator1 == null) {
			ovalAnimator1 = ValueAnimator.ofFloat(0,500*mDensity);
			ovalAnimator1.setDuration(3000);
			ovalAnimator1.setRepeatCount(3);
		//	ovalAnimator1.setRepeatMode(ValueAnimator.REVERSE);
			ovalAnimator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
						@Override
						public void onAnimationUpdate(ValueAnimator animation) {
							float animationValue = (Float)animation.getAnimatedValue();
							move1Y = animationValue;
							invalidate();
							setVisibility(View.VISIBLE);
						}
					});
			ovalAnimator1.addListener(new AnimatorListenerAdapter() {
				@Override
				public void onAnimationEnd(Animator animation) {
					super.onAnimationEnd(animation);
					release();
				}
			});
		}
		ovalAnimator1.setInterpolator(new BounceInterpolator());
		ovalAnimator1.start();
		if (ovalAnimator2 == null) {
			ovalAnimator2 = ValueAnimator.ofFloat(0,500*mDensity);
			ovalAnimator2.setDuration(3000);
			ovalAnimator2.setRepeatCount(3);
			ovalAnimator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
						@Override
						public void onAnimationUpdate(ValueAnimator animation) {
							float animationValue = (Float)animation.getAnimatedValue();
							move2Y = animationValue;
							invalidate();
						}
					});
			ovalAnimator2.addListener(new AnimatorListenerAdapter() {
				@Override
				public void onAnimationEnd(Animator animation) {
					super.onAnimationEnd(animation);
					release();
				}
			});
		}
		ovalAnimator2.setInterpolator(new AnticipateOvershootInterpolator());
		ovalAnimator2.start();
		if (ovalAnimator3 == null) {
			ovalAnimator3 = ValueAnimator.ofFloat(0,500*mDensity);
			ovalAnimator3.setDuration(3000);
			ovalAnimator3.setRepeatCount(3);
		//	ovalAnimator1.setRepeatMode(ValueAnimator.REVERSE);
			ovalAnimator3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
						@Override
						public void onAnimationUpdate(ValueAnimator animation) {
							float animationValue = (Float)animation.getAnimatedValue();
							move3Y = animationValue;
							invalidate();
						}
					});
			ovalAnimator3.addListener(new AnimatorListenerAdapter() {
				@Override
				public void onAnimationEnd(Animator animation) {
					super.onAnimationEnd(animation);
					release();
				}
			});
		}
		ovalAnimator3.setInterpolator(new AccelerateDecelerateInterpolator());
		ovalAnimator3.start();
		
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
