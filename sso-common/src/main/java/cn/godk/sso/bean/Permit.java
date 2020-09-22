package cn.godk.sso.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 *
 *  通行证   token  cookie 封装类的 顶级父类
 * @author wt
 * @program project-sso
 * @create 2020-09-15  17:16
 */
@Setter
@Getter
@ToString
public class Permit {

    public enum Type{
        /**
         *  cookie集成类型
         */
        cookie,
        /**
         *  token集成类型
         */
        token;
    }

    public Permit() {
    }

    public Permit(String key) {
        this.key = key;
        this.date = new Date();
    }

    /**
     *  通行证 唯一值
     */
    private String key;
    /**
     *  生成日期
     */
    private Date date;

    /**
     *  为哪一个 service 生成
     */
    private String appId;

    /**
     *  用户信息
     */
    private String username;

    /**
     * 登陆类型
     */
    private Type type;


}
