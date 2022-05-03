package com.echo.crawler.service.impl;

import com.echo.crawler.entity.SpiderEntity;
import com.echo.crawler.mapper.SpiderMapper;
import com.echo.crawler.service.SpiderService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class SpiderServiceImpl implements SpiderService {

    @Autowired
    SpiderMapper spiderMapper;

    @Override
    public List<SpiderEntity> findAllSpiders() {
        return spiderMapper.findAll();
    }

    @Override
    public SpiderEntity findOneByName(String name) {
        return spiderMapper.findByName(name);
    }

    @Override
    public boolean updateSpider(boolean docker, String dockerID, String dockerName, String name) {
        return spiderMapper.updateDocker(docker, dockerID, dockerName, name);
    }

    @Override
    public boolean updateSpider(boolean file, String filePath, String name) {
        return spiderMapper.updateFile(file, filePath, name);
    }

    @Override
    public boolean updateSpider(SpiderEntity spider, String name) {
        return spiderMapper.updateSpider(spider, name);
    }

    @Override
    public boolean updateSpider(boolean status, String name) {
        return spiderMapper.updateStatus(status, name);
    }

    @Override
    public boolean add(SpiderEntity spider) {
        return spiderMapper.add(spider);
    }

    @Override
    public boolean deleteSpider(String name) {
        return spiderMapper.delete(name);
    }
}
