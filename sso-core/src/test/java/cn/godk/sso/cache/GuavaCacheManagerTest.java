package cn.godk.sso.cache;

import cn.godk.sso.cache.guava.GuavaCacheManager;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author wt
 * @program project-sso
 * @create 2020-09-18  13:31
 */
public class GuavaCacheManagerTest {

    @Test
    public void testGuava() {
        GuavaCacheManager<String> guavaCacheManager = new GuavaCacheManager<>(Cache.TOKEN);
        String test = guavaCacheManager.get("test");
        Assert.assertNull(test);
        String s = guavaCacheManager.create("test", "value", 100);
        Assert.assertEquals("value", s);

        String test2 = guavaCacheManager.get("test");
        Assert.assertEquals("value", test2);

    }

}
