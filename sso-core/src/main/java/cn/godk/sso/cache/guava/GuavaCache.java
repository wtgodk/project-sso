package cn.godk.sso.cache.guava;

import com.google.common.cache.Cache;
import com.google.common.collect.Maps;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

/**
 * guava 缓存
 *
 * @author godk
 * @program aircraft
 * @create 2019-05-06  11:16
 */
public class GuavaCache {

    protected static Map<Guava, Cache> caches = null;
    private static GuavaCache instance;

    private GuavaCache() {
    }

    public static GuavaCache getInstance() {
        if (instance == null) {
            synchronized (GuavaCache.class) {
                if (instance == null) {
                    instance = new GuavaCache();
                    caches = Maps.newHashMap();
                }
            }
        }
        return instance;
    }

    /**
     * 添加缓存
     *
     * @param key
     * @param cache
     */
    public synchronized void addCache(Guava key, Cache cache) {
        caches.put(key, cache);
    }

    /**
     * 切换cache
     *
     * @param key
     * @return
     */
    public synchronized Cache getCache(Guava key) {
        return caches.get(key);
    }


    /**
     * 获取
     *
     * @param key
     * @return
     * @throws ExecutionException
     */
    public synchronized <T> T get(Object key, Guava guavaKey, Class<T> clazz) {
        try {
            return getVal(key, guavaKey, clazz);
        } catch (Exception e) {
            remove(key, guavaKey);
            if (clazz == List.class) {
                ArrayList list = new ArrayList();
                set(key, list, guavaKey);
                return (T) new ArrayList();
            }
            return null;
        }
    }


    /**
     * 获取
     *
     * @param key
     * @return
     * @throws ExecutionException
     */
    @SuppressWarnings("unchecked")
    private <T> T getVal(Object key, Guava guavaKey, Class<T> clazz) throws ExecutionException {
        Cache<Object, T> switchCache = getCache(guavaKey);
        T resultVal = switchCache.get(key, new Callable<T>() {
            @Override
            public T call() {
                return null;
            }
        });
        return resultVal;
    }


    /**
     * 添加
     *
     * @param key
     * @param value
     * @return
     */
    public synchronized <T> T set(Object key, T value, Guava guavaKey) {
        Cache<Object, T> cache = getCache(guavaKey);
        cache.put(key, value);
        return value;
    }


    /**
     * 长度
     *
     * @return
     */
    public synchronized long size(Guava guavaKey) {
        Cache<Object, Object> cache = getCache(guavaKey);
        return cache.size();
    }

    /**
     * 移出
     *
     * @param key
     */
    public synchronized void remove(Object key, Guava guavaKey) {
        Cache<Object, Object> cache = getCache(guavaKey);
        cache.invalidate(key);
    }


    public synchronized void removeIfExist(Object key, Guava guavaKey) {
        Cache<Object, Object> cache = getCache(guavaKey);
        if (cache != null && key != null) {
            cache.invalidate(key);
        }
    }


    /**
     * 删除所有缓存
     *
     * @return
     */
    public synchronized void removeAll(Guava guavaKey) {
        Cache<Object, Object> cache = getCache(guavaKey);
        cache.invalidateAll();
    }

}
