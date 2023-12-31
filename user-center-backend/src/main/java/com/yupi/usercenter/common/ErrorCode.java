package com.yupi.usercenter.common;

/**
 * 错误码
 *
 * @author Zilin Xu
 */
public enum ErrorCode {

    SUCCESS(0, "ok", ""),
    PARAMS_ERROR(40000, "Request Parameter Error", ""),
    NULL_ERROR(40001, "Request Data is Empty", ""),
    NOT_LOGIN(40100, "Not Logged In", ""),
    NO_AUTH(40101, "No Permission", ""),
    SYSTEM_ERROR(50000, "Internal Server Error", "");

    private final int code;

    /**
     * 状态码信息
     */
    private final String message;

    /**
     * 状态码描述（详情）
     */
    private final String description;

    ErrorCode(int code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }
}
