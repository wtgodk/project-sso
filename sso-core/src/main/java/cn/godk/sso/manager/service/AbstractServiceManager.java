package cn.godk.sso.manager.service;


import cn.godk.sso.Service;
import cn.godk.sso.cache.CacheManager;
import lombok.Getter;
import lombok.Setter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 *
 *   service manager 抽象类
 *
 *    定义输入参数，中间逻辑等
 *
 * @author wt
 * @program project-sso
 * @create 2020-09-14  10:10
 */
@Setter
@Getter
public abstract class AbstractServiceManager implements ServiceManager {

    /**
     * request
      */
    private ServletRequest request;
    /**
     * response
     */
    private ServletResponse response;

    /**
     *   缓存接口
     */
    private CacheManager<Service> cacheManager;


    public ServletRequest getRequest() {
        return request;
    }

    public void setRequest(ServletRequest request) {
        this.request = request;
    }
}
