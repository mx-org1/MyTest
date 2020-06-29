
package com.example.demo.controller;

import java.math.BigDecimal;

import org.junit.Test;

/**

* @author 作者 Your-Name: mx

* @version 创建时间：2019年8月16日 下午3:48:04

* 类说明

*/

public class BigDecimalController {

	@Test
	public void compute() {
		double num1=0.01;
		double num2=0.03;
		double num3=num2-num1;
		double d=0.777;
		double d1=-0.777;
		long round = Math.round(d);
		long round1 = Math.round(d1);
		System.out.println("0.77用math的round方法结果为"+round);
		System.out.println("0.77用math的round方法结果为"+round1);
		System.out.println("double类型的（0.03-0.01=）"+num3);
		BigDecimal decimal=new BigDecimal("3");
		BigDecimal decimal1=new BigDecimal("1");
		//除法	四舍五入，碰五进位
		BigDecimal divide = decimal1.divide(decimal,2, BigDecimal.ROUND_HALF_UP);
		System.out.println("除法1/3="+divide);
		//加法
		BigDecimal add = decimal.add(decimal1);
		System.out.println(add);
		//减法
		BigDecimal decima2=new BigDecimal("0.03");
		BigDecimal decima3 = new BigDecimal("0.01");
		BigDecimal subtract = decima2.subtract(decima3);
		System.out.println("0.03-0.01="+subtract);
		//乘法
		BigDecimal decima4 = new BigDecimal("2");
		BigDecimal decima5 = new BigDecimal("2");
		BigDecimal multiply = decima4.multiply(decima5);
		System.out.println("2*2="+multiply);
		//四舍五入，碰到五进位
		BigDecimal decima6 = new BigDecimal("0.777777");
		BigDecimal setScale = decima6.setScale(2,BigDecimal.ROUND_HALF_UP);
		System.out.println(setScale);
		BigDecimal decima7 = new BigDecimal("1.22");
		System.out.println(decima7);
		double d11=1.22;
		//初始化时最好不要用double创建，用字符串创建
		BigDecimal bigDecimal = new BigDecimal(d11);
		System.out.println("1.22用double创建则为"+bigDecimal);
		//比较的时候用compareto

	}



}
