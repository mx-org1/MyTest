package com.example.demo.controller;

import java.net.URL;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;


import junit.framework.Assert;

@RunWith(SpringRunner.class)
//指定web环境，随机端口
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
/**
 * 单元测试
 * @author JD-DZ327
 *
 */
public class TestDemo {
	//记录器
	Logger logger =LoggerFactory.getLogger(getClass());
	//这个对象是运行在web环境的时候加载到spring容器中
    @Autowired
    private TestRestTemplate testRestTemplate;
    /**
     * @LocalServerPort 提供了 @Value("${local.server.port}") 的代替
     */
    @LocalServerPort
    private int port;
    private URL base;
    @Before
    public void setUp() throws Exception {
    	String url = String.format("http://localhost:%d/", port);
    	System.out.println(String.format("port is : [%d]", port));
    	this.base = new URL(url);
    	}

     @Test
    public void testShow(){
    	 //用单元测试这个warn是最低打印级别
    	logger.warn("日志控制台打印");
    	 Encoder encoder = Base64.getEncoder();
		 byte[] encode = encoder.encode("abc".getBytes());
		 System.out.println("abc加密:"+new String(encode));
		 Decoder decoder=Base64.getDecoder();
		 byte[] decode = decoder.decode(new String(encode));
		 System.out.println("abc解密"+new String(decode));
		 
        String context = testRestTemplate.getForObject("/emp/test?id=100",String.class);
        Assert.assertEquals("show10",context);
    }
}
