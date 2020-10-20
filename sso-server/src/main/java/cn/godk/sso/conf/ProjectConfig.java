package cn.godk.sso.conf;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * 系统配置
 *
 * @author wt
 * @program project-sso
 * @create 2020-10-20  13:44
 */

@Getter
@Setter
@ToString
@Configuration
@EnableConfigurationProperties(ProjectConfig.class)
@PropertySource("classpath:application.yml")
@ConfigurationProperties(prefix = "project.cookie")
public class ProjectConfig {

    /**
     * cookie 名称
     */
    private String cookieName = "sso_cookie_name";


}
