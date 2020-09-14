package cn.godk.sso.manager.service.token.handler;

import cn.godk.sso.manager.service.token.Token;

import javax.servlet.ServletRequest;

/**
 *
 *  token 处理器
 *   获取、生成 token
 * @author wt
 * @program project-sso
 * @create 2020-09-14  10:28
 */
public interface TokenHandler {

    /**
     *   获取Request中的 token
     * @param tokenName  token key
     * @param request Request
     * @return
     */

    Token get(String tokenName, ServletRequest request);

    /**
     *   生成
     * @param tokenName  token key
     * @param appId appid
     * @return
     */
    Token create(String tokenName,String appId);


}
