package cn.godk.sso.controller.page;

import cn.godk.sso.SsoLoginHelper;
import cn.godk.sso.conf.Constant;
import cn.godk.sso.manager.service.Service;
import cn.godk.sso.rollback.constant.RollbackConstant;
import cn.godk.sso.cookie.CookieUtils;
import cn.godk.sso.bean.Cookie;
import cn.godk.sso.bean.Permit;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
public class LoginController {



    /**
     *   登录页面，该页面需要存在登陆成功后跳转地址
     *
     *
     * @param backUrl  登陆成功后 回调地址  TRUE
     * @return
     */
    @RequestMapping("/login.html")
    public String login(@RequestParam(name = "backUrl",required = false) String backUrl, String appId,HttpServletRequest request,  Model model){
        log.info("[{}] load page : login ,param [backUrl]->[{}]",new Date(),backUrl);
        // 校验 是否已经登陆了
        String token = CookieUtils.get(request, Constant.COOKIE_NAME);
       Permit cookie =  SsoLoginHelper.check(token,appId);
      if(cookie!=null){
          // 已经登陆了直接跳转到指定页面
          StringBuilder rollback = new StringBuilder();
        if(StringUtils.isNotBlank(backUrl)){
            rollback.append(backUrl);
            if(backUrl.contains("?")){
                rollback.append("&");
            }else{
                rollback.append("?");
            }
            // TODO 这里可能需要更改
            rollback.append(RollbackConstant.SERVICE_TICKET).append("=").append(cookie.getKey());
        }else{
            rollback.append("/");
        }
          return  "redirect:" +rollback.toString() ;
      }
        model.addAttribute("backUrl",backUrl);
        model.addAttribute("appId",appId);
        return "/login.html?backUrl=" + backUrl +"&appId="+ appId;
    }

    /**
     *   校验登录
     *
     * @param username  用户名
     * @param password  密码
     * @param rememberMe 记住我  0 默认  1、记住
     * @param backUrl 跳转地址
     * @return
     */
    @RequestMapping("/check_login")
    public  String login(@RequestParam(name = "username")String username,
                         @RequestParam(name = "password")String password,
                         @RequestParam(name = "appId")String appId,
                         @RequestParam(name = "rememberMe",defaultValue = "0")int rememberMe,
                         @RequestParam(name = "backUrl",required = false)String backUrl,HttpServletResponse response){
        log.info("[{}] load page : login check ,param [username,password,appId,rememberMe,backUrl]->[{},{},{},{},{}]",new Date(),username,password,appId,rememberMe,backUrl);
        Permit permit = SsoLoginHelper.login(appId, username, password, Permit.Type.cookie);
        if(permit==null){
            // 登录失败 ，验证不通过
            return  "redirect:/login.html?backUrl="+ backUrl;
        }
        CookieUtils.set(response,Constant.COOKIE_NAME,permit.getKey(),rememberMe == 1);
        StringBuilder rollback = new StringBuilder();
        if(StringUtils.isNotBlank(backUrl)){
            rollback.append(backUrl);
            if(backUrl.contains("?")){
                rollback.append("&");
            }else{
                rollback.append("?");
            }
            // TODO 这里可能需要更改
            rollback.append(RollbackConstant.SERVICE_TICKET).append("=").append(permit.getKey());
        }else{
            rollback.append("/");
        }
        return "redirect:" + rollback.toString();
    }


    /**
     *   退出登录
     * @param appId  申请退出的appId
     * @param backUrl 退出后跳转地址
     * @param request  request
     * @param response Response
     * @return
     */
    @RequestMapping("/logout")
    public String logout(@RequestParam(name = "appId") String appId,
                         @RequestParam(name = "backUrl")String backUrl,
                         HttpServletRequest request,
                         HttpServletResponse response){
        log.info("[{}] load page : logout ,param [appId,backUrl]->[{},{}]",new Date(),appId,backUrl);
        String cookie = CookieUtils.get(request, Constant.COOKIE_NAME);
        SsoLoginHelper.logout(appId, cookie);
        CookieUtils.destroy(request,response,Constant.COOKIE_NAME);

        return  "redirect:/login.html?backUrl="+ backUrl +"&appId="+ appId;
    }






}
