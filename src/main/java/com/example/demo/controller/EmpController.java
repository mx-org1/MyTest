package com.example.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.example.demo.entity.Emp;
import com.example.demo.entity.EmpNew;
import com.example.demo.mapper.EmpDao;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("/emp")
public class EmpController {

	@Autowired
	private EmpDao empDao;

	@Autowired
	RedisTemplate redisTemplate;

	@Autowired
	StringRedisTemplate stringRedisTemplate;

	@ResponseBody()
	@RequestMapping("/list")
	public Emp getList() {
		ValueOperations<String, Emp> operations = redisTemplate.opsForValue();
		ValueOperations<String, Object> operations2 = redisTemplate.opsForValue();
		Emp emp = operations.get("emp:object");
		System.out.println(emp);
		//redis里面没有就放到redis里面，更新，删除，添加都先插入数据库，以数据库为准，redis放的值可以不准确，但是数据库要准确
		//if(emp==null) {
			Emp emp1=new Emp(1,"马勋",111);
			// int count = empDao.getCount();
			List<Map<String, Object>> list = empDao.getList();
			int size = list.size();
			String valueOf = String.valueOf(size);
			System.out.println("返回来的list"+list);
			 // 具体使用  放对象或者数组的话用json转成字符串或者用ValueOperations<String, Object> operations2 = redisTemplate.opsForValue();类似的
			System.out.println(valueOf);
			String jsonString = JSON.toJSONString(list);
			System.out.println("jsonString"+jsonString);
			List<Map<String,String>> list1=new ArrayList<Map<String,String>>();
			Map<String,String> map=new HashMap<String,String>();
			map.put("str1", "1111");
			map.put("str2", "abc");
			list1.add(map);
			System.out.println("list1"+list1);
			//value为数字也带引号了
			System.out.println("listString"+JSON.toJSONString(list1));
			JSONArray parseObject = JSON.parseArray(jsonString);
			System.out.println("parseObject"+parseObject);
			//value为数字没有带引号，"[{\"emp_name\":\"mx1\",\"id\":1,\"dept_id\":111},{\"emp_name\":\"mx2\",\"id\":2,\"dept_id\":111},{\"emp_name\":\"mx3\",\"id\":3,\"dept_id\":111},{\"emp_name\":\"mx\",\"id\":4,\"dept_id\":111},{\"emp_name\":\"mx\",\"id\":5,\"dept_id\":111},{\"emp_name\":\"mx\",\"id\":6,\"dept_id\":111},{\"emp_name\":\"mx\",\"id\":7,\"dept_id\":111}]"
			operations2.set("emp:jsonObjectArray", jsonString,60, TimeUnit.SECONDS);
			Object object = operations2.get("emp:jsonObjectArray");
			System.out.println(object+"..............");
			operations.set("emp:object", emp1, 5, TimeUnit.HOURS);
			operations2.set("emp:object", emp1, 5, TimeUnit.HOURS);

			List<String> mlist = new ArrayList<>();
		    mlist.add("zhu");
		    mlist.add("wen");
		    mlist.add("tao");
		    // List转成数组
		    String[] array = mlist.toArray(new String[0]);
		    // 输出数组
		    for (int i = 0; i < array.length; i++) {
		        System.out.println("array--> " + array[i]);
		    }

		    List<String> list111=new ArrayList<String>();
		    list111.add("aa");
		    list111.add("bb");

		    String[] targetArr=new String[list111.size()];
		    list111.toArray(targetArr);
		    for(String s:targetArr){
		        System.out.println(s);
		    }
	//	}

		return emp;

	}

	@ResponseBody()
	@RequestMapping("/test")
	public String test(@RequestParam String id) {
		System.out.println(id);
		return null;
	}
	/**
	 * 获得员工列表
	 * @return
	 */
	@ResponseBody()
	@RequestMapping("/getEmpList")
	public Object getEmpList(Integer pageNo,Integer pageSize,String input) {
		PageHelper.startPage(pageNo,pageSize);
		if(StringUtils.isNotEmpty(input)) {
			input="%"+input+"%";
		}
		List<EmpNew> empList = empDao.getEmpList(input);
		PageInfo<EmpNew> pageInfo = new PageInfo<EmpNew>(empList);
		return pageInfo;
	}
	/**
	 * 员工添加
	 * @param emp_name
	 * @param dept_id
	 * @return
	 */
	@ResponseBody()
	@RequestMapping("/addEmp")
	public String addEmp(String emp_name,@RequestParam(required=true,defaultValue="111") String dept_id) {
		empDao.addEmp(emp_name,dept_id);
		return "success";
	}

	@ResponseBody()
	@RequestMapping("/updateEmp")
	public String updateEmp(String id,String emp_name,@RequestParam(required=true,defaultValue="111") String dept_id) {
		int updateEmp = empDao.updateEmp(id,emp_name,dept_id);
		return "success";
	}
	//batchDeleteEmp
	/**
	 * 这个是单条删除
	 * @param id
	 * @param emp_name
	 * @param dept_id
	 * @return
	 */
	@ResponseBody()
	@RequestMapping("/deleteEmp")
	public String deleteEmp(String id) {
		empDao.deleteEmp(id);
		return "success";
	}

	@ResponseBody()
	@RequestMapping("/batchDeleteEmp")
	public String batchDeleteEmp(String ids) {
		String[] idsArray = ids.split(",");
		List<String> idsList=new ArrayList<String>();
		for (int i = 0; i < idsArray.length; i++) {
			idsList.add(idsArray[i]);
		}
		System.out.println("idsArray:"+idsArray);
		System.out.println("idsList:"+idsList);
		int batchDeleteEmp=empDao.batchDeleteEmp(idsList);
		return "success";
	}
}