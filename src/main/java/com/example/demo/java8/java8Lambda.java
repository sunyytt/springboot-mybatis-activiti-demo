package com.example.demo.java8;

public class java8Lambda {
    public static void main(String[] args) {
        MathOperation add =(a,b)->a+b;
        MathOperation sub =(a,b)->a-b;
        System.out.println("add = [" + add + "]");
        System.out.println("5+10=" + operate(5,10,add));
        System.out.println("2-1=" + operate(2,1,sub));
        System.out.println("java8 以前");
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("The old runable now is using!");
            }
        }).start();
        System.out.println("java8 啦");
        new Thread(()-> System.out.println("It's a lambda function!")).start();
    }
    //注意是内部类
    //可以简化 线程
    interface MathOperation {
        int operation(int a, int b);
    }

    interface GreetingService {
        void sayMessage(String message);
    }

    private static int operate(int a, int b, MathOperation mathOperation){
        return mathOperation.operation(a, b);
    }
}
