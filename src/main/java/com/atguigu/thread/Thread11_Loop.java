package com.atguigu.thread;

public class Thread11_Loop {
    public static void main(String[] args) {
        ShareData11 shareData11 = new ShareData11();

        Thread t1 = new Thread(()->{

            for (int i = 0; i <3 ; i++) {
                shareData11.print5();
            }
        });
        Thread t2= new Thread(()->{

            try {
                t1.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int i = 0; i <3 ; i++) {
                shareData11.print10();
            }
        });
        Thread t3= new Thread(()->{

            try {
                t2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int i = 0; i <3 ; i++) {
                shareData11.print15();
            }
        });
        
        t1.start();
        t2.start();
        t3.start();
    }
}

class ShareData11 {


    public synchronized void print5() {
        for (int i =1; i <6 ; i++) {
            System.out.println(i);
        }
    }

    public synchronized void print10() {
        for (int i = 6; i < 11; i++) {
            System.out.println(i);
        }
    }

    public synchronized void print15() {

        for (int i = 11; i <16 ; i++) {
            System.out.println(i);
        }
    }
}
