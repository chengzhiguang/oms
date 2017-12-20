package com.chengzg.oms.exception;

import com.chengzg.oms.utils.ErrorMessageLoader;
import com.google.common.base.Strings;

/**
 * @author dongh38@ziroom
 * @Date 16/10/23
 * @Time 下午8:49
 */
public class ServiceException extends RuntimeException{
    public ErrorCode errorCode;
    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ServiceException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public ServiceException(int code) {
        super(ErrorMessageLoader.getString(code));

        this.code = code;

        String message = ErrorMessageLoader.getString(code);
        if (!Strings.isNullOrEmpty(message)) {
            this.message = message;
        }
    }
    public ServiceException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
