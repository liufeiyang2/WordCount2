package com.atguigu.thread;

public class Thread02_Create {
    public static void main(String[] args) {
        //创建线程 -1
        Thread t1= new MyThread();
        t1.start();

        //创建线程 -2
        Thread t2=new Thread( new MyRunnable());
        t2.start();

        //创建线程 -3
        Thread t3=new Thread( new Runnable() {
            @Override
            public void run() {
                System.out.println("run3方法执行");
            }
        });
        t3.start();
        //创建线程 -4
        Thread t4=new Thread(()->{
            System.out.println("执行run4方法");
        });
        t4.start();

    }
}

class MyRunnable implements Runnable{

    @Override
    public void run() {
        System.out.println("run2方法执行");
    }
}
class MyThread extends Thread{
    @Override
    public void run() {
        System.out.println("执行run1方法");
    }
}