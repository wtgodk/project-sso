package cn.godk.sso.vo;

import lombok.Getter;
import lombok.Setter;

/**
 *
 *  用户 认证信息
 *
 * @author wt
 * @program project-sso
 * @create 2020-09-16  17:18
 */
@Setter
@Getter
public class CertificationInfo {

    public CertificationInfo(String username, String password,LoginUser loginUser) {
        this.username = username;
        this.password = password;
        this.loginUser = loginUser;
    }

    /**
     *  用户名
     */
    private String username;

    /**
     *  密码
     */
    private String password;
    /**
     *   用户详细信息
     */
    private LoginUser loginUser;

}
