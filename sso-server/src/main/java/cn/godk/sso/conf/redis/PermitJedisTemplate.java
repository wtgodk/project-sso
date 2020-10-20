package cn.godk.sso.conf.redis;

import cn.godk.sso.bean.Permit;
import cn.godk.sso.manager.service.Service;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;

/**
 *
 *  permit redis template
 * @author wt
 * @program project-sso
 * @create 2020-10-20  10:55
 */

@EqualsAndHashCode(callSuper = true)
@Data
@EnableCaching
@Configuration
@EnableConfigurationProperties(PermitJedisTemplate.class)
@PropertySource("classpath:application.yml")
@ConfigurationProperties(prefix = "project.redis.permit")
public class PermitJedisTemplate extends  Jedis{


    @Resource
    private RedisConf redisConf;



    @Override
    public String getHost() {
        return StringUtils.isBlank(super.getHost()) ? redisConf.getHost() : super.getHost();
    }

    @Override
    public int getPort() {
        return super.getPort() == 0 ? redisConf.getPort() : super.getPort();
    }

    @Override
    public String getPassword() {
        return StringUtils.isBlank(super.getPassword()) ? redisConf.getPassword() : super.getPassword();
    }


    /**
     * 配置redis连接工厂
     *
     * @return
     */
    @Bean(name = "redisPermitConnectionFactory")
    public RedisConnectionFactory redisPermitConnectionFactory() {
        return redisConf.createJedisConnectionFactory(getDatabase(), getHost(), getPort(), getPassword(), getTimeout());
    }

    /**
     * 配置redisTemplate 注入方式使用@Resource(name="") 方式注入
     *
     * @return
     */

    @Bean(name = "permitRedisTemplate")
    public RedisTemplate<String, Permit> permitRedisTemplate() {
        RedisTemplate<String,Permit> template = new RedisTemplate<>();
        template.setConnectionFactory(redisPermitConnectionFactory());
        redisConf.setSerializer(template,new StringRedisSerializer(),new Jackson2JsonRedisSerializer<>(Permit.class));
        template.afterPropertiesSet();
        return template;
    }
}
