package cn.godk.sso.handler;

import cn.godk.sso.bean.Permit;

/**
 *
 *  验证相关 handler
 * @author wt
 * @program project-sso
 * @create 2020-09-17  10:50
 */
public interface VerificationHandler {
    /**
     *   获取缓存中 token
     * @param key  缓存key
     * @return
     */

    Permit get(String key);

    /**
     *   保存 token
     * @param permit
     */
    void save(Permit permit);

    /**
     *   生成
     * @param key  token key
     * @param appId appid
     * @param type  对应service类型
     * @return
     */
    Permit create(String key, String appId, Permit.Type type);


    /**
     *    删除指定 permit
     * @param key
     */
    Permit del(String key);


}
