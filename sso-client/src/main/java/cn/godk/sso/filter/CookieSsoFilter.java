package cn.godk.sso.filter;

import cn.godk.sso.bean.Permit;
import cn.godk.sso.cookie.CookieUtils;
import cn.godk.sso.utils.EncodeUtils;
import cn.godk.sso.utils.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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
import java.util.Date;

import cn.godk.sso.bean.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.utils.URLEncodedUtils;

/**
 *
 *   cookie 共享方式 sso 集成  filter  该集成方法不支持跨域访问
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
     *  保存在 session中的值
     */
    private String key;
    /**
     *  是否 跨域请求 ,默认为非跨域请求
     */
    private boolean crossDomain = false;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse res = (HttpServletResponse)response;
        String servletPath = req.getServletPath();
        Result<Permit> result = null;
        if(isCrossDomain()){
            String token = (String) req.getSession().getAttribute(key);
            if(token!=null){
                Permit cookiePermit = new Permit(token);
                cookiePermit.setAppId(getAppId());
                result = check(cookiePermit);
            }else{
                // 当前系统未登录
                String serviceTicket = req.getParameter("service_ticket");
                if(StringUtils.isNotBlank(serviceTicket)){
                    Permit cookiePermit = new Permit(serviceTicket);
                    cookiePermit.setAppId(getAppId());
                    // TODO  后续改成 单独校验值 类似 cas st
                    result = check(cookiePermit);
                }
            }
            if(result==null || result.getData()==null){
                // 未登录
                redirect(res,servletPath);
                return;
            }
            Permit data = result.getData();
                // 已登录
                req.getSession().setAttribute(key,result.getData().getKey());
                req.getSession().setAttribute("ssoUserName",result.getData().getUsername());
        }else{
            String cookie = CookieUtils.get(req, key);
            if(cookie == null){
                // 未登录 ，跳转到登录页面
                redirect(res,servletPath);
                return;
            }else{
                // 验证 token 是否有效  appId,token
                Permit cookiePermit = new Permit(cookie);
                cookiePermit.setAppId(getAppId());
                 result = HttpUtil.doPost(getSsoServer() + "/check", cookiePermit, new TypeReference<Result<Permit>>() {
                });
                if(result==null || result.getCode()==1){
                    // 未登录或登陆已经失效
                    redirect(res,servletPath);
                    //  chain.doFilter(req,res);
                    return ;
                }
                // TODO  code == 其他
                int code = result.getCode();
                // token 有效
                Permit permit =  result.getData();
                // 用户名
                String username = permit.getUsername();
                // session中存储 用户名
                req.getSession().setAttribute("ssoUserName",username);
            }
        }

        chain.doFilter(req,res);
    }



    public void redirect(HttpServletResponse response, String rollback){
        try {
            response.sendRedirect(getSsoLoginUrl() +"?backUrl="+ EncodeUtils.encodeURL(getSsoClientUrl() + rollback) +"&appId="+getAppId());
        } catch (IOException e) {
            log.error("[{}] redirect url , [rollback]->[{}]",new Date(),rollback);
            throw new RuntimeException(e);
        }
    }








}
