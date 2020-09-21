package cn.godk.sso.exception;

/**
 *
 *
 *  权限不足 登录失败
 * @author wt
 * @program project-sso
 * @create 2020-09-17  13:29
 */
public class DenyLoginFailException extends LoginFailException{

    public DenyLoginFailException(String message) {
        super(message);
    }
}
