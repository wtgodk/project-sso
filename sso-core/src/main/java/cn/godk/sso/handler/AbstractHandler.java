package cn.godk.sso.handler;

import cn.godk.sso.bean.Permit;
import cn.godk.sso.cache.CacheManager;
import cn.godk.sso.handler.rule.DefaultRule;
import cn.godk.sso.handler.rule.Rule;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.List;

/**
 * token handler
 *
 * @author wt
 * @program project-sso
 * @create 2020-09-14  10:32
 */
@Setter
@Getter
@Slf4j
public abstract class AbstractHandler implements VerificationHandler {


    /**
     * 缓存
     */
    private CacheManager<Permit> cacheManager;
    /**
     * token 生成规则
     */
    private Rule rule;
    /**
     * token 失效时间
     */
    private long timeout = 1000 * 30;

    public AbstractHandler() {
        rule = new DefaultRule();
    }

    public AbstractHandler(Rule rule) {
        this.rule = rule;
    }
    public AbstractHandler(CacheManager<Permit> cacheManager, Rule rule) {
        this(rule);
        this.cacheManager = cacheManager;
    }
    public AbstractHandler(CacheManager<Permit> cacheManager) {
        this();
        this.cacheManager = cacheManager;
    }

    @Override
    public Permit create(String username, String appId, Permit.Type type) {
        log.debug("[{}] default handler create token ,[username,appId,type]->[{},{},{}]", new Date(), username, appId, type);
        Permit token = rule.create();
        token.setAppId(appId);
        token.setUsername(username);
        token.setType(type);
        getCacheManager().create(token.getKey(), token, timeout);
        return token;
    }

    @Override
    public List<Permit> get() {

        return getCacheManager().all();
    }

    @Override
    public Permit get(String token) {
        log.debug("[{}] default handler get token ,[token]->[{}]", new Date(), token);
        // 为空 那么token 已失效
        return getCacheManager().get(token);
    }

    @Override
    public void save(Permit permit) {
        log.debug("[{}] default handler save permit ,[permit]->[{}]", new Date(), permit);
        // 为空 那么token 已失效
        getCacheManager().create(permit.getKey(), permit, timeout);
    }

    @Override
    public Permit del(String token) {
        log.debug("[{}] default handler del token ,[token]->[{}]", new Date(), token);
        Permit permit = getCacheManager().delIfExist(token);
        return permit;
    }
}
