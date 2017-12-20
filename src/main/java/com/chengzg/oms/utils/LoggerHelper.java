package com.chengzg.oms.utils;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Maps;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author dongh38@ziroom
 * @Date 2016/11/9
 * @Time 9:15
 * @Description
 * @Since 1.0.0
 */
public class LoggerHelper {

    private LoggerHelper() {
        // no Construct
    }


    /**
     *   b
     * @param model
     * @param prefix
     * @return
     */
    public static String logMessage(Object model,String prefix) {
        StringBuilder builder = new StringBuilder(prefix).append(":");
        return builder.append(model.toString() == null ? " model is null " : model.toString()).toString();
    }

    /**
     *
     * @param request
     * @param prefix  标识业务前缀
     * @return
     */
    public static String logMessage(HttpServletRequest request, String prefix) {
        try {
            StringBuilder builder = new StringBuilder(prefix).append(":");
            Map<String, String[]> parameterMap = request.getParameterMap();
            Map<String, Object> values = Maps.transformValues(parameterMap, new Function<String[], Object>() {
                @Override
                public Object apply(String[] input) {
                    return Joiner.on("").join(input);
                }
            });
            String queryString = Joiner.on(",").withKeyValueSeparator("=").join(values);
            return builder.append(queryString).toString();
        } catch (Exception e) {
            return null;
        }

    }
}
