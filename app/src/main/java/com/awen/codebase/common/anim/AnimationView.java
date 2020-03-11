package com.awen.codebase.common.anim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.AppCompatImageView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;

import com.awen.codebase.R;

public class AnimationView extends AppCompatImageView {
    private TranslateAnimation translateAnimation;
    private RotateAnimation rotateAnimation;
	private ValueAnimator delayAnimator;
	private DisplayMetrics dm;
	private Bitmap mIcon;
	private Context mContext;
	public AnimationView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	    //AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
		this.mContext = context;
		dm = mContext.getResources().getDisplayMetrics();
		setImageResource(R.drawable.ball);
		mIcon = ((BitmapDrawable)getResources().getDrawable(R.drawable.ball)).getBitmap();
	    TranslateAnimation translateAnimation = new TranslateAnimation(0, 0, dm.widthPixels, dm.heightPixels);
	    translateAnimation.setRepeatCount(3);
	    RotateAnimation rotateAnimation = new RotateAnimation(0, 360,mIcon.getWidth()/2, mIcon.getHeight()/2);
	    rotateAnimation.setDuration(1000);
	    rotateAnimation.setRepeatCount(100);
	    AnimationSet animationSet = new AnimationSet(false);
	    animationSet.addAnimation(translateAnimation);
	    animationSet.addAnimation(rotateAnimation);
	    setAnimation(rotateAnimation);
	    rotateAnimation.startNow();
	    showTime();
	}
	   private void showTime(){
		   	if (delayAnimator == null) {
		   		delayAnimator = ValueAnimator.ofFloat(0,10);
		   		delayAnimator.setDuration(12000);
		   		delayAnimator.addListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							super.onAnimationEnd(animation);
							setVisibility(View.GONE);
						}
					});
				}
		   	delayAnimator.start();
		   }
    		
}
