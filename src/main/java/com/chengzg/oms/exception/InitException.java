package com.chengzg.oms.exception;

/**
 * @author dongh38@ziroom
 * @Date 2016/10/26
 * @Time 17:12
 * @Description
 * @Since 1.0.0
 */
public class InitException extends RuntimeException{

    public InitException(String message, Throwable cause) {
        super(message, cause);
    }

    public InitException(String message) {
        super(message);
    }

}
