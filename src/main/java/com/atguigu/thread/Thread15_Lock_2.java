package com.atguigu.thread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Thread15_Lock_2 {

    public static void main(String[] args) {


        // 工具类
        //循环屏障
        CyclicBarrier cb = new CyclicBarrier(5, ()-> {
            System.out.println( "人员全部到齐，开始开会" );
        });

        for ( int i = 1; i <= 5; i++ ) {
            new Thread(()-> {
                System.out.println( Thread.currentThread().getName() + "到达会议室" );
                try {
                    cb.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }, "参会人员"+i).start();
        }
    }
    }

