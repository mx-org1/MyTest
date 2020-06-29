package com.example.demo.interceptor;

import java.net.URLDecoder;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


import com.example.demo.config.HttpClientTest;

@Component
public class SSOInterceptor implements HandlerInterceptor {
	// 这个方法是在访问接口之前执行的，我们只需要在这里写验证登陆状态的业务逻辑，就可以在用户调用指定接口之前验证登陆状态了
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//直接看cookie有的话去redis查，能查到放行
		String user_token=null;
		StringBuffer requestURL = request.getRequestURL();
		//判断有没有会话
			Cookie[] cookies = request.getCookies();
			if(cookies!=null&&cookies.length>0) {
				for (int i = 0; i < cookies.length; i++) {
					if (cookies[i].getName().equals("USER_TOKEN")) {
						user_token = cookies[i].getValue();
						break;
					}
				}
			}
			//判断是不是没有cookie
			if(StringUtils.isEmpty(user_token)) {
				response.sendRedirect("http://he.mx.com:8888/login/login?redirectUrl=" + requestURL);
				return false;
			}else {
				//去redis里面查这个cookie的token还能用不能了
				String sendGetData = HttpClientTest.sendGetData("http://he.mx.com:8888/login/lookTokenFromRedis?token="+user_token,"utf-8");
				System.out.println("从redis里面查的数据为"+sendGetData);
				if(StringUtils.isNotEmpty(sendGetData)) {
					if(request.getSession().getAttribute("user")!=null) {
						return true;
					}else {
						request.getSession().setAttribute("user", sendGetData);
						return true;
					}
				}else {
					response.sendRedirect("http://he.mx.com:8888/login/login?redirectUrl=" + requestURL);
					return false;
				}
			}
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			@Nullable ModelAndView modelAndView) throws Exception {
	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
			@Nullable Exception ex) throws Exception {
	}
}