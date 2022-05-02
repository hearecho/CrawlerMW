package com.echo.crawler.service.impl;

import com.echo.crawler.entity.ServerEntity;
import com.echo.crawler.mapper.ServerMapper;
import com.echo.crawler.service.ServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServerServiceImpl implements ServerService {
    @Autowired
    ServerMapper serverMapper;

    @Override
    public List<ServerEntity> getAllServers() {
        return serverMapper.queryAll();
    }

    @Override
    public List<ServerEntity> getAllServersByStatus(int status) {
        return serverMapper.queryByStatus(status);
    }

    @Override
    public boolean addServer(ServerEntity serverEntity) {
        return serverMapper.insert(serverEntity.getIp(), serverEntity.getUser(), serverEntity.getPwd(),
                serverEntity.getStatus(), serverEntity.getPort());
    }

    @Override
    public ServerEntity findByIp(String ip) {
        return serverMapper.findByIp(ip);
    }

    @Override
    public boolean deleteIp(String ip) {
        return serverMapper.delete(ip);
    }

    @Override
    public boolean updateStatus(String ip, int status) {
        return serverMapper.updateStatus(ip, status);
    }
}
