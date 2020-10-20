package cn.godk.sso;

import cn.godk.sso.cache.Cache;
import cn.godk.sso.cache.CacheManager;
import cn.godk.sso.cache.guava.GuavaCacheManager;
import cn.godk.sso.manager.permission.DefaultPermissionManager;
import cn.godk.sso.manager.permission.PermissionManager;
import cn.godk.sso.vo.PermissionInfo;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * @author wt
 * @program project-sso
 * @create 2020-10-19  13:54
 */
public class DefaultPermissionManagerTest {

    @Test
    public void testInit() {
        CacheManager<PermissionInfo> cacheManager = new GuavaCacheManager<>(Cache.PERMISSION);
        Map<String, PermissionInfo> map = Maps.newHashMap();
        Set<String> set1 = Sets.newHashSet(Arrays.asList("1", "2"));
        Set<String> set2 = Sets.newHashSet(Arrays.asList("3", "4"));
        map.put("1", new PermissionInfo(set1));
        map.put("2", new PermissionInfo(set2));

        PermissionManager permissionManager = new DefaultPermissionManager(cacheManager, map);
        permissionManager.addRolesByAppId("3", Sets.newHashSet(Arrays.asList("5", "6")));
        Set<String> rolesByAppId = permissionManager.getRolesByAppId("3");
        System.out.println(rolesByAppId.toString());
        permissionManager.removeRolesByAppId("3", Sets.newHashSet(Collections.singletonList("6")));
        Set<String> rolesByAppId1 = permissionManager.getRolesByAppId("3");
        System.out.println(rolesByAppId1.toString());

        permissionManager.updateRolesByAppId("3", Sets.newHashSet(Arrays.asList("8", "9")));
        Set<String> rolesByAppId2 = permissionManager.getRolesByAppId("3");
        System.out.println(rolesByAppId2.toString());

        Set<String> strings = permissionManager.delAllServiceRoles("3");

        Set<String> rolesByAppId3 = permissionManager.getRolesByAppId("3");
        System.out.println(rolesByAppId3 != null ? rolesByAppId3.toString() : null);


        Set<String> rolesByAppId4 = permissionManager.getRolesByAppId("2");
        System.out.println(rolesByAppId4 != null ? rolesByAppId4.toString() : null);
    }

}
