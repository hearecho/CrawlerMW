package com.echo.crawler.service;

import org.springframework.data.redis.core.ListOperations;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * T代表的是Value的类型
 * @param <T>
 */
@Service
public interface RedisService<T> {
    /**
     * 添加 key: String 缓存 带有过期时间
     * @param key
     * @param value
     * @param time
     * @return
     */
    boolean cacheValue(String key, T value, long time);

    /**
     * 添加 key: String 缓存 不带有过期时间
     * @param key
     * @param value
     * @return
     */
    boolean cacheValue(String key, T value);

    /**
     * 判断key:string 缓存是否存在
     * @param key
     * @return
     */
    boolean containsValueKey(String key);

    /**
     * 判断 key:set缓存是否存在
     * @param key
     * @return
     */
    boolean containsSetKey(String key);

    /**
     * 判断keu:list缓存是否存在
     * @param key
     * @return
     */
    boolean containsListKey(String key);

    /**
     * 查询缓存key是否存在
     * @param key
     * @return
     */
    boolean containsKey(String key);

    /**
     * 根据key获取缓存
     * @param key
     * @return
     */
    T getValue(String key);

    /**
     * 根据key删除缓存
     * @param key
     * @return
     */
    boolean removeValue(String key);

    boolean removeSet(String key);

    boolean removeList(String key);

    boolean cacheSet(String key, T value, long time);

    boolean cacheSet(String key, T value);

    boolean cacheSet(String key, Set<T> value, long time);

    boolean cacheSet(String key, Set<T> value);

    Set<T> getSet(String key);

    boolean cacheList(String key, T value, long time);

    boolean cacheList(String key, T value);

    boolean cacheList(String key, List<T> value, long time);

    boolean cacheList(String key, List<T> value);

    List<T> getList(String key, long start, long end);

    long getListSize(String key);

    long getListSize(ListOperations<String, T> listOps, String k);

    boolean removeOneOfList(String key);
}
