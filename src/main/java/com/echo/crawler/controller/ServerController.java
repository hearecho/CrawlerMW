package com.echo.crawler.controller;

import com.echo.crawler.entity.ServerEntity;
import com.echo.crawler.entity.SpiderEntity;
import com.echo.crawler.response.R;
import com.echo.crawler.service.RedisService;
import com.echo.crawler.service.ServerService;
import com.echo.crawler.utils.IPUtil;
import com.echo.crawler.utils.PaginationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 主机管理，主要负责主机的状态查询，刷新主机状态，获取主机信息
 */
@RestController
@RequestMapping("/api/server")
public class ServerController {
    @Autowired
    ServerService serverService;

    @Autowired
    RedisService<ServerEntity> redisService;

    private final String refreshAllKey = "refreshAllHost";

    @GetMapping("/all")
    public R getAllServers(@RequestParam(name = "status", required = false, defaultValue = "") String status,
                           @RequestParam(name = "page", required = false) int page,
                           @RequestParam(name = "pageSize", required = false) int pageSize) {
        List<ServerEntity> allServers;
        R r = R.ok();
        if ("".equals(status)) {
            allServers = serverService.getAllServers();
        } else {
            int st = Integer.parseInt(status);
            allServers = serverService.getAllServersByStatus(st);
        }
        int num = allServers.size();
        // 分页
        int[] indexes = PaginationUtil.getRange(page,pageSize,num);
        List<ServerEntity> res = allServers.subList(indexes[0], indexes[1]);
        for (ServerEntity server: res) {
            // 模糊密码
            server.setPwd("******");
        }
        return r.data(res, res.size());
    }

    /**
     * 加入新的主机
     * @param ip
     * @param user
     * @param pwd
     * @param port
     * @param status
     * @return
     */
    @PostMapping("/insert")
    public R newServer(@RequestParam(name = "ip") String ip,
                       @RequestParam(name = "user") String user,
                       @RequestParam(name = "pwd") String pwd,
                       @RequestParam(name = "port") String port,
                       @RequestParam(name = "status", required = false) int status) {
        ServerEntity serverEntity = serverService.findByIp(ip);
        if (serverEntity != null) {
            return R.error().message("duplicate ip, if need update, please invoke other api!");
        }
        serverEntity = new ServerEntity(ip, user, pwd, port, status);
        boolean res = serverService.addServer(serverEntity);
        if (!res) {
            // 出错
            return R.error();
        }
        return R.ok();
    }

    /**
     * 删除对应的主机
     * @param ip 主机ip
     * @return
     */
    @DeleteMapping("/delete")
    public R deleteHost(@RequestParam(name = "ip")String ip) {
        boolean res = serverService.deleteIp(ip);
        if (!res) {
            return R.error().message("delete error");
        }
        return R.ok();
    }

    /**
     * 更新主机的状态信息
     * @param ip 主机ip
     * @return
     */
    @GetMapping("/refresh")
    public R refreshServers(@RequestParam(name = "ip", required = false, defaultValue = "")String ip) throws InterruptedException {
        List<ServerEntity> serverEntities = new ArrayList<>();
        String key = "refreshHost";
        // 更新操作
        // 这里需要做的是在redis进行缓存，并设置一定的过期时间
        // 防止频繁刷新，不仅会导致慢接口，还会导致网络资源的浪费
        if (redisService.containsListKey(key)) {
            // 命中了redis
            long size = redisService.getListSize(key);
            serverEntities = redisService.getList(key, 0, size);
        } else {
            // 未命中，则需要重新刷新所有的ip
            if ("".equals(ip)) {
                serverEntities = serverService.getAllServers();
            } else {
                ServerEntity server = serverService.findByIp(ip);
                serverEntities.add(server);
            }
            // 使用多线程（线程池）并发请求所有的ping请求
            // 并使用CountDownLatch
            int totalThread = serverEntities.size();
            CountDownLatch countDownLatch = new CountDownLatch(totalThread);
            ExecutorService executorService = Executors.newCachedThreadPool();
            for (ServerEntity server: serverEntities) {
                executorService.execute(() -> {
                    boolean newStatus =  IPUtil.ping(server.getIp());
                    if (newStatus && server.getStatus() == 0) {
                        server.setStatus(1);
                        serverService.updateStatus(server.getIp(), 1);
                    } else if (!newStatus && server.getStatus() == 1) {
                        server.setStatus(0);
                        serverService.updateStatus(server.getIp(), 0);
                    }
                    countDownLatch.countDown();
                });
            }
            countDownLatch.await();
            executorService.shutdown();
            // 在redis中插入
            // 设置过期时间 30s
            redisService.cacheList(key, serverEntities, 30);
            // todo 返回更新之后的hos
        }
        return R.ok().data(serverEntities, serverEntities.size());
    }



}
