
package com.example.demo.controller.after927;

import java.util.concurrent.CountDownLatch;

/** 

* @author 作者 Your-Name: mx

* @version 创建时间：2020年6月1日 上午10:58:29 

* 类说明 

*/

public class CountDownLatchTest {

    public static void main(String[] args) {
        CountDownLatch begin = new CountDownLatch(1);
        CountDownLatch end = new CountDownLatch(2);

        for(int i=0; i<2; i++){
            Thread thread = new Thread(new Player(begin,end),String.valueOf(i));
            thread.start();
        }

        try{
        	Thread.sleep(10);
            System.out.println("the race begin");
            begin.countDown();
            end.await();//await() 方法具有阻塞作用，也就是说主线程在这里暂停
            System.out.println("the race end");
        }catch(Exception e){
            e.printStackTrace();
        }

    }
}



class Player implements Runnable{

    private CountDownLatch begin;

    private CountDownLatch end;

    Player(CountDownLatch begin,CountDownLatch end){
        this.begin = begin;
        this.end = end;
    }

    public void run() {

        try {
        	begin.await();//因为此时已经为0了，所以不阻塞
            System.out.println(Thread.currentThread().getName() + " start !");;
            System.out.println(Thread.currentThread().getName() + " arrived !");
            end.countDown();//countDown() 并不是直接唤醒线程,当end.getCount()为0时线程会自动唤醒
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
