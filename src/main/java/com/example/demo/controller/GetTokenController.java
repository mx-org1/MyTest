
package com.example.demo.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.config.TokenConfig;

/**

* @author 作者 Your-Name: mx

* @version 创建时间：2019年7月22日 上午10:57:15

* 类说明

*/
@Controller
public class GetTokenController {



	//http://localhost:8888/getToken?username=mx
	@RequestMapping("getToken")
	public String getToken(@RequestParam String username,Model model,HttpServletRequest request) {
		String token = TokenConfig.getToken(username);
		model.addAttribute("token", token);
		//要看到ip不能用localhost
		 String ip = request.getRemoteAddr();
	 		System.out.println("ip:"+ip);
	 		String headerIP = request.getHeader("x-real-ip");
	 		if(headerIP == null || "".equals(headerIP) || "null".equals(headerIP)){
	 			headerIP = request.getHeader("x-forwarded-for");
	 		}
	 		System.out.println("headerIP:"+headerIP);
		return "token";

	}

	//eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJteCIsInVzZXJuYW1lIjoibXgiLCJpYXQiOjE1NjM3NjQ5NTYsImV4cCI6MTU2Mzg1MTM1Nn0.qX1b6tFaSTmv89u5-zcYXrsjaYwuFkneO6KEGfdGIpQ
	@ResponseBody
	@RequestMapping("checkToken")
	public void checkToken(@RequestParam String token) {
		 try {
			TokenConfig.checkToken(token);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@ResponseBody
	@RequestMapping("getTokenFromStorage")
	public String getTokenFromStorage(HttpServletRequest request) {
		String header = request.getHeader("Access-Token");
		System.out.println("header:"+header);
		return header;

}
}