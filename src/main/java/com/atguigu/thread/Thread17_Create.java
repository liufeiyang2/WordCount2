package com.atguigu.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class Thread17_Create {
    public static void main(String[] args) throws Exception {
       // MyCallable call = new MyCallable();
        MyRunnable1 myRunnable1 = new MyRunnable1();
        //FutureTask<String> task = new FutureTask<>(call);

        Thread t=new Thread(myRunnable1);
        //Thread t = new Thread(task);
        t.start();

//        String result = task.get();
//        System.out.println("result="+result);
        System.out.println("main方法执行完毕");

    }
}
//Runnable&Callable
//Callable支持泛型
//Callable方法有返回值
//Callable可以抛出异常


// JUC提供了创建线程的新的方式
class MyCallable implements Callable<String> {
    @Override
    public String call() throws Exception {
        System.out.println("callable执行");
        return "thread";
    }
}

class MyRunnable1 implements Runnable {

    @Override
    public void run() {
        System.out.println( "执行Runnable run方法" );
    }

}