package cn.godk.sso.filter;

import cn.godk.sso.ParamStore;
import cn.godk.sso.bean.Permit;
import cn.godk.sso.bean.result.Result;
import cn.godk.sso.cookie.CookieUtils;
import cn.godk.sso.utils.EncodeUtils;
import cn.godk.sso.utils.HttpUtil;
import com.alibaba.fastjson.TypeReference;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * cookie 共享方式 sso 集成  filter  该集成方法不支持跨域访问
 *
 * @author wt
 * @program project-sso
 * @create 2020-09-21  09:25
 */
@Setter
@Getter
@Slf4j
public class CookieSsoFilter extends AbstractSsoFilter {

    /**
     * 保存在 session中的值
     */
    private String key;
    /**
     * 是否 跨域请求 ,默认为非跨域请求
     */
    private boolean crossDomain = false;




    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        if(greenLight(req.getServletPath())){
            chain.doFilter(req, res);
            return;
        }
        boolean redirect;
        if (isCrossDomain()) {
            redirect = crossDomain(req, res);
        } else {
            redirect = sameDomain(req, res);
        }
        if (!redirect) {
            chain.doFilter(req, res);
        }
    }


    /**
     * 重定向
     *
     * @param response
     * @param rollback
     */
    public void redirect(HttpServletResponse response, String rollback) {
        try {
            response.sendRedirect(ParamStore.ssoLoginUrl + "?backUrl=" + EncodeUtils.encodeURL(getSsoClientUrl() + rollback) + "&appId=" + ParamStore.appId);
        } catch (IOException e) {
            log.error("[{}] redirect url , [rollback]->[{}]", new Date(), rollback);
            throw new RuntimeException(e);
        }
    }


    /**
     * 客户端无法获取到 服务端 cookie时使用该方法。（跨域）
     *
     *
     *   获取 session中是否有token信息如果有说明该系统登陆过，如果没有说明没有登陆过，
     *   尝试获取 service_ticket，如果存在则加入session中key信息，如果不存在跳转到登陆页面
     *
     *   该实现并不完美，存在较多问题。
     *
     * @param req
     * @param res
     * @return 是否重定向
     */
    private boolean crossDomain(HttpServletRequest req, HttpServletResponse res) {
        Result<Permit> result = null;
        String token = (String) req.getSession().getAttribute(key);
        if (token != null) {
            result = check(token);
            if (isRedirect(result, req, res)) {
                req.getSession().invalidate();
                return true;
            }
        } else {
            // 当前系统未登录
            String serviceTicket = req.getParameter("service_ticket");
            if (StringUtils.isNotBlank(serviceTicket)) {
                // TODO  后续改成 单独校验值 类似 cas st
                result = check(serviceTicket);
            }
        }
        if (isRedirect(result, req, res)) {
            return true;
        }
        Permit data = result.getData();
        // 已登录
        req.getSession().setAttribute(key, data.getKey());
        req.getSession().setAttribute("ssoUserName", data.getUsername());
        return false;
    }


    /**
     * 客户端可以获取到 服务端 cookie（同域名）
     *
     * @param req
     * @param res
     * @return 是否重定向
     */
    private boolean sameDomain(HttpServletRequest req, HttpServletResponse res) {
        String servletPath = req.getServletPath();
        String cookie = CookieUtils.get(req, key);
        if (cookie == null) {
            // 未登录 ，跳转到登录页面
            redirect(res, servletPath);
            return true;
        } else {
            // 验证 token 是否有效  appId,token
            Result<Permit> result = check(cookie);
            if (isRedirect(result, req, res)) {
                return true;
            }
            // token 有效
            assert result != null;
            Permit permit = result.getData();
            // 用户名
            String username = permit.getUsername();
            // session中存储 用户名
            req.getSession().setAttribute("ssoUserName", username);
            return false;
        }
    }

    /**
     * 是否重定向
     *
     * @param result 校验结果
     * @param req    request
     * @param res    response
     * @return
     */
    private boolean isRedirect(Result<Permit> result, HttpServletRequest req, HttpServletResponse res) {
        if (result == null || result.getData() == null) {
            redirect(res, req.getServletPath());
            return true;
        }
        return false;
    }


}
