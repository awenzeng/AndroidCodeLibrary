package com.awen.codebase.common.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;

public class BitmapUtil {

    /**
     * 从sd卡读图片
     *
     * @param path
     * @return
     */
    public static Bitmap getBitmap(String path, int width, int height) {
        File file = new File(path);
        if (!file.exists())
            return null;
        BitmapFactory.Options op = new BitmapFactory.Options();
        op.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, op);

        int h = op.outHeight;
        int w = op.outWidth;
        if (w > h) {
            if (w > width || h > height) {
                float scaleWidth = w / ((float) width);
                float scaleHeight = h / ((float) height);
                op.inSampleSize = Math.round(Math.min(scaleWidth, scaleHeight));
            }
        } else {
            if (w > width || h > height) {
                float scaleWidth = w / ((float) height);
                float scaleHeight = h / ((float) width);
                op.inSampleSize = Math.round(Math.min(scaleWidth, scaleHeight));
            }
        }
        op.inJustDecodeBounds = false;

        Bitmap bm = BitmapFactory.decodeFile(path, op);
        /*
         * if(bm == null) return null;
         * System.out.println(String.format("path:%s", path));
         * System.out.println
         * (String.format("outH:%d, outW:%d, sclH:%d, sclW:%d, sam:%d", h, w,
         * bm.getHeight(), bm.getWidth(), op.inSampleSize));
         */

