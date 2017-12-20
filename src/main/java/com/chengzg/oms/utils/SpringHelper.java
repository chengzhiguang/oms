package com.chengzg.oms.utils;

import org.springframework.web.context.WebApplicationContext;

public class SpringHelper {

	/**
	 * 获取spring依赖注入的对象
	 * 
	 * @param name
	 * @return Object Bean
	 */
	public static Object getBean(String name) {
		WebApplicationContext ctx = AppContext.getInstance().getAppContext();
		return ctx.getBean(name);
	}
	
}
