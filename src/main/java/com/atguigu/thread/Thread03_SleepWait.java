package com.atguigu.thread;

public class Thread03_SleepWait {
    public static void main(String[] args) {
        Thread t1 = new Thread();
        Thread t2 = new Thread();

        //sleep为静态方法
        //t1不会休眠
        //sleep会让调用者的线程休眠
        //main线程休眠
//        try {
//            t1.sleep(100);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //wait是成员方法
        //t2会等待
        try {
            synchronized (t2){
                t2.wait();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("main方法执行完毕");
    }
}
