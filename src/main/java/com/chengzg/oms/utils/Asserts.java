package com.chengzg.oms.utils;

import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author dongh38@ziroom
 * @Date 2016/11/3
 * @Time 14:43
 * @Description
 * @Since 1.0.0
 */
public class Asserts {
    private static Logger logger = LoggerFactory.getLogger(Asserts.class);

    public static <T> T checkNullOrEmpty(T value,String errorMessage) {
        if (value instanceof String) {
            if (Strings.isNullOrEmpty((String) value)) {
                logger.debug("checkNullOrEmpty 参数校验异常  {}", value);
                throw new IllegalArgumentException(errorMessage);
            }
        }  else if (value == null) {
            logger.debug("checkNullOrEmpty 参数校验异常  {}", value);
            throw new IllegalArgumentException(errorMessage);
        }
        return value;
    }

}
