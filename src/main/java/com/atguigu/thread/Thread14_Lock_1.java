package com.atguigu.thread;

import java.util.concurrent.CountDownLatch;

public class Thread14_Lock_1 {
    public static void main(String[] args) throws Exception{
        //工具类
        CountDownLatch latch = new CountDownLatch(5);

        for (int i = 1; i <=5 ; i++) {
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+"打扫完成，离开");
                //倒数
                latch.countDown();
            },"学生"+i).start();
        }
       latch.await();
        System.out.println("老师锁门");

    }
}
