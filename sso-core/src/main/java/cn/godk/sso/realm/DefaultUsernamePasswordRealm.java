package cn.godk.sso.realm;

import cn.godk.sso.vo.LoginUser;
import cn.godk.sso.vo.PermissionInfo;

import java.util.Map;

/**
 * @author wt
 * @program project-sso
 * @create 2020-09-17  13:36
 */
public class DefaultUsernamePasswordRealm extends AbstractUsernamePasswordRealm {


    @Override
    LoginUser authenticate(String username, String password) {
        LoginUser loginUser = new LoginUser();
        loginUser.setUsername("test");
        Map<String, Object> params = loginUser.getParams();
        params.put("name","godk");
        params.put("id",1);
        return loginUser;
    }

    @Override
    PermissionInfo authorize(String username) {
        //TODO  权限相关搜索
        return new PermissionInfo();
    }
}
