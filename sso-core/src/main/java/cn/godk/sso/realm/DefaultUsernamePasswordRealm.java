package cn.godk.sso.realm;

import cn.godk.sso.vo.LoginUser;
import cn.godk.sso.vo.PermissionInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 *
 *   用户信息校验、权限信息获取
 * @author wt
 * @program project-sso
 * @create 2020-09-17  13:36
 */
@Setter
@Getter
public class DefaultUsernamePasswordRealm extends AbstractUsernamePasswordRealm {

    public DefaultUsernamePasswordRealm(IUserService userService) {
        this.userService = userService;
    }

    private IUserService userService;


    @Override
    LoginUser authenticate(String username, String password) {
        return userService.queryUserByUsernameAndPassword(username,password);
    }

    @Override
    PermissionInfo authorize(String username) {
        PermissionInfo permissionInfo = userService.queryUserPermissionByUsername(username);
        return permissionInfo==null?new PermissionInfo(): permissionInfo;
    }
}
