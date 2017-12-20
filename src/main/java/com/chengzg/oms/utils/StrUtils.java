package com.chengzg.oms.utils;

import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.Iterator;

/**
 * 字符串工具类
 * 
 * @author thinkpad
 * 
 */
public class StrUtils {
	/**
	 * 判断字符串是否不为空或者不为空字符
	 * 
	 * @param str
	 *            允许为NULL
	 * @return
	 */
	public static boolean isNotNullOrBlank(Object o) {
		if (null == o) {
			return false;
		} else {
			String str = o.toString();
			if ("".equals(str.trim())) {
				return false;
			} else {
				return true;
			}
		}
	}

	/**
	 * 判断字符串是否为空或者不为空字符
	 * 
	 * @param str
	 *            允许为NULL
	 * @return
	 */
	public static boolean isNullOrBlank(Object o) {
		return !isNotNullOrBlank(o);
	}

	/**
	 * 将sourceObject转换为字符串后与compareStr对象比较，如果相等就返回afterIsEqualToObject对象，
	 * 不相等就返回sourceObject
	 * 
	 * @param sourceObject
	 *            被比较的对象
	 * @param compareStr
	 *            比较的对象
	 * @param object
	 *            如果相等，则返回afterIsEqualToObject对象
	 */
	public static Object toStringForCompare(Object sourceObject,
			String compareStr, Object afterIsEqualToObject) {
		if (null == sourceObject) {
			return sourceObject;
		} else if (sourceObject.toString().equals(compareStr)) {
			return compareStr;
		} else {
			return null;
		}

	}

	/**
	 * 如果对象为空，则返回空字符串
	 * 
	 * @param str
	 * @return
	 */
	public static String isNullToBank(Object str) {
		if (null == str) {
			return "";
		} else {
			return str.toString();
		}
	}

	/**
	 * 将对象转换为String类型
	 * 
	 * @param str
	 * @return
	 */
	public static String ObjectToStr(Object o, String defaultValue) {
		if (null == o) {
			return defaultValue;
		} else {
			return o.toString().trim();
		}
	}

	public static String ObjectToStr(Object o) {
		if (null == o) {
			return null;
		} else {
			return o.toString().trim();
		}
	}

	/**
	 * 将对象转换为String类型
	 * 
	 * @param str
	 * @return
	 */
	public static String ObjectToString(Object o) {
		if (null == o) {
			return null;
		} else {
			return o.toString();
		}
	}

	/**
	 * 将对象转换为Long类型
	 * 
	 * @param str
	 * @return
	 */
	public static Long ObjectToLong(Object o, Long defaultValue) {
		if (null == o) {
			return defaultValue;
		} else {
			return (Long) o;
		}
	}

	/**
	 * 将对象转换为Long类型
	 * 
	 * @param str
	 * @return
	 */
	public static Long ObjectToLong(Object o) {
		if (null == o) {
			return null;
		} else {
			return new Long(o.toString());
		}
	}

	/**
	 * 将对象转换为Integer类型
	 * 
	 * @param str
	 * @return
	 */
	public static Integer ObjectToInteger(Object o) {
		if (null == o) {
			return null;
		} else {
			return ((java.math.BigDecimal) o).intValue();
		}
	}

	/**
	 * 集合转换成字符串
	 * 
	 * @param collection
	 *            集合
	 * @param separator
	 *            分隔符
	 * @return
	 */
	public static String CollectionToStr(Collection collection,
			String separator, boolean all) {
		Iterator it = collection.iterator();
		Object object = null;
		String str = "";
		while (it.hasNext()) {
			object = it.next();
			str = str + object.toString() + separator;
		}
		if (collection.size() > 0) {
			if (all) {
				str = "," + str;
			} else {
				str = str.substring(0, str.length() - (separator.length()));
			}
		}
		return str;
	}
	
	public static String solutionGarbled(String str,String code) throws UnsupportedEncodingException{
		if(str!=null){
			str = new String(str.getBytes("iso8859-1"),code);
		}
		return str;
		
	}
	/**

     * 从 Unicode 形式的字符串转换成对应的编码的特殊字符串。 如 "\u9EC4" to "黄".

     * Converts encoded \\uxxxx to unicode chars

     * and changes special saved chars to their original forms

     *

     * @param in

     *        Unicode编码的字符数组。

     * @param off

     *        转换的起始偏移量。

     * @param len

     *        转换的字符长度。

     * @param convtBuf

     *        转换的缓存字符数组。

     * @return 完成转换，返回编码前的特殊字符串。

     */

    public static String fromEncodedUnicode(char[] in, int off, int len) {

        char aChar;

        char[] out = new char[len]; // 只短不长

        int outLen = 0;

        int end = off + len;

 


        while (off < end) {

            aChar = in[off++];

            if (aChar == '\\') {

                aChar = in[off++];

                if (aChar == 'u') {

                    // Read the xxxx

                    int value = 0;

                    for (int i = 0; i < 4; i++) {

                        aChar = in[off++];

                        switch (aChar) {

                        case '0':

                        case '1':

                        case '2':

                        case '3':

                        case '4':

                        case '5':

                        case '6':

                        case '7':

                        case '8':

                        case '9':

                            value = (value << 4) + aChar - '0';

                            break;

                        case 'a':

                        case 'b':

                        case 'c':

                        case 'd':

                        case 'e':

                        case 'f':

                            value = (value << 4) + 10 + aChar - 'a';

                            break;

                        case 'A':

                        case 'B':

                        case 'C':

                        case 'D':

                        case 'E':

                        case 'F':

                            value = (value << 4) + 10 + aChar - 'A';

                            break;

                        default:

                            throw new IllegalArgumentException("Malformed \\uxxxx encoding.");

                        }

                    }

                    out[outLen++] = (char) value;

                } else {

                    if (aChar == 't') {

                        aChar = '\t';

                    } else if (aChar == 'r') {

                        aChar = '\r';

                    } else if (aChar == 'n') {

                        aChar = '\n';

                    } else if (aChar == 'f') {

                        aChar = '\f';

                    }

                    out[outLen++] = aChar;

                }

            } else {

                out[outLen++] = (char) aChar;

            }

        }

        return new String(out, 0, outLen);

    }
public static void main(String[] args) {
	String str="20160303";
	System.out.println(str.substring(0, 4));
}

}
