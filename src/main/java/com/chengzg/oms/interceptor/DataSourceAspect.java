/**
 * 
 */
package com.chengzg.oms.interceptor;

import com.chengzg.oms.common.DataSource;
import com.chengzg.oms.common.DynamicDataSourceHolder;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;


/**
 * 通过切面动态配置数据源
 * @author liuys
 * 2015年8月11日
 */
public class DataSourceAspect {
	
 private static Logger logger = Logger.getLogger(DataSourceAspect.class);
	
	public void before(JoinPoint point)
    {
		//logger.info("--111--"+point.toString());
        Object target = point.getTarget();
        String method = point.getSignature().getName();
        Class<?>[] classz = target.getClass().getInterfaces();
        Signature sign=point.getSignature();
        MethodSignature msgin=(MethodSignature) sign;
        Class<?>[] parameterTypes = msgin.getMethod().getParameterTypes();
        try {
            Method m = classz[0].getMethod(method, parameterTypes);
            if (m != null && m.isAnnotationPresent(DataSource.class)) {
            	DataSource data = m
                        .getAnnotation(DataSource.class);
                DynamicDataSourceHolder.putDataSource(data.value());
            } else {
                DynamicDataSourceHolder.putDataSource("master");
            }
            
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
