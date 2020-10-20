package cn.godk.sso.controller.rest;

import cn.godk.sso.bean.result.Result;
import cn.godk.sso.manager.permission.PermissionManager;
import cn.godk.sso.vo.PermissionInfo;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;

/**
 * 服务权限管理
 *
 * @author wt
 * @program project-sso
 * @create 2020-10-19  14:37
 */
@RestController
@RequestMapping("/permission")
@Slf4j
public class PermissionController {
    @Resource
    private PermissionManager permissionManager;

    /**
     * 服务-权限管理 查询全部权限信息
     *
     * @return
     */
    @RequestMapping("/all")
    public Result permission() {
        log.info("[{}] permission manager : query all ", new Date());
        List<PermissionInfo> permissionInfos = permissionManager.queryAll();
        return new Result<>(permissionInfos);
    }

    /**
     * remove permission by appId
     *
     * @param appId service id
     * @return
     */
    @RequestMapping("/remove/{appId}")
    public Result remove(@PathVariable(name = "appId") String appId, String[] roles) {
        log.info("[{}] permission manager : remove service roles  by appId ， param [appId,roles]->[{},{}]", new Date(), appId, roles != null ? Arrays.toString(roles) : Sets.newHashSet());
        permissionManager.removeRolesByAppId(appId, new HashSet<>(Arrays.asList(roles == null ? new String[0] : roles)));
        return new Result<>();
    }


    /**
     * update permission by appId
     *
     * @param appId
     * @return
     */
    @RequestMapping("/update/{appId}")
    public Result update(@PathVariable(name = "appId") String appId, String[] roles) {
        log.info("[{}] permission manager : update service roles  by appId ， param [appId,roles]->[{},{}]", new Date(), appId, roles != null ? Arrays.toString(roles) : Sets.newHashSet());
        permissionManager.updateRolesByAppId(appId, new HashSet<>(Arrays.asList(roles == null ? new String[0] : roles)));
        return new Result<>();
    }


    /**
     * get permission by appId
     *
     * @param appId service id
     * @return
     */
    @RequestMapping("/get/{appId}")
    public Result get(@PathVariable(name = "appId") String appId) {
        log.info("[{}] permission manager : get service roles  by appId ， param [appId]->[{}]", new Date(), appId);
        Set<String> roles = permissionManager.getRolesByAppId(appId);
        return new Result<>(new PermissionInfo(appId, roles));
    }

    /**
     * add role to service by appId
     *
     * @param appId service id
     * @param roles role list
     * @return
     */
    @RequestMapping("/add/{appId}")
    public Result add(@PathVariable(name = "appId") String appId, String[] roles) {
        log.info("[{}] permission manager : add service roles  by appId ， param [appId,roles]->[{},{}]", new Date(), appId, roles != null ? Arrays.toString(roles) : Sets.newHashSet());
        permissionManager.addRolesByAppId(appId, new HashSet<>(Arrays.asList(roles == null ? new String[0] : roles)));
        return new Result<>();
    }


}
