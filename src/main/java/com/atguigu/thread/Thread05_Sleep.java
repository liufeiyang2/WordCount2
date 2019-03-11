package com.atguigu.thread;

public class Thread05_Sleep {
    public static void main(String[] args) throws  Exception{
// 共享对象
        ShareData05 sd05 = new ShareData05();
        new Thread( ()->{
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            sd05.flg=true;

        }).start();

        while (true){
            //Thread.sleep(100);

            //同步代码块，要求所有数据在主存中获取，涉及加锁和解锁的问题
            synchronized (sd05){
                if(sd05.flg){
                    System.out.println("flg=true");
                    break;
                }
            }

        }

    }
}

class ShareData05{
    //volatile关键字必须从主存中获取
    //public boolean flg=false;

    public boolean flg=false;
}
