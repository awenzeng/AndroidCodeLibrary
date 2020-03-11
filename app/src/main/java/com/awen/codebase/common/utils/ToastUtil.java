package com.awen.codebase.common.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class ToastUtil {
	private static Toast mToast;
	public static int SDK_VERSION = android.os.Build.VERSION.SDK_INT;
        /**
         * 
         * @param context  环境
         * @param str      提示内容(String)
         * @param isTime   true:Toast.LENGTH_LONG false:Toast.LENGTH_SHORT
         */
	public static void showToast(Context context, String str, boolean isTime){
		if(mToast == null){
			mToast = Toast.makeText(context, str, isTime ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
		}else{
		    if(SDK_VERSION < 14)
			mToast.cancel();
        	    mToast.setDuration(isTime ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
        	    mToast.setText(str);
		}
		mToast.show();
	}
	 /**
         * 
         * @param context  环境
         * @param id      提示内容(res资源)
         * @param isTime   true:Toast.LENGTH_LONG false:Toast.LENGTH_SHORT
         */
	public static void showToast(Context context, int resId, boolean isTime){
		if(mToast == null){
			mToast = Toast.makeText(context, resId, isTime ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
		}else{
		    if(SDK_VERSION < 14)
			mToast.cancel();
        	    mToast.setDuration(isTime ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
        	    mToast.setText(resId);
		}
		mToast.show();
	}
	 /**
         * 
         * @param context  环境
         * @param id      提示内容(res资源)
         *  @param id     提示图片(res资源)
         * @param isTime   true:Toast.LENGTH_LONG false:Toast.LENGTH_SHORT
         */
	public static void showToast(Context context, int resId01,int resId02, boolean isTime){
		if(mToast == null){
			mToast = Toast.makeText(context, resId01, isTime ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
			mToast.setGravity(Gravity.CENTER, 0, 0);
		        LinearLayout view = (LinearLayout) mToast.getView();
			ImageView imageView = new ImageView(context);
			imageView.setImageResource(resId02);
			view.addView(imageView, 0);
		}else{
		    if(SDK_VERSION < 14)
			mToast.cancel();
//		    mToast.setDuration(isTime ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
//		    mToast.setText(resId01);
//		    mToast.setGravity(Gravity.CENTER, 0, 0);
//        	    LinearLayout view = (LinearLayout) mToast.getView();
//        	    ImageView imageView = new ImageView(context);
//        	    imageView.setImageResource(resId02);
//        	    view.addView(imageView, 0);
		}
		mToast.show();
	}
	 /**
         * 
         * @param context  环境
         * @param string   提示内容
         *  @param id      提示图片(res资源)
         * @param isTime   true:Toast.LENGTH_LONG false:Toast.LENGTH_SHORT
         */
	public static void showToast(Context context, String resId01,int resId02, boolean isTime){
		if(mToast == null){
			mToast = Toast.makeText(context, resId01, isTime ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
			mToast.setGravity(Gravity.CENTER, 0, 0);
			LinearLayout view = (LinearLayout) mToast.getView();
			ImageView imageView = new ImageView(context);
			imageView.setImageResource(resId02);
			view.addView(imageView, 0);
			
		}else{
		    if(SDK_VERSION < 14)
			mToast.cancel();
//		    mToast.setDuration(isTime ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
//		    mToast.setGravity(Gravity.CENTER, 0, 0);
//		    mToast.setText(resId01);
//        	    LinearLayout view = (LinearLayout) mToast.getView();
//        	    ImageView imageView = new ImageView(context);
//        	    imageView.setImageResource(resId02);
//        	    view.addView(imageView, 0);
		}
		mToast.show();
	}
	 /**
         * 
         * @param context  环境
         *  @param view    布局文件
         * @param isTime   true:Toast.LENGTH_LONG false:Toast.LENGTH_SHORT
         */
	public static void showToast(Context context, View view, boolean isTime){
		if(mToast == null){
		    mToast = new Toast(context);
            	    mToast.setGravity(Gravity.CENTER, 0, 0);
            	    mToast.setDuration(isTime ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
            	    mToast.setView(view);
		}else{
		    if(SDK_VERSION < 14)
			mToast.cancel();
		    mToast.setGravity(Gravity.CENTER, 0, 0);
            	    mToast.setDuration(isTime ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
            	    mToast.setView(view);
		}
		mToast.show();
	}
}
