
package com.example.demo.exception;

/**

* @author 作者 Your-Name: mx

* @version 创建时间：2019年10月12日 下午4:15:48

* 类说明

*/

public class NoTokenException extends Exception{
	int code=500;
	public NoTokenException() {
		super("header里面没有token");
	}
}
