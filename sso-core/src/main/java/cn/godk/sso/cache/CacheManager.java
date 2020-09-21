package cn.godk.sso.cache;

/**
 *
 *  缓存接口
 *
 * @author wt
 * @program project-sso
 * @create 2020-09-14  10:23
 */
public interface CacheManager<T> {

    /**
     *  获取 缓存
     * @param key
     * @return
     */
    T get(String key);

    /**
     *   删除缓存
     * @param key
     */
    void del(String key);

    /**
     *   如果存在则删除
     * @param key
     */
    T delIfExist(String key);

    /**
     *  更新 缓存
     * @param key
     * @return
     */
    T update(String key);

    /**
     *   创建缓存
     * @param key  key
     * @param value  缓存值
     * @param expired 过期时间  毫秒
     * @return
     */
    T create(String key,T value,long expired);
}
