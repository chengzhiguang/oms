package com.chengzg.oms.exception;
/**
 * Created by czg on 2016/10/31.
 */
public class PeriodException extends ServiceException {
    public PeriodException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
