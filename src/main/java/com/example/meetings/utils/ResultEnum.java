package com.example.meetings.utils;

public enum ResultEnum {
    /**
     * 成功
     */
    SUCCESS(200),
    /**
     * 没有权限
     */
    NO_PRIORITY(300),
    /**
     * 失败
     */
    FAIL(400),
    /**
     * 接口不存在
     */
    NOT_FOUND(404),
    /**
     * 服务器内部错误
     */
    INTERNAL_SERVER_ERROR(500);

    public int code;

    ResultEnum(int code) {
        this.code = code;
    }
}
