package cn.godk.sso;


import cn.godk.sso.utils.EncodeUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

/**
 *
 *  系统登出
 * @author wt
 * @program project-sso
 * @create 2020-10-13  11:06
 */
public abstract class LogoutHelper {


    /**
     *   退出登录
     * @param  path 跳转路径  client server base url 后缀
     * @param  request request
     * @return
     */
    public static String logout(HttpServletRequest request,String path){
        request.getSession().invalidate();
        StringBuilder sb = new StringBuilder("redirect:");
        String uri = ParamStore.ssoClientUrl + path;

        try {
            sb.append(ParamStore.ssoServer).append("/logout?appId=").append(ParamStore.appId).append("&backUrl=").append(EncodeUtils.encodeURL(uri));
        } catch (UnsupportedEncodingException ignored) {}
         return sb.toString();
    }
}
