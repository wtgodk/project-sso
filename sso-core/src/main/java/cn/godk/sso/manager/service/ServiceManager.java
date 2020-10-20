package cn.godk.sso.manager.service;

import cn.godk.sso.bean.Permit;

import java.util.List;

/**
 * 服务管理接口
 *
 * @author wt
 * @program project-sso
 * @create 2020-09-14  10:07
 */
public interface ServiceManager {

    /**
     * 获取 当前service 是否存在
     *
     * @param permit 参数
     * @return
     */
    Service getService(Permit permit);

    /**
     * 获取所有在线service
     *
     * @return
     */
    List<Service> getService();

    /**
     * 清除指定token，所有使用该token的服务下线
     *
     * @param token 通行证
     */
    Service delByToken(String token);

    /**
     * 清除指定通行证，所有使用该通行证的服务下线
     */
    Service delService(Permit permit);

    /**
     * 更新 service 在线时间
     *
     * @param permit 通行证
     * @param appId  服务ID
     * @return
     */
    Service updateService(Permit permit, String appId);


}
