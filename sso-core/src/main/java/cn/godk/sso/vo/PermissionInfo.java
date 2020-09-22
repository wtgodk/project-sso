package cn.godk.sso.vo;

import com.google.common.collect.Sets;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * 权限相关信息
 *
 * @author wt
 * @program project-sso
 * @create 2020-09-17  13:18
 */
@Setter
@Getter
public class PermissionInfo {


    /**
     * 角色信息
     */
    private Set<String> roles = Sets.newHashSet();
}
