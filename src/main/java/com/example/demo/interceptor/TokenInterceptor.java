
package com.example.demo.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.config.TokenConfig;
import com.example.demo.exception.NoTokenException;
import com.example.demo.exception.TokenErrorException;
import com.example.demo.exception.TokenExpiredException;

import io.jsonwebtoken.Claims;

/**

* @author 作者 Your-Name: mx

* @version 创建时间：2019年10月12日 上午11:14:24

* 类说明

*/
@Component
public class TokenInterceptor implements HandlerInterceptor{
	/**
	 * 不知道为啥一进拦截器前端就报跨域问题但是入口那里已经写过了，这里添加过后就不报错了，但是还有一个bug就是程序运行一会后端前端就都报错了
	 * 前端两个错一个是预检请求不是200第二个是说跨域请求不是http OK啥的（意思就是返回不正常），后来我把响应码改成200解决了，但是又报跨域问题了
	 * 这个时候查出来在Access-Control-Allow-Headers加上自己定义的头部就好了
	 * 后端的错是按理说会进我自己写的自定义异常也没有进，平常报错是没提示的竟然有提示了（就是没进自己定义的异常处理里面），前端问题解决了以后，后端自己解决了
	 *https://www.cnblogs.com/caimuqing/p/6733405.html看这个解决了
	 *https://blog.csdn.net/java_green_hand0909/article/details/78740765跨域参数介绍
	 * @param request
	 * @param response
	 */
	 private void setCorsMappings(HttpServletRequest request, HttpServletResponse response){
	        String origin = request.getHeader("Origin");
	        response.setHeader("Access-Control-Allow-Origin", origin);
	        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
	        response.setHeader("Access-Control-Max-Age", "3600");
	        //这里的设置必须把自己定义的头部加上
        	response.setHeader("Access-Control-Allow-Headers", "Content-Type,token");
	        //response.setHeader("Access-Control-Allow-Headers", "x-requested-with,Authorization,Content-Type");
	        response.setHeader("Access-Control-Allow-Credentials", "true");
	    }

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		setCorsMappings(request, response);
		if(request.getMethod().equals("OPTIONS")) {
			response.setStatus(200);
			 return true;
		}
		 // 地址过滤
        String uri = request.getRequestURI() ;
        if (uri.contains("/login")){
            return true ;
        }
        // Token 验证
        String header = request.getHeader("token");
        if(StringUtils.isNotEmpty(header)) {
        	 Claims claims = TokenConfig.getTokenClaim(header);
        	 //判断token是否是伪造的或者过期了
        	 if(claims==null) {
        		 response.setStatus(401);
        		 throw new TokenErrorException();
        	 }
        }else {

        	  throw new NoTokenException();
        }


		return true;
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			@Nullable ModelAndView modelAndView) throws Exception {
	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
			@Nullable Exception ex) throws Exception {
	}

}
