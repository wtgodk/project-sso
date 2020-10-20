package cn.godk.sso.cache;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;

import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author wt
 * @program project-sso
 * @create 2020-10-20  09:54
 */
@Slf4j
@Setter
@Getter
public abstract class AbstractRedisCacheManager<T> implements IRedisCacheManager<T> {


    private RedisTemplate<String, T> template;
    /**
     * key 前缀，可以用于区别不同类型缓存
     */
    private String keyPrefix = "";

    public AbstractRedisCacheManager(RedisTemplate<String, T> template) {
        this.template = template;
    }

    public AbstractRedisCacheManager(RedisTemplate<String, T> template, Cache cache) {
        this.template = template;
        this.keyPrefix = cache.name();
    }

    @Override
    public T create(String key, T val, long expired) {
        template.opsForValue().set(getKey(key), val, expired, TimeUnit.MILLISECONDS);
        return val;
    }


    @Override
    public T get(String key) {
        return template.opsForValue().get(getKey(key));
    }

    @Override
    public void del(String key) {
        template.delete(getKey(key));
    }

    @Override
    public void clear() {
        Objects.requireNonNull(template.getConnectionFactory()).getConnection().flushDb();
    }

    @Override
    public long size() {
        return Objects.requireNonNull(Objects.requireNonNull(template.getConnectionFactory()).getConnection().dbSize()).intValue();
    }

    @Override
    public Set<String> keys(String keys) {
        return template.keys(keys);

    }

    @Override
    public T delIfExist(String key) {
        T t = get(key);
        if (t != null) {
            del(key);
            return t;
        }
        return null;
    }

    @Override
    public T refresh(String key) {
        return get(key);
    }

    @Override
    public List<T> mGet(String... keys) {
        List<String> keyList = Lists.newArrayList();
        if (keys != null && keys.length > 0) {
            for (String key : keys) {
                keyList.add(getKey(key));
            }
        }
        return template.opsForValue().multiGet(keyList);
    }

    @Override
    public Map<String, T> mGet(List<String> keys) {
        List<Object> objects = template.executePipelined(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                for (String key : keys) {
                    try {
                        connection.get(getKey(key).getBytes("utf8"));
                    } catch (UnsupportedEncodingException e) {
                        log.error("{} redis mGet pipeline key.getBytes exception : {}", new Date(), e);
                    }
                }
                return null;
            }
        }, template.getValueSerializer());
        Map<String, T> map = Maps.newHashMap();
        for (int i = 0; i < keys.size(); i++) {
            String label = keys.get(i);
            T value = (T) objects.get(i);
            if (value == null) {
                continue;
            }
            map.put(label, value);
        }
        return map;
    }


    @Override
    public Set<T> scan(String match, int count) {
        Set<T> execute = getTemplate().execute(new RedisCallback<Set<T>>() {
            @Override
            public Set<T> doInRedis(RedisConnection connection) throws DataAccessException {
                Set<T> binaryKeys = new HashSet<>();
                Cursor<byte[]> cursor = connection.scan(new ScanOptions.ScanOptionsBuilder().match(match).count(count).build());
                while (cursor.hasNext()) {
                    binaryKeys.add((T) cursor.next());
                }
                return binaryKeys;
            }
        });
        return execute;
    }

    /**
     * 为key 添加 key prefix
     *
     * @param key 原始 key
     * @return
     */
    protected String getKey(String key) {

        return keyPrefix + (key == null ? "" : key.trim());

    }

}
