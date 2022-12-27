package com.awen.codebase.model.thread;

import android.util.Log;

import java.util.PriorityQueue;
import java.util.concurrent.CountDownLatch;

/**
 * @ClassName: ThreadTest
 * @Author: AwenZeng
 * @CreateDate: 2021/5/10 11:00
 * @Description:
 */
public class ThreadTest {

    private static int queueSize = 10;
    private static PriorityQueue<Integer> queue =
            new PriorityQueue<Integer>(queueSize);

    public static void printfTwoArray() {

        int[] a = {1, 2, 3, 4,5};
        int[] b = {'a', 'b', 'c', 'd'};
        StringBuilder stringBuilder = new StringBuilder();
        Object lock = new Object();
        PrintThread thread1 = new PrintThread(a, lock, stringBuilder);
        thread1.start();

        PrintThread thread2 = new PrintThread(b, lock, stringBuilder);
        thread2.start();
    }

    public static void printfConsumerAndProduct(){

        Producer producer = new Producer();
        Consumer consumer = new Consumer();

        producer.start();
        consumer.start();
    }

    static class PrintThread extends Thread {
        private int[] array;
        StringBuilder stringBuilder;
        Object lock;

        public PrintThread(int[] _array, Object _lock, StringBuilder _stringBuilder) {
            array = _array;
            lock = _lock;
            stringBuilder = _stringBuilder;
        }

        @Override
        public void run() {
            super.run();
//                while(true){
            for (int i = 0; i < array.length; i++) {
                synchronized (lock) {
                    try {
                        lock.notify();
                        if (array[i] > 64) {
                            stringBuilder.append((char) array[i]);
                        } else {
                            stringBuilder.append(array[i]);
                        }
                        lock.wait(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
//                    }

                    if(i == array.length -1){
                        i = -1;
                    }

                    if(stringBuilder.length() >50){
                        break;
                    }
                }
            }
            Log.i("ThreadTest", stringBuilder.toString());
        }

    }

    static class Consumer extends Thread{
        @Override
        public void run() {
            consume();
        }

        private void consume() {
            while(true){
                synchronized (queue) {
                    while(queue.size() == 0){
                        try {
                            Log.i("ThreadTest", "队列空，等待数据");
                            queue.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            queue.notify();
                        }
                    }
                    queue.poll();          //每次移走队首元素
                    queue.notify();
                    Log.i("ThreadTest", "从队列取走一个元素，队列剩余"+
                            queue.size()+"个元素");
                }
            }
        }
    }

    static class Producer extends Thread{

        boolean isNeedCycyle = true;

        @Override
        public void run() {
            produce();
        }

        private void produce() {
            while(isNeedCycyle){
                synchronized (queue) {
                    while(queue.size() == queueSize){
                        try {
                            Log.i("ThreadTest", "队列满，等待有空余空间");
                            isNeedCycyle = false;
                            queue.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            queue.notify();
                        }
                    }
                    if(isNeedCycyle){
                        queue.offer(1);        //每次插入一个元素
                        queue.notify();
                        Log.i("ThreadTest", "向队列取中插入一个元素，队列剩余空间："+ (queueSize-queue.size()));
                    }
                }
            }
        }
    }
}
