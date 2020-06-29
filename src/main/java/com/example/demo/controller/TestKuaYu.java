
package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**

* @author 作者 Your-Name: mx

* @version 创建时间：2019年9月16日 下午12:38:23

* 类说明

*/
@Controller
@RequestMapping("/kuaYu")
public class TestKuaYu {

	@RequestMapping("/home")
	@ResponseBody
	public Object  homeMes() {
		User user =new User();
		user.name="mx";
		user.age=18;
		return user;
	}
}
