package cn.godk.sso.manager.user;

import cn.godk.sso.bean.Permit;
import cn.godk.sso.cache.CacheManager;
import cn.godk.sso.vo.LoginUser;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author wt
 * @program project-sso
 * @create 2020-09-27  15:49
 */
@Setter
@Getter
public abstract class AbstractUserManager implements UserManager {

    /**
     *
     */
    private CacheManager<Permit> cacheManager;


    public AbstractUserManager(CacheManager<Permit> cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Override
    public Permit getOnline(String username) {
        return cacheManager.get(username);
    }

    @Override
    public List<Permit> getAllOnline() {
        return cacheManager.all();
    }

    @Override
    public void create(String username, Permit permit) {
        cacheManager.create(username,permit,-1);
    }

    @Override
    public void remove(String username) {
        cacheManager.delIfExist(username);
    }
}
