package cn.godk.sso.manager.permission;

import cn.godk.sso.cache.CacheManager;
import cn.godk.sso.vo.PermissionInfo;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 *
 *  权限管理
 * @author wt
 * @program project-sso
 * @create 2020-10-19  13:24
 */
@Setter
@Getter
@Slf4j
public class DefaultPermissionManager extends AbstractPermissionManager {


    public DefaultPermissionManager(CacheManager<PermissionInfo> cacheManager, Map<String, PermissionInfo> permissionManagerMap) {
        super(cacheManager);
        this.permissionManagerMap = permissionManagerMap;
        initServicePermission();
    }
    private Map<String,PermissionInfo> permissionManagerMap;

    @Override
    public void initServicePermission() {
        log.info("[{}] init service permission start",new Date());
        if(permissionManagerMap!=null && permissionManagerMap.size()>0){
            permissionManagerMap.forEach((k,v)->{
                v.setAppId(k);
                getCacheManager().create(k,v,-1);
            });
        }
        log.info("[{}] init service permission end",new Date());
    }






}
