package com.chengzg.oms.interceptor;


import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 类说明   : controller性能分析
 * 作者        :liuys
 * 日期        :2015年8月17日下午7:22:50
 * 版本号    : V1.0
 */

public class PerformenceMonitor  {

	private static Logger logger = LoggerFactory.getLogger(PerformenceMonitor.class);
	
	
	public Object aroundMethod(ProceedingJoinPoint pjd) throws Throwable{
    {
    	long begin = System.currentTimeMillis();
    	Object o = pjd.proceed();
		long end = System.currentTimeMillis();
		if(pjd.getTarget().getClass().getInterfaces().length==1){
			logger.info(pjd.getTarget().getClass().getInterfaces()[0].getName()+"."+pjd.getSignature().getName()+"总共执行花费时间为:"+(end-begin)+"毫秒");  
		}else{
			logger.info(pjd.getTarget().getClass()+"."+pjd.getSignature().getName()+"总共执行花费时间为:"+(end-begin)+"毫秒");  
		}
		
		
		return o;
    }
	}
	}
