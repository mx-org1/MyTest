
package com.example.demo.exception;

/** 

* @author 作者 Your-Name: mx

* @version 创建时间：2019年10月12日 下午3:50:54 

* 类说明 

*/

public class TokenExpiredException extends RuntimeException{
	public TokenExpiredException() {
		super("token过期了");
	}
}
