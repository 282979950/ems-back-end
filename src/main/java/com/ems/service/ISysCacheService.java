package com.ems.service;

/**
 * 系统缓存服务接口
 *
 * @author litairan on 2018/7/25.
 */
public interface ISysCacheService {

    /**
     * 获取SYS_CACHE缓存
     *
     * @param key
     * @return
     */
    Object get(String key);

    /**
     * 获取SYS_CACHE缓存
     *
     * @param key
     * @param defaultValue
     * @return
     */
    Object get(String key, Object defaultValue);

    /**
     * 写入SYS_CACHE缓存
     *
     * @param key
     * @return
     */
    void put(String key, Object value);

    /**
     * 从SYS_CACHE缓存中移除
     *
     * @param key
     * @return
     */
    void remove(String key);

    /**
     * 获取缓存
     *
     * @param cacheName
     * @param key
     * @return
     */
    Object get(String cacheName, String key);

    /**
     * 获取缓存
     *
     * @param cacheName
     * @param key
     * @param defaultValue
     * @return
     */
    Object get(String cacheName, String key, Object defaultValue);

    /**
     * 写入缓存
     *
     * @param cacheName
     * @param key
     * @param value
     */
    void put(String cacheName, String key, Object value);

    /**
     * 从缓存中移除
     *
     * @param cacheName
     * @param key
     */
    void remove(String cacheName, String key);

    /**
     * 从缓存中移除所有
     *
     * @param cacheName
     */
    void removeAll(String cacheName);
}
