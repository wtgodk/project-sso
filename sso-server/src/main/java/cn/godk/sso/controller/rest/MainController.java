package cn.godk.sso.controller.rest;

import cn.godk.sso.SsoLoginHelper;
import cn.godk.sso.bean.Permit;
import cn.godk.sso.bean.result.Result;
import cn.godk.sso.manager.service.ServiceManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 登录、登出  相关操作 controller
 *
 * @author wt
 * @program project-sso
 * @create 2020-09-11  16:40
 */
@RestController
@RequestMapping("/")
@Slf4j
public class MainController {

    /**
     * 登陆验证方法
     * @param username 用户名 TRUE
     * @param password 密码 TRUE
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result<Permit> login(String appId, String username, String password) {
        log.info("[{}] <API> username password information check ,param [appId,username,password]->[{},{},{}]", new Date(), appId,username, password);
        Permit permit = SsoLoginHelper.login(appId,username,password, Permit.Type.token);
         if(permit==null){
             return new Result<>(1,null,"fail");
         }
        return new Result<>(permit);
    }

    /**
     * 登陆状态校验
     * @param permit token信息 {@link Permit}
     * @return
     */
    @RequestMapping(value = "/check", method = RequestMethod.POST)
    public Result<Permit> check(@RequestBody Permit permit) {
        log.info("[{}] <API> login status check ,param [permit]->[{}]", new Date(), permit.toString());
        Permit check = SsoLoginHelper.check(permit.getKey(), permit.getAppId());
        return new Result<>(check);
    }

    /**
     * 系统登出
     * @param appId 系统唯一ID  TRUE
     * @param token 登录状态信息token TRUE
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public Result<Permit> logout(String appId, String token) {
        log.info("[{}] <API> system logout ,param [appId,token]->[{},{}]", new Date(), appId, token);
        SsoLoginHelper.logout(token,appId);
        return new Result<>();
    }


}
