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
 * @author wt
 * @program project-sso
 * @create 2020-09-27  14:17
 */
@Setter
@Getter
public class TokenSsoFilter extends CookieSsoFilter {
    /**
     *   token key  default `token`
     */
    private String token = "token";
    /**
     *   获取token handler
     *
     */
    private TokenHandler tokenHandler;

    public TokenSsoFilter(TokenHandler tokenHandler) {
        this.tokenHandler = tokenHandler;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String token = tokenHandler.get(getToken(), request);
        if(token==null){
            //可以尝试获取 cookie如果存在cookie 那么也可以校验登录
            String cookie = CookieUtils.get(req, getToken());
            if (cookie != null) {
                // 验证 token 是否有效  appId,token
                Result<Permit> result = check(cookie);
                if (result == null || result.getData() == null) {
                    // 登录失效
                    throw new NotLoggedInException();
                }
                // token 有效
                operation(request,response,result.getData());
            }else{
                //未登录，抛出异常
                throw new NotLoggedInException();
            }
        }
        // 验证 token 是否有效  appId,token
        Result<Permit> result = check(token);
        if (result == null || result.getData() == null) {
            // 登录失效
            throw new NotLoggedInException();
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


}
