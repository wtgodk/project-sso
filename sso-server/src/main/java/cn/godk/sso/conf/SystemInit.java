package cn.godk.sso.conf;

import cn.godk.sso.SsoLoginHelper;
import cn.godk.sso.bean.Permit;
import cn.godk.sso.cache.CacheManager;
import cn.godk.sso.cache.guava.Guava;
import cn.godk.sso.cache.guava.GuavaCacheManager;
import cn.godk.sso.handler.DefaultHandler;
import cn.godk.sso.handler.VerificationHandler;
import cn.godk.sso.handler.rule.DefaultRule;
import cn.godk.sso.manager.DefaultSecurityManager;
import cn.godk.sso.manager.SecurityManager;
import cn.godk.sso.manager.permission.DefaultPermissionManager;
import cn.godk.sso.manager.permission.PermissionManager;
import cn.godk.sso.manager.service.DefaultServiceManager;
import cn.godk.sso.manager.service.Service;
import cn.godk.sso.manager.service.ServiceManager;
import cn.godk.sso.realm.DefaultUsernamePasswordRealm;
import cn.godk.sso.realm.IUserService;
import cn.godk.sso.realm.Realm;
import cn.godk.sso.vo.CertificationInfo;
import cn.godk.sso.vo.PermissionInfo;
import com.google.common.collect.Maps;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Set;

/**
 * 系统初始化
 *
 * @author wt
 * @program project-sso
 * @create 2020-09-18  15:11
 */
@Component
public class SystemInit {


    @Resource
    private IUserService userService;
    @Resource
    private PermissionConf permissionConf;
    /**
     * service manager
     *
     * @param cacheManager
     * @return
     */
    @Bean
    public ServiceManager serviceManager(CacheManager<Service> cacheManager) {
        return new DefaultServiceManager(cacheManager);
    }

    /**
     * service cache manager
     *
     * @return
     */
    @Bean
    public CacheManager<Service> serviceCacheManager() {
        return new GuavaCacheManager<>(Guava.SERVICE);
    }

    /**
     * service cache manager
     *
     * @return
     */
    @Bean
    public CacheManager<Permit> tokenCacheManager() {
        return new GuavaCacheManager<>(Guava.TOKEN);
    }

    /**
     *   Verification Handler for permit manager
     * @param tokenCacheManager
     * @return
     */
    @Bean
    public VerificationHandler verificationHandler(CacheManager<Permit> tokenCacheManager){
        DefaultHandler defaultHandler = new DefaultHandler();
        defaultHandler.setRule(new DefaultRule());
        defaultHandler.setCacheManager(tokenCacheManager);
        return defaultHandler;
    }
    /**
     * security manager
     *
     * @param serviceManager
     * @return
     */
    @Bean
    public SecurityManager securityManager(ServiceManager serviceManager,VerificationHandler verificationHandler,Realm realm) {
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager(serviceManager,realm);
        defaultSecurityManager.setVerificationHandler(verificationHandler);
        SsoLoginHelper.setSecurityManager(defaultSecurityManager);
        return defaultSecurityManager;
    }

    /**
     *   用户信息验证、权限信息获取等操作
     * @return
     */
    @Bean
    public Realm realm(PermissionManager permissionManager){
        DefaultUsernamePasswordRealm defaultUsernamePasswordRealm = new DefaultUsernamePasswordRealm(userService);
        defaultUsernamePasswordRealm.setPermission(true);
        defaultUsernamePasswordRealm.setPermissionManager(permissionManager);
        return defaultUsernamePasswordRealm;
    }

    @Bean
    public PermissionManager permissionManager()
    {
        Map<String,PermissionInfo> serviceRoles = permissionConf.getServiceRoles();
        return new DefaultPermissionManager(new GuavaCacheManager<>(Guava.PERMISSION), serviceRoles);
    }




}
