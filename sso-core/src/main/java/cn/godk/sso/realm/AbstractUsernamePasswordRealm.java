package cn.godk.sso.realm;

import cn.godk.sso.exception.AccountErrorLoginFailException;
import cn.godk.sso.exception.DenyLoginFailException;
import cn.godk.sso.manager.permission.PermissionManager;
import cn.godk.sso.vo.CertificationInfo;
import cn.godk.sso.vo.LoginUser;
import cn.godk.sso.vo.PermissionInfo;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.Set;

/**
 * @author wt
 * @program project-sso
 * @create 2020-09-16  17:14
 */
@Setter
@Getter
@Slf4j
public abstract class AbstractUsernamePasswordRealm implements Realm {
    /**
     * 权限认证 开启
     */
    private boolean permission = false;

    /**
     *  权限严格校验，必须设置了service permission的服务才可以登录 {该参数仅在开启权限认证时生效}
     */
    private boolean forcePermissionVerify = false;

    /**
     *  服务登陆权限管理
     */
    private PermissionManager permissionManager;


    public AbstractUsernamePasswordRealm() {
    }

    public AbstractUsernamePasswordRealm(PermissionManager permissionManager) {
        permission = true;
        if (permissionManager == null) {
            throw new RuntimeException("If permission authentication is enabled, [PermissionManager] must be configured");
        }
        this.permissionManager = permissionManager;
    }

    @Override
    public CertificationInfo login(String appId, String username, String password) {
        LoginUser loginUser = authenticate(username, password);
        if (loginUser != null && permission) {
            log.debug("[{}]  login permission verification ", new Date());
            if (permissionManager == null) {
                throw new RuntimeException("If permission authentication is enabled, [PermissionManager] must be configured");
            }
            PermissionInfo permissionInfo = authorize(username);
            Set<String> roles = permissionInfo.getRoles();
            // 是否可以登录该系统、目前仅使用角色进行管理，未进行详细的权限控制（感觉不太需要详细的权限控制，仅作登录限制即可）
            Set<String> rolesByAppId = permissionManager.getRolesByAppId(appId);

            // TODO  可能需要加一个如果用户 roles为空时的处理
            if (rolesByAppId != null && rolesByAppId.size() > 0) {
                rolesByAppId.retainAll(roles);
                if (rolesByAppId.size() == 0) {
                    throw new DenyLoginFailException("The user does not have permission to log in to the system");
                }
            }else if(forcePermissionVerify){
                throw new DenyLoginFailException("The user does not have permission to log in to the system");
            }


        } else if (loginUser == null) {
            throw new AccountErrorLoginFailException("Account password does not match");
        }
        // 登陆成功
        return new CertificationInfo(username, password, loginUser);
    }

    /**
     * 验证用户
     *
     * @param username 用户名
     * @param password 密码
     * @return
     */
    abstract LoginUser authenticate(String username, String password);

    /**
     * 获取授权内容
     *
     * @param username 用户名
     * @return
     */
    abstract PermissionInfo authorize(String username);


}
