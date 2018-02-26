package com.awen.codebase.anim;

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

import com.awen.codebase.R;

public class MoreFrameView extends View {
	private Context mContext;
	private Bitmap iconList[];
	private Bitmap mIcon;
	private float mDensity;
	private int[] resList;
	private Paint mPaint;
	private DisplayMetrics dm;
	private Resources res;
	private ValueAnimator lineAnimator;
	private int duration = 1500;

	public MoreFrameView(Context context, int[] resList) {
		super(context);
		// TODO Auto-generated constructor stub
		this.mContext = context;
		mPaint = new Paint();
		dm = mContext.getResources().getDisplayMetrics();
		res = getResources();
		mDensity = dm.density;
		
		if(resList == null){
			resList = new int[]{
					R.drawable.btn_selected1,R.drawable.btn_selected2,R.drawable.btn_selected3,R.drawable.btn_selected4,
					R.drawable.btn_selected5,R.drawable.btn_selected6,R.drawable.btn_selected7,R.drawable.btn_selected8,
					R.drawable.btn_selected9,R.drawable.btn_selected10,R.drawable.btn_selected11,R.drawable.btn_selected12
			};
		}
		this.resList = resList;
		iconList = new Bitmap[resList.length];
		for(int i=0;i< resList.length;i++){
			iconList[i] = BitmapFactory.decodeResource(res,resList[i]);
		}
		showView();
	}
    
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawBitmap(mIcon, 10 * mDensity, 300 * mDensity, mPaint);
	}
	

	private void showView() {
		if (lineAnimator == null) {
			lineAnimator = ValueAnimator.ofInt(0, resList.length-1);
			lineAnimator.setDuration(duration);
			lineAnimator.setRepeatCount(3);
			lineAnimator
					.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
						@Override
						public void onAnimationUpdate(ValueAnimator animation) {
							int animationValue = (Integer) animation
									.getAnimatedValue();
							mIcon = iconList[animationValue];
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
		lineAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
		lineAnimator.start();
	}
	public void setDuration(int time){
		this.duration = time;
	}
	
	
    public void setResArra(int[] resList){
    	this.resList = resList;
		iconList = new Bitmap[resList.length];
		for(int i=1;i<=resList.length;i++){
			iconList[i] = BitmapFactory.decodeResource(res,resList[i]);
		}
    }
	/**
	 * 释放资源
	 * 
	 * @author AwenZeng
	 */
	public void release() {
		setVisibility(View.GONE);
		if (mIcon != null) {
			mIcon.recycle();
			mIcon = null;
		}
		if (iconList != null && iconList.length != 0) {
			for (int i = 1; i < iconList.length; i++) {
				iconList[i].recycle();
				iconList[i] = null;
			}
		}
	}
}
