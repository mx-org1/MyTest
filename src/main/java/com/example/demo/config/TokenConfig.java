
package com.example.demo.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import java.util.Date;
/**

* @author 作者 Your-Name: mx

* @version 创建时间：2019年7月22日 上午10:53:25

* 类说明

*/
public class TokenConfig {
	final static String base64EncodedSecretKey = "base64EncodedSecretKey";//私钥
    final static long TOKEN_EXP = 1000 * 60 * 60 *2;//过期时间, 1000 * 60 * 60 *2存活时间俩小时
    //final static long TOKEN_EXP = 10*1000;// 10s测试使用10秒

    public static String getToken(String userName) {
    	return Jwts.builder()
                .setSubject(userName)
                .claim("username", userName)
                //这里是可以添加多个信息的，解析返回的是Claims，这个接口继承了Map
                //.claim("Id", 1)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXP)) /*过期时间*/
                .signWith(SignatureAlgorithm.HS256, base64EncodedSecretKey)
                .compact();
    }

    //解析token
    public static void checkToken(String token) throws ServletException {
        try {
        	//从token中解析到的claims=={sub=mx11, username=mx11, Id=1, iat=1570845097, exp=1570852297}
            final Claims claims = Jwts.parser().setSigningKey(base64EncodedSecretKey).parseClaimsJws(token).getBody();
            System.out.println("从token中解析到的claims=="+claims);
            String username= (String) claims.get("username");
            System.out.println("username=="+username);



        } catch (ExpiredJwtException e1) {
            throw new ServletException("token expired");
        } catch (Exception e) {
            throw new ServletException("other token exception");
        }
    }

    /*
     * 获取 Token 中注册信息，但是token过期了是会报错的返回的还是null
     */
    public static Claims getTokenClaim (String token) {
        try {
            return Jwts.parser().setSigningKey(base64EncodedSecretKey).parseClaimsJws(token).getBody();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    /*
     * Token 是否过期验证
     */
    public static boolean isTokenExpired (Date expirationTime) {
        return expirationTime.before(new Date());
    }
}
