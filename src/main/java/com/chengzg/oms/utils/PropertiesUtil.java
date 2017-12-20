package com.chengzg.oms.utils;



import org.apache.log4j.Logger;

import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Properties;

/**
 * Properties 工具类
 *
 * Created by zhanjunwei on 15/3/16.
 */
public class PropertiesUtil {


    private static PropertiesUtil propertiesUtil = new PropertiesUtil();

    //默认属性文件路径
    private final static String PROPERTIES_CONFIG_PATH = "/project.properties";

    private static Logger logger = Logger.getLogger(PropertiesUtil.class);

    /**
     * 获取属性key对应字符串
     * @param key
     * @return
     * @throws Exception
     */
    public  static String getPropValAsString(String key){
        try {
            Properties prop = propertiesUtil.getPropFile();
            Object obj = prop.get(key);
            if(obj != null && !"".equals(obj.toString().trim())){
                return String.valueOf(obj).trim();
            }else{
                throw new Exception("属性文件SysConstant.properties中没有找到相应的key："+key);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }
    
    /**
     * 获取属性key对应字符串
     * @param key
     * @param args
     * @return
     * @throws Exception
     */
    public  static String getPropValAsString(String key, Object[] args){
        try {
            Properties prop = propertiesUtil.getPropFile();
            Object obj = prop.get(key);
            if(obj != null && !"".equals(obj.toString().trim())){
                String str = String.valueOf(obj).trim();
                str = MessageFormat.format(str, args);
                return str;
            }else{
                throw new Exception("属性文件SysConstant.properties中没有找到相应的key："+key);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 获取属性key对应字符串
     * @param key
     * @param path
     * @return
     * @throws Exception
     */
    public  static String getPropValAsString(String key, String path){
        try {
            Properties prop = propertiesUtil.getPropFile(path);
            Object obj = prop.get(key);
            if(obj != null && !"".equals(obj.toString().trim())){
                return String.valueOf(obj).trim();
            }else{
                throw new Exception("属性文件SysConstant.properties中没有找到相应的key："+key);
            }
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }
        return "";
    }
    
    /**
     * 获取属性key对应字符串
     * @param key
     * @param path
     * @param args
     * @return
     * @throws Exception
     */
    public  static String getPropValAsString(String key, Object[] args, String path){
        try {
            Properties prop = propertiesUtil.getPropFile(path);
            Object obj = prop.get(key);
            if(obj != null && !"".equals(obj.toString().trim())){
            	String str = String.valueOf(obj).trim();
                str = MessageFormat.format(str, args);
                return str;
            }else{
                throw new Exception("属性文件SysConstant.properties中没有找到相应的key："+key);
            }
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }
        return "";
    }

    /**
     * 获取属性key对应int
     * @param key
     * @return
     * @throws Exception
     */
    public  static int getPropValAsInt(String key) {
        try {
            Properties prop = propertiesUtil.getPropFile();
            Object obj=prop.get(key);
            if(obj != null && !"".equals(obj.toString().trim())){
                return (Integer.valueOf(String.valueOf(obj)));
            }else{
                throw new Exception("属性文件SysConstant.properties中没有找到相应的key："+key);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return -1;
    }

    /**
     * 获取属性key对应int
     * @param key
     * @param path
     * @return
     * @throws Exception
     */
    public  static int getPropValAsInt(String key, String path) {

        try{
            Properties prop = propertiesUtil.getPropFile(path);
            Object obj=prop.get(key);
            if(obj != null && !"".equals(obj.toString().trim())){
                return (Integer.valueOf(String.valueOf(obj)));
            }else{
                throw new Exception("属性文件SysConstant.properties中没有找到相应的key："+key);
            }
        } catch (Exception e){
            logger.error(e.getMessage(), e);
        }
        return  -1;

    }

    /**
     * 获取Properties文件
     * @return
     * @throws Exception
     */
    private Properties getPropFile()throws Exception {
        return getPropFile(PROPERTIES_CONFIG_PATH);
    }


    /**
     * 获取Properties文件
     * @return
     * @throws Exception
     */
    private Properties getPropFile(String path)throws Exception {
        InputStream inputStream = this.getClass().getResourceAsStream(path);
        Properties prop = new Properties();
        prop.load(inputStream);
        return prop;
    }

}
