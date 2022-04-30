package com.echo.crawler.response;

import lombok.Getter;

@Getter
public enum ResultCodeEnum {
    /**
     * 成功
     */
    SUCCESS(true,200,"成功"),
    /**
     * 未知错误
     */
    UNKNOWN_ERROR(false,20001,"未知错误"),
    /**
     * 参数错误
     */
    PARAM_ERROR(false,20002,"参数错误"),

    /**
     * 空指针异常
     */
    NULL_POINT(false,20003,"空指针异常"),
    /**
     *
     */
    HTTP_CLIENT_ERROR(false,20004,"客户端错误"),

    DB_ERROR(false,20005,"查询出错");
    /**
     * 响应是否成功
     */
    private Boolean success;
    /**
     * 响应状态码
     */
    private Integer code;
    /**
     * 响应信息
     */
    private String message;

    ResultCodeEnum(boolean success, Integer code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }
}
