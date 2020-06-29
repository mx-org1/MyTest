
package com.example.demo.controller.after927;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.util.HttpUtil;

/**

* @author 作者 Your-Name: mx

* @version 创建时间：2019年10月24日 下午4:38:06

* 类说明：github第三方登录，第一步githubLogin方法跳转到github授权页面，
* 第二步根据授权用户跳转到githubCallback，这个方法到github拿token，通过token到github拿到不同授权用户的授权信息了，根据信息进行处理
https://blog.csdn.net/qq_40147863/article/details/90546754
*/
@Controller
@RequestMapping("/github")
public class GithubController {

		@RequestMapping(value = "/login")
	    public String githubLogin() {
			//感觉state没有什么意义，传什么就返回什么
	        String githubState = "adgasgdsdhgi";
	        return "redirect:https://github.com/login/oauth/authorize?client_id=bb8db33cd1da51a1b594&redirect_uri=http://localhost:8888/github/callback&state=" + githubState;
	    }

		@RequestMapping(value = "/callback", method = RequestMethod.GET)
	    @ResponseBody
	    public String githubCallback(String code, String state) {
	        System.out.println("==>state:" + state);
	        System.out.println("==>code:" + code);

	        // Step2：通过 Authorization Code 获取 AccessToken
	        String githubAccessTokenResult = HttpUtil.sendGet("https://github.com/login/oauth/access_token",
	                "client_id=bb8db33cd1da51a1b594&client_secret=9d42af3950f9b0ac076f442e2c5969559fee5a70&code=" + code +
	                        "&redirect_uri=http://localhost:8888/github/callback");

	        System.out.println("==>githubAccessTokenResult: " + githubAccessTokenResult);

	        /**
	         * 以 & 为分割字符分割 result
	         */
	        String[] githubResultList = githubAccessTokenResult.split("&");
	        List<String> params = new ArrayList<>();

	        // paramMap 是分割后得到的参数对, 比说 access_token=ad5f4as6gfads4as98fg
	        for (String paramMap : githubResultList) {
	            if (!"scope".equals(paramMap.split("=")[0])){
	                // 再以 = 为分割字符分割, 并加到 params 中
	                params.add(paramMap.split("=")[1]);
	                "abc".compareTo("abb");
	                
	            }
	        }

	        //此时 params.get(0) 为 access_token;  params.get(1) 为 token_type

	        // Step2：通过 access_token 获取用户信息
	        String githubInfoResult = HttpUtil.sendGet("https://api.github.com/user","access_token="+params.get(0));

	        return githubInfoResult;
	    }

}
