package cn.godk.sso.filter;


import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import java.io.IOException;
import java.util.Date;

/**
 *
 *
 *   sso client filter
 * @author wt
 * @program project-sso
 * @create 2020-09-21  09:22
 */
@Setter
@Getter
@Slf4j
public abstract class AbstractSsoFilter implements Filter {

    /**
     *   获取 配置参数key值  sso服务地址
     */
    private  String ssoServerUrlKey = "ssoServerUrl";
    /**
     *   获取 配置参数key值  sso 退出登录地址
     */
    private  String ssoLogoutUrlKey = "ssoLogoutUrl";

    /**
     *   过滤路径 ，不进行拦截的路径
     */
    private  String excludedPathsKey = "excludedPaths";

    /**
     *   登陆页面地址
     */
    private  String ssoLoginUrlKey = "ssoLoginUrlKey";

    /**
     *   服务ID
     */
    private String appId;
    /**
     *   sso 地址
     *
     */
    private String ssoServer;


    /**
     *   sso 登陆页面地址
     */
    private String ssoLoginUrl;
    /**
     *   退出登录地址
     */
    private String logoutPath;
    /**
     *  排除路径
     */
    private String excludedPaths;


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("[{}] filter config init ",new Date());
        // 参数初始化
        ssoServer = filterConfig.getInitParameter(ssoServerUrlKey);
        logoutPath = filterConfig.getInitParameter(ssoLogoutUrlKey);
        excludedPaths = filterConfig.getInitParameter(excludedPathsKey);
        ssoLoginUrl = filterConfig.getInitParameter(ssoLoginUrlKey);

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        chain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }
}
