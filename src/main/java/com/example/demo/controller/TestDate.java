
package com.example.demo.controller;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**

* @author 作者 Your-Name: mx

* @version 创建时间：2019年7月11日 下午5:38:24

* 类说明

*/

public class TestDate {
	private static Logger log = LoggerFactory.getLogger(TestDate.class);

    private int count = 0;
//main方法先注释了
/*    public static void main(String[] args) {
    	//不配置日志也是会打印的
     	log.info("11");
        TestDate testDate = new TestDate();
        testDate.test1();
    }*/

    public void test1(){
        Date date = new Date();
        String name1 = "wangerbei";
        test2(date,name1);
        System.out.println(date+name1);
    }

    public void test2(Date dateP,String name2){
        dateP = null;
        name2 = "zhangsan";
    }

    public void test3(){
        count++;
    }

    public void  test4(){
        int a = 0;
        {
            int b = 0;
            b = a+1;
        }
        int c = a+1;
    }
}