package cn.godk.sso.manager;

import java.util.Set;

/**
 *
 *  TODO  权限管理 Manager
 * @author wt
 * @program project-sso
 * @create 2020-09-17  13:22
 */
public interface PermissionManager {

    /**
     *  获取允许登录指定app的角色
     * @param appId
     * @return
     */
    Set<String> getRolesByAppId(String appId);
}
