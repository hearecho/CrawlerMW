package com.echo.crawler.service;

import com.echo.crawler.entity.ServerEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ServerService {
    /**
     * 获取全部的服务器
     * @return
     */
    List<ServerEntity> getAllServers();

    /**
     * 获取全部某个状态的服务器
     * @param status
     * @return
     */
    List<ServerEntity> getAllServersByStatus(int status);

    /**
     * 新增一个新的服务器
     * @param serverEntity
     * @return
     */
    boolean addServer(ServerEntity serverEntity);

    /**
     * 通过ip获取Server，如果没有则返回nil
     * @param ip
     * @return
     */
    ServerEntity findByIp(String ip);
}
