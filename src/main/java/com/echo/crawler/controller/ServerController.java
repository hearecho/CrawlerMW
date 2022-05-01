package com.echo.crawler.controller;

import com.echo.crawler.entity.ServerEntity;
import com.echo.crawler.response.R;
import com.echo.crawler.service.ServerService;
import com.echo.crawler.utils.PaginationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
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
        int endIndex = startIndex + pageSize;
        if (endIndex > num) {
            endIndex = num;
        }
        List<ServerEntity> res = allServers.subList(startIndex, endIndex);
        return r.data(res, res.size());
    }
    @PostMapping("/insert")
    public R newServer(@RequestBody ServerEntity serverEntity) {
        boolean res = serverService.addServer(serverEntity);
        if (!res){
            // 出错
            return R.error();
        }
        return R.ok();
    }

}
