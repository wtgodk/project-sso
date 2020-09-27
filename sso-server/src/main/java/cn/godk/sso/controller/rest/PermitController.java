package cn.godk.sso.controller.rest;

import cn.godk.sso.bean.Permit;
import cn.godk.sso.bean.result.Result;
import cn.godk.sso.controller.base.BaseController;
import cn.godk.sso.handler.VerificationHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 *
 * permit manager Controller (simple)
 * @author wt
 * @program project-sso
 * @create 2020-09-22  16:44
 */
@RequestMapping("/permit")
@RestController
@Slf4j
public class PermitController extends BaseController {

    @Resource
    private VerificationHandler verificationHandler;

    /**
     *   permit 令牌管理，查询所有令牌
     * @return
     */
    @RequestMapping("/all")
    public Result<List<Permit>> permit(){
        log.info("[{}] permit manager : query all ",new Date());
        List<Permit> permits = verificationHandler.get();
        return new Result<>(permits);
    }

    /**
     *   remove token by token
     * @param token
     * @return
     */
    @RequestMapping("/remove")
    public Result<String> remove(String token){
        log.info("[{}] permit manager : remove by token ",new Date());
        verificationHandler.del(token);
        return new Result<>();
    }


}
