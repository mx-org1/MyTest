
package com.example.demo.redislock;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/** 

* @author 作者 Your-Name: mx

* @version 创建时间：2020年1月10日 下午5:40:54 

* 类说明 https://www.cnblogs.com/williamjie/p/9395659.html
https://www.jianshu.com/p/47fd7f86c848这个还讲了可重入锁
要在使用到redis的地方建立和关闭连接，每次建立连接了要关闭，而且每次连都要是一个新的连接，不能用老连接
参考https://blog.csdn.net/buyiyangdexia/article/details/100673229是一个错误解决
*/
@Service
public class RedisTool {
	private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "PX";

    private static final Long RELEASE_SUCCESS = 1L;
    
    @Autowired
    JedisPool jedisPool;
    /**
     * 尝试获取分布式锁
     * @param jedis Redis客户端
     * @param lockKey 锁
     * @param requestId 请求标识
     * @param expireTime 超期时间
     * @return 是否获取成功
     */
    public  boolean tryGetDistributedLock(String lockKey, String requestId, int expireTime) {
    	Jedis jedis=null;
    	try {
    		jedis = jedisPool.getResource();
			String result = jedis.set(lockKey, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);
			if (LOCK_SUCCESS.equals(result)) {
				return true;
			}
			return false;
		} finally {
			jedis.close();
			
		}

    }
    
    /**
     * 释放分布式锁
     * @param jedis Redis客户端
     * @param lockKey 锁
     * @param requestId 请求标识
     * @return 是否释放成功
     */
    public  boolean releaseDistributedLock(String lockKey, String requestId) {
    	Jedis jedis =null;
        try {
        	 jedis = jedisPool.getResource();
			String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
			Object result = jedis.eval(script, Collections.singletonList(lockKey),
					Collections.singletonList(requestId));
			if (RELEASE_SUCCESS.equals(result)) {
				return true;
			}
			return false;
		} finally {
			jedis.close();
		}

    }
    
}
