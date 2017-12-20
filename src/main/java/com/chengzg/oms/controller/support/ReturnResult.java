package com.chengzg.oms.controller.support;

import com.chengzg.oms.utils.ErrorMessageLoader;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author dongh38@ziroom
 * @Date 16/10/23
 * @Time 下午5:07
 */
@ToString
public class ReturnResult implements Serializable{
    private static String STATUS_SUCCESS = "success";
    private static String STATUS_FAILURE = "failure";
    private static Integer CODE_SUCCESS = 0;


    /**
     * 错误码 0 成功 其他都是失败
     */
    private int code;
    /**
     * 状态  success成功，failure失败
     */
    private String status;

    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private  Object data;



    public ReturnResult(String status, int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.status = status;
    }


    public static BodyBuilder builder() {
        return new BodyBuilder();
    }


    public static ReturnResult ok() {
        return new ReturnResult(STATUS_SUCCESS, CODE_SUCCESS, ErrorMessageLoader.getString(CODE_SUCCESS), null);
    }

    public static ReturnResult ok(Object data) {
        return new ReturnResult(STATUS_SUCCESS, CODE_SUCCESS, ErrorMessageLoader.getString(CODE_SUCCESS), data);
    }

    public static ReturnResult failure(int code, String message) {
        return new ReturnResult(STATUS_FAILURE, code, message, null);
    }

    public static ReturnResult failure(int code) {
        return new ReturnResult(STATUS_FAILURE, code, ErrorMessageLoader.getString(code), null);
    }


    public static class BodyBuilder {
        private int code;
        private String status;

        private String message;

        private  Object data;

        public BodyBuilder code(int code) {
            this.code = code;
            return this;
        }

        public BodyBuilder message(String message) {
            this.message = message;
            return this;
        }

        public BodyBuilder data(Object data) {
            this.data = data;
            return this;
        }

        public BodyBuilder status(String status) {
            this.status = status;
            return this;
        }

        public ReturnResult build() {
            return new ReturnResult(status,code,message, data);
        }

    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
