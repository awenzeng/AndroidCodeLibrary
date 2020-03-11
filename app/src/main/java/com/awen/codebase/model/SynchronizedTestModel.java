package com.awen.codebase.model;

import com.awen.codebase.common.utils.LogUtil;

/**
 * @ClassName: SynchronizedTestModel
 * @Author: AwenZeng
 * @CreateDate: 2020/3/11 18:17
 * @Description:
 */
public class SynchronizedTestModel {
    private boolean ready = true;
    private int result = 0;
    private int number = 1;

    public void write() {
        ready = true;
        number = 2;
    }

    public void read() {
        if (ready) {
            result = number * 3;
        }
        LogUtil.androidLog("SynchronizedTest", "result is " + result);
    }

    public class TestThread extends Thread {
        private boolean flag;

        public TestThread(boolean flag) {
            this.flag = flag;
        }

        @Override
        public void run() {
            // TODO Auto-generated method stub
            while (true) {
                if (flag) {
                    write();
                } else {
                    read();
                }

                try {
                    Thread.sleep(1500);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}
