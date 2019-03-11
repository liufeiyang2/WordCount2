package com.atguigu.thread;

import java.util.concurrent.atomic.AtomicInteger;

public class Thread08_Atomic {
    public static void main(String[] args) throws Exception {
        //共享对象
        ShareData08 sd8 = new ShareData08();

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                sd8.produce();
                System.out.println("count produce=" + sd8.count);
            }
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                sd8.consume();
                System.out.println("count consume=" + sd8.count);
            }

        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("final = " + sd8.count);
        System.out.println("main方法执行完毕");

    }
}

class ShareData08 {
   // public int count = 0;

    //public volatile int count=0;
//使用原子类
    public AtomicInteger count=new AtomicInteger(0);
    /*生产数据
    ++ 运算符不具备原子性，可能会被其他线程的指令打断
    volatile关键字只能解决内存可见性问题，无法解决原子性问题
    1）增加synchronized关键字
    2）从JDK1.5后，JDK提供更加丰富的线程对象，用于提高线程问题，称之为JUC
    JavaUtilConcurrent
    java.util.concurrent

 */

    public void produce() {
        //count++;
        count.getAndIncrement();
    }

    public void consume() {
        //count--;
        count.getAndDecrement();

    }


}