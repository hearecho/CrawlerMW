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
}
