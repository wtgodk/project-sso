package cn.godk.sso.manager.permission;

import cn.godk.sso.cache.CacheManager;
import cn.godk.sso.vo.PermissionInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author wt
 * @program project-sso
 * @create 2020-09-27  15:49
 */
@Setter
@Getter
@Slf4j
public abstract class AbstractPermissionManager implements PermissionManager {

    /**
     * 缓存，该缓存不能过时，要求使用永久缓存，服务启动便调用 {@link PermissionManager#initServicePermission()} 初始化服务权限缓存
     */
    private CacheManager<PermissionInfo> cacheManager;


    public AbstractPermissionManager(CacheManager<PermissionInfo> cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Override
    public List<PermissionInfo> queryAll() {
        log.info("[{}] get all service roles ", new Date());
        List<PermissionInfo> all = cacheManager.all();

        return all == null ? Lists.newArrayList() : all;
    }

    @Override
    public Set<String> getRolesByAppId(String appId) {
        log.info("[{}] get service roles by app id ,param [appId]->[{}]", new Date(), appId);
        PermissionInfo permissionInfo = getCacheManager().get(appId);
        if (permissionInfo != null) {
            return permissionInfo.getRoles();
        }
        return null;
    }


    @Override
    public Set<String> removeRolesByAppId(String appId, Set<String> roles) {
        log.info("[{}] remove service roles by app id ,param [appId,roles]->[{},{}]", new Date(), appId, roles != null ? roles.toString() : null);
        PermissionInfo permissionInfo = getCacheManager().get(appId);
        if (permissionInfo != null) {
            Set<String> current = permissionInfo.getRoles();
            if (roles == null || roles.size() == 0) {
                return current;
            }
            current.removeAll(roles);
            permissionInfo.setRoles(current);
            getCacheManager().create(appId, permissionInfo, -1);
            return current;
        }
        return Sets.newHashSet();
    }

    @Override
    public Set<String> addRolesByAppId(String appId, Set<String> roles) {
        log.info("[{}] add service roles by app id ,param [appId,roles]->[{},{}]", new Date(), appId, roles != null ? roles.toString() : null);
        PermissionInfo permissionInfo = getCacheManager().get(appId);
        if (permissionInfo == null) {
            permissionInfo = new PermissionInfo(appId, roles == null ? Sets.newHashSet() : roles);
        } else {
            Set<String> current = permissionInfo.getRoles();
            current.addAll(roles == null ? Sets.newHashSet() : roles);
        }
        getCacheManager().create(appId, permissionInfo, -1);
        return permissionInfo.getRoles();
    }

    @Override
    public Set<String> updateRolesByAppId(String appId, Set<String> roles) {
        log.info("[{}] update service roles by app id ,param [appId,roles]->[{},{}]", new Date(), appId, roles != null ? roles.toString() : null);
        PermissionInfo permissionInfo = getCacheManager().get(appId);
        if (permissionInfo == null) {
            permissionInfo = new PermissionInfo(appId, roles == null ? Sets.newHashSet() : roles);
        }
        permissionInfo.setRoles(roles);
        getCacheManager().create(appId, permissionInfo, -1);
        return permissionInfo.getRoles();
    }

    @Override
    public Set<String> delAllServiceRoles(String appId) {
        log.info("[{}] del  service all roles by app id ,param [appId]->[{}]", new Date(), appId);
        PermissionInfo permissionInfo = getCacheManager().delIfExist(appId);
        return permissionInfo != null ? permissionInfo.getRoles() : Sets.newHashSet();
    }
}
