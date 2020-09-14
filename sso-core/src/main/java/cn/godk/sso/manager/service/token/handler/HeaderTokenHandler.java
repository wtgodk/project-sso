package cn.godk.sso.manager.service.token.handler;

import cn.godk.sso.manager.service.token.Token;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

/**
 *
 *  token 保存在 header
 * @author wt
 * @program project-sso
 * @create 2020-09-14  10:32
 */

public class HeaderTokenHandler extends AbstractTokenHandler{




    @Override
    public Token get(String tokenName, ServletRequest request) {
        HttpServletRequest httpServletRequest = (HttpServletRequest)request;
        String token =  httpServletRequest.getHeader(tokenName);
        return new Token(token);
    }




}
