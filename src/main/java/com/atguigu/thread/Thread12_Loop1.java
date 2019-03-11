package com.atguigu.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Thread12_Loop1 {
    public static void main(String[] args) {
        ShareData12 shareData12 = new ShareData12();

        Thread t1 = new Thread(() -> {

            for (int i = 0; i < 3; i++) {
                shareData12.print5();
            }
        });
        Thread t2 = new Thread(() -> {

            for (int i = 0; i < 3; i++) {
                shareData12.print10();
            }
        });
        Thread t3 = new Thread(() -> {


            for (int i = 0; i < 3; i++) {
                shareData12.print15();
            }
        });

        t1.start();
        t2.start();
        t3.start();
    }

}

class ShareData12 {
    public ReentrantLock lock = new ReentrantLock();
    private Condition c5 = lock.newCondition();
    private Condition c10 = lock.newCondition();
    private Condition c15 = lock.newCondition();

    private int printnum=5;

    public void print5() {
        lock.lock();

        while (printnum !=5){
            try {
                c5.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (int i = 1; i < 6; i++) {

            System.out.println(i);
        }
        printnum=10;
        c10.signal();
        lock.unlock();
    }

    public void print10() {
        lock.lock();
        while (printnum!=10){
            try {
                c10.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (int i = 6; i < 11; i++) {
            System.out.println(i);
        }
        printnum=15;
        c15.signal();
        lock.unlock();
    }

    public void print15() {
        lock.lock();
        while (printnum!=15){
            try {
                c15.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (int i = 11; i < 16; i++) {
            System.out.println(i);
        }
        printnum=5;
        c5.signal();
        lock.unlock();
    }
}
