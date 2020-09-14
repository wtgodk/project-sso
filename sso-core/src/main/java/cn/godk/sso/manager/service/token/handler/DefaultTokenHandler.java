package cn.godk.sso.manager.service.token.handler;

import cn.godk.sso.manager.service.token.Token;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletRequest;
import java.util.Date;

/**
 *
 *
 *   默认token处理。
 *
 *   使用  @see cn.godk.sso.manager.service.token.handler.AbstractTokenHandler中token创建方法
 *   <br/>
 *
 *   token先从header中获取，如果不存在那么从param中获取。
 * @author wt
 * @program project-sso
 * @create 2020-09-14  10:36
 */
@Slf4j
@Setter
@Getter
public class DefaultTokenHandler extends AbstractTokenHandler {


    private HeaderTokenHandler headerTokenHandler =  new HeaderTokenHandler();
    private ParamTokenHandler paramTokenHandler =  new ParamTokenHandler();

    @Override
    public Token get(String tokenName, ServletRequest request) {
        log.debug("[{}] default token handler get token ,[tokenName]->[{}]",new Date(),tokenName);
        Token token = headerTokenHandler.get(tokenName, request);
        if(token==null){
            return paramTokenHandler.get(tokenName,request);
        }
        return token;
    }



}
