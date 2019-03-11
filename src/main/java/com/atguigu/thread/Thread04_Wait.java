package com.atguigu.thread;

public class Thread04_Wait {
    public static void main(String[] args) throws Exception {
        ShareData04 s = new ShareData04();
        //生产者
        Thread t1 = new Thread(()->{
            for ( int i = 0; i < 10; i++ ) {
                s.produce();

                System.out.println( "count produce =" + s.count );
            }
        });
        //消费者
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                s.consume();
                System.out.println("count consume =" + s.count);
            }


        });
        Thread t3= new Thread(()->{
            s.produce();
            System.out.println("count produce ="+s.count);

        });
        //消费者
        Thread t4= new Thread(()->{
            s.consume();
            System.out.println("count consume ="+s.count);

        });

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        t1.join();
        t2.join();

        System.out.println("final" + s.count);
        System.out.println("main 方法执行完毕");

    }
}

class ShareData04 {
    public int count = 0;

    public synchronized void produce() {
        while (count == 1) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        count++;

        notifyAll();
    }

    public synchronized void consume() {
        while (count == 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        count--;
        notifyAll();
    }


}