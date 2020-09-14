package cn.godk.sso.manager.service;

import cn.godk.sso.Service;

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
    Service getService();

    /**
     * 移除 指定service
     * @param appId
     */
    void  delByAppId(String appId);

    /**
     *  清除指定token，所有使用该token的服务下线
     * @param token  token
     */
    void delByToken(String token);

    /**
     *  清除指定token，所有使用该token的服务下线
     */
    void delService();

    /**
     *   更新 service 在线时间
     * @return
     */
    Service updateService();



}
