package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * 四种开启多线程的方法
 * @author JD-DZ327
 * 在JDK1.5之前，创建线程就只有两种方式，即继承java.lang.Thread类和实现java.lang.Runnable接口；
 * 而在JDK1.5以后，增加了两个创建线程的方式，即实现java.util.concurrent.Callable接口和线程池。
 * 调用start方法方可启动线程，而run方法只是thread的一个普通方法调用，还是在主线程里执行。https://blog.csdn.net/qq_42478956/article/details/88238377
 */
public class FourMultiThread {
	 /**
	  * 继承thread类
	  * @param args
	  */
	public static void main(String[] args) {
	        //设置线程名字
	        Thread.currentThread().setName("main thread");
	        MyThread myThread = new MyThread();
	        myThread.setName("子线程:");
	        //开启线程
	        myThread.start();
	        for(int i = 0;i<5;i++){
	            System.out.println(Thread.currentThread().getName() + i);
	        }
	    }
	}

class MyThread extends Thread{
	    //重写run()方法
	    public void run(){
	        for(int i = 0;i < 10; i++){
	            System.out.println(Thread.currentThread().getName() + i);
	        }
	    }
}
//实现Runnable接口
class RunnableTest {




    public static void main(String[] args) {
        //设置线程名字
        Thread.currentThread().setName("main thread:");
        Thread thread = new Thread(new MyRunnable());
        thread.setName("子线程:");
        //开启线程
        thread.start();
        for(int i = 0; i <5;i++){
            System.out.println(Thread.currentThread().getName() + i);
        }
        //
        new Thread(new Runnable() {
      		 @Override
    		    public void run() {
    		        for (int i = 0; i < 10; i++) {
    		            System.out.println(Thread.currentThread().getName() + i);
    		        }
    		    }
    	 }).run();

        new Thread(
        	()->{

        	}
        		).run();

    }
}

class MyRunnable implements Runnable {

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(Thread.currentThread().getName() + i);
        }
    }
}
//实现Callable接口
class CallableTest {

    public static void main(String[] args) {
        //执行Callable 方式，需要FutureTask 实现实现，用于接收运算结果
        FutureTask<Integer> futureTask = new FutureTask<Integer>(new MyCallable());
        new Thread(futureTask).start();
        //接收线程运算后的结果,虽然这里面没有体现出来并发执行，但是实际上也是并发执行的
        try {
            Integer sum = futureTask.get();
            System.out.println("总合为："+sum);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}

class MyCallable implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        int sum = 0;
        for (int i = 0; i < 100; i++) {
            sum += i;
        }
        return sum;
    }
}
//线程池实现
class ThreadPoolExecutorTest {

    public static void main(String[] args) {
        //创建线程池
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        ThreadPool threadPool = new ThreadPool();
        for(int i =0;i<5;i++){
            //为线程池分配任务
            executorService.submit(threadPool);
        }
        //关闭线程池
        executorService.shutdown();
    }
}

class ThreadPool implements Runnable {

    @Override
    public void run() {
        for(int i = 0 ;i<10;i++){
            System.out.println(Thread.currentThread().getName() + ":" + i);
        }
    }

}

class Test {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        List<Future<String>> futures = new ArrayList<Future<String>>();
        for(int i=0;i<10;i++){
            //使用future接受处理结果
            Future<String> future = executorService.submit(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    System.out.println("线程名称"+Thread.currentThread().getName());
                    return Thread.currentThread().getName();
                }
            });
            futures.add(future);
        }
        try {
            for(Future<String> future : futures){
                //get方法会阻塞当前线程，直到任务执行完成返回结果
            	//get（）方法可以当任务结束后返回一个结果，如果调用时，工作还没有结束，则会阻塞线程，直到任务执行完毕
                //所以这点结束后才会打印线程池关闭
            	System.out.println("返回结果====="+future.get());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //开始关闭线程池
        executorService.shutdown();
        System.out.println("线程池关闭完成");

    }
}