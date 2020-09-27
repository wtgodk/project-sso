package cn.godk.sso.manager.user;

import cn.godk.sso.bean.Permit;
import cn.godk.sso.cache.CacheManager;
import lombok.Getter;
import lombok.Setter;

/**
 *  在线用户管理
 * @author wt
 * @program project-sso
 * @create 2020-09-27  15:53
 */
@Setter
@Getter
public class DefaultUserManager extends AbstractUserManager {
    public DefaultUserManager(CacheManager<Permit> cacheManager) {
        super(cacheManager);
    }
}
