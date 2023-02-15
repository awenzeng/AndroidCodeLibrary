package com.awen.codebase.model.thread;

import android.util.Log;

import java.util.Queue;
import java.util.Stack;

public class ProduceConsumerTest {
    private Object lock = new Object();
    private Stack<String> queue = new Stack<>();

    public void start(){
        Thread producer = new Thread(new Producer());
        Thread consumer = new Thread(new Consumer());
        producer.start();
        consumer.start();

    }

    class Producer implements Runnable{
        @Override
        public void run() {
            while (true){
                synchronized (lock){
                    if(queue.size() == 5){
                        lock.notify();
                        try {
                            Log.i("ProduceConsumer","-------产品已生产，请消费者享用-------");
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            lock.notify();
                        }
                    }
                    queue.add("产品");

                    Log.i("ProduceConsumer","产品已生产"+queue.size()+"个");
                }

            }

        }
    }

    class Consumer implements Runnable{
        @Override
        public void run() {
            while (true){
                synchronized (lock){
                    if(queue.size()==0){
                        lock.notify();
                        try {
                            Log.i("ProduceConsumer","-------产品已用完，请继续生产-------");
                            lock.wait();
                        } catch (InterruptedException e) {
                            lock.notify();
                        }
                    }
                    queue.pop();
                    Log.i("ProduceConsumer","产品已享用还剩余" + queue.size() + "个");
                }
            }

        }
    }



}
