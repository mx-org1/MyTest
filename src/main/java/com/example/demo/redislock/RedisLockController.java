
package com.example.demo.redislock;

import java.util.UUID;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/** 

* @author 作者 Your-Name: mx

* @version 创建时间：2020年1月10日 下午5:54:22 

* 类说明 

*/
@Controller
public class RedisLockController {
	
	@Autowired
	private RedisTool redisTool;
	//用于计算抢到的有多少个
	int count=0;
	@ResponseBody
	@RequestMapping("/getLock")
	public boolean getLock() {
		/*Jedis jedis = jedisPool.getResource();
		jedis.select(0);
		jedis.set("k1","v1")*/
		return  redisTool.tryGetDistributedLock("lockabc", "123", 100000);
	}
	
	@ResponseBody
	@RequestMapping("/releaseLock")
	public void releaseLock() {
		redisTool.releaseDistributedLock("lockabc", "1231");
	}
	@ResponseBody
	@RequestMapping("/lockTest")
	public void lockTest() throws InterruptedException {
		count=0;
		final CyclicBarrier barrier = new CyclicBarrier(1000,new Runnable() {
            public void run() {
               System.out.println("1000个并发启动了");
            }
        });
		 //创建线程池
        ExecutorService executorService = Executors.newFixedThreadPool(1000);
		for (int i = 0; i < 1000; i++) {
			executorService.execute(()->{
				try {
					String randomUUID = UUID.randomUUID().toString();
					//等待其他线程执行
					barrier.await();
					//获取到锁执行
					if(redisTool.tryGetDistributedLock("lockabc",randomUUID, 100000)) {
						System.out.println("count:"+(++count));
					}
					Thread.sleep(10);
					redisTool.releaseDistributedLock("lockabc", randomUUID);
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		}
	}
}
