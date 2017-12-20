package com.chengzg.oms.utils;

import com.chengzg.oms.exception.InitException;
import com.netflix.config.*;

import java.io.IOException;

/**
 * @author dongh38@ziroom
 * @Date 2016/10/26
 * @Time 16:54
 * @Description
 * @Since 1.0.0
 */
public class ConfigLoader {

    static {
        try {
            ConfigurationManager.loadCascadedPropertiesFromResources("project");
        } catch (IOException e) {
            throw new InitException("error initializing project.properties",e);
        }
    }

    public static String getString(String key,String defaultValue) {
        DynamicStringProperty stringProperty = DynamicPropertyFactory.getInstance().getStringProperty(key, defaultValue);
        return stringProperty.get();
    }

    public static Integer getInteger(String key,int defaultValue) {
        DynamicIntProperty intProperty = DynamicPropertyFactory.getInstance().getIntProperty(key,defaultValue);
        return intProperty.get();
    }

    public static Long getLong(String key,long defaultValue) {
        DynamicLongProperty longProperty = DynamicPropertyFactory.getInstance().getLongProperty(key, defaultValue);
        return longProperty.get();
    }

    public static Boolean getBoolean(String key,Boolean defaultValue) {
        DynamicBooleanProperty booleanProperty = DynamicPropertyFactory.getInstance().getBooleanProperty(key,defaultValue);
        return booleanProperty.get();
    }

}
