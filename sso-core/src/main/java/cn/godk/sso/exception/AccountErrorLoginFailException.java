package cn.godk.sso.exception;

/**
 * 账号密码错误 登录失败
 * 拒绝
 *
 * @author wt
 * @program project-sso
 * @create 2020-09-17  13:27
 */
public class AccountErrorLoginFailException extends LoginFailException {

    public AccountErrorLoginFailException(String message) {
        super(message);
    }
}
