package cn.godk.sso.cache.guava;

import cn.godk.sso.cache.CacheManager;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 本地缓存
 * <p>
 * 本地缓存永久存储 不会过期 需要手动过期
 *
 * @author wt
 * @program project-sso
 * @create 2020-09-15  17:02
 */
@Setter
@Getter
public class GuavaCacheManager<T> implements CacheManager<T> {

    public static GuavaCache guavaCache = GuavaCache.getInstance();
    public Class<T> clazz;
    private Guava guava;

    public GuavaCacheManager(Guava guava) {
        Cache<String, T> cache = CacheBuilder.newBuilder().maximumSize(100).build();
        guavaCache.addCache(guava, cache);
        this.guava = guava;
    }

    /**
     * 带有 失效监听的缓存
     *
     * @param guava    缓存 key
     * @param listener 监听
     */
    public GuavaCacheManager(Guava guava, RemovalListener<String, T> listener) {
        Cache<String, T> cache = CacheBuilder.newBuilder().maximumSize(100).removalListener(listener).build();
        guavaCache.addCache(guava, cache);
        this.guava = guava;
    }

    @Override
    public T get(String key) {
        return (T) guavaCache.get(key, guava, Object.class);
    }

    @Override
    public List<T> all() {
        return (List<T>) guavaCache.getAllValue(guava,Object.class);
    }

    @Override
    public void del(String key) {
        guavaCache.remove(key, guava);
    }

    @Override
    public T delIfExist(String key) {
        T t = get(key);
        if (t != null) {
            del(key);
        }
        return t;
    }

    @Override
    public T update(String key) {
        return null;
    }

    @Override
    public T create(String key, T value, long expired) {
        guavaCache.set(key, value, guava);
        return value;
    }


}
