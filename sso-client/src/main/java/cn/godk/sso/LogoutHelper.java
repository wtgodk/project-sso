package cn.godk.sso;


import cn.godk.sso.bean.Permit;
import cn.godk.sso.bean.result.Result;
import cn.godk.sso.utils.EncodeUtils;
import cn.godk.sso.utils.HttpUtil;
import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Maps;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * 系统登出
 *
 * @author wt
 * @program project-sso
 * @create 2020-10-13  11:06
 */
public abstract class LogoutHelper {


    /**
     * 退出登录 (cookie)
     *
     * @param path    跳转路径  client server base url 后缀
     * @param request request
     * @return
     */
    public static String logout(HttpServletRequest request, String path) {
        request.getSession().invalidate();
        StringBuilder sb = new StringBuilder("redirect:");
        String uri = ParamStore.ssoClientUrl + path;
        try {
            sb.append(ParamStore.ssoServer).append("/logout?appId=").append(ParamStore.appId).append("&backUrl=").append(EncodeUtils.encodeURL(uri));
        } catch (UnsupportedEncodingException ignored) {
        }
        return sb.toString();
    }

    /**
     * token 登录 退出
     *
     * @param request
     */
    public static void logout(HttpServletRequest request) {

        Map<String, String> param = Maps.newHashMap();
        param.put("appId", ParamStore.appId);
        param.put("token", (String) request.getSession().getAttribute(ParamStore.tokenKey));
        HttpUtil.doPost(ParamStore.ssoServer + "/destroy", param, new TypeReference<Result<Permit>>() {
        });
        request.getSession().invalidate();
    }


}
