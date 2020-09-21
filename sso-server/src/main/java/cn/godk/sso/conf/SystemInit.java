package cn.godk.sso.conf;

import cn.godk.sso.SsoLoginHelper;
import cn.godk.sso.cache.CacheManager;
import cn.godk.sso.cache.guava.Guava;
import cn.godk.sso.cache.guava.GuavaCacheManager;
import cn.godk.sso.manager.DefaultSecurityManager;
import cn.godk.sso.manager.SecurityManager;
import cn.godk.sso.manager.service.DefaultServiceManager;
import cn.godk.sso.manager.service.Service;
import cn.godk.sso.manager.service.ServiceManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 *
 *
 *  系统初始化
 * @author wt
 * @program project-sso
 * @create 2020-09-18  15:11
 */
@Component
public class SystemInit {


    /**
     *   service manager
     * @param cacheManager
     * @return
     */
    @Bean
    public ServiceManager serviceManager(CacheManager<Service> cacheManager){
       return new DefaultServiceManager(cacheManager);
    }

    /**
     *  service cache manager
     * @return
     */
    @Bean
    public CacheManager<Service> serviceCacheManager(){
        return new GuavaCacheManager<>(Guava.SERVICE);
    }


    /**
     *  security manager
     * @param serviceManager
     * @return
     */
    @Bean
    public SecurityManager securityManager(ServiceManager serviceManager){
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager(serviceManager);
        SsoLoginHelper.setSecurityManager(defaultSecurityManager);
        return defaultSecurityManager;
    }




}
