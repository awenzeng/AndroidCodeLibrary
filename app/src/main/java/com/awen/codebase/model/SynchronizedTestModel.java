package com.awen.codebase.model;

import com.awen.codebase.common.utils.LogUtil;

/**
 * @ClassName: SynchronizedTestModel
 * @Author: AwenZeng
 * @CreateDate: 2020/3/11 18:17
 * @Description:
 */
public class SynchronizedTestModel {
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
            // TODO Auto-generated method stub
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
