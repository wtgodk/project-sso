package cn.godk.sso.controller.page;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

/**
 *
 *   页面跳转接口controller
 *
 * @author wt
 * @program project-sso
 * @create 2020-09-11  16:36
 */
@Controller
@Slf4j
@RequestMapping("/")
public class MainPageController {

    /**
     *   登录页面，该页面需要存在登陆成功后跳转地址
     *
     *
     * @param backUrl  登陆成功后 回调地址  TRUE
     * @return
     */
    @RequestMapping("/login.html")
    public String login(@RequestParam(name = "backUrk") String backUrl){
        log.info("[{}] load page : login ,param [backUrl]->[{}]",new Date(),backUrl);
        return "/login";
    }

}
