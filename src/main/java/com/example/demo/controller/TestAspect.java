
package com.example.demo.controller;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.example.demo.exception.TokenErrorException;

import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author 作者 Your-Name: mx
 *
 * @version 创建时间：2019年7月19日 下午5:17:29
 *
 *          类说明：https://blog.csdn.net/u010096717/article/details/82221263
 *
 */

@Aspect
@Component
@Slf4j
public class TestAspect {

	// com.kzj.kzj_rabbitmq.controller 包中所有的类的所有方法切面
	// @Pointcut("execution(public * com.kzj.kzj_rabbitmq.controller.*.*(..))")

	// 只针对 MessageController 类切面
	// @Pointcut("execution(public *
	// com.kzj.kzj_rabbitmq.controller.MessageController.*(..))")

	// 统一切点,对com.kzj.kzj_rabbitmq.controller及其子包中所有的类的所有方法切面
	@Pointcut("execution(public * com.example.demo.controller..*.*(..))")
	public void Pointcut() {
	}

	// 前置通知
	@Before("Pointcut()")
	public void beforeMethod(JoinPoint joinPoint) {
		log.info("调用了前置通知");
		  // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 记录下请求内容
        log.info("URL : " + request.getRequestURL().toString());
        log.info("HTTP_METHOD : " + request.getMethod());
        log.info("IP : " + request.getRemoteAddr());
        log.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        log.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));
	}

	// @After: 后置通知
	@After("Pointcut()")
	public void afterMethod(JoinPoint joinPoint) {
		log.info("调用了后置通知");
	}

	// @AfterRunning: 返回通知 rsult为返回内容
	/**
	 * 后置返回通知 这里需要注意的是: 如果参数中的第一个参数为JoinPoint，则第二个参数为返回值的信息
	 * 如果参数中的第一个参数不为JoinPoint，则第一个参数为returning中对应的参数
	 * returning：限定了只有目标方法返回值与通知方法相应参数类型时才能执行后置返回通知，否则不执行，
	 * 对于returning对应的通知方法参数为Object类型将匹配任何目标返回值
	 *
	 * @param joinPoint
	 * @param keys
	 */
	@AfterReturning(value = "Pointcut()", returning = "result")
	public void afterReturningMethod(JoinPoint joinPoint, Object result) {
		log.info("调用了返回通知");
	}

	/**
	 * 后置异常通知
	 *  定义一个名字，该名字用于匹配通知实现方法的一个参数名，当目标方法抛出异常返回后，将把目标方法抛出的异常传给通知方法；
	 *  throwing:限定了只有目标方法抛出的异常与通知方法相应参数异常类型时才能执行后置异常通知，否则不执行，
	 *           对于throwing对应的通知方法参数为Throwable类型将匹配任何异常。
	 * @param joinPoint
	 * @param exception
	 * 原文链接：https://blog.csdn.net/lmb55/article/details/82470388
	 */
	@AfterThrowing(value = "Pointcut()", throwing = "e")
	public void afterReturningMethod(JoinPoint joinPoint, Exception e) {
		log.info("调用了异常通知");
	}

	// @Around：环绕通知
	@Around("Pointcut()")
	public Object Around(ProceedingJoinPoint pjp) throws Throwable {
		log.info("around执行方法之前");
		Object object = pjp.proceed();
		log.info("around执行方法之后--返回值：" + object);
		return object;
	}

}