package cn.godk.sso.manager.service.token.handler;

import cn.godk.sso.manager.service.token.Token;
import cn.godk.sso.manager.service.token.rule.DefaultTokenRule;
import cn.godk.sso.manager.service.token.rule.TokenRule;
import lombok.Getter;
import lombok.Setter;

/**
 *  token handler
 *
 * @author wt
 * @program project-sso
 * @create 2020-09-14  10:32
 */
@Setter
@Getter
public abstract  class AbstractTokenHandler implements TokenHandler {



    private TokenRule tokenRule = new DefaultTokenRule();

    @Override
    public Token create(String tokenName, String appId) {
        Token token = tokenRule.create();
        token.setAppId(appId);
        token.setTokenName(tokenName);
        return  token;
    }
}
