package com.echo.crawler.utils;

import javafx.util.Pair;

/**
 * 分页工具，分页的时候，我们应该尽可能的在业务层处理分页
 */
public class PaginationUtil {
    public static int[] getRange(int page, int pageSize, int num) {
        int startIndex = 0;
        int endIndex = pageSize;
        if (page > 0) {
            startIndex = (page-1) * pageSize;
        }
        if (startIndex > num) {
            startIndex = num;
        }
        endIndex = startIndex + pageSize;
        if (endIndex > num) {
            endIndex = num;
        }
        int[] res = new int[2];
        res[0] = startIndex;
        res[1] = endIndex;
        return res;
    }
}
