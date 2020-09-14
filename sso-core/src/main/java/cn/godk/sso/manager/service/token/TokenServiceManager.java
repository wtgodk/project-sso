package cn.godk.sso.manager.service.token;

import cn.godk.sso.Service;
import cn.godk.sso.manager.service.AbstractServiceManager;
import cn.godk.sso.manager.service.token.handler.DefaultTokenHandler;
import cn.godk.sso.manager.service.token.handler.TokenHandler;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * @author wt
 * @program project-sso
 * @create 2020-09-14  10:21
 */
@Setter
@Getter
@Slf4j
public class TokenServiceManager extends AbstractServiceManager {
    /**
     *   token 保存 key
     */
    private String tokenName = "token";

    /**
     *   token 处理器
     */
    private TokenHandler tokenHandler;


    public TokenServiceManager() {
        this.tokenHandler = new DefaultTokenHandler();
    }

    public TokenServiceManager(TokenHandler tokenHandler) {
        this.tokenHandler = tokenHandler;
    }

    public TokenServiceManager(String tokenName, TokenHandler tokenHandler) {
        this.tokenName = tokenName;
        this.tokenHandler = tokenHandler;
    }

    public TokenServiceManager(String tokenName) {
        this();
        this.tokenName = tokenName;
    }

    @Override
    public Service getService() {
        log.debug("[{}] get service by request",new Date());
        Token token = tokenHandler.get(tokenName, getRequest());
        if(token ==null){
            log.debug("[{}] request token is empty ,can not find service , [tokenName]->[{}]",new Date(),tokenName);
            return null;
        }
        return getCacheManager().get(token.getToken());
    }

    @Override
    public void delByAppId(String appId) {
        // TODO 踢出、强制下线 指定服务

    }

    @Override
    public void delService() {
        Token token = tokenHandler.get(tokenName, getRequest());
        if(token ==null){
            log.debug("[{}] request token is empty ,can not find service , [tokenName]->[{}]",new Date(),tokenName);
            return;
        }
        delByToken(token.getToken());
    }

    @Override
    public void delByToken(String token) {
        log.info("[{}] del service by token , [token]-[{}]",new Date(),token);
        // 下线 登出
        if(token==null){
            log.debug("[{}] token is null ,No need to delete",new Date());
        }
//        Service service = getCacheManager().get(token);
//        if(service!=null){
//            getCacheManager().del(token);
//        }
        getCacheManager().delIfExist(token);
    }

    @Override
    public Service updateService() {
        Token token = tokenHandler.get(tokenName, getRequest());
        if (token != null) {
            return getCacheManager().update(token.getToken());
        }
        // 刷新 缓存时间
        log.debug("[{}] request token is empty ,can not find service , [tokenName]->[{}]",new Date(),tokenName);
        return null;
    }







}
