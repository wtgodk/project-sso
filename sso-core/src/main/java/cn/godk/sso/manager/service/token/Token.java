package cn.godk.sso.manager.service.token;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 *
 *   token info 封装
 *
 *
 *    //TODO  完善token 信息
 * @author wt
 * @program project-sso
 * @create 2020-09-14  10:30
 */
@Setter
@Getter
@ToString
public class Token {

    public Token(String token) {
        this.token = token;
        date = new Date();
    }

    /**
     *  token 字符串
     */
   private  String token;


    /**
     *  生成日期
     */
    private Date date;

    /**
     *  为哪一个 service 生成
     */
    private String appId;

    /**
     *    保存 token  key值
     */
    private String tokenName;


}
