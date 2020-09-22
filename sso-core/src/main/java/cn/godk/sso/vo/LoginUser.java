package cn.godk.sso.vo;

import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * 用户信息封装
 *
 * @author wt
 * @program project-sso
 * @create 2020-09-17  13:12
 */
@Setter
@Getter
public class LoginUser {

    /**
     * 用户名
     */
    private String username;

    /**
     * 额外参数
     */
    private Map<String, Object> params = Maps.newHashMap();
}
