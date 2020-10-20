package cn.godk.sso.cache;

import java.util.List;

/**
 * 缓存接口
 *
 * @author wt
 * @program project-sso
 * @create 2020-09-14  10:23
 */
public interface CacheManager<T> {

    /**
     * 获取 缓存
     *
     * @param key
     * @return
     */
    T get(String key);

    /**
     * 获取缓存所有内容
     *
     * @return
     */
    List<T> all();

    /**
     * 删除缓存
     *
     * @param key
     */
    void del(String key);

    /**
     * 如果存在则删除
     *
     * @param key
     * @return
     */
    T delIfExist(String key);

    /**
     * 刷新 缓存
     *
     * @param key
     * @return
     */
    T refresh(String key);

    /**
     * 创建缓存
     *
     * @param key     key
     * @param value   缓存值
     * @param expired 过期时间  毫秒
     * @return
     */
    T create(String key, T value, long expired);
}
