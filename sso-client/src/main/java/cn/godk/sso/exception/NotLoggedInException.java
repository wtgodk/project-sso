package cn.godk.sso.exception;

/**
 * not logged in exception
 *
 * @author wt
 * @program project-sso
 * @create 2020-09-27  14:28
 */
public class NotLoggedInException extends RuntimeException {

    public NotLoggedInException() {
    }

    public NotLoggedInException(String message) {
        super(message);
    }

    public NotLoggedInException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotLoggedInException(Throwable cause) {
        super(cause);
    }

    public NotLoggedInException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
