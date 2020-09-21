package cn.godk.sso.realm;

import cn.godk.sso.vo.CertificationInfo;

/**
 *
 *  认证方法
 * @author wt
 * @program project-sso
 * @create 2020-09-16  17:11
 */
public interface Realm {


    /**
     *   获取身份认证信息
     * @param username  用户名
     * @param password 密码
     * @param appId  登录服务 id，可以用于控制权限
     * @return
     */
    CertificationInfo login(String appId,String username, String password);


    // TODO 后续添加权限认证

}
