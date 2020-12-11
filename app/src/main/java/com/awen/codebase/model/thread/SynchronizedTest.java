package com.awen.codebase.model.thread;

import com.awen.codebase.common.utils.LogUtil;

/**
 * synchronize--多线程锁操作
 */
public class SynchronizedTest {
    private int number = 1;
    private byte[] a = new byte[0];
    public volatile boolean isClose = false;

    public void read() {
        synchronized (a){
            number++;
            LogUtil.androidLog("SynchronizedTest", "Thread name:" + Thread.currentThread().getName() + ",number is " + number);
        }
    }

    public class TestThread extends Thread {
        String name;
        int time = 1000;

        public TestThread(String name) {
            super(name);
            this.name = name;
        }

        @Override
        public void run() {
            while (!isClose) {
                read();
                try {
                    Thread.sleep(time);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
