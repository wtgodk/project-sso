package cn.godk.sso.cache;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * redis 缓存
 *
 * @author wt
 * @program project-sso
 * @create 2020-10-20  10:02
 */
public interface IRedisCacheManager<T> extends CacheManager<T> {

    /**
     * keys
     *
     * @param keys keys
     * @return
     */
    Set<String> keys(String keys);

    /**
     * redis m get 方法
     *
     * @param keys keys
     * @return
     */
    List<T> mGet(String... keys);

    /**
     * redis m get 方法
     *
     * @param keys keys
     * @return
     */
    Map<String, T> mGet(List<String> keys);

    /**
     * 清空缓存
     */
    void clear();

    /**
     * 缓存条数统计
     *
     * @return
     */
    long size();

    /**
     * scan  遍历 key
     *
     * @param match 通配符
     * @param count 一次获取数目
     * @return
     */
    public Set<T> scan(String match, int count);

}
