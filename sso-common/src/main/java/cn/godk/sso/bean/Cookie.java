package cn.godk.sso.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * @author wt
 * @program project-sso
 * @create 2020-09-18  11:22
 */
@Setter
@Getter
public class Cookie extends Permit {

    public Cookie(String key) {
        super(key);
        setType(Type.cookie);
    }

}
