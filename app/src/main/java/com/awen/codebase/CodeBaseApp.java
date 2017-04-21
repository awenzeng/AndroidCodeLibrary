package com.awen.codebase;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.view.Display;

public class CodeBaseApp extends Application {
    private static CodeBaseApp instance;
    private Typeface mGobalFont = null;
    public static boolean bUseFont = true;
    public static int SDK_VERSION = android.os.Build.VERSION.SDK_INT;
    @Override
    public void onCreate() {
	super.onCreate();
	instance = this;
	bUseFont = false;
    }

    public static CodeBaseApp getInstance() {
	return instance;
    }
    /**
     * 修改字体
     * @return
     */
    public Typeface getDefaultFont() {
	if (!CodeBaseApp.bUseFont) {
	    return null;
	} else if (CodeBaseApp.getInstance().mGobalFont == null) {
	    CodeBaseApp.getInstance().mGobalFont = Typeface.createFromAsset(CodeBaseApp.getInstance().getAssets(), "font/umfont.ttf");
	}
	return CodeBaseApp.getInstance().mGobalFont;
    }
    /**
     * 获取手机尺寸
     * @param context
     * @return
     */
    public int[] getPhoneSize(Context context){
    	int[] i = null;
    	Display display = ((Activity)context).getWindowManager().getDefaultDisplay();  
    	i[0] = display.getWidth();  
    	i[1] = display.getHeight(); 
    	return i;
    }
    
    /**
     * 获取手机分辨率
     * @return
     */
    public int[] getPhonePixels(){
    	int[] i = null;
    	DisplayMetrics dm = new DisplayMetrics();   
    	dm = getResources().getDisplayMetrics();   
    	float density = dm.density;           //密度
    	i[0] = dm.widthPixels;   
    	i[1] = dm.heightPixels;   
    	float xdpi = dm.xdpi;   
    	float ydpi = dm.ydpi;  
    	return i;
    }
}
