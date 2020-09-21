package cn.godk.sso.manager.service;

import cn.godk.sso.bean.Permit;
import lombok.Getter;
import lombok.Setter;

/**
 * @author wt
 * @program project-sso
 * @create 2020-09-14  10:08
 */
@Setter
@Getter
public class Service {



    public Service() {
    }

    public Service(String appId) {
        this.appId = appId;
    }

    /**
     *  服务ID
     */
    private String appId;
    /**
     *   服务登录类型
     */
    private Permit.Type type;

    /**
     *  用户 唯一 值。
     */
    private String username;




}
