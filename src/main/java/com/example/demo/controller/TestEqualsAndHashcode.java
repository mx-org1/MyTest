
package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

/**

* @author 作者 Your-Name: mx

* @version 创建时间：2019年8月13日 上午10:30:05

* 类说明 ：测试equals和hashcode
关于hashcode的规定：1.两个对象相等，hashcode一定相等，
					2.hashcode相等，俩对象不一定相等（相等为用equals比较）
*/

public class TestEqualsAndHashcode {
	String add;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((add == null) ? 0 : add.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TestEqualsAndHashcode other = (TestEqualsAndHashcode) obj;
		if (add == null) {
			if (other.add != null)
				return false;
		} else if (!add.equals(other.add))
			return false;
		return true;
	}

	public static void main(String[] args) {
		Map<TestEqualsAndHashcode,Object> map=new HashMap<TestEqualsAndHashcode,Object>();
		TestEqualsAndHashcode testEqualsAndHashcode=new TestEqualsAndHashcode("a");
		TestEqualsAndHashcode testEqualsAndHashcode1=new TestEqualsAndHashcode("a");
		map.put(testEqualsAndHashcode, "1");
		map.put(testEqualsAndHashcode1, "2");
		System.out.println(map.size());

	}

	public TestEqualsAndHashcode(String add) {
		super();
		this.add = add;
	}

}
