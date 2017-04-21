package com.awen.codebase.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class Image3DView extends ImageView {
	// 是否为Reflection模式
	private boolean mReflectionMode = true;

	public Image3DView(Context context) {
		super(context);
	}

	public Image3DView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// 取得原始图片的bitmap并重画
		Bitmap originalImage = ((BitmapDrawable) this.getDrawable())
				.getBitmap();
		DoReflection(originalImage);
	}

	public Image3DView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		Bitmap originalImage = ((BitmapDrawable) this.getDrawable())
				.getBitmap();
		DoReflection(originalImage);
	}

	public void setReflectionMode(boolean isRef) {
		mReflectionMode = isRef;
	}

	public boolean getReflectionMode() {
		return mReflectionMode;
	}

	// 偷懒了,只重写了setImageResource,和构造函数里面干了同样的事情
	@Override
	public void setImageResource(int resId) {
		Bitmap originalImage = BitmapFactory.decodeResource(getResources(),
				resId);
		DoReflection(originalImage);
		// super.setImageResource(resId);
	}

	private void DoReflection(Bitmap originalImage) {
		final int reflectionGap = 4; // 原始图片和反射图片中间的间距
		int width = originalImage.getWidth();
		int height = originalImage.getHeight();

		// 反转
		Matrix matrix = new Matrix();
		matrix.preScale(1, -1);
		// reflectionImage就是下面透明的那部分,可以设置它的高度为原始的3/4,这样效果会更好些
		Bitmap reflectionImage = Bitmap.createBitmap(originalImage, 0, 0,
				width, (height/4)*3, matrix, false);
		// 创建一个新的bitmap,高度为原来的两倍
		Bitmap bitmapWithReflection = Bitmap.createBitmap(width,
				(height + height), Config.ARGB_8888);
		Canvas canvasRef = new Canvas(bitmapWithReflection);

		// 先画原始的图片
		canvasRef.drawBitmap(originalImage, 0, 0, null);
		// 画间距
		Paint deafaultPaint = new Paint();
		canvasRef.drawRect(0, height, width, height + reflectionGap,
				deafaultPaint);

		// 画被反转以后的图片
		canvasRef.drawBitmap(reflectionImage, 0, height + reflectionGap, null);
		// 创建一个渐变的蒙版放在下面被反转的图片上面
		Paint paint = new Paint();
		LinearGradient shader = new LinearGradient(0,
				originalImage.getHeight(), 0, bitmapWithReflection.getHeight()
						+ reflectionGap, 0x80ffffff, 0x00ffffff, TileMode.CLAMP);
		// Set the paint to use this shader (linear gradient)
		paint.setShader(shader);
		// Set the Transfer mode to be porter duff and destination in
		paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
		// Draw a rectangle using the paint with our linear gradient
		canvasRef.drawRect(0, height, width, bitmapWithReflection.getHeight()
				+ reflectionGap, paint);
		// 调用ImageView中的setImageBitmap
		this.setImageBitmap(bitmapWithReflection);
	}
}