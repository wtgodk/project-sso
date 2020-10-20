package cn.godk.sso.conf.redis;

import lombok.Getter;
import lombok.Setter;

/**
 * @author wt
 * @program project-sso
 * @create 2020-10-20  10:55
 */
@Setter
@Getter
public class Jedis {



    private int database = 0;

    private String host = "127.0.0.1";

    private int port = 6379;

    private String password;

    private int timeout ;
}
