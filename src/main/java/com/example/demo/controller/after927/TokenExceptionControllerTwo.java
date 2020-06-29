
package com.example.demo.controller.after927;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

/**

* @author 作者 Your-Name: mx

* @version 创建时间：2019年10月12日 下午4:37:28

* 类说明

*/
//@Configuration
public class TokenExceptionControllerTwo implements HandlerExceptionResolver{
	    @Override
	    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse response, Object o, Exception e) {
	        ModelAndView mv = new ModelAndView(new MappingJackson2JsonView());
	        Map<String, String> map = new HashMap<>();
	        map.put("status", "error");
	       /* if (e instanceof DataIntegrityViolationException) {
	            map.put("msg", "该角色尚有关联的资源或用户，删除失败!");
	        }*/
	        mv.addAllObjects(map);
	        return mv;
	    }

}