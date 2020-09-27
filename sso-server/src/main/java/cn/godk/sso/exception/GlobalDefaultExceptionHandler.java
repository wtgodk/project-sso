package cn.godk.sso.exception;

import cn.godk.sso.bean.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

/**
 * 全局异常捕获 处理
 *
 * @author weitao
 * @program aircraft
 * @create 2019-04-25  13:49
 */
@ControllerAdvice
@Slf4j
public class GlobalDefaultExceptionHandler {

    /**
     * 声明要捕获的异常
     *
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object defaultExceptionHandler(HttpServletRequest request, Exception e) {
        String uri = request.getRequestURI();
        //未知错误
        Result<?> result;
        if (e instanceof LoginFailException) {
            result = new Result<>(2, e.getMessage(), "fail");
            log.info(new Date() + "login fail " + e);
        } else if (e instanceof RuntimeException) {
            result = new Result<>(1, "exception: 系统异常：" + e, null);
            setLogger(e, "EXCEPTION:", uri);
        } else {
            result = new Result<>(-1, "exception: 未知错误：" + e, null);
            setLogger(e, "EXCEPTION:", uri);
        }
        return result;
    }

    /**
     * 日志  日志打印详细 异常信息
     *
     * @param e       异常信息
     * @param content 详细内容
     */
    public static void setLogger(Exception e, String content, String requestUrl) {
        StringWriter sw = null;
        PrintWriter pw = null;
        try {
            sw = new StringWriter();
            pw = new PrintWriter(sw);
            //将出错的栈信息输出到printWriter中
            e.printStackTrace(pw);
            pw.flush();
            sw.flush();
        } finally {
            if (sw != null) {
                try {
                    sw.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (pw != null) {
                pw.close();
            }
        }
        log.error(new Date() + content + ":\n 请求链接：" + requestUrl + "\n 异常信息：" + sw.toString());
    }


}
