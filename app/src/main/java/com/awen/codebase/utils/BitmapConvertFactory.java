package com.awen.codebase.utils;

import java.io.ByteArrayOutputStream;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.awen.codebase.CodeBaseApp;

/**
 * 各种图片类型之间的转换
 * @author Awen
 *
 */
public class BitmapConvertFactory {

    /**
     * Drawable->Bitmap
     * Drawable转换为Bitmap
     * @param drawable
     * @return
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
		Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight(),
		drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888: Bitmap.Config.RGB_565);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight());
		drawable.draw(canvas);
	return bitmap;
    }
    /**
     * Bitmap->Drawable
     * Bitmap转换为Drawable
     * @param bitmap
     * @return
     */
    public static Drawable bitmapToDrawable(Bitmap bitmap) {
	
  	return new BitmapDrawable(bitmap);
  	
      }
    /**
     * resId->Bitmap
     * resId(drawable)转换为Bitmap
     * @param resID
     * @return
     */
    public static Bitmap resIdToBitmap(int resID) {

	Resources res = CodeBaseApp.getInstance().getResources();

	return BitmapFactory.decodeResource(res, resID);

    }

    /**
     * Bitmap->byte[]
     * Bitmap转换为byte[]
     * @param bm
     * @return
     */
    public static byte[] Bitmap2Bytes(Bitmap bm) {
	
	ByteArrayOutputStream baos = new ByteArrayOutputStream();
	bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
	return baos.toByteArray();
    }

    /**
     * byte[] → Bitmap
     * byte[]转换为Bitmap
     * @param b
     * @return
     */
    public static Bitmap Bytes2Bimap(byte[] b) {
	
	if (b.length == 0) {
	    return null;
	}
	return BitmapFactory.decodeByteArray(b, 0, b.length);  
    }
}
