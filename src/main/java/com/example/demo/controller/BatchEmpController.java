
package com.example.demo.controller;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.entity.Emp;
import com.example.demo.mapper.EmpDao;

import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author 作者 Your-Name: mx
 *
 * @version 创建时间：2019年7月9日 下午3:53:28
 *
 *          类说明
 *
 */

@Slf4j
@Controller
public class BatchEmpController {

	@Autowired
	EmpDao empDao;

	@ResponseBody
	@RequestMapping("batchInsert")
	public void BatchInsert() {
		log.info("批量插入");
		List<Emp> empList = new ArrayList<Emp>();
		Emp emp ;
		for (int i = 0; i < 10; i++) {
			emp = new Emp();
			emp.setEmp_name("mx" + i);
			emp.setDept_id(23124);
			empList.add(emp);
		}
		System.out.println(empList);
		empDao.getList();
		try {
			int batchInsert = empDao.batchInsert(empList);
			System.out.println(batchInsert);
		} catch (Exception e) {
				System.out.println("批量插入失败");
		}
	/*	<foreach collection="list" separator="," item="emp"
				index="index">
				(null,#{emp.emp_name},#{emp.dept_id})
			</foreach>*/

		// Emp[] emp=new Emp[] {};
	}

	@ResponseBody
	@RequestMapping("batchUpdate")
	public void BatchUpdate() {
		log.info("批量更新");
		// 用数组创建了多个对象，多个引用
		/*List<Emp> empList1 = new ArrayList<Emp>();
		Emp[] empArray = new Emp[1000000];
		long begin = System.currentTimeMillis();
		for (int i = 0; i < 1000000; i++) {
			empArray[i] = new Emp();
			empArray[i].setId(i);
			empArray[i].setEmp_name("mx" + i);
			empArray[i].setDept_id(111);
			empList1.add(i, empArray[i]);
		}
		long end = System.currentTimeMillis();
		long time = end - begin;
		System.out.println("begin   " + begin);
		System.out.println("end   " + end);
		System.out.println("数组消耗时间为" + time);*/

		//循环内创建对象和循环外声明循环内创造对象效率是一样的
		//arraylist和linkedlist在最后添加元素时间arraylist效率低于linkedlist
		List empList = new ArrayList();
		long begin1 = System.currentTimeMillis();
		for (int i = 0; i < 10; i++) {
			Emp emp = new Emp();
			emp.setId(i);
			emp.setEmp_name("mx" + i);
			emp.setDept_id(111);
			empList.add(i, emp);
		}
		int batchUpdate = empDao.batchUpdate(empList);
		try {

		} catch (Exception e) {
			log.error("批量更新错了");
		}
		/*long end1 = System.currentTimeMillis();
		long time1 = end1 - begin1;
		System.out.println("begin1    " + begin1);
		System.out.println("end1   " + end1);
		System.out.println("消耗时间为" + time1);

		List empList2 = new LinkedList();
		long begin2 = System.currentTimeMillis();
		Emp emp;
		for (int i = 0; i < 1000000; i++) {
			emp = new Emp();
			emp.setId(i);
			emp.setEmp_name("mx" + i);
			emp.setDept_id(111);

		}
		long end2 = System.currentTimeMillis();
		long time2 = end2 - begin2;
		System.out.println("begin2    " + begin2);
		System.out.println("end2   " + end2);
		System.out.println("消耗时间为LinkedList" + time2);*/




	}
	/**
	 * list中add会涉及到元素的移动
	 *
	 */
	@Test
	public void test() {
		List<Integer> empList111 = new ArrayList<>();
		empList111.add(0,1);
		empList111.add(1,2);
		empList111.add(2,3);
		empList111.add(0, 111);
		//set的返回值为原来这个位置的值
		Integer set = empList111.set(0,1);
		System.out.println(set);
		System.out.println(empList111);

		/*
		 * 1.
		 * 分别insert200000条数据到linkedlist和arraylist用add加索引方法
		 * 由于是在末尾插入数据，arraylist的速度比linkedlist的速度反而要慢      
		 */
		List<String> empList = new ArrayList<String>();
		List<String> empLinkedList = new LinkedList<String>();
		long begin1 = System.currentTimeMillis();
		for(int i=0;i<200000;i++) {
			empList.add(i, "emplist"+i);
		}
		long end1 = System.currentTimeMillis();
		System.out.println("ArrayList从0条添加到二十万条数据（用add加索引的方法）添加消耗时间为"+(end1-begin1));
		long begin2 = System.currentTimeMillis();
		for(int i=0;i<200000;i++) {
			empLinkedList.add(i, "emplist"+i);
		}
		long end2 = System.currentTimeMillis();
		System.out.println("linkedList从0条添加到二十万条数据（用add加索引的方法）添加消耗时间为"+(end2-begin2));


		/**
		 * 2.
		 * 分别insert200000条数据到linkedlist和arraylist直接用add方法效率arraylist略高于linkedList
		 *
		 */
		List<String> empList1 = new ArrayList<String>();
		List<String> empLinkedList1 = new LinkedList<String>();
		long begin11 = System.currentTimeMillis();
		for(int i=0;i<200000;i++) {
			empList1.add("emplist"+i);
		}
		long end11 = System.currentTimeMillis();
		System.out.println("ArrayList从0条添加到二十万条数据（直接用add方法）添加消耗时间为"+(end11-begin11));
		long begin22 = System.currentTimeMillis();
		for(int i=0;i<200000;i++) {
			empLinkedList1.add("emplist"+i);
		}
		long end22 = System.currentTimeMillis();
		System.out.println("linkedList从0条添加到二十万条数据（直接用add方法）添加消耗时间为"+(end22-begin22));

		/**
		 * 3.
		 * 从末尾开始删除  ArrayList效率高于linkedlist
		 */
	/*	long begin3 = System.currentTimeMillis();
		for(int i=199999;i>=0;i--) {
			empList.remove(i);
		}
		long end3 = System.currentTimeMillis();
		System.out.println("ArrayList从末未开始删除消耗时间为"+(end3-begin3));
		long begin4 = System.currentTimeMillis();
		for(int i=199999;i>=0;i--) {
			empLinkedList.remove(i);
		}
		long end4 = System.currentTimeMillis();
		System.out.println("linkedList从末未开始删除消耗时间为"+(end4-begin4));*/

		/**
		 * 4.
		 * 从1000条开始删除两个list消耗时间都为0
		 */
		/*
		long begin3 = System.currentTimeMillis();
		for(int i=1000;i>=200000;i++) {
			empList.remove(i);
		}
		long end3 = System.currentTimeMillis();
		System.out.println("ArrayList从1000条开始删除消耗时间为"+(end3-begin3));
		long begin4 = System.currentTimeMillis();
		for(int i=1000;i>=200000;i++) {
			empLinkedList.remove(i);
		}
		long end4 = System.currentTimeMillis();
		System.out.println("linkedList从1000条开始删除消耗时间为"+(end4-begin4));*/

		/**
		 * 随机查找的话ArrayList时间都为0，LinkedList时间不为0
		 */
		/*long begin4 = System.currentTimeMillis();
		empList.get(100000);
		long end4 = System.currentTimeMillis();
		System.out.println("ArrayList随机查找第十万条时间为"+(end4-begin4));

		long begin5 = System.currentTimeMillis();
		empLinkedList.get(100000);
		long end5 = System.currentTimeMillis();
		System.out.println("LinkedList随机查找第十万条时间为"+(end5-begin5));
*/
		/**
		 * 5.
		 *在二十万条数据前添加20000条数据LinkedList效率远大于ArrayList
		 */
		long begin6 = System.currentTimeMillis();
		for (int i = 0; i < 20000; i++) {
			empList.add(i,null);
		}
		long end6 = System.currentTimeMillis();
		System.out.println("ArrayList在二十万条数据前添加2000条数据时间为"+(end6-begin6));

		long begin7 = System.currentTimeMillis();
		for (int i = 0; i < 20000; i++) {
			empLinkedList.add(i,null);
		}
		long end7 = System.currentTimeMillis();
		System.out.println("LinkedList在二十万条数据前添加2000条数据时间为"+(end7-begin7));



		/**
		 * 6.
		 *在二十万条数据后添加2000000条数据ArrayList效率远大于LinkedList
		 */
		/*long begin8 = System.currentTimeMillis();
		for (int i = 0; i < 2000000; i++) {
			empList.add(null);
		}
		long end8 = System.currentTimeMillis();
		System.out.println("ArrayList在二十万条数据后添加2000000条数据时间为"+(end8-begin8));

		long begin9 = System.currentTimeMillis();
		for (int i = 0; i < 2000000; i++) {
			empLinkedList.add(null);
		}
		long end9 = System.currentTimeMillis();
		System.out.println("LinkedList在二十万条数据后添加2000000条数据时间为"+(end9-begin9));*/

		/**
		 * 7.
		 * 到这里时数据有220000条，向第210000条加数据时，第210000条后的元素会在后面
		 * 效率一个308一个9486,相差太远了，没想出来原因
		 */
		long begin8 = System.currentTimeMillis();
		System.out.println(empList.size());
		System.out.println(empLinkedList.size());
		for (int i =  200000; i < 400000; i++) {
			empList.add(i,null);
		}
		long end8 = System.currentTimeMillis();
		System.out.println("ArrayList在第二十万条后插入二十万条数据时间为"+(end8-begin8));

		long begin9 = System.currentTimeMillis();
		for (int i =  200000; i < 400000; i++) {
			empLinkedList.add(i,null);
		}
		long end9 = System.currentTimeMillis();
		System.out.println("LinkedList在第二十万条后插入二十万条数据时间为"+(end9-begin9));
		//8.查找操作indexOf,lastIndexOf,contains等，两者差不多。
	}


}
