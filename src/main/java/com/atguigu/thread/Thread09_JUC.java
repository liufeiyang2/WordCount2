package com.atguigu.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Thread09_JUC {
    public static void main(String[] args) throws Exception {
        //synchronized 重量级的锁

        ShareData09 sd04 = new ShareData09();
        // 生产者
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                sd04.produce();

                System.out.println("count produce = " + sd04.count);
            }
        });


        // 消费者
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                sd04.consume();
                System.out.println("count consume = " + sd04.count);
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("final = " + sd04.count);
        System.out.println("main方法执行完毕");
    }
}

//JUC中明确锁的概念
//早期锁的方式：synchronized,wait,notifyAll
//juc方法：ReentrantLock,Condition,await,signalAll
 class ShareData09 {

    //创建锁对象
    ReentrantLock lock = new ReentrantLock();
    Condition condition=lock.newCondition();

    public int count = 0;

    public void produce() {
        lock.lock();
        while (count == 1) {
            try {
               condition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }


        count++;
        condition.signalAll();
        //释放锁
        lock.unlock();

    }

    public void consume() {
        lock.lock();
        while (count == 0) {
            try {
                condition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        count--;
        //释放信号
        condition.signalAll();

        lock.unlock();

    }


    /*public synchronized void produce() {
        while (count==1){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        count ++;
        notifyAll();
    }

    public synchronized void consume() {
        while (count==0){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        count--;
        notifyAll();

    }*/
}