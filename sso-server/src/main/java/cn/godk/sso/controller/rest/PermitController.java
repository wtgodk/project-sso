package cn.godk.sso.controller.rest;

import cn.godk.sso.bean.Permit;
import cn.godk.sso.bean.result.Result;
import cn.godk.sso.controller.base.BaseController;
import cn.godk.sso.handler.VerificationHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 * permit manager Controller
 * @author wt
 * @program project-sso
 * @create 2020-09-22  16:44
 */
@RequestMapping("/permit")
@RestController
@Slf4j
public class PermitController extends BaseController {
    @Resource(name = "")
    private VerificationHandler verificationHandler;


    @RequestMapping("/all")
    public Result<List<Permit>> permit(){
        List<Permit> permits = verificationHandler.get();
        return new Result<>(permits);
    }
}
