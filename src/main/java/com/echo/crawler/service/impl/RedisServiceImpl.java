package com.echo.crawler.service.impl;

import com.echo.crawler.service.RedisService;
import org.omg.CORBA.TIMEOUT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class RedisServiceImpl implements RedisService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    /**
     * 自定义三种key的前缀，用于分辨不同雷丁的value值
     */
    private static final String KEY_PREFIX_KEY = "info:key";
    private static final String KEY_PREFIX_SET = "info:key";
    private static final String KEY_PREFIX_LIST = "info:key";

    private final RedisTemplate<String, Object> redisTemplate;

    public RedisServiceImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    @Override
    public boolean cacheValue(String key, Object value, long time) {
        try {
            String k = KEY_PREFIX_KEY + key;
            ValueOperations<String, Object> ops = redisTemplate.opsForValue();
            ops.set(k, value);
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            log.error("redis 缓存失败key:[{}],value:[{}]", key, value);
        }
        return false;
    }

    @Override
    public boolean cacheValue(String key, Object value) {
        return cacheValue(key, value, -1);
    }

    @Override
    public boolean containsValueKey(String key) {
        return containsKey(KEY_PREFIX_KEY + key);
    }

    @Override
    public boolean containsSetKey(String key) {
        return containsKey(KEY_PREFIX_SET + key);
    }

    @Override
    public boolean containsListKey(String key) {
        return containsKey(KEY_PREFIX_LIST+key);
    }

    @Override
    public boolean containsKey(String key) {
        try {
            return Boolean.TRUE.equals(redisTemplate.hasKey(key));
        } catch (Exception e) {
            log.error("judge has key error; key:[{}]", key);
        }
        return false;
    }

    @Override
    public Object getValue(String key) {
        try {
            ValueOperations<String, Object> ops = redisTemplate.opsForValue();
            return ops.get(KEY_PREFIX_KEY+key);
        } catch (Exception e) {
            log.error("get cache error; key:[{}]; error:{}", key, e.getMessage());
        }
        return null;
    }

    public boolean remove(String key) {
        try{
            redisTemplate.delete(key);
            return true;
        } catch (Exception e) {
            log.error("delete error; key:[{}], error:[{}]", key, e.getMessage());
        }
        return false;
    }
    @Override
    public boolean removeValue(String key) {
        return remove(KEY_PREFIX_KEY + key);
    }

    @Override
    public boolean removeSet(String key) {
        return remove(KEY_PREFIX_SET + key);
    }

    @Override
    public boolean removeList(String key) {
        return remove(KEY_PREFIX_LIST + key);
    }

    @Override
    public boolean cacheSet(String key, Object value, long time) {
        try {
            String k = KEY_PREFIX_SET + key;
            SetOperations<String,Object> ops = redisTemplate.opsForSet();
            ops.add(k, value);
            if (time > 0) {
                redisTemplate.expire(k, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            log.error("redis 缓存失败key:[{}],value:[{}]", key, value);
        }
        return false;
    }

    @Override
    public boolean cacheSet(String key, Object value) {
        return cacheSet(key, value, -1);
    }

    @Override
    public boolean cacheSet(String key, Set<Object> value, long time) {
        try {
            String k = KEY_PREFIX_SET + key;
            SetOperations<String,Object> ops = redisTemplate.opsForSet();
            ops.add(k, value.toArray(new Object[0]));
            if (time > 0) {
                redisTemplate.expire(k, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            log.error("redis 缓存失败key:[{}],value:[{}]", key, value);
        }
        return false;
    }

    @Override
    public boolean cacheSet(String key, Set<Object> value) {
        return cacheSet(key, value, -1);
    }

    @Override
    public Set<Object> getSet(String key) {
        try {
            SetOperations<String, Object> ops = redisTemplate.opsForSet();
            return ops.members(KEY_PREFIX_SET+key);
        } catch (Exception e) {
            log.error("get cache error; key:[{}]; error:{}", key, e.getMessage());
        }
        return null;
    }

    @Override
    public boolean cacheList(String key, Object value, long time) {
        try {
            String k = KEY_PREFIX_LIST + key;
            ListOperations<String,Object> ops = redisTemplate.opsForList();
            ops.rightPush(k, value);
            if (time > 0) {
                redisTemplate.expire(k, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            log.error("redis 缓存失败key:[{}],value:[{}]", key, value);
        }
        return false;
    }

    @Override
    public boolean cacheList(String key, Object value) {
        return cacheList(key,value,-1);
    }

    @Override
    public boolean cacheList(String key, List<Object> value, long time) {
        try {
            String k = KEY_PREFIX_LIST + key;
            ListOperations<String,Object> ops = redisTemplate.opsForList();
            ops.rightPushAll(k, value);
            if (time > 0) {
                redisTemplate.expire(k, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            log.error("redis 缓存失败key:[{}],value:[{}]", key, value);
        }
        return false;
    }

    @Override
    public boolean cacheList(String key, List<Object> value) {
        return cacheList(key, value, -1);
    }

    @Override
    public List<Object> getList(String key, long start, long end) {
        try {
            ListOperations<String, Object> ops = redisTemplate.opsForList();
            return ops.range(KEY_PREFIX_LIST+key, start, end);
        } catch (Exception e) {
            log.error("get cache error; key:[{}]; error:{}", key, e.getMessage());
        }
        return null;
    }

    @Override
    public long getListSize(String key) {
        try {
            ListOperations<String, Object> ops = redisTemplate.opsForList();
            return ops.size(KEY_PREFIX_LIST+key);
        } catch (Exception e) {
            log.error("get list size error; key:[{}]; error:{}", key, e.getMessage());
        }
        return 0;
    }

    @Override
    public long getListSize(ListOperations<String, Object> listOps, String k) {
        try {
            return listOps.size(k);
        } catch (Exception e) {
            log.error("get list size error; key:[{}]; error:{}", KEY_PREFIX_LIST + k, e.getMessage());
        }
        return 0;
    }

    @Override
    public boolean removeOneOfList(String key) {
        try {
            String k = KEY_PREFIX_LIST + key;
            ListOperations<String,Object> ops = redisTemplate.opsForList();
            ops.rightPop(k);
            return true;
        } catch (Exception e) {
            log.error("delete error; key:[{}], error:[{}]", key, e.getMessage());
        }
        return false;
    }
}
