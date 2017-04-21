package com.awen.codebase.data;

import java.io.File;
import java.lang.ref.WeakReference;

import android.os.AsyncTask;

import com.awen.codebase.utils.FileUtil;


public class CacheClearTask extends AsyncTask<Void, Void, Boolean> {
	private String path;
	private WeakReference<ClearCacheListener> listenerRef;
	
	
	public CacheClearTask(String path) {
	    super();
	    // TODO Auto-generated constructor stub
	    this.path = path;
	}

	public void setClearCacheListener(ClearCacheListener listener) {
		listenerRef = new WeakReference<CacheClearTask.ClearCacheListener>(listener);
	}

	@Override
	protected Boolean doInBackground(Void... params) {
		
		File file = new File(path);
		if(!file.exists())
			return true;
		
		if(listenerRef == null) {
			long size = FileUtil.getDirSize(file);
			if(size < 300 * 1024 * 1024)
				return true;
		}
		
		return FileUtil.delDir(file);

	}
	
	/* (non-Javadoc)
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */
	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
		
		if(listenerRef == null)
			return;
		
		ClearCacheListener listener = listenerRef.get();
		if(listener == null)
			return;
		
		listener.onClearCacheCompleted(result);
	}

	public interface ClearCacheListener {
		void onClearCacheCompleted(boolean isSucceed);
	}

}
