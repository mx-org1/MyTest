
package com.example.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**

* @author 作者 Your-Name: mx

* @version 创建时间：2019年10月30日 下午1:55:31

* 类说明 将Service层的服务异步化，
* 在executeAsync()方法上增加注解@Async("asyncServiceExecutor")，
*asyncServiceExecutor方法是前面ExecutorConfig.java中的方法名，
* 表明executeAsync方法进入的线程池是asyncServiceExecutor方法创建的
原文链接：https://blog.csdn.net/m0_37701381/article/details/81072774

*/

@Service
public class AsyncServiceImpl implements AsyncService {
    private static final Logger logger = LoggerFactory.getLogger(AsyncServiceImpl.class);
    int count;
    @Override
    @Async("asyncServiceExecutor")
    public void executeAsync() {
        logger.info("start executeAsync");
        count++;
        System.out.println("总共执行了"+count+"次");
        System.out.println("异步线程要做的事情");
        System.out.println("可以在这里执行批量插入等耗时的事情");

        logger.info("end executeAsync");
    }
}
