package cn.godk.sso.service.impl;

import cn.godk.sso.conf.JdbcConfig;
import cn.godk.sso.dao.UserRepository;
import cn.godk.sso.realm.IUserService;
import cn.godk.sso.vo.LoginUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

/**
 *
 *
 *   user service interface impl
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
        log.debug("[{}] user service interface : queryUserByUsernameAndPassword , param [username,password]->[{},{}]",new Date(),username,password);
        String loginCheck = jdbcConfig.getLoginCheck();
        Map<String, Object> userInfo = userRepository.select(loginCheck, username, password);
        if(userInfo ==null){
            return null;
        }
        return  new LoginUser(username,userInfo);
    }
}
