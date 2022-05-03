package com.echo.crawler;

import com.echo.crawler.entity.ServerEntity;
import com.echo.crawler.service.ServerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
class RedisTests {
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ServerService serverService;

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

}
