
package com.example.demo.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
* https://m.jb51.net/article/46882.htm
*
* @author 作者 Your-Name: mx
* @version 创建时间：2019年9月27日 上午10:49:19
*  类说明
*/
@Controller
public class CookieTestController {
	/**
	 *	所有查找删除默认域名都是当前域名he.mx.com只有设置的时候才更改
	 *默认路径都是/
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/setCookie")
	@ResponseBody
	public String setCookie(HttpServletRequest request, HttpServletResponse response) {
		Cookie cookie = new Cookie("cookieTest", "111");
		//cookie.setPath("/setCookie");
		//所有查找删除默认域名都是当前域名he.mx.com只有设置的时候才更改
		cookie.setDomain("mx.com");
		cookie.setMaxAge(60);
		response.addCookie(cookie);
		return null;
		}
	/**
	 * 	读取Cookie
 		该方法可以读取当前路径以及“直接父路径”的所有Cookie对象，
 		如果没有任何Cookie的话，则返回null,这里就读取不到上面放的cookie，而且会读父域名下路径为/的cookie
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/getCookie")
	@ResponseBody
	public Object getCookie(HttpServletRequest request, HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();

		return cookies;
	}
	/**
	 * .删除时，如果当前路径下没有键为"key"的Cookie，则查询全部父路径，
	 * 	检索到就执行删除操作(每次只能删除一个与自己最近的父路径Cookie)
	 * 如果写了路径，此路径找不到就不删除了
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/delCookie")
	@ResponseBody
	public Object delCookie(HttpServletRequest request, HttpServletResponse response) {
		Cookie cookie = new Cookie("cookieTest", "");
		cookie.setMaxAge(0);
		//cookie.setPath("/setCookie");
		response.addCookie(cookie);
		return null;
	}
}
