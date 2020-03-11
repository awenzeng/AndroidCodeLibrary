package com.awen.codebase.common.anim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.view.View;

import com.awen.codebase.R;

public class MergeView extends View {
    private Bitmap bmp1;
    private Bitmap bmp2;
    private Bitmap resultBmp;
	private Context mContext;
	private float mDensity;
	private float moveX = 0;
	private float moveY = 0;
	private Paint mPaint;
	private DisplayMetrics dm;
	private Resources res;
	private ValueAnimator delayAnimator;
	public MergeView(Context context) {
		super(context);
		this.mContext = context;
		mPaint = new Paint();
		dm = mContext.getResources().getDisplayMetrics();
		res = getResources();
		mDensity = dm.density;
		bmp1 = BitmapFactory.decodeResource(res,
				R.drawable.one);
		bmp2 = BitmapFactory.decodeResource(res,
				R.drawable.two);
		mergeBitmap();
		showTime();
	}
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawBitmap(resultBmp, 0, 200 * mDensity, mPaint);
	}
	 /*
     * 使用Canvas合并Bitmap
     */
    private void mergeBitmap() {
        resultBmp = Bitmap.createBitmap(dm.widthPixels,dm.heightPixels/2-100, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(resultBmp);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(bmp1, 0, 0, mPaint);
        canvas.drawBitmap(bmp2, bmp1.getWidth(),0, mPaint);
    }
    private void showTime(){
    	if (delayAnimator == null) {
    		delayAnimator = ValueAnimator.ofFloat(0,10);
    		delayAnimator.setDuration(1500);
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
		   if(bmp1!=null){
			   bmp1.recycle();
			   bmp1 = null;
		   }
		   if(bmp2!=null){
			   bmp2.recycle();
			   bmp2 = null;
		   }
		   
		   if(resultBmp!=null){
			   resultBmp.recycle();
			   resultBmp = null;
		   }
	   }
}
