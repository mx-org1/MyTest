
package com.example.demo.exception;

/**

* @author 作者 Your-Name: mx

* @version 创建时间：2019年10月12日 下午3:50:54

* 类说明

*/

public class TokenErrorException extends RuntimeException{
	int code=500;
	public TokenErrorException() {
		super("token不正确或者token已过期");
	}
}
