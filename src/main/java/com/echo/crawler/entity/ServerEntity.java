package com.echo.crawler.entity;

import lombok.Data;

import java.io.Serializable;


@Data
public class ServerEntity implements Serializable {
    /**
     * 服务器ip地址
     */
    private String ip;

    /**
     * 服务器的用户
     */
    private String user;

    /**
     * 服务器密码
     */
    private String pwd;

    /**
     * 服务器状态
     */
    private Integer status;

    /**
     * 服务器连接端口
     */
    private String port;

    public ServerEntity() {

    }
    public ServerEntity(String ip, String user, String pwd, String port, int status) {
        this.ip = ip;
        this.port = port;
        this.user = user;
        this.pwd = pwd;
        this.status = status;
    }
}
