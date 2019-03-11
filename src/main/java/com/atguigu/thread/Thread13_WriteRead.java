package com.atguigu.thread;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Thread13_WriteRead {
    public static void main(String[] args) {
        RedSpider redSpider = new RedSpider();
        new Thread(() -> {
            redSpider.write("hello Thread!!");

        }).start();

        for (int i = 1; i <= 20; i++) {
            new Thread(() -> {

                redSpider.read();

            }, "学生" + i).start();
        }


    }
}

//读数据时不能写
//写数据时不能读
//多个线程读数据无所谓

class RedSpider {
    public String content = "";

    private ReentrantReadWriteLock lock=new ReentrantReadWriteLock();

    public void write(String s) {
        lock.writeLock().lock();
        content = s;

        System.out.println("教师写入了数据=" + content);
        lock.writeLock().unlock();
    }

    public void read() {

        lock.readLock().lock();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "读取数据=" + content);
        lock.readLock().unlock();
    }
}