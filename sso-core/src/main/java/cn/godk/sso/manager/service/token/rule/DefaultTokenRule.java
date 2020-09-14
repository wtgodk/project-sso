package cn.godk.sso.manager.service.token.rule;

import cn.godk.sso.manager.service.token.Token;

import java.util.UUID;

/**
 * @author wt
 * @program project-sso
 * @create 2020-09-14  11:06
 */
public class DefaultTokenRule implements TokenRule {



    @Override
    public Token create() {
        String uuid = UUID.randomUUID().toString().replace("-", "").substring(0, 20);
        return new Token(uuid);
    }
}
