package cn.godk.sso.service.impl;

import cn.godk.sso.conf.JdbcConfig;
import cn.godk.sso.conf.constant.Role;
import cn.godk.sso.dao.UserRepository;
import cn.godk.sso.realm.IUserService;
import cn.godk.sso.vo.LoginUser;
import cn.godk.sso.vo.PermissionInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * user service interface impl
 *
 * @author wt
 * @program project-sso
 * @create 2020-09-27  09:32
 */
@Service
@Slf4j
public class UserServiceImpl implements IUserService {

    @Resource
    private JdbcConfig jdbcConfig;

    @Resource(name = "userRepository")
    private UserRepository userRepository;


    @Override
    public LoginUser queryUserByUsernameAndPassword(String username, String password) {
        log.debug("[{}] user service interface : queryUserByUsernameAndPassword , param [username,password]->[{},{}]", new Date(), username, password);
        String loginCheck = jdbcConfig.getLoginCheck();
        Map<String, Object> userInfo = userRepository.queryForMap(loginCheck, username, password);
        if (userInfo == null) {
            return null;
        }
        return new LoginUser(username, userInfo);
    }

    @Override
    public PermissionInfo queryUserPermissionByUsername(String username) {
        log.debug("[{}] user service interface : queryUserPermissionByUsername , param [username]->[{}]", new Date(), username);
        String roleQuery = jdbcConfig.getRoleQuery();
        List<Map<String, Object>> roleMapList = userRepository.queryForList(roleQuery, username);
        PermissionInfo permissionInfo = new PermissionInfo();
        if (roleMapList != null && roleMapList.size() > 0) {
            Set<String> roles = permissionInfo.getRoles();
            for (Map<String, Object> roleMap : roleMapList) {
                if (roleMap != null) {
                    String role = (String) roleMap.get(Role.ROLE_NAME);
                    if (StringUtils.isNotBlank(role)) {
                        roles.add(role);
                    }
                }
            }
        }
        return permissionInfo;
    }
}
