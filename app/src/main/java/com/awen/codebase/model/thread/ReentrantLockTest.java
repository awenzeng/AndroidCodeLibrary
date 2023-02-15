package com.awen.codebase.model.thread;

import java.util.concurrent.locks.ReentrantLock;

import java.util.concurrent.TimeUnit;


public class ReentrantLockTest {
    public static ReentrantLock reenT = new ReentrantLock();//参数默认false，不公平锁
    private ReentrantLock lock = new ReentrantLock(true); //公平锁  速度慢与不公平锁

    /**
     * 使用场景:(1)比如一个定时任务,第一次定时任务未完成,重复发起了第二次,直接返回flase;
     * (2)用在界面交互时点击执行较长时间请求操作时，防止多次点击导致后台重复执行
     */

    public static void tryLockTest() {
        if (reenT.tryLock()) {
            //如果已经被lock，则立即返回false不会等待，
            //达到忽略操作的效果 ,当执行1000线程时，有些未获得对象锁的线程，会自动跳过
            try {
                //操作
                System.out.println("aaaa" + Thread.currentThread().getName());
            } finally {
                reenT.unlock();
            }


        }
    }

    /**
     * 使用场景:(1)同步操作 类似于synchronized  如果被其它资源锁定，会在此等待锁释放，达到暂停的效果
     * ReentrantLock存在公平锁与非公平锁  而且synchronized都是公平的
     */
    public static void lockTest() {
        try {
            reenT.lock(); //如果被其它资源锁定，会在此等待锁释放，达到暂停的效果
            //操作
            System.out.println("aaaa" + Thread.currentThread().getName());


        } finally {
            reenT.unlock();
        }
    }


    /**
     * 使用场景:(1)如果发现该操作正在执行,等待一段时间，如果规定时间未得到锁,放弃。防止资源处理不当，线程队列溢出,出现死锁
     */
    public static void trylockTimeTest() {
        try {
            if (reenT.tryLock(5, TimeUnit.SECONDS)) {  //如果已经被lock，尝试等待5s，看是否可以获得锁，如果5s后仍然无法获得锁则返回false继续执行
                try {
                    //操作
                    System.out.println("aaaa" + Thread.currentThread().getName());
                    //等待10S则只有一个可以访问
                    Thread.sleep(10000);
                } finally {
                    reenT.unlock();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace(); //当前线程被中断时(interrupt)，会抛InterruptedException
        }


    }

    /**
     * 使用场景:(1)中断正在进行的操作立刻释放锁继续下一操作.比如 取消正在同步运行的操作，来防止不正常操作长时间占用造成的阻塞
     */
    public static void lockInterruptTest() {
        try {
            reenT.lockInterruptibly();
            //操作
            System.out.println("aaaa" + Thread.currentThread().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            reenT.unlock();
        }
    }

    public void startTest(){
        //同时启动1000个线程，去进行i++计算，看看实际结果
        for (int i = 0; i < 10; i++) {
            Thread a =   new Thread(new Runnable() {
                @Override
                public void run() {
                    ReentrantLockTest.lockInterruptTest();
                }
            });
            a.start();
            //a.interrupt();

        }
    }
}

