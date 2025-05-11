//package com.app.trys.aop;
//
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.aspectj.lang.annotation.Pointcut;
//import org.springframework.stereotype.Component;
//
//import java.util.Arrays;
//
//@Aspect
//@Component
//public class LogAspects {
//	@Pointcut("within(com.app.trys.init.impl..*)")
//	public void pointCut() {
//	}
//
//	@Before("pointCut()")
//	public void logStart(JoinPoint joinPoint){
//		Object[] args = joinPoint.getArgs();
//		System.out.println(joinPoint.getSignature().getName()+" 运行。。。@Before "+ Arrays.asList(args));
//	}
//}