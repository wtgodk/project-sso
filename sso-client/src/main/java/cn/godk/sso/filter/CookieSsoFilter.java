package cn.godk.sso.filter;

import cn.godk.sso.bean.Permit;
import cn.godk.sso.cookie.CookieUtils;
import cn.godk.sso.utils.EncodeUtils;
import cn.godk.sso.utils.HttpUtil;
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


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse res = (HttpServletResponse)response;

        String cookie = CookieUtils.get(req, key);
        String servletPath = req.getServletPath();
        if(cookie == null){
            // 未登录 ，跳转到登录页面
            redirect(res,servletPath);

        }else{
            // 验证 token 是否有效  appId,token
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("appId",getAppId());
            jsonObject.put("token",cookie);
            Result<Permit> result = HttpUtil.doPost(getSsoServer() + "/check", jsonObject, new TypeReference<Result<Permit>>() {
            });
            if(result==null || result.getCode()==1){
                // 未登录或登陆已经失效
                redirect(res,servletPath);
                chain.doFilter(req,res);
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
        chain.doFilter(req,res);
    }



    public void redirect(HttpServletResponse response, String rollback){
        try {
            response.sendRedirect(getSsoLoginUrl() +"?"+ EncodeUtils.encodeURL(rollback) +"&appId="+getAppId());
        } catch (IOException e) {
            log.error("[{}] redirect url , [rollback]->[{}]",new Date(),rollback);
            throw new RuntimeException(e);
        }
    }








}
