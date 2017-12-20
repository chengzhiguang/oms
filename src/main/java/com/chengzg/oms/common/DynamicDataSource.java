/**
 * 
 */
package com.chengzg.oms.common;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 配置读取数据库的数据源
 * @author chengzg
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource#determineCurrentLookupKey()
	 */
	@Override
	protected Object determineCurrentLookupKey() {
		String source = DynamicDataSourceHolder.getDataSouce();
		// TODO Auto-generated method stub
		return source;
	}

}
