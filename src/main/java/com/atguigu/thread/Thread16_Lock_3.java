package com.atguigu.thread;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Thread16_Lock_3 {
    public static void main(String[] args) {

        //信号灯
        Semaphore semaphore = new Semaphore(3);

        for (int i = 1; i <=6 ; i++) {
            new Thread(()->{

                try {
                    //抢占资源
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName()+">>>>>>>>>停车位");
                    Thread.sleep(new Random().nextInt(1000));
                    System.out.println(Thread.currentThread().getName()+"<<<<<<<<<停车位");
                    //释放资源
                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            },"汽车"+i).start();
        }
    }
}
