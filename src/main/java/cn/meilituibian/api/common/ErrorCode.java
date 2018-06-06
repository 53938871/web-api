package cn.meilituibian.api.common;

import org.springframework.http.HttpStatus;

public enum ErrorCode{
    //10用户信息
    SUCCESS(0, "success"),
    RESOURCE_NOT_FOUND(404, "没有此信息"),
    BAD_REQUEST(400, "错误的请求"),
    USER_NOT_FOUND(10404, "没有此用户"),
    USER_NAME_IS_EMPTY(101400, "用户名不能为空"),
    PHONE_IS_EMPTY(102400, "手机号码不能为空"),
    PHONE_IS_EXISTS(102400, "此手机号码已存在"),
    INTERNAL_SERVER_ERROR(500, "内部服务错误");
   ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static ErrorCode getErrorCode(int code) {
        for (ErrorCode errorCode: ErrorCode.values()) {
            if (code == errorCode.code) {
                return errorCode;
            }
        }
        return ErrorCode.BAD_REQUEST;
    }
}
