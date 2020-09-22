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


    @RequestMapping("/all")
    public Result<List<Service>> service(){
        log.info("[{}] service manager :load all online service",new Date());
        List<Service> service = serviceManager.getService();
        return new Result<>(service);
    }





}
