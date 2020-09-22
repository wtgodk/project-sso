package cn.godk.sso.handler.rule;


import cn.godk.sso.bean.Permit;

import java.util.UUID;

/**
 * 默认创建方法
 *
 * @author wt
 * @program project-sso
 * @create 2020-09-14  11:06
 */
public class DefaultRule implements Rule {


    @Override
    public Permit create() {
        String uuid = UUID.randomUUID().toString().replace("-", "").substring(0, 20);
        return new Permit(uuid);
    }
}
