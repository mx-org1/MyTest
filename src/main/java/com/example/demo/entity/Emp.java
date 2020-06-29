
package com.example.demo.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**

* @author 作者 Your-Name: mx

* @version 创建时间：2019年7月9日 下午4:11:40

* 类说明

*/
@Slf4j
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Emp implements Serializable{

	private int id;
	private  String emp_name;
	private int dept_id;
	public void testLog() {
		log.info("测试lombok的日志打印");
	}
}
