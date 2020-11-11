package cn.godk.sso.filter;


import cn.godk.sso.ParamStore;
import cn.godk.sso.bean.Permit;
import cn.godk.sso.bean.result.Result;
import cn.godk.sso.matcher.AntPathMatcher;
import cn.godk.sso.utils.HttpUtil;
import cn.godk.sso.utils.PathUtils;
import com.alibaba.fastjson.TypeReference;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.util.Date;

/**
 * sso client filter
 *
 * @author wt
 * @program project-sso
 * @create 2020-09-21  09:22
 */
@Setter
@Getter
@Slf4j
public abstract class AbstractSsoFilter extends HttpServlet implements Filter {

    /**
     * 获取 app id key
     */
    private final String appIdKey = "appId";
    /**
     * 获取 配置参数key值  sso服务地址
     */
    private String ssoServerUrlKey = "ssoServerUrl";
    /**
     * 获取 配置参数key值  sso 退出登录地址
     */
    private String ssoLogoutUrlKey = "ssoLogoutUrl";
    /**
     * 过滤路径 ，不进行拦截的路径
     */
    private String excludedPathsKey = "excludedPaths";
    /**
     * 登陆页面地址
     */
    private String ssoLoginUrlKey = "ssoLoginUrl";
    /**
     * 获取客户端 地址
     */
    private String ssoClientUrlKey = "ssoClientUrl";


    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("[{}] filter config init ", new Date());
        // 参数初始化
        ParamStore.ssoServer = filterConfig.getInitParameter(ssoServerUrlKey);
        ParamStore.logoutPath = filterConfig.getInitParameter(ssoLogoutUrlKey);
        ParamStore.excludedPaths = filterConfig.getInitParameter(excludedPathsKey);
        ParamStore.ssoLoginUrl = filterConfig.getInitParameter(ssoLoginUrlKey);
        ParamStore.appId = filterConfig.getInitParameter(appIdKey);
        ParamStore.ssoClientUrl = filterConfig.getInitParameter(ssoClientUrlKey);

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }


    public String getSsoClientUrl() {
        return PathUtils.pathCompletion(ParamStore.ssoClientUrl);
    }

    /**
     * token 、 cookie 校验方法
     *
     * @param token
     * @return
     */
    protected Result<Permit> check(String token) {
        Permit permit = new Permit(token);
        permit.setAppId(ParamStore.appId);
        return HttpUtil.doPostJson(ParamStore.ssoServer + "/check", permit, new TypeReference<Result<Permit>>() {
        });

    }

    /**
     * 放行路径
     *
     * @param servletPath 请求路径
     * @return
     */
    protected boolean greenLight(String servletPath) {
        // excluded path check
        if (ParamStore.excludedPaths != null && ParamStore.excludedPaths.trim().length() > 0) {
            for (String excludedPath : ParamStore.excludedPaths.split(",")) {
                String uriPattern = excludedPath.trim();
                // 支持ANT表达式
                if (antPathMatcher.match(uriPattern, servletPath)) {
                    // excluded path, allow
                    return true;
                }
            }
        }
        return false;
    }

}
