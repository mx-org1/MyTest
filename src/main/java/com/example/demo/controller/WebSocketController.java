
package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**

* @author 作者 Your-Name: mx

* @version 创建时间：2019年7月12日 下午4:54:08

* 类说明

*/
@Controller
public class WebSocketController {

	@RequestMapping("toWebSocket")
	public String toWebSocket() {
		return "webSocket";
	}


}
