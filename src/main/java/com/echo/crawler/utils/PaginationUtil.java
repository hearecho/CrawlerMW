package com.echo.crawler.utils;

/**
 * 分页工具，分页的时候，我们应该尽可能的在业务层处理分页
 */
public class PaginationUtil {
    public static int getStartIndex(int page, int pageSize) {
         int startIndex = 0;
        if (page > 0) {
            startIndex = (page-1) * pageSize;
        }
        return startIndex;
    }
}
