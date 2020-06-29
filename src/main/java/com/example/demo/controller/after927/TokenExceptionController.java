
package com.example.demo.controller.after927;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.demo.exception.TokenErrorException;
import com.example.demo.exception.TokenExpiredException;

/**
 *
 * @author 作者 Your-Name: mx
 *
 * @version 创建时间：2019年10月12日 下午3:55:26 https://www.jianshu.com/p/40096e2772f7	比对的
 *           https://mp.weixin.qq.com/s/9c0j2GzCNmtdOL8EfCV_bA
 *          江南一点雨的感觉（用到了instance of）有点老就没有用 类说明 自定义处理异常，这样就可以返回自己定义的信息，以前原生的可能可读性差些
 *		自定义的请求异常虽然异常了但是返回的状态码是200，当然也不会进到错误的处理里面
 */
@ControllerAdvice
public class TokenExceptionController {
	/**
	 *
	 *第一个注解是告诉框架这个方法是用来处理什么异常的；
	 *第二个注解是为了接口返回的；
	 *第三个注解是告诉框架在服务器发生什么错误的时候来走这个方法（HttpStatus.INTERNAL_SERVER_ERROR是服务器错误的时候500）
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.UNAUTHORIZED)	//INTERNAL_SERVER_ERROR	这里就可以自定义返回的状态码，400请求格式错误，401未认证，403服务器拒绝访问
	public Map<String, Object> handleTokenErrorException(Exception ex,HttpServletResponse response) {
		response.setStatus(403);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", 401);
		map.put("message", ex.getMessage());
		return map;
	}
}