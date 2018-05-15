package cn.meilituibian.api.exception;

public class ErrorResponseEntity {
    private boolean isSuccess;
    private Object data;
    private int errorCode;
    private String errorMsg;

    public static ErrorResponseEntity fail(Object data, int errorCode, String errorMsg) {
        ErrorResponseEntity entity = new ErrorResponseEntity();
        entity.setSuccess(false);
        entity.setData(data);
        entity.setErrorCode(errorCode);
        entity.setErrorMsg(errorMsg);
        return entity;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
