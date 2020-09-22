package cn.godk.sso.filter;


import cn.godk.sso.bean.Permit;
import cn.godk.sso.bean.result.Result;
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
    private String ssoLoginUrlKey = "ssoLoginUrlKey";
    /**
     * 获取客户端 地址
     */
    private String ssoClientUrlKey = "ssoClientUrlKey";
    /**
     * 服务ID
     */
    private String appId;
    /**
     * sso 地址
     */
    private String ssoServer;


    /**
     * sso 登陆页面地址
     */
    private String ssoLoginUrl;
    /**
     * 退出登录地址
     */
    private String logoutPath;
    /**
     * 排除路径
     */
    private String excludedPaths;

    /**
     * 客户端 地址
     */
    private String ssoClientUrl;


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("[{}] filter config init ", new Date());
        // 参数初始化
        this.ssoServer = filterConfig.getInitParameter(ssoServerUrlKey);
        this.logoutPath = filterConfig.getInitParameter(ssoLogoutUrlKey);
        this.excludedPaths = filterConfig.getInitParameter(excludedPathsKey);
        this.ssoLoginUrl = filterConfig.getInitParameter(ssoLoginUrlKey);
        this.appId = filterConfig.getInitParameter(appIdKey);
        this.ssoClientUrl = filterConfig.getInitParameter(ssoClientUrlKey);

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }


    public String getSsoClientUrl() {
        return PathUtils.pathCompletion(ssoClientUrl);
    }

    /**
     * token 、 cookie 校验方法
     *
     * @param permit
     * @return
     */
    protected Result<Permit> check(Permit permit) {
        return HttpUtil.doPost(getSsoServer() + "/check", permit, new TypeReference<Result<Permit>>() {
        });

    }


}
