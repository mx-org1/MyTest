
package com.example.demo.controller.after927;

/**

* @author 作者 Your-Name: mx1111

* @version 创建时间：2019年11月7日 下午5:26:10

* 类说明 https://blog.csdn.net/fanrenxiang/article/details/80623884

*/

public class AtomicIntegerTest {

    private static final int THREADS_CONUT = 20;
    //不管加不加volatile 还是不能保证原子性的，volatile只是可见性
    public static int count = 0;

    public static void increase() {
        count++;
    }

    public static void main(String[] args) {
        Thread[] threads = new Thread[THREADS_CONUT];
        for (int i = 0; i < THREADS_CONUT; i++) {
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 1000; i++) {
                        increase();
                    }
                }
            });
            threads[i].start();
        }

        while (Thread.activeCount() > 1) {
        	System.out.println("Thread.activeCount() :"+Thread.activeCount());
            Thread.yield();
        }
        System.out.println(count);
    }
}
