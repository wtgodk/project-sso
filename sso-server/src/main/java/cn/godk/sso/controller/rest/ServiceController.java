package cn.godk.sso.controller.rest;

import cn.godk.sso.bean.result.Result;
import cn.godk.sso.controller.base.BaseController;
import cn.godk.sso.manager.service.Service;
import cn.godk.sso.manager.service.ServiceManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 *
 * service manager controller
 * @author wt
 * @program project-sso
 * @create 2020-09-22  16:41
 */
@RequestMapping("/service")
@Slf4j
@RestController
public class ServiceController extends BaseController {


    @Resource
    private ServiceManager serviceManager;

    /**
     *  simple service manager query all service
     * @return
     */
    @RequestMapping("/all")
    public Result<List<Service>> service(){
        log.info("[{}] service manager :load all online service",new Date());
        List<Service> service = serviceManager.getService();
        return new Result<>(service);
    }


    /**
     *   simple service Manager  remove service by app id
     * @param appId 服务ID
     * @return
     */
    @RequestMapping("/removeByAppId")
    public Result<String> removeByAppId(String appId){
        log.info("[{}] service manager : remove service by app id ,param [appId]->[{}]",new Date(),appId);
        serviceManager.delByAppId(appId);
        return new Result<>();
    }

    /**
     *  simple service manager  remove service by  token
     * @param token 在线码
     * @return
     */
    @RequestMapping("/removeByToken")
    public Result<String> removeByToken(String token){
        log.info("[{}] service manager : remove service by token ,param [token]->[{}]",new Date(),token);
        serviceManager.delByToken(token);
        return new Result<>();
    }


    /**
     *   simple service Manager remove service  by username
     * @param username 用户名
     * @return
     */
    @RequestMapping("/removeByUser")
    public Result<String> removeByUser(String username){
        log.info("[{}] service manager : remove service by username ,param [username]->[{}]",new Date(),username);
        serviceManager.delByUsername(username);
        return new Result<>();
    }



}
