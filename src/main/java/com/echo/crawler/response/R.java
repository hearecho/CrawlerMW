package com.echo.crawler.response;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class R {
    /**
     * 请求是否处理成功
     */
    private Boolean success;

    /**
     * 返回状态码
     */
    private Integer code;

    /**
     * 显示的信息
     */
    private String message;
    private int count;
    private Map<String, Object> data = new HashMap<>();


    // 构造器私有
    private R(){}

    /**
     * 返回成功
     * @return 返回R对象
     */
    public static R ok() {
        R r = new R();
        r.setSuccess(ResultCodeEnum.SUCCESS.getSuccess());
        r.setCode(ResultCodeEnum.SUCCESS.getCode());
        r.setMessage(ResultCodeEnum.SUCCESS.getMessage());
        return r;
    }

    /**
     * 通用返回失败，未知错误
     * @return 返回R对象
     */
    public static R error() {
        R r = new R();
        r.setSuccess(ResultCodeEnum.UNKNOWN_ERROR.getSuccess());
        r.setCode(ResultCodeEnum.UNKNOWN_ERROR.getCode());
        r.setMessage(ResultCodeEnum.UNKNOWN_ERROR.getMessage());
        return r;
    }

    /**
     * 设置结果，形参为结果枚举
     * @param result 当前返回结果
     * @return 返回R对象
     */
    public static R setResult(ResultCodeEnum result) {
        R r = new R();
        r.setSuccess(result.getSuccess());
        r.setCode(result.getCode());
        r.setMessage(result.getMessage());
        return r;
    }

    /**
     * 自定义返回数据
     * @param map 定义返回数据传入是哈希表
     * @return 返回R对象
     */
    public R data(Map<String,Object> map,int count) {
        this.count = count;
        this.setData(map);
        return this;
    }

    /**
     * 通用设置data
     * @param key
     * @param value
     * @return
     */
    public R data(String key,Object value,int count) {
        this.count = count;
        this.data.put(key, value);
        return this;
    }

    /**
     * 通用设置data
     * @param value
     * @return
     */
    public R data(Object value,int count) {
        this.count = count;
        this.data.put("items", value);
        return this;
    }
    /**
     * 设置状态信息
     * @param message
     * @return
     */
    public R message(String message) {
        this.setMessage(message);
        return this;
    }

    /**
     * 自定义状态码
     * @param code
     * @return
     */
    public R code(Integer code) {
        this.setCode(code);
        return this;
    }

    /**
     * 自定义返回结果
     * @param success
     * @return
     */
    public R success(Boolean success) {
        this.setSuccess(success);
        return this;
    }
}
