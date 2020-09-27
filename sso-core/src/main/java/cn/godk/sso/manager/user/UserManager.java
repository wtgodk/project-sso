package cn.godk.sso.manager.user;

import cn.godk.sso.bean.Permit;
import cn.godk.sso.vo.LoginUser;

import java.util.List;

/**
 *
 *  在线用户管理
 * @author wt
 * @program project-sso
 * @create 2020-09-27  15:44
 */
public interface UserManager {

    /**
     *  获取指定用户的在线信息
     * @param username  用户名
     * @return
     */
    Permit getOnline(String username);

    /**
     *  获取所有 在线用户
     * @return
     */
    List<Permit>  getAllOnline();

    /**
     *  添加在线用户缓存
     * @param username 用户名
     * @param permit  在线信息
     */
    void create(String username,Permit permit);

    /**
     *
     *  移除指定在线用户（强制用户下线）
     * @param username  用户名
     */
    void remove(String username);
}
