package com.chengzg.oms.exception;

/**
 * @author dongh38@ziroom
 * @Date 16/10/23
 * @Time 下午8:54
 * 通用异常状态码
 */
public enum ErrorCode {

    BAD_REQUEST(400, 400),
    UNAUTHORIZED(401, 401),
    FORBIDDEN(403, 403),
    ORDER_STATUS_WRONG(500, 400),
    GOODS_STATUS_WRONG(502, 400),
    ORDER_CREATION_FAILED(500, 400),
    ORDER_EXPIRED(503 ,400),
    PAY_FEE_WRONG(504, 403),
    NO_TOKEN(1102, 401),
    DATA_BAD(505, 400),
    PERIOD_WRONG(506,400),
    INCOME_WRONG(507,400),
    DATA_DUPLICATE(508,400),
    ;
    private int code;
    private int status;

    ErrorCode(int code, int status) {
        this.code = code;
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public int getStatus() {
        return status;
    }
}
