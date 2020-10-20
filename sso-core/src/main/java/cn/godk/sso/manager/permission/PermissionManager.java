package cn.godk.sso.manager.permission;

import cn.godk.sso.vo.PermissionInfo;

import java.util.List;
import java.util.Set;

/**
 * 权限管理 Manager
 *
 * @author wt
 * @program project-sso
 * @create 2020-09-17  13:22
 */
public interface PermissionManager {


    /**
     * 查询所有角色-service缓存内容
     *
     * @return
     */
    List<PermissionInfo> queryAll();

    /**
     * 获取允许登录指定app的角色
     *
     * @param appId
     * @return
     */
    Set<String> getRolesByAppId(String appId);


    /**
     * 初始化 服务与权限内容
     */
    void initServicePermission();


    /**
     * 移除指定service的 允许登陆角色
     *
     * @param appId service id
     * @param roles 角色集合
     * @return
     */
    Set<String> removeRolesByAppId(String appId, Set<String> roles);

    /**
     * 为指定service添加角色权限
     *
     * @param appId service id
     * @param roles 角色集合
     * @return
     */
    Set<String> addRolesByAppId(String appId, Set<String> roles);

    /**
     * 全量更新 指定service 角色
     *
     * @param appId service id
     * @param roles 角色集合
     * @return
     */
    Set<String> updateRolesByAppId(String appId, Set<String> roles);

    /**
     * 清空指定service的所有角色
     *
     * @param appId
     * @return
     */
    Set<String> delAllServiceRoles(String appId);

}
