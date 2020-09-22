package cn.godk.sso.manager.service;


import cn.godk.sso.cache.CacheManager;
import lombok.Getter;
import lombok.Setter;

/**
 * service manager 抽象类
 * <p>
 * 定义输入参数，中间逻辑等
 *
 * @author wt
 * @program project-sso
 * @create 2020-09-14  10:10
 */
@Setter
@Getter
public abstract class AbstractServiceManager implements ServiceManager {
    /**
     * 缓存接口
     */
    private CacheManager<Service> cacheManager;


    public AbstractServiceManager(CacheManager<Service> cacheManager) {
        this.cacheManager = cacheManager;
    }
}
