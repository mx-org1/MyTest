
package com.example.demo.controller.after927;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.entity.TreeNode;
import com.example.demo.exception.TokenErrorException;
import com.example.demo.service.PowerService;

/**

* @author 作者 Your-Name: mx

* @version 创建时间：2019年10月9日 下午12:51:32

* 类说明

*/
@Controller
@RequestMapping("/power")
public class PowerController {

	@Autowired
	PowerService powerService;

	@ResponseBody
	@RequestMapping("/getListByUserId")
	public Object getListByUserId(HttpServletRequest request,HttpServletResponse response) throws Exception {
		//响应设置状态码，再用到异常同一管理，前端接收到401的状态码，也能看到返回来的错误信息（江南一点雨的异常处理）
		//response.setStatus(401);
		 String header = request.getHeader("token");
		 System.out.println("header:"+header);
		List<TreeNode> backPowerList = powerService.getBackPowerList(1);
		 //throw new TokenErrorException();
		return backPowerList;

	}


	@ResponseBody
	@RequestMapping("/getPowerIdsByUserId")
	public Object getPowerIdsByUserId(HttpServletRequest request,HttpServletResponse response,Integer id) throws Exception {
		List<Integer> powerIds = powerService.getPowerIdsByUserId(id);
		return powerIds;

	}
	/**
	 * 获取所有权限
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getAllPowerList")
	public Object getAllPowerList() {
		List<TreeNode> backPowerList = powerService.getAllBackList();
		return backPowerList;
	}
	/**
	 * 更新角色权限
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/updatePowerById")
	public Object updatePowerById(int id,int[] checkedKeys) {
		System.out.println("id:"+id+"   checkedKeys:"+Arrays.toString(checkedKeys));
		powerService.updatePowerById(checkedKeys,id);
		return null;
	}

	@ResponseBody
	@RequestMapping("/test")
	public Object test(HttpServletRequest request,HttpServletResponse response,String ids ) throws Exception {
		System.out.println("ids："+ids);
		return ids;

	}


}
