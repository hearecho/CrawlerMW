package com.echo.crawler.controller;

import com.echo.crawler.entity.ServerEntity;
import com.echo.crawler.response.R;
import com.echo.crawler.service.ServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/server")
public class ServerController {
    @Autowired
    ServerService serverService;

    @GetMapping("/all")
    public R getAllServers() {
        List<ServerEntity> allServers;
        R r = R.ok();
        allServers = serverService.getAllServers();
        int num = allServers.size();
        return r.data(allServers, num);
    }

}
