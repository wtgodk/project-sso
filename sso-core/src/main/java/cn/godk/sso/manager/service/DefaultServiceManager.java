package cn.godk.sso.manager.service;

import cn.godk.sso.bean.Permit;
import cn.godk.sso.cache.CacheManager;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author wt
 * @program project-sso
 * @create 2020-09-14  10:21
 */
@Setter
@Getter
@Slf4j
public class DefaultServiceManager extends AbstractServiceManager {

    /**
     * 到期时间  默认 30M
     */
    private long expire = 1000 * 60 * 30;

    public DefaultServiceManager(CacheManager<Service> cacheManager) {
        super(cacheManager);
    }

    @Override
    public Service getService(Permit permit) {
        log.debug("[{}] get service by request", new Date());
        String key = permit.getKey();
        if (key == null) {
            log.debug("[{}] request token is empty ,can not find service , [permit]->[{}]", new Date(), permit.toString());
        }
        return getCacheManager().get(key);
    }

    @Override
    public List<Service> getService() {
        return getCacheManager().all();
    }


    @Override
    public Service delService(Permit permit) {
        String key = permit.getKey();
        if (key == null) {
            log.debug("[{}] request token is empty ,can not find service , [permit]->[{}]", new Date(), permit.toString());
        }
        return delByToken(key);
    }

    @Override
    public Service delByToken(String token) {
        log.info("[{}] del service by token , [token]-[{}]", new Date(), token);
        // 下线 登出
        if (token == null) {
            log.debug("[{}] request token is empty ,can not find service , [token]->[{}]", new Date(), token);
        }
        return getCacheManager().delIfExist(token);
    }

    @Override
    public Service updateService(Permit permit, String appId) {
        String key = permit.getKey();
        if (key == null) {
            log.debug("[{}] request token is empty ,can not find service , [permit]->[{}]", new Date(), permit.toString());
        }
        Service service = getService(permit);
        if (service == null) {
            service = new Service();
            service.setType(permit.getType());
            service.setUsername(permit.getUsername());
            service.setToken(key);
        }
        Set<String> appIdList = service.getAppId();
        appIdList.add(appId);
        return getCacheManager().create(key, service, expire);
    }


}
