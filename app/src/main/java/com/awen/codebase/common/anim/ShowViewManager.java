package com.awen.codebase.common.anim;

import android.content.Context;
import android.graphics.PixelFormat;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

public class ShowViewManager {
    private Context mContext;
	private float mDensity;
	private WindowManager mWindowManager;
	private WindowManager.LayoutParams mParams;
	private View lastView;
	public static final int HEIGHT = 70;
	public static final int MAGRIN_TOP = 72;
	private static final int MARGIN_HORIZONTAL = 10;
	public ShowViewManager(Context context) {
		super();
		this.mContext = context;
		mDensity = mContext.getResources().getDisplayMetrics().density;
		mWindowManager = (WindowManager) mContext
				.getSystemService(Context.WINDOW_SERVICE);
	}
   public void showView(View view){
		if (mParams == null) {
			mParams = new WindowManager.LayoutParams();
			mParams.x = 0;
			mParams.y = 0;
			mParams.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
			final DisplayMetrics dm = mContext.getResources()
					.getDisplayMetrics();
			final int width = dm.widthPixels;
			final int height = dm.heightPixels;
			mParams.width = width;
			mParams.height = height;
			mParams.type = WindowManager.LayoutParams.TYPE_PHONE;
			mParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
					| WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
			mParams.format = PixelFormat.RGBA_8888;
			view.setFocusable(false);
		}
		if(lastView!=null){
			mWindowManager.removeView(lastView);
		}
		lastView = view;
		mWindowManager.addView(view, mParams);
   }

   public void removeView(){
	   if(lastView!=null){
		   mWindowManager.removeView(lastView);
	   }
   }
}
