package cn.godk.sso.controller.rest;

import cn.godk.sso.manager.service.Service;
import cn.godk.sso.manager.service.ServiceManager;
import cn.godk.sso.bean.Permit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

/**
 *
 *
 *   登录、登出  相关操作 controller
 * @author wt
 * @program project-sso
 * @create 2020-09-11  16:40
 */
@RestController
@RequestMapping("/")
@Slf4j
public class MainController {


    @Resource
    private ServiceManager serviceManager;

    /**
     *  登陆验证方法
     * @param username 用户名 TRUE
     * @param password 密码 TRUE
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login(String appId,String username,String password){
        log.info("[{}] <API> username password information check ,param [username,password]->[{},{}]",new Date(),username,password);
        return "/login";
    }

    /**
     *  登陆状态校验
     * @param appId 系统唯一ID  TRUE
     * @param token 登录状态信息token TRUE
     * @return
     */
    @RequestMapping(value = "/check",method = RequestMethod.POST)
    public String check(String appId, String token){
        log.info("[{}] <API> login status check ,param [appId,token]->[{},{}]",new Date(),appId,token);
        Service service =serviceManager.getService(new Permit(token));
        if(service==null){
            //token 失效
            return "/token";
        }
        String serverId = service.getAppId();
        if(serverId.equals(appId)){
            // 同一个服务
        }else{
            // 新增服务
        //    serviceManager.updateService()


        }

        return "/token";
    }


    /**
     *  系统登出
     * @param appId 系统唯一ID  TRUE
     * @param token 登录状态信息token TRUE
     * @return
     */
    @RequestMapping(value = "/logout",method = RequestMethod.POST)
    public String logout(String appId,String token){
        log.info("[{}] <API> system logout ,param [appId,token]->[{},{}]",new Date(),appId,token);
        return "/token";
    }



}
