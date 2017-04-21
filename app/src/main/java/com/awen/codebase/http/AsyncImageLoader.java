package com.awen.codebase.http;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.awen.codebase.utils.ThreadPools;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;

public class  AsyncImageLoader {
	// 为了加快速度，在内存中开启缓存（主要应用于重复图片较多时，或者同一个图片要多次被访问，比如在ListView时来回滚动）
	public Map<String, SoftReference<Drawable>> imageCache = new HashMap<String, SoftReference<Drawable>>();

//	private ExecutorService executorService = Executors.newFixedThreadPool(5);
	private final Handler handler = new Handler();

	/**
	 * 
	 * @param imageUrl
	 *            图像url地址
	 * @param imageCallback
	 *            回调接口
	 * @return 返回内存中缓存的图像，第一次加载返回null
	 */
	public Drawable loadDrawable(final Context context, final String imageUrl,
			final ImageCallback1 imageCallback) {
		// 如果缓存过就从缓存中取出数据
		if (imageCache.containsKey(imageUrl)) {
			SoftReference<Drawable> softReference = imageCache.get(imageUrl);
			if (softReference.get() != null) {
				System.out.println("using softReference~!~");
				return softReference.get();
			}
		}
		if (HttpUtils.is_Intent(context)) {
			ThreadPools.getInstance().execute(new Runnable() {
				public void run() {
					try {
						final Drawable drawable = HttpUtils.loadImageFromUrl(imageUrl);
						if (drawable != null) {
							imageCache.put(imageUrl,new SoftReference<Drawable>(drawable));
							handler.post(new Runnable() {
								public void run() {
									imageCallback.imageLoaded(drawable,imageUrl);
								}
							});
						}
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				}
			});
//			executorService.submit(new Runnable() {
//				public void run() {
//					try {
//						final Drawable drawable = HttpUtils.loadImageFromUrl(imageUrl);
//						if (drawable != null) {
//							imageCache.put(imageUrl,new SoftReference<Drawable>(drawable));
//							handler.post(new Runnable() {
//								public void run() {
//									imageCallback.imageLoaded(drawable,imageUrl);
//								}
//							});
//						}
//					} catch (Exception e) {
//						throw new RuntimeException(e);
//					}
//				}
//			});
		}
		return null;

	}

	// 对外界开放的回调接口
	public interface ImageCallback1 {
		// 注意 此方法是用来设置目标对象的图像资源
		public void imageLoaded(Drawable imageDrawable, String imageUrl);
	}
}
