package com.chengzg.oms.utils;

import com.chengzg.oms.exception.ServiceException;
import com.google.common.base.Strings;
import org.apache.log4j.Logger;

import java.util.Date;

//import javax.servlet.http.HttpServletRequest;

public class Utility {
	private static Logger logger = Logger.getLogger(Utility.class);
	
	/**
	 * 根据ip字符串获取相应的long
	 * @param ip
	 * @return
	 */
	public static Long getLongByIp(String ip) {
		try {
			String[] ips = ip.split("[.]");     
	        long num =  16777216L*Long.parseLong(ips[0]) + 65536L*Long.parseLong(ips[1]) + 256*Long.parseLong(ips[2]) + Long.parseLong(ips[3]);     
	        return num;
		} catch (Exception e) {
			logger.error("getLongByIp 异常：" + e.getMessage());
		}
		return null;
	}
	
	/**
	 * 根据ip的long获取ip字符串
	 * @param ipLong
	 * @return
	 */
	public static String getIpByLong(long ipLong) {  
		try {
			logger.info("getIpByLong Ip数字：" + ipLong);
			//long ipLong = 1037591503;     
	        long mask[] = {0x000000FF,0x0000FF00,0x00FF0000,0xFF000000};     
	        long num = 0;     
	        StringBuffer ipInfo = new StringBuffer();     
	        for(int i=0;i<4;i++){     
	            num = (ipLong & mask[i])>>(i*8);     
	            if(i>0) ipInfo.insert(0,".");     
	            ipInfo.insert(0,Long.toString(num,10));     
	        }     
	        logger.info("getIpByLong IP字符串：" + ipInfo.toString());
	        return ipInfo.toString();   
		} catch (Exception e) {
			logger.error("getIpByLong 异常" + e.getMessage());
		}
		return null;
          
    }  

	/**
	 * 获取一个唯一标示
	 * @return
	 */
	public static String getOnlySign() {
		try {
			String sign = java.util.UUID.randomUUID().toString();
			logger.info("getOnlySign 得到的标示：" + sign.toUpperCase());
			return sign.toUpperCase();
		} catch (Exception e) {
			logger.info("getOnlySign 异常" + e.getMessage());
		}
		return null;
	}

	public static String formatInt(int num, int length) {
		return String.format("%0"+length+"d",num);
	}

	public static String formatLong(long num, int length) {
		return String.format("%0"+length+"d",num);
	}





	public static String utf82gbk(String utf8) {

		try {
			String iso = new String(utf8.getBytes("GBK"),"ISO-8859-1");
			return new String(iso.getBytes("ISO-8859-1"),"GBK");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getOrderNumByMaxId(String fixStr, boolean adDate, Integer maxId, Integer numLength) {
		if(Strings.isNullOrEmpty(fixStr)) {
			logger.error("getOrderNum 订单编号生成失败，fixStr为空");
			throw new ServiceException(100);
		}
		if(maxId == null) {
			logger.error("getOrderNum 订单编号生成失败，maxId为空");
			throw new ServiceException(100);
		}
		if(numLength == null) {
			logger.error("getOrderNum 订单编号生成失败，numLength为空");
			throw new ServiceException(100);
		}

		StringBuffer numBuf = new StringBuffer();
		numBuf.append(fixStr);
		if(adDate) {
			String dateStr = TimeUtility.formatTimeStr(new Date(), "yyyyMMdd");
			numBuf.append(dateStr);
		}

		String numStr = formatLong(maxId, numLength - fixStr.length());

		numBuf.append(numStr);
		return numBuf.toString();
	}


	/**
	 * 生成最大编号
	 * @param prefixStr 前缀
	 * @param addDate 编号是否有日期
	 * @param num 当前最大编号
	 * @param digit 小数位数
	 * @return
	 */
	public static String getOrderNumByMaxCode(String prefixStr, boolean addDate, String num, int digit){
		String result = "";
		String dateStr = TimeUtility.formatTimeStr(new Date(), TimeUtility.TIME_FORMAT_YYYYMMDD);
		if (Strings.isNullOrEmpty(num)) {
			result += prefixStr;
			if(addDate){
				result += dateStr;
			}

			String digital = "";
			for (int i = 0; i < digit-1; i++) {
				digital += "0";
			}
			digital += "0";
			result += digital;
		}
		else{
			result = num;
		}
		String numStr = result.substring(result.length() - digit);
		int iNum = Integer.parseInt(numStr) + 1;
		String newNumStr = Integer.toString(iNum);
		int differ = digit - newNumStr.length();
		String addZero = "";
		for (int j = 0; j < differ; j++) {
			addZero += "0";
		}
		newNumStr = addZero + newNumStr;
		String Num = result.substring(0, result.length() - digit) + newNumStr;
		return Num;
	}
}
