package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.example.demo.config.TokenConfig;
import com.example.demo.entity.User;
import com.example.demo.util.CookieUtils;
@Controller
@RequestMapping("/login")
public class LoginController {

	@Autowired
    StringRedisTemplate stringRedisTemplate;

	@Autowired
    RedisTemplate<String, String> redisTemplate;


	@RequestMapping("/login")
	public String hello(HttpServletRequest request, String redirectUrl,Model model) {
		System.out.println("登录");
		//String contextPath = request.getContextPath();//为空""
		System.out.println("登录"+redirectUrl);
		model.addAttribute("redirectUrl", redirectUrl);
        return "/hello";
	}
	/**
	 * 以前的登录
	 * @param username
	 * @param request
	 * @return
	 */
	/*@RequestMapping("/toLogin")
	public String login(@RequestParam() String username,HttpServletRequest request) {
		System.out.println("放session");
		//每个浏览器的sessionid是不一样的，而且获得的session也是和每个浏览器对应的
        HttpSession session = request.getSession();
        session.setAttribute("username", username);

		return "SessionDemo";
	}*/
	/**
	 * 这个是往会话里放东西，实际是想模仿登录验证成功了
	 * @param username
	 * @param request
	 * @return
	 */
	@RequestMapping("/fangSession")
	@ResponseBody
	public String fangSession(@RequestParam() String username,HttpServletRequest request) {
		System.out.println("放session");
		//每个浏览器的sessionid是不一样的，而且获得的session也是和每个浏览器对应的
        HttpSession session = request.getSession();
        session.setAttribute("username", username);

		return "success";
	}
	@RequestMapping("/kan")
	public String toLogin() {
		return "SessionDemo";
	}
	/**
	 * 通过cookie去redis里面查token是否失效，如果没有失效还得刷新有效时间
	 * @param token
	 * @param request
	 * @return
	 */
	@RequestMapping("/lookTokenFromRedis")
	@ResponseBody
	public String lookTokenFromRedis(@RequestParam() String token,HttpServletRequest request) {
		ValueOperations<String, String> operation = stringRedisTemplate.opsForValue();
		String userInfoFromRedis = operation.get("USER_TOKEN"+token);
		//如果token查到没失效，更新有效时间
		if(StringUtils.isNotEmpty(userInfoFromRedis)) {
			stringRedisTemplate.expire("USER_TOKEN",1000,TimeUnit.SECONDS);
		}

		return userInfoFromRedis;
	}
	/**
	 * 现在的登录，登录成功后放到redis，session在拦截器里放
	 * @param username
	 * @param request
	 * @return
	 */
	@RequestMapping("/toLogin")
	public String login(@RequestParam() String username,HttpServletRequest request,HttpServletResponse response) {
		ValueOperations<String, String> operation = stringRedisTemplate.opsForValue();
		User user=new User("001","mx","123");
		String newToken = UUID.randomUUID().toString();
		//看别人的cookieutil，感觉写的很乱
        CookieUtils.setCookie(request, response, "USER_TOKEN", newToken);
        String userToJsonString = JSON.toJSONString(user);
		operation.set("USER_TOKEN"+newToken,userToJsonString,1000,TimeUnit.SECONDS);

		return "SessionDemo";
	}
	/**
	 * 河马的登录
	 * @param username
	 * @param password
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/HeMaLogin")
	public Object HeMaLogin(String username,String password) {
		System.out.println("用户名"+username);
		if("maxun".equals(username)&&"123".equals(password)) {
			System.out.println("密码正确");
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("token", TokenConfig.getToken(username));
			map.put("username", "maxun");
			return map;
		}
		System.out.println("密码错误");
		return null;

	}
}
