package cn.godk.sso;


import cn.godk.sso.bean.Permit;
import cn.godk.sso.manager.SecurityManager;

/**
 * sso 登录 管理入口
 *
 * @author wt
 * @program project-sso
 * @create 2020-09-16  16:42
 */
public class SsoLoginHelper {


    private static SecurityManager securityManager;

    /**
     * 系统登录
     *
     * @param appId    服务 ID
     * @param username 用户名
     * @param password 密码
     */
    public static Permit login(String appId, String username, String password, Permit.Type type) {
        return getSecurityManager().login(appId, username, password, type);
    }

    /**
     * 校验token是否有效
     *
     * @param token
     */
    public static Permit check(String token, String appId) {
        return getSecurityManager().check(token, appId);
    }


    /**
     * 登出
     *
     * @param token
     */
    public static void logout(String token, String appId) {
        getSecurityManager().logout(token, appId);
    }


    public static SecurityManager getSecurityManager() {
        // todo 加一个默认的
        return securityManager;
    }

    public static void setSecurityManager(SecurityManager securityManager) {
        SsoLoginHelper.securityManager = securityManager;
    }
}
