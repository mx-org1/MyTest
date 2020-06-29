
package com.example.demo.controller;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.example.demo.util.CookieUtils;
/**

* @author 作者 Your-Name: mx

* @version 创建时间：2019年9月26日 上午10:21:34

* 类说明

*/
@Controller
public class RedisController {


	@Autowired
    StringRedisTemplate stringRedisTemplate;

	@Autowired
    RedisTemplate<String, String> redisTemplate;

	@RequestMapping("/redis")
	@ResponseBody
	public String setKeyUseRedis(HttpServletRequest request, HttpServletResponse response) {
		ValueOperations<String, String> operation = stringRedisTemplate.opsForValue();
		/*User user=new User("id:1","mx","123");
		String userToJsonString = JSON.toJSONString(user);
		redisTemplate.opsForValue().set("name","mx");
		operation.set("mx", userToJsonString,100,TimeUnit.SECONDS);
		String string = operation.get("mx");
		System.out.println("name"+string);
		boolean empty = StringUtils.isEmpty(operation.get("mx"));
		System.out.println("是否有键值："+empty);*/
		// 生成token
		 String token = CookieUtils.getCookieValue(request, "USER_TOKEN");
		 if(token==null) {
			// 添加写 cookie 的逻辑，cookie 的有效期是关闭浏览器就失效。
			 	String newToken = UUID.randomUUID().toString();
		        CookieUtils.setCookie(request, response, "USER_TOKEN", newToken);
		 }
        //String token = UUID.randomUUID().toString();
        //redisTemplate.opsForValue().set("token",token);
		return token;

	}


}
