package cn.godk.sso.token.handler;


import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

/**
 * token 保存在 header
 *
 * @author wt
 * @program project-sso
 * @create 2020-09-14  10:32
 */

public class HeaderTokenHandler extends AbstractTokenHandler {


    @Override
    public String get(String tokenName, ServletRequest request) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        return httpServletRequest.getHeader(tokenName);
    }


}
