
package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**

* @author 作者 Your-Name: mx

* @version 创建时间：2019年10月9日 上午10:38:06

* 类说明	测试从数据库直接返回实体，数据库没有那个字段但是属性里面有，是不会报错的，值会是默认值

*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmpNew {
	public int id;
	public  String emp_name;
	public int dept_id;
	public String test;
	public int a;
}
