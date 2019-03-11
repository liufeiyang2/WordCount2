package com.atguigu.thread;

public class Thread07_Synchronized {
    //线程八锁
    // 1）一个对象，两个同步方法（A,B）,哪一个方法先执行
//   线程先抢到资源会先执行
// 2）一个对象，两个同步方法（A,B），但是在A方法中休眠4秒，哪一个方法先执行
//   第一个线程抢占资源，先执行，但是由于休眠了4秒，应该时第二线程执行，但是由于第一个线程没有释放对象锁
//   即使第二线程抢占了CPU的资源，但是依然无法执行，需要等待第一个线程释放对象锁。才能执行
//   所以 结果为等待4秒后，先打印A，再打印B
    // 3) 一个对象，一个同步方法（A）,一个普通成员方法（C）,哪一个方法先执行
//   结果为：因为不需要等待对象锁，所以先打印C，等待4秒后，再打印A
// 4) 两个对象，两个同步方法（A，B）,哪一个方法先执行
//   结果为：不同的对象有不同的对象锁，互相不影响，所以先打印B，等待4秒后，再打印A
    public static void main(String[] args) {
        ShareData06 sd06 = new ShareData06();
        ShareData06 sd66 = new ShareData06();

        Thread t1 = new Thread(() -> {
            sd06.printA();
        });


        Thread t2 = new Thread(() -> {
            sd66.printB();
        });

        t1.start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t2.start();


    }
}

class ShareData06 {
    public synchronized void printA() {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("aaaa");
    }

    public synchronized void printB() {
        System.out.println("bbbb");
    }

    public void printC() {
        System.out.println("cccc");
    }

}