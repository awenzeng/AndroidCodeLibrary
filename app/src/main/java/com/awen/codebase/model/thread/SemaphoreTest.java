package com.awen.codebase.model.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 信号量--多线程操作（递增）
 */
public class SemaphoreTest {
    private static int num = 3;
    private static int initNum = 0;
    private static Semaphore semaphore = new Semaphore(initNum);
    private static ExecutorService executorService = Executors.newFixedThreadPool(num);


    public SemaphoreTest() {

        executorService.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("A在上厕所");
                try {
                    Thread.sleep(4000);
                    semaphore.release();
                    System.out.println("A上完了");
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {

                }
            }
        });
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("B在上厕所");
                try {
                    Thread.sleep(2000);
                    semaphore.release();
                    System.out.println("B上完了");
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {

                }
            }
        });
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("C在上厕所");
                try {
                    Thread.sleep(3000);
                    semaphore.release();
                    System.out.println("C上完了");
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {

                }
            }
        });
        System.out.println("等待所有人从厕所回来开会...");
        try{
            semaphore.acquire(num);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        System.out.println("所有人都好了，开始开会...");
        executorService.shutdown();
    }
}
