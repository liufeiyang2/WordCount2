package com.atguigu.thread;

public class Thread01_Safe1 {
    public static void main(String[] args) {
    //创建资源
        final ShareData01 sd1=new ShareData01();

                Thread t1=new Thread( ()->{
                    sd1.username="zhangsan";

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(sd1.username);
                }
                );


        Thread t2=new Thread( ()->{
            sd1.username="lisi";

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(sd1.username);
        }
        );
        t1.start();
        t2.start();
        System.out.println("main方法执行完毕");
    }


}

class ShareData01{

    public String username="";
}
