
package com.example.demo.controller.after927;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.service.AsyncService;

/**

* @author 作者 Your-Name: mx

* @version 创建时间：2019年10月30日 下午1:57:50

* 类说明

*/
@Controller
public class ThreadPoolController {

	@Autowired
	private AsyncService asyncService;

	@GetMapping("/async")
	@ResponseBody
	public void async(){
	    asyncService.executeAsync();
	}
}
