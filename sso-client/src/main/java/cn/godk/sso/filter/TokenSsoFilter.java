package cn.godk.sso.filter;

import cn.godk.sso.bean.Permit;
import cn.godk.sso.bean.result.Result;
import cn.godk.sso.cookie.CookieUtils;
import cn.godk.sso.exception.NotLoggedInException;
import cn.godk.sso.token.handler.DefaultTokenHandler;
import cn.godk.sso.token.handler.TokenHandler;
import cn.godk.sso.utils.HttpUtil;
import com.alibaba.fastjson.TypeReference;
import lombok.Getter;
import lombok.Setter;
import org.omg.SendingContext.RunTime;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 *
 *  token 集成方式
 *
 *   登录失败将抛出异常
 *   登陆成功调用
 *
 *
 * @author wt
 * @program project-sso
 * @create 2020-09-27  14:17
 */
@Setter
@Getter
public class TokenSsoFilter extends AbstractSsoFilter {
    /**
     *   token key  default `token`
     */
    private String token = "token";
    /**
     *   获取token handler
     *
     */
    private TokenHandler tokenHandler ;

    public TokenSsoFilter() {
        tokenHandler =new DefaultTokenHandler();
    }
    public TokenSsoFilter(TokenHandler tokenHandler) {
        this.tokenHandler = tokenHandler;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        if(greenLight(req.getServletPath()) || !isAjaxRequest(req)){
            chain.doFilter(req, response);
            return;
        }
        String token = tokenHandler.get(getToken(), request);
        if(token==null){
            //可以尝试获取 cookie如果存在cookie 那么也可以校验登录
            String cookie = CookieUtils.get(req, getToken());
            if (cookie != null) {
                // 验证 token 是否有效  appId,token
                Result<Permit> result = check(cookie);
                if (result == null || result.getData() == null) {
                    // 登录失效
                    exception(request,response,new NotLoggedInException());
                    return;
                }
                // token 有效
                operation(request,response,result.getData());
            }else{
                //未登录，抛出异常
                exception(request,response,new NotLoggedInException());
                return;
            }
        }
        // 验证 token 是否有效  appId,token
        Result<Permit> result = check(token);
        if (result == null || result.getData() == null) {
            // 登录失效
            exception(request,response,new NotLoggedInException());
            return;
        }else{
            // token 有效
            operation(request,response,result.getData());
        }
        super.doFilter(request, response, chain);
    }

    /**
     *   当前系统未登录，但是浏览器可以获取到登陆状态且登陆成功，成功后操作
     * @param request req
     * @param response res
     * @param permit  登陆成功，相关信息
     */
    public void operation(ServletRequest request, ServletResponse response,Permit permit ){

    }

    /**
     *   异常处理方法
     *   用于子类重写，抛出等处理操作
     * @param e  异常信息
     */
    protected void exception(ServletRequest request, ServletResponse response,RuntimeException e){
        throw e;
    }

    /***
     * 判断一个请求是否为AJAX请求,是则返回true
     * @param request
     * @return
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        String requestType = request.getHeader("X-Requested-With");
        //如果requestType能拿到值，并且值为 XMLHttpRequest ,表示客户端的请求为异步请求，那自然是ajax请求了，反之如果为null,则是普通的请求
        if(requestType == null){
            return false;
        }
        return true;
    }




}
