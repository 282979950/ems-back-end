package com.ems.service.impl;

import com.ems.service.ISysCacheService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.Set;

/**
 * 系统缓存服务实现类
 *
 * @author litairan on 2018/7/25.
 */
@Slf4j
@Service("iSysCacheService")
@Lazy(true)
public class SysCacheServiceImpl implements ISysCacheService {

    @Autowired
    private CacheManager cacheManager;

    private static final String SYS_CACHE = "SYS_CACHE";

    public Object get(String key) {
        return get(SYS_CACHE, key);
    }

    public Object get(String key, Object defaultValue) {
        Object value = get(key);
        return value != null ? value : defaultValue;
    }

    public void put(String key, Object value) {
        put(SYS_CACHE, key, value);
    }

    public void remove(String key) {
        remove(SYS_CACHE, key);
    }

    public Object get(String cacheName, String key) {
        return getCache(cacheName).get(getKey(key));
    }

    public Object get(String cacheName, String key, Object defaultValue) {
        Object value = get(cacheName, getKey(key));
        return value != null ? value : defaultValue;
    }

    public void put(String cacheName, String key, Object value) {
        getCache(cacheName).put(getKey(key), value);
    }

    public void remove(String cacheName, String key) {
        getCache(cacheName).remove(getKey(key));
    }

    public void removeAll(String cacheName) {
        Cache<String, Object> cache = getCache(cacheName);
        Set<String> keys = cache.keys();
        for (Iterator<String> it = keys.iterator(); it.hasNext(); ) {
            cache.remove(it.next());
        }
        log.info("清理缓存： {} => {}", cacheName, keys);
    }

    /**
     * 获取缓存键名，多数据源下增加数据源名称前缀
     *
     * @param key
     * @return
     */
    private String getKey(String key) {
        return key;
    }

    /**
     * 获得一个Cache，没有则显示日志。
     *
     * @param cacheName
     * @return
     */
    private Cache<String, Object> getCache(String cacheName) {
        Cache<String, Object> cache = cacheManager.getCache(cacheName);
        if (cache == null) {
            throw new RuntimeException("当前系统中没有定义“" + cacheName + "”这个缓存。");
        }
        return cache;
    }

}
