
package com.example.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.mapper.EmpDao;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**

* @author 作者 Your-Name: mx

* @version 创建时间：2019年7月24日 上午11:34:44

* 类说明

*/
@Controller
public class PageHelperController {
	@Autowired
	EmpDao empDao;

	 @GetMapping("/getAllPerson")
	    public String getAllPerson(Model model,@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum){
	        PageHelper.startPage(pageNum,5);
	         List<Map<String, Object>> list = empDao.getList();
	        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(list);
	        System.out.println("pageInfo111"+pageInfo.getList());
	        model.addAttribute("pageInfo",pageInfo);
	        return "pageHelper";
	    }
}
