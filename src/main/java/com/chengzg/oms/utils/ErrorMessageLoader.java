package com.chengzg.oms.utils;

import com.chengzg.oms.exception.InitException;
import com.netflix.config.ConfigurationManager;
import com.netflix.config.DynamicPropertyFactory;
import com.netflix.config.DynamicStringProperty;

import java.io.IOException;

/**
 * @author dongh38@ziroom
 * @Date 2016/10/26
 * @Time 16:54
 * @Description
 * @Since 1.0.0
 */
public class ErrorMessageLoader {

    static {
        try {
            ConfigurationManager.loadCascadedPropertiesFromResources("errorcode_msg_zh");
        } catch (IOException e) {
            throw new InitException("error initializing errorcode_msg_zh.properties",e);
        }
    }

    public static String getString(Integer code) {
        DynamicStringProperty stringProperty = DynamicPropertyFactory.getInstance().getStringProperty("api.errcode." + code, "");
        return stringProperty.get();
    }

}
