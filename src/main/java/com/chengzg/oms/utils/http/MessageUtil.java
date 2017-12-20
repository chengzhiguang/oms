package com.chengzg.oms.utils.http;


import com.chengzg.oms.utils.http.bean.HttpPoster;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;

/**
 * 消息工具类
 * 
 * @author chengzg
 * 
 */
public class MessageUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(MessageUtil.class);

	public static String getTransmessageStr(String requrl) {

		String result = null;

		if (requrl == null) {
			logger.info("http 请求 地址 为 null");
			return result;
		}
		logger.info("请求url="+requrl);
		String strjson = null;

		try {
			strjson = HttpPoster.get(requrl);
		} catch (Exception e) {
			logger.debug(e.getMessage());
		}
		logger.info("getTransmessage HTTP 请求返回数据位[" + strjson + "]");
		if (strjson == null) {
			logger.info("http 请求 返回json 为 null");
			return result;
		}
		logger.info("http 请求 返回json 为 " + strjson);
		result = strjson;

		
		return result;
	}
	
	public static String getTransmessageJson(String requrl) {

		String result = null;

		if (requrl == null) {
			logger.info("http 请求 地址 为 null");
			return result;
		}
		logger.info("请求url="+requrl);
		String strjson = null;

		try {
			strjson = HttpPoster.get(requrl, "application/json", "UTF-8", true, null);
		} catch (Exception e) {
			logger.debug(e.getMessage());
		}
		logger.info("getTransmessage HTTP 请求返回数据位[" + strjson + "]");
		if (strjson == null) {
			logger.info("http 请求 返回json 为 null");
			return result;
		}
		logger.info("http 请求 返回json 为 " + strjson);
		result = strjson;

		
		return result;
	}
	
	
	/**
	 * 获取信息
	 * @param requrl 请求地址
	 * @param json post 内容
	 * @param resultKey 返回值中要解析展示部分的key 例{"result":1,"brand":[{中的brand为要解析的值
	 * @return
	 */
	public static String postTransmessageStr(String requrl, String str) {
		String result = null;
		if (requrl == null) {
			logger.debug("http 请求 地址 为 null");
			return result;
		}
		logger.debug("请求url="+requrl);
		String strjson = null;

		try {
			strjson = HttpPoster.postWithRes(requrl, str);
		} catch (Exception e) {
			logger.error("postTransmessageStr 异常", e);
		}

		if (strjson == null) {
			logger.debug("http 请求 返回json 为 null");
			return result;
		}
		result = strjson;
		return result;
	}
	
	/**
	 * 获取信息
	 * @param requrl 请求地址
	 * @param json post 内容
	 * @param resultKey 返回值中要解析展示部分的key 例{"result":1,"brand":[{中的brand为要解析的值
	 * @return
	 */
	public static String postTransmessageJson(String requrl, String str) {
		String result = null;
		if (requrl == null) {
			logger.debug("http 请求 地址 为 null");
			return result;
		}
		logger.debug("请求url="+requrl);
		String strjson = null;

		try {
			strjson = HttpPoster.postWithResJson(requrl, str);
		} catch (Exception e) {
			logger.error("postTransmessageStr 异常", e);
		}

		if (strjson == null) {
			logger.debug("http 请求 返回json 为 null");
			return result;
		}
		result = strjson;
		return result;
	}

	/**
	 * 获取ams网站接口参数
	 * @param map 参数列表
	 * @param isSign 是否加密
	 * @param signValue 加密的value
	 * @return
	 */
	public static String getParameter(Map<String, Object> map) {
		try {
			String result = "";
			if (map == null) {
				return result;
			}
			
			Set<String> keys = map.keySet();
			for (String key : keys) {
				if(map.get(key) != null) {
					if(result != null && !"".equals(result)) {
						result = result + "&";
					}
					result = result + "" + key +"=" + map.get(key);
				}
			}
			return result;
		} catch (Exception e) {

		}
		return null;
		
	}

}
