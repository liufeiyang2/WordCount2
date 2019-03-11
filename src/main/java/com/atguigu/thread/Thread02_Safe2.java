package com.atguigu.thread;

public class Thread02_Safe2 {
    public static void main(String[] args) {
        //String StringBuilder StringBuffer
        StringBuilder sb=new StringBuilder();
        String s="";
        for (int i=0;i<=1000;i++){
            s +=i;
            //sb.append(i);
        }
        System.out.println(s);
    }
}
