package cn.godk.sso.manager;


import cn.godk.sso.bean.Permit;

/**
 * 核心类 安全管理器
 *
 * @author wt
 * @program project-sso
 * @create 2020-09-16  17:01
 */
public interface SecurityManager {


    /**
     * 登录验证
     *
     * @param appId    系统服务id
     * @param username 用户名
     * @param password 密码
     * @param type     登录类型 {@link Permit.Type }
     * @return
     */
    Permit login(String appId, String username, String password, Permit.Type type);

    /**
     * 退出登录
     *
     * @param appId 系统服务id ，可以记录 那个系统申请退出
     * @param token 在线 token
     */
    void logout(String token, String appId);


    /**
     * 验证指定TOKEN是否在线
     *
     * @param appId 系统服务ID
     * @param token 待验证 token
     * @return
     */
    Permit check(String token, String appId);

}
