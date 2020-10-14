package cn.godk.sso;

import cn.godk.sso.bean.Permit;
import cn.godk.sso.bean.result.Result;
import cn.godk.sso.utils.HttpUtil;
import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 *   系统登陆，仅适用于客户端使用自己的登陆页面时
 *
 * @author wt
 * @program project-sso
 * @create 2020-10-14  10:05
 */
public abstract class LoginHelper {


    /**
     *  系统登录校验
     * @param username  用户名
     * @param password 密码
     */
    public static Result<Permit> login(String username,String password){
        StringBuilder param = new StringBuilder(ParamStore.ssoServer).append("/login");

        Map<String,String> map = Maps.newHashMap();
        map.put("username",username);
        map.put("password",password);
        map.put("appId",ParamStore.appId);
        Result<Permit> result = HttpUtil.doPost(param.toString(), map, new TypeReference<Result<Permit>>() {
        });
        return result;
    }
}
