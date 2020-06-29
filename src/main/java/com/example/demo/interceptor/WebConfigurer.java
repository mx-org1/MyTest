package com.example.demo.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfigurer implements WebMvcConfigurer {

    /*@Autowired
    private SSOInterceptor SSOInterceptor;
    */
    @Autowired
    private	TokenInterceptor tokenInterceptor;
    //貌似这里加这个也可以自己还没有测试
   /* @Override
    public void addCorsMappings(CorsRegistry registry) {
        //允许全部请求跨域
        registry.addMapping("/**");
    }*/

  // 这个方法是用来配置静态资源的，比如html，js，css，等等
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
	  //registry.addResourceHandler("/static/css/**");

  }

  // 这个方法用来注册拦截器，我们自己写好的拦截器需要通过这里添加注册才能生效
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
      // addPathPatterns("/**") 表示拦截所有的请求，
      // excludePathPatterns("/login", "/register") 表示除了登陆与注册之外，因为登陆注册不需要登陆也可以访问,还可以加排除静态文件"/css/**","/js/**"
     // registry.addInterceptor(tokenInterceptor).addPathPatterns("/**").excludePathPatterns("/login/**", "/register/**");
     // super.myHandlerInterceptor(registry);    //较新Spring Boot的版本中这里可以直接去掉，否则会报错
  }
}