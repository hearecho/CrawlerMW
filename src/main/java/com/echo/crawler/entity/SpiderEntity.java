package com.echo.crawler.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SpiderEntity implements Serializable {
    /**
     * ID信息
     */
    public Integer ID;
    /**
     * 爬虫名称
     */
    public String name;
    /**
     * 是否支持文件发布
     */
    public boolean file;
    /**
     * 文件路径
     */
    public String filePath;
    /**
     * 是否支持docker发布
     */
    public boolean docker;
    /**
     * docker对应的名称，主要用于抓取
     */
    public String dockerName;
    /**
     * dockerID docker对应的镜像ID，主要用于运行
     */
    public String dockerID;
    /**
     * 该爬虫是否可用
     */
    public boolean status;
    /**
     * 该爬虫的创建时间
     */
    public Date createAt = new Date();

    public boolean distribute;
    public SpiderEntity() {
    }

    public SpiderEntity(String name, boolean file, boolean docker, boolean status, boolean distribute) {
        this.name = name;
        this.file = file;
        this.docker = docker;
        this.status = status;
        this.distribute = distribute;
    }

    public SpiderEntity(String name, boolean file, String filePath, boolean docker,
                        String dockerName, String dockerID,boolean distribute) {
        this.name = name;
        this.file = file;
        this.filePath = filePath;
        this.docker = docker;
        this.dockerName = dockerName;
        this.dockerID = dockerID;
        this.distribute = distribute;
    }
}
