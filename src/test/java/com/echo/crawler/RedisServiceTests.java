package com.echo.crawler;

import com.echo.crawler.entity.ServerEntity;
import com.echo.crawler.service.RedisService;
import com.echo.crawler.service.ServerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class RedisServiceTests {
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ServerService serverService;

    @Autowired
    private RedisService<ServerEntity> redisService;
    @Test
    void getHostStatus() {
        String ip = "192.168.1.5";
//        redisTemplate.delete("host-"+ip);
        ServerEntity server = (ServerEntity) redisTemplate.opsForValue().get("host-"+ip);
        if (server == null) {
            System.out.println("redis 没有命中");
            server = serverService.findByIp(ip);
            redisTemplate.opsForValue().set("host-"+ip, server);
        }
        System.out.println(server);
    }

    @Test
    void refreshHost(){
        String key = "host:refresh:all";
        List<ServerEntity> serverEntities = (List<ServerEntity>) redisTemplate.opsForValue().get(key);
        if (serverEntities == null) {
            System.out.println("redis 没有命中");
            // 说明不存在该key
            serverEntities = serverService.getAllServers();
            // 设置expire time
            redisTemplate.opsForValue().set(key, serverEntities, 30, TimeUnit.SECONDS);
        }
        System.out.println(serverEntities);
    }

    /**
     * 测试存取String的功能
     */
    @Test
    void TestString() {
        String ip = "192.168.1.5";
        ServerEntity server = redisService.getValue("host-"+ip);
        if (server == null) {
            System.out.println("redis 未命中");
            server = serverService.findByIp(ip);
            redisService.cacheValue("host-"+ip, server, 30);
        }
//        redisService.removeValue("host-"+ip);
        System.out.println(server);
    }

    /**
     * 测试存取List
     */
    @Test
    void TestList() {

    }

}
