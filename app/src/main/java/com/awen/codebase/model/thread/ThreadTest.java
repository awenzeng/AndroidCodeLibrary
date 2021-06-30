package com.awen.codebase.model.thread;

import android.util.Log;

import java.util.concurrent.CountDownLatch;

/**
 * @ClassName: ThreadTest
 * @Author: AwenZeng
 * @CreateDate: 2021/5/10 11:00
 * @Description:
 */
public class ThreadTest {

    private StringBuilder stringBuilder;
    public void printlfTwoArray(int[] a,int[] b){
       stringBuilder = new StringBuilder();
        Log.i("ThreadTest",stringBuilder.toString());
    }

    class PrintThread extends Thread{
        private int[] array;
        byte[] lock = new byte[0];

        public PrintThread(int[] _array) {
            array  = _array;
        }

        @Override
        public void run() {
            super.run();
            synchronized (lock){

            }
        }
    }
}
