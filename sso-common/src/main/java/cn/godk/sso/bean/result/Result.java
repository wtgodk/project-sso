package cn.godk.sso.bean.result;

import lombok.Getter;
import lombok.Setter;

/**
 *
 *  返回值
 * @author wt
 * @program project-sso
 * @create 2020-09-16  15:34
 */
@Setter
@Getter
public class Result<T>  {

    /**
     *  返回结果  -0 请求成功
     *          -1 请求失败
     */
    private int code;

    /**
     *  具体内容
     */
    private T data;

    /**
     *  提示信息
     */
    private String message;


}