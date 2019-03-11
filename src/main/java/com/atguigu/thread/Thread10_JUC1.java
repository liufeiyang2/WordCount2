package com.atguigu.thread;

import java.util.concurrent.locks.ReentrantLock;

public class Thread10_JUC1 {
    public static void main(String[] args) {
        ShareData10 shareData10 = new ShareData10();

        for (int i = 0; i <2 ; i++) {
            new Thread(()->{
                shareData10.print();

            },"线程"+i).start();
        }


    }
}
class  ShareData10{
    //锁ReentrantLock 中加参数 true，公平锁
    ReentrantLock lock=new ReentrantLock(true);

    public void print(){

        while (true){
           lock.lock();
            System.out.println(Thread.currentThread().getName()+"--打印信息");
            lock.unlock();
        }


    }



}
