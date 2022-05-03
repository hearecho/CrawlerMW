package com.echo.crawler.service;


import com.echo.crawler.entity.SpiderEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SpiderService {

    /**
     * 查询所有的爬虫
     * @return
     */
    List<SpiderEntity> findAllSpiders();

    /**
     * 根据名称获取对应的爬虫
     * @param name
     * @return
     */
    SpiderEntity findOneByName(String name);

    /**
     * 更新对应的服务
     * @param docker
     * @param dockerID
     * @param dockerName
     * @param name
     * @return
     */
    boolean updateSpider(boolean docker, String dockerID, String dockerName, String name);
    boolean updateSpider(boolean file, String filePath, String name);
    boolean updateSpider(boolean status, String name);

    boolean updateSpider(SpiderEntity spider, String name);
    /**
     * 新增服务
     * @param spider
     * @return
     */
    boolean add(SpiderEntity spider);

    /**
     * 删除对应的spider
     * @param name
     * @return
     */
    boolean deleteSpider(String name);
}
