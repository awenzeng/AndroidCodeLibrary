package com.awen.codebase.utils;

import java.util.concurrent.ExecutorService;

import java.util.concurrent.Executors;

public class ThreadPools {

	private ExecutorService ThreadExecutorService = Executors.newFixedThreadPool(5);
	
	private static ThreadPools instance = null;
	
	public static ThreadPools getInstance() {
		if (null == instance|| (null != instance && instance.ThreadExecutorService.isShutdown())) {
			instance = new ThreadPools();
		}
		return instance;
	}
	
	public static void destory() {
		if (null != instance) {
			if (!instance.ThreadExecutorService.isShutdown()) {
				instance.ThreadExecutorService.shutdown();
			}
		}
		instance = null;
	}
	
	public void execute(Runnable runnable) {
		if (null != runnable) {
			ThreadExecutorService.execute(runnable);
		}
	}
}
