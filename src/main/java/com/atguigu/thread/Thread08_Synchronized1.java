package com.atguigu.thread;

public class Thread08_Synchronized1 {

    // 线程八锁 ： synchronized 8种使用情况
// 5）一个对象，两个静态同步方法（A,B）,哪一个方法先执行
//  静态同步方法等同于给类加锁，所以只要访问静态同步方法，都需要判断是否能够获取锁
//  结果为：等待4秒后，先执行A，再打印B
// 6) 一个对象，一个静态同步方法（A）,一个成员同步方法(B),哪一个方法先执行
//  结果为：打印B， 等待4秒后，再执行A
// 7) 两个对象，两个静态同步方法（A,B）,哪一个方法先执行
//  结果为：等待4秒后，先执行A，再打印B
// 8) 两个对象，一个静态同步方法（A），一个成员同步方法(B),哪一个方法先执行
//  结果为：打印B， 等待4秒后，再执行A
    public static void main(String[] args) {

        ShareData07 sd07 = new ShareData07();
        ShareData07 sd77 = new ShareData07();

        Thread t1 = new Thread(()-> {
            sd07.printA();
        });


        Thread t2 = new Thread(()-> {
            sd77.printB();
//            sd07.printB();
        });

        t1.start();
//        try {
//            Thread.sleep(100);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        t2.start();

    }


}
class ShareData07 {
    public static synchronized void printA() {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println( "aaaa" );
    }

    public static synchronized void printB() {
        System.out.println( "bbbb" );
    }

    public void printC() {
        System.out.println( "cccc" );
    }
}