package com.chengzg.oms.exception;

/**
 * Created by czg on 2016/10/31.
 */
public class IncomeException extends ServiceException {
    public IncomeException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
