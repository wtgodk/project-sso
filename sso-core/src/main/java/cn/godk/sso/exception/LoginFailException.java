package cn.godk.sso.exception;

/**
 *
 *  登录失败异常
 * @author wt
 * @program project-sso
 * @create 2020-09-17  13:26
 */
public class LoginFailException extends RuntimeException{

    public LoginFailException(String message) {
        super(message);
    }
}
