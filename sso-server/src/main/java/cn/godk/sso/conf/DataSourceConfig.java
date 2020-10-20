package cn.godk.sso.conf;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.Data;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * 配置数据源,使用阿里Druid数据源
 *
 * @author wt
 * @program cas-server
 * @create 2019-12-27  11:38
 */
@Data
@Configuration
@EnableConfigurationProperties(DataSourceConfig.class)
@PropertySource("classpath:application.yml")
@ConfigurationProperties(prefix = "project.jdbc.datasource")
public class DataSourceConfig {

    /**
     * 数据源驱动类型
     */
    private String driver;

    /**
     * 连接地址
     */
    private String url;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 配置最大连接
     */
    private int maxActive = 200;
    /**
     * 配置初始连接
     */
    private int initialSize = 20;
    /**
     * 最小空闲连接
     */
    private int minIdle = 20;
    /**
     * 链接等待超时时间
     */
    private long maxWait = 60000L;
    /**
     * /间隔多久进行检测,关闭空闲连接
     */
    private long timeBetweenEvictionRunsMillis = 60000L;

    /**
     * 一个连接最小生存时间
     */
    private long minEvictableIdleTimeMillis = 300000L;

    /**
     * 配置Druid数据源
     *
     * @return
     * @throws SQLException
     */
    @Bean(name = "dataSource", destroyMethod = "close", initMethod = "init")
    public DataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        //配置最大连接
        dataSource.setMaxActive(maxActive);
        //配置初始连接
        dataSource.setInitialSize(initialSize);
        //配置最小连接
        dataSource.setMinIdle(minIdle);
        //连接等待超时时间
        dataSource.setMaxWait(maxWait);
        //间隔多久进行检测,关闭空闲连接
        dataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        //一个连接最小生存时间
        dataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        //连接等待超时时间 单位为毫秒 缺省启用公平锁，
        //并发效率会有所下降， 如果需要可以通过配置useUnfairLock属性为true使用非公平锁
        dataSource.setUseUnfairLock(true);
        //用来检测是否有效的sql
        dataSource.setValidationQuery("select 'x'");
        dataSource.setTestWhileIdle(true);
        //申请连接时执行validationQuery检测连接是否有效，配置为true会降低性能
        dataSource.setTestOnBorrow(false);
        //归还连接时执行validationQuery检测连接是否有效，配置为true会降低性能
        dataSource.setTestOnReturn(false);
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(@Qualifier("dataSource") DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource);
        return jdbcTemplate;
    }
}
