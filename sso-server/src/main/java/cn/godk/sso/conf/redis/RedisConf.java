package cn.godk.sso.conf.redis;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;

/**
 *
 *  redis configuration
 * @author wt
 * @program project-sso
 * @create 2020-10-20  10:39
 */
@Getter
@Setter
@ToString
@Configuration
@EnableConfigurationProperties(RedisConf.class)
@PropertySource("classpath:application.yml")
@ConfigurationProperties(prefix = "project.redis")
public class RedisConf  extends Jedis{
    /**
     *  连接池最大连接数（使用负值表示没有限制）
     */
    private int redisPoolMaxActive = 15;
    /**
     * 连接池中的最大空闲连接
     */
    private int redisPoolMaxWait =5000;
    /**
     * 连接池最大阻塞等待时间（使用负值表示没有限制）
     */
    private int redisPoolMaxIdle = 8;
    /**
     * 连接池中的最小空闲连接
     */
    private int redisPoolMinIdle = 2;

    /**
     * 配置Key的生成方式
     *
     * @return
     */
    @Bean
    public KeyGenerator keyGenerator() {
        return (o, method, objects) -> {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(o.getClass().getName())
                    .append(method.getName());
            for (Object object : objects) {
                stringBuilder.append(object.toString());
            }
            return stringBuilder.toString();
        };
    }

    /**
     * 创建redis连接工厂
     *
     * @param dbIndex
     * @param host
     * @param port
     * @param password
     * @param timeout
     * @return
     */
    public JedisConnectionFactory createJedisConnectionFactory(int dbIndex, String host, int port, String password, int timeout) {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setDatabase(dbIndex);
        redisStandaloneConfiguration.setHostName(host);
        redisStandaloneConfiguration.setPassword(password);
        redisStandaloneConfiguration.setPort(port);
        JedisClientConfiguration.JedisClientConfigurationBuilder jpcf = JedisClientConfiguration.builder();
        JedisPoolConfig jedisPoolConfig = setPoolConfig(redisPoolMaxIdle, redisPoolMinIdle, redisPoolMaxActive, redisPoolMaxWait, true);
        jpcf.usePooling().poolConfig(jedisPoolConfig);
        jpcf.readTimeout(Duration.ofMillis(timeout));
        JedisClientConfiguration build = jpcf.build();

        return new JedisConnectionFactory(redisStandaloneConfiguration, build);

    }

    /**
     * 配置CacheManager
     *
     * @param redisConnectionFactory
     * @return
     */
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        return RedisCacheManager.create(redisConnectionFactory);
    }

    /**
     * 设置连接池属性
     *
     * @param maxIdle
     * @param minIdle
     * @param maxActive
     * @param maxWait
     * @param testOnBorrow
     * @return
     */
    public JedisPoolConfig setPoolConfig(int maxIdle, int minIdle, int maxActive, int maxWait, boolean testOnBorrow) {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxIdle(maxIdle);
        poolConfig.setMinIdle(minIdle);
        poolConfig.setMaxTotal(maxActive);
        poolConfig.setMaxWaitMillis(maxWait);
        poolConfig.setTestOnBorrow(testOnBorrow);
        return poolConfig;
    }

    /**
     * 设置RedisTemplate的序列化方式
     *
     * @param redisTemplate
     */
    public <T> void setSerializer(RedisTemplate<String,T> redisTemplate,RedisSerializer<?> key,RedisSerializer<?> val) {
        //设置键（key）的序列化方式  String
        redisTemplate.setKeySerializer(key);
        //设置值（value）的序列化方式
        redisTemplate.setValueSerializer(val);
        redisTemplate.afterPropertiesSet();
    }




}
