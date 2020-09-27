package cn.godk.sso.conf;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 *
 *
 *  相关 sql 语句 配置文件
 * @author wt
 * @program project-sso
 * @create 2020-09-27  09:48
 */
@Getter
@Setter
@ToString
@Configuration
@EnableConfigurationProperties(JdbcConfig.class)
@PropertySource("classpath:application.yml")
@ConfigurationProperties(prefix = "project.sql")
public class JdbcConfig {

    /**
     *   用户登录验证查询sql语句
     */
    private String loginCheck = "select * from user u where username = ? and password = ?";

}
