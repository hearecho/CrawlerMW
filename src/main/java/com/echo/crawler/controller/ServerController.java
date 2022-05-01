package com.echo.crawler.controller;

import com.echo.crawler.entity.ServerEntity;
import com.echo.crawler.response.R;
import com.echo.crawler.service.ServerService;
import com.echo.crawler.utils.PaginationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/server")
public class ServerController {
    @Autowired
    ServerService serverService;

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
        int startIndex = PaginationUtil.getStartIndex(page, pageSize);
        if (startIndex > num) {
            startIndex = num;
        }
        int endIndex = startIndex + pageSize;
        if (endIndex > num) {
            endIndex = num;
        }
        List<ServerEntity> res = allServers.subList(startIndex, endIndex);
        return r.data(res, res.size());
    }

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

}
