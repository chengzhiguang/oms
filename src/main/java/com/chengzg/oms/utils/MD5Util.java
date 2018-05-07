package com.chengzg.oms.utils;

import org.apache.log4j.Logger;

import java.security.MessageDigest;

/**
 * MD5加密工具类
 * @author chengzg
 *
 */
public class MD5Util {
	private static Logger logger = Logger.getLogger(MD5Util.class);
	
	/**
	 * 进行MD5加密
	 * @param s 需要加密的字符串
	 * @return 加密后的字符串
	 */
	public static String MD5(String str) {
        char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};       

        try {
        	if(!StrUtils.isNotNullOrBlank(str)) {
            	logger.debug("MD5 加密字符串为空");
        		return null;
        	}
        	logger.debug("MD5 加密前字符串：" + str);
            byte[] btInput = str.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char strs[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                strs[k++] = hexDigits[byte0 >>> 4 & 0xf];
                strs[k++] = hexDigits[byte0 & 0xf];
            }
            logger.debug("MD5 加密后字符串：" + new String(strs).toLowerCase());
            
            return new String(strs).toLowerCase();
        } catch (Exception e) {
        	logger.error("MD5 异常：" + e.getMessage());
            return null;
        }
    }
	
	public static void main(String[] args) {
		String str = "admin";
		System.out.println("result:" + MD5(str));
		logger.debug("result:" + MD5(str));
	}
}
