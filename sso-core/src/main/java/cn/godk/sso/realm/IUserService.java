package cn.godk.sso.realm;

import cn.godk.sso.vo.LoginUser;

/**
 *
 *
 *  用户管理 service  interface
 * @author wt
 * @program project-sso
 * @create 2020-09-27  09:31
 */
public interface IUserService {


    /**
     *  根据用户名密码查询用户信息
     * @param username 用户名
     * @param password  密码
     * @return
     */
    LoginUser queryUserByUsernameAndPassword(String username,String password);
}
