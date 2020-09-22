package cn.godk.sso.token.handler;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletRequest;
import java.util.Date;

/**
 * 默认token处理。
 * <p>
 * 使用  @see cn.godk.sso.manager.service.token.handler.AbstractTokenHandler中token创建方法
 * <br/>
 * <p>
 * token先从header中获取，如果不存在那么从param中获取。
 *
 * @author wt
 * @program project-sso
 * @create 2020-09-14  10:36
 */
@Slf4j
@Setter
@Getter
public class DefaultTokenHandler extends AbstractTokenHandler {


    private HeaderTokenHandler headerTokenHandler = new HeaderTokenHandler();
    private ParamTokenHandler paramTokenHandler = new ParamTokenHandler();

    @Override
    public String get(String tokenName, ServletRequest request) {
        log.debug("[{}] default token handler get token ,[tokenName]->[{}]", new Date(), tokenName);
        String token = headerTokenHandler.get(tokenName, request);
        if (token == null) {
            return paramTokenHandler.get(tokenName, request);
        }
        return token;
    }


}
