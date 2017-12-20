package com.chengzg.oms.utils;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

public class AppContext {
	
	private static AppContext instance;

	private volatile WebApplicationContext appContext;
	
	public synchronized static AppContext getInstance() {
		if (instance == null) {
			instance = new AppContext();
		}
		return instance;
	}

	private AppContext() {
		this.appContext = ContextLoader.getCurrentWebApplicationContext();
	}

	public WebApplicationContext getAppContext() {
		return appContext;
	}
	
}
