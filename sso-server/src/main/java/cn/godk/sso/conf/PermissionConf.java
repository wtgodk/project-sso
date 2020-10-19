package cn.godk.sso.conf;

import cn.godk.sso.vo.PermissionInfo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Map;
import java.util.Set;

/**
 *
 *  服务权限信息
 * @author wt
 * @program project-sso
 * @create 2020-10-19  14:20
 */
@Getter
@Setter
@ToString
@Configuration
@EnableConfigurationProperties(JdbcConfig.class)
@PropertySource("classpath:application.yml")
@ConfigurationProperties(prefix = "project.permission")
public class PermissionConf {

    /**
     *   权限信息初始化配置
     */
    private Map<String, PermissionInfo> serviceRoles;





}
