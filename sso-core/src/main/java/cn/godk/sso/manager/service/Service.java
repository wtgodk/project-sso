package cn.godk.sso.manager.service;

import cn.godk.sso.bean.Permit;
import com.google.common.collect.Sets;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * @author wt
 * @program project-sso
 * @create 2020-09-14  10:08
 */
@Setter
@Getter
public class Service  implements Serializable {


    /**
     * 服务ID 集合
     */
    private Set<String> appId = Sets.newHashSet();
    /**
     * 服务登录类型
     */
    private Permit.Type type;
    /**
     * 用户 唯一 值。
     */
    private String username;
    /**
     *  使用的 token内容
     */
    private String token;


    public Service() {
    }




}
