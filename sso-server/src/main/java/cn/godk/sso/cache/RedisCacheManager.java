package cn.godk.sso.cache;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 *
 *
 *  redis cache manager
 * @author wt
 * @program project-sso
 * @create 2020-10-20  09:45
 */
@Setter
@Getter
@Slf4j
public class RedisCacheManager<T> extends AbstractRedisCacheManager<T> {


    public RedisCacheManager(RedisTemplate<String, T> template) {
        super(template);
    }

    public RedisCacheManager(RedisTemplate<String, T> template, Cache cache) {
        super(template, cache);
    }

    @Override
    public List<T> all() {
        Set<String> keys = keys("*");
        List<T> vals = Lists.newArrayList();
      if(keys!=null && keys.size()>0){
          for (String key : keys) {
              T t = getTemplate().opsForValue().get(key);
              vals.add(t);
          }
      }
        return vals;
    }





}