        return bm;

    }

    public static Bitmap getBitmap(String path) {
        if (path == null || path.length() == 0)
            return null;

        File file = new File(path);
        if (!file.exists())
            return null;
        BitmapFactory.Options op = new BitmapFactory.Options();
        op.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, op);
        int h = op.outHeight;
        int w = op.outWidth;
        int dstWidth = 320;
        int dstHeight = 240;
        if (w > h) {
            if (w > dstWidth || h > dstHeight) {
                float scaleWidth = w / ((float) dstWidth);
                float scaleHeight = h / ((float) dstHeight);
                op.inSampleSize = Math.round(Math.min(scaleWidth, scaleHeight));
            }
        } else {
            if (w > dstWidth || h > dstHeight) {
                float scaleWidth = w / ((float) dstHeight);
                float scaleHeight = h / ((float) dstWidth);
                op.inSampleSize = Math.round(Math.min(scaleWidth, scaleHeight));
            }
        }
        op.inJustDecodeBounds = false;

        Bitmap bm = BitmapFactory.decodeFile(path, op);
		/*if(bm == null)
			return null;
		System.out.println(String.format("path:%s", path));
		System.out.println(String.format("outH:%d, outW:%d, sclH:%d, sclW:%d, sam:%d", h, w, bm.getHeight(), bm.getWidth(), op.inSampleSize));*/

        return bm;

    }

    public static int[] getBitmapBounds(String path) {
        try {
            BitmapFactory.Options op = new BitmapFactory.Options();
            op.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, op);

            int h = op.outHeight;
            int w = op.outWidth;

            return new int[]{w, h};
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 保存图片到SD卡
     *
     * @param bitmap
     * @param path
     * @return
     */
    public static String saveBitmap(Bitmap bitmap, String path) {
        if (path == null || bitmap == null)
            return null;
        try {
            BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(path));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
            return path;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 从SD卡读图片
     *
     * @param path
     * @param w
     * @param h
     * @return
     */
    public static Bitmap convertToBitmap(String path, int w, int h) {

        BitmapFactory.Options opts = new BitmapFactory.Options();

        // 设置为ture只获取图片大小
        opts.inJustDecodeBounds = true;
        opts.inPreferredConfig = Bitmap.Config.ARGB_8888;

        // 返回为空
        BitmapFactory.decodeFile(path, opts);
        int width = opts.outWidth;
        int height = opts.outHeight;
        float scaleWidth = 0.f, scaleHeight = 0.f;
        if (width > w || height > h) {
            // 缩放
            scaleWidth = ((float) width) / w;
            scaleHeight = ((float) height) / h;
        }
        opts.inJustDecodeBounds = false;
        float scale = Math.max(scaleWidth, scaleHeight);
        opts.inSampleSize = (int) scale;
        WeakReference<Bitmap> weak = new WeakReference<Bitmap>(BitmapFactory.decodeFile(path, opts));
        return Bitmap.createScaledBitmap(weak.get(), w, h, true);
    }

    /**
     * 从sd卡取规定大小的图片
     *
     * @param path
     * @param dstWidth
     * @param dstHeight
     * @return
     */
    public static Bitmap createScaledBitmap(String path, int dstWidth,
                                            int dstHeight) {
        if (path == null)
            return null;
        Bitmap bitmap = getBitmap(path, dstWidth, dstHeight);
        if (bitmap == null)
            return null;

        return createScaledBitmap(bitmap, dstWidth, dstHeight);

    }

    /**
     * 改变图片大小的尺寸
     *
     * @param bitmap
     * @param dstWidth
     * @param dstHeight
     * @return
     */
    public static Bitmap createScaledBitmap(Bitmap bitmap, int dstWidth,
                                            int dstHeight) {

        if (bitmap == null)
            return null;
        int h = bitmap.getHeight();
        int w = bitmap.getWidth();

        if (w > h) {
            if (w > dstWidth || h > dstHeight) {
                float scaleWidth = ((float) dstWidth) / w;
                float scaleHeight = ((float) dstHeight) / h;
                float scale = Math.min(scaleWidth, scaleHeight);
                Matrix matrix = new Matrix();
                matrix.postScale(scale, scale);
                Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, w, h,
                        matrix, true);
                bitmap.recycle();
                bitmap = null;
                return newBitmap;
            } else {
                return bitmap;
            }
        } else {
            if (w > dstWidth || h > dstHeight) {
                float scaleWidth = ((float) dstHeight) / w;
                float scaleHeight = ((float) dstWidth) / h;
                float scale = Math.min(scaleWidth, scaleHeight);
                Matrix matrix = new Matrix();
                matrix.postScale(scale, scale);
                Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
                bitmap.recycle();
                bitmap = null;
                return newBitmap;
            } else {
                return bitmap;
            }
        }

    }

    /**
     * 旋转图片
     *
     * @param b
     * @param degrees
     * @return
     */
    public static Bitmap rotate(Bitmap b, int degrees) {
        if (degrees != 0 && b != null) {
            Matrix m = new Matrix();
            m.setRotate(degrees, (float) b.getWidth() / 2, (float) b.getHeight() / 2);
            try {
                Bitmap b2 = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), m, true);
                if (b != b2) {
                    b.recycle();
                    b = b2;
                }
            } catch (OutOfMemoryError ex) {
                // We have no memory to rotate. Return the original bitmap.
            }
        }
        return b;
    }

    /**
     * 从网络上下载图片
     *
     * @param url
     * @return
     */
    public static Bitmap getBitmapFromUrl(String url) {
        InputStream is = null;
        try {
            URL u = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) u.openConnection();
            conn.setRequestMethod("GET");
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                is = conn.getInputStream();
                return BitmapFactory.decodeStream(is);
            } else
                conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (is != null)
                    is.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } finally {
        }
        return null;

    }

    /**
     * 从SD卡
     * 解决加载图片过大outofmemory
     *
     * @param filePath
     * @return
     */
    public static Bitmap createImageThumbnail(String filePath) {
        Bitmap bitmap = null;
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, opts);

        opts.inSampleSize = computeSampleSize(opts, -1, 128 * 128);
        opts.inJustDecodeBounds = false;

        try {
            bitmap = BitmapFactory.decodeFile(filePath, opts);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return bitmap;
    }

    public static int computeSampleSize(BitmapFactory.Options options,
                                        int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength, maxNumOfPixels);
        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }
        return roundedSize;
    }

    private static int computeInitialSampleSize(BitmapFactory.Options options,
                                                int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;
        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(Math.floor(w / minSideLength), Math.floor(h / minSideLength));
        if (upperBound < lowerBound) {
            // return the larger one when there is no overlapping zone.
            return lowerBound;
        }
        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }

    /**
     * 给图片加圆角
     *
     * @param bitmap
     * @param angle
     * @return
     */
    public static Bitmap toRoundCorner(Bitmap bitmap, int angle) {

        if (bitmap == null)
            return null;
        // Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
        // bitmap.getHeight(), Config.ARGB_8888);

        Bitmap output = Bitmap.createBitmap(70, 70, Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xFF424242;

        final Paint paint = new Paint();

        // final Rect rect = new Rect(0, 0, bitmap.getWidth(),
        // bitmap.getHeight());
        final Rect rect = new Rect(0, 0, 70, 70);

        final RectF rectF = new RectF(rect);

        final float roundPx = angle;

        paint.setAntiAlias(true);

        canvas.drawARGB(0, 0, 0, 0);

        paint.setColor(color);

        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));

        Bitmap bitmap2 = Bitmap.createScaledBitmap(bitmap, 70, 70, false);

        canvas.drawBitmap(bitmap2, rect, rect, paint);
        return output;
    }

    /**
     * 添加倒影，原理，先翻转图片，由上到下放大透明度
     *
     * @param originalImage
     * @return
     */
    public static Bitmap createReflectedImage(Bitmap originalImage) {
        // The gap we want between the reflection and the original image
        final int reflectionGap = 4;

        int width = originalImage.getWidth();
        int height = originalImage.getHeight();

        // This will not scale but will flip on the Y axis
        Matrix matrix = new Matrix();
        matrix.preScale(1, -1);

        // Create a Bitmap with the flip matrix applied to it.
        // We only want the bottom half of the image
        Bitmap reflectionImage = Bitmap.createBitmap(originalImage, 0,
                height / 2, width, height / 2, matrix, false);

        // Create a new bitmap with same width but taller to fit reflection
        Bitmap bitmapWithReflection = Bitmap.createBitmap(width,
                (height + height / 2), Config.ARGB_8888);

        // Create a new Canvas with the bitmap that's big enough for
        // the image plus gap plus reflection
        Canvas canvas = new Canvas(bitmapWithReflection);
        // Draw in the original image
        canvas.drawBitmap(originalImage, 0, 0, null);
        // Draw in the gap
        Paint defaultPaint = new Paint();
        canvas.drawRect(0, height, width, height + reflectionGap, defaultPaint);
        // Draw in the reflection
        canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);

        // Create a shader that is a linear gradient that covers the reflection
        Paint paint = new Paint();
        LinearGradient shader = new LinearGradient(0,
                originalImage.getHeight(), 0, bitmapWithReflection.getHeight()
                + reflectionGap, 0x70ffffff, 0x00ffffff, TileMode.CLAMP);
        // Set the paint to use this shader (linear gradient)
        paint.setShader(shader);
        // Set the Transfer mode to be porter duff and destination in
        paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
        // Draw a rectangle using the paint with our linear gradient
        canvas.drawRect(0, height, width, bitmapWithReflection.getHeight()
                + reflectionGap, paint);

        return bitmapWithReflection;
    }
}
