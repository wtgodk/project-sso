package cn.godk.sso.manager;

import cn.godk.sso.bean.Permit;
import cn.godk.sso.handler.VerificationHandler;
import cn.godk.sso.manager.service.ServiceManager;
import cn.godk.sso.manager.user.UserManager;
import cn.godk.sso.realm.Realm;
import cn.godk.sso.vo.CertificationInfo;
import cn.godk.sso.vo.LoginUser;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * 安全管理器 ，提供系统 登录验证、登录推出等服务
 *
 * @author wt
 * @program project-sso
 * @create 2020-09-16  17:08
 */
@Setter
@Getter
@Slf4j
public class DefaultSecurityManager implements SecurityManager {

    /**
     * service manager
     */
    private ServiceManager serviceManager;
    /**
     * cookie token key生成 handler
     */
    private VerificationHandler verificationHandler;
    /**
     * 在线用户缓存
     */
    private UserManager userManager;
    /**
     * 自定义验证方法 插入
     */
    private Realm realm;


    public DefaultSecurityManager(ServiceManager serviceManager, Realm realm) {
        this.serviceManager = serviceManager;
        this.realm = realm;
    }

    @Override
    public Permit login(String appId, String username, String password, Permit.Type type) {
        log.debug("[{}] securityManager login operation , param [appId,username,password,type]->[{},{},{},{}]", new Date(), appId, username, password, type.name());
        // 只要不抛出异常 说明登录成功
        CertificationInfo authenticate = realm.login(appId, username, password);
        LoginUser loginUser = authenticate.getLoginUser();
        Permit permit = verificationHandler.create(loginUser.getUsername(), appId, type);
        // 加一个 permit 类型
        serviceManager.updateService(permit, appId);

        //    userManager.create(loginUser.getUsername(),permit);
        return permit;
    }

    @Override
    public void logout(String token, String appId) {
        log.info("[{}] securityManager logout operation , param [token,appId]->[{},{}]", new Date(), token, appId);
        verificationHandler.del(token);
        serviceManager.delByToken(token);
    }

    @Override
    public Permit check(String token, String appId) {
        if (StringUtils.isBlank(token)) {
            return null;
        }
        Permit permit = verificationHandler.get(token);
        if (permit == null) {
            // 不在线
            return null;
        }
        // 添加、刷新 service
        serviceManager.updateService(permit, appId);
        return permit;
    }
}
