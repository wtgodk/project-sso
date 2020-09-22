package cn.godk.sso.token.handler;


import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

/**
 * token 保存在 请求参数中
 *
 * @author wt
 * @program project-sso
 * @create 2020-09-14  10:31
 */
public class ParamTokenHandler extends AbstractTokenHandler {

    @Override
    public String get(String tokenName, ServletRequest request) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        return httpServletRequest.getParameter(tokenName);
    }
}
