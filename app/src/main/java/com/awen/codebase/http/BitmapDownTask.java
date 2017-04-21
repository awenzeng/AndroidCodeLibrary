package com.awen.codebase.http;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

public class BitmapDownTask extends AsyncTask<String, Integer, Bitmap> {
	
	private WeakReference<BitmapDownListener> listenerRef;
	
	public BitmapDownTask(BitmapDownListener listener) {
		listenerRef = new WeakReference<BitmapDownTask.BitmapDownListener>(listener);
	}

	@Override
	protected Bitmap doInBackground(String... params) {
		String imageUrl = params[0];

		InputStream inStream = null;
		try {
			URL url = new URL(imageUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				inStream = conn.getInputStream();
				Bitmap bm = BitmapFactory.decodeStream(inStream);
				return bm;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(inStream != null)
					inStream.close();
				inStream = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */
	@Override
	protected void onPostExecute(Bitmap result) {
		BitmapDownListener listener = listenerRef.get();
		if(listener != null)
			listener.onBitmapDownCompleted(result);
	}

	public interface BitmapDownListener {
		public void onBitmapDownCompleted(Bitmap bm);
	}

}
