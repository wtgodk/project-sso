package cn.godk.sso;


import cn.godk.sso.manager.SecurityManager;
import cn.godk.sso.bean.Permit;

/**
 *
 *
 *    sso 登录 管理入口
 *
 * @author wt
 * @program project-sso
 * @create 2020-09-16  16:42
 */
public class SsoLoginHelper {


    private static SecurityManager securityManager;

    /**
     *   系统登录
     * @param appId  服务 ID
     * @param username  用户名
     * @param password 密码
     */
    public static Permit login(String appId, String username, String password, Permit.Type type){
        return securityManager.login(appId, username, password,type);
    }

    /**
     *  校验token是否有效
     * @param token
     */
    public static Permit check(String token,String appId){
        return  securityManager.check(token,appId);
    }


    /**
     *   登出
     * @param token
     */
    public static void logout(String token,String appId){
        securityManager.logout(token,appId);
    }


    public static SecurityManager getSecurityManager() {
        // todo 加一个默认的
        return securityManager;
    }

    public static void setSecurityManager(SecurityManager securityManager) {
        SsoLoginHelper.securityManager = securityManager;
    }
}