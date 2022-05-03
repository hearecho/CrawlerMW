package com.echo.crawler.controller;

import com.echo.crawler.entity.SpiderEntity;
import com.echo.crawler.response.R;
import com.echo.crawler.service.SpiderService;
import com.echo.crawler.utils.PaginationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
    @PostMapping("/update")
    public R updateSpider(@RequestParam(name = "name") String name,
                          @RequestParam(name = "spider") SpiderEntity spider) {
        SpiderEntity one = spiderService.findOneByName(name);
        if (one == null) {
            return R.error().message("unknown spider name");
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
}
