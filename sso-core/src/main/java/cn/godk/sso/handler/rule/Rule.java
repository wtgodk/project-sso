package cn.godk.sso.handler.rule;


import cn.godk.sso.bean.Permit;

/**
 * 生成方法
 *
 * @author wt
 * @program project-sso
 * @create 2020-09-14  11:05
 */
public interface Rule {


    /**
     * 生成
     *
     * @return
     */
    public Permit create();
}
