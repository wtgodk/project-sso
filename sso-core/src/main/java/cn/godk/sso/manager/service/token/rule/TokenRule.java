package cn.godk.sso.manager.service.token.rule;

import cn.godk.sso.manager.service.token.Token;

/**
 *  token 生成方法
 * @author wt
 * @program project-sso
 * @create 2020-09-14  11:05
 */
public interface TokenRule {


    /**
     * token 生成
     * @return
     */
    public Token create();
}
