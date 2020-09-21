package cn.godk.sso.manager.service;

import cn.godk.sso.bean.Permit;

/**
 *
 *   服务管理接口
 * @author wt
 * @program project-sso
 * @create 2020-09-14  10:07
 */
public interface ServiceManager {

    /**
     *   获取 当前service 是否存在
     * @return
     */
    Service getService(Permit permit);

    /**
     * 移除 指定service
     * @param appId
     */
    void  delByAppId(String appId);

    /**
     *  清除指定token，所有使用该token的服务下线
     * @param token  通行证
     */
    void delByToken(String token);

    /**
     *   移除指定用户的service
     */
    void delByUsername(String userKey);

    /**
     *  清除指定通行证，所有使用该通行证的服务下线
     */
    void delService(Permit permit);

    /**
     * 更新 service 在线时间
     * @param permit 通行证
     * @param appId 服务ID
     * @return
     */
    Service updateService(Permit permit,String appId);



}
