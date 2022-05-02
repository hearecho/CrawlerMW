package com.echo.crawler.expection;

import com.echo.crawler.response.R;
import com.echo.crawler.response.ResultCodeEnum;
import com.jcraft.jsch.JSchException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;

/**
 * 全局错误处理，防止直接返回springboot自带的response
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    /**-------- 通用异常处理方法 --------**/
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R error(Exception e) {
        e.printStackTrace();

        return R.error();
    }

    /**-------- 指定异常处理方法 --------**/
    @ExceptionHandler(NullPointerException.class)
    @ResponseBody
    public R error(NullPointerException e) {
        /** 可以添加日志 */
        return R.setResult(ResultCodeEnum.NULL_POINT);
    }

    /**-----------Jsch连接出错----------------**/
    @ExceptionHandler(JSchException.class)
    @ResponseBody
    public R error(JSchException e) {
        return R.error().message(e.getMessage());
    }

    /**----------------IOException-----------**/
    @ExceptionHandler(IOException.class)
    @ResponseBody
    public R error(IOException e) {
        return R.error().message(e.getMessage());
    }

    @ExceptionHandler(HttpClientErrorException.class)
    @ResponseBody
    public R error(IndexOutOfBoundsException e) {
        e.printStackTrace();
        return R.setResult(ResultCodeEnum.HTTP_CLIENT_ERROR);
    }
}
