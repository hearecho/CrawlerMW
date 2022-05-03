package com.echo.crawler.controller;

import com.echo.crawler.entity.SpiderEntity;
import com.echo.crawler.response.R;
import com.echo.crawler.service.SpiderService;
import com.echo.crawler.utils.PaginationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/spider")
public class SpiderController {

    @Autowired
    SpiderService spiderService;

    /**
     * 获取全部的爬虫项目
     *
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/all")
    public R allSpiders(@RequestParam(name = "page") int page,
                        @RequestParam(name = "pageSize") int pageSize) {
        List<SpiderEntity> spiderEntities = new ArrayList<>();
        spiderEntities = spiderService.findAllSpiders();
        int num = spiderEntities.size();
        int[] indexes = PaginationUtil.getRange(page, pageSize, num);
        List<SpiderEntity> res = spiderEntities.subList(indexes[0], indexes[1]);
        return R.ok().data(res, res.size());
    }

    /**
     * 更新spider的相关信息
     *
     * @param name spider名称，用于定位
     * @return
     */
    @PostMapping("/update/{name}")
    public R updateSpider(@PathParam("name") String name,
                          @ModelAttribute SpiderEntity spider) {
        SpiderEntity one = spiderService.findOneByName(name);
        if (one == null) {
            return R.error().message("unknown spider name");
        }
        if (!one.name.equals(spider.name)) {
            // 更改了名称 需要确认数据库中没有对应的name
            SpiderEntity temp = spiderService.findOneByName(spider.name);
            if (temp != null) {
                return R.error().message("duplicate name");
            }
        }
        boolean res = spiderService.updateSpider(spider, name);
        if (!res) {
            return R.error();
        }
        return R.ok().message("update success");
    }

    /**
     * 删除对应的spider
     * @param name spider名称，用于定位
     * @return
     */
    @DeleteMapping("/delete")
    public R deleteSpider(@RequestParam(name = "name") String name) {
        SpiderEntity one = spiderService.findOneByName(name);
        if (one == null) {
            return R.error().message("unknown spider name");
        }
        boolean res = spiderService.deleteSpider(name);
        if (!res) {
            return R.error();
        }
        return R.ok().message("delete success");
    }

    /**
     * @ModelAttribute 作用是防止出现
     * Content type 'multipart/form-data;boundary=----WebKitFormBoundarynZRnvRobJ6LfOyby;charset=UTF-8' not supported错误
     * @param spider
     * @return
     */
    @PostMapping("/add")
    public R newSpider(@ModelAttribute SpiderEntity spider) {
        spider.status = true;
        boolean res = spiderService.add(spider);
        if (res) {
            return R.ok().message("add success");
        }
        return R.error().message("add fail");
    }
}
