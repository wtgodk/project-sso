package cn.godk.sso.cookie;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * cookie operation utils
 *
 * @author wt
 * @program project-sso
 * @create 2020-09-17  11:00
 */
public class CookieUtils {


    // 默认缓存时间,单位/秒, 2H
    private static final int COOKIE_MAX_AGE = Integer.MAX_VALUE;
    // 保存路径,根路径
    private static final String COOKIE_PATH = "/";

    /**
     * 从 request 中获取 cookie
     *
     * @param request    request
     * @param cookieName cookie key
     * @return
     */
    public static String get(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }


    /**
     * 保存 cookie
     *
     * @param response response
     * @param key      cookie key
     * @param value    value
     * @param remember 记住我
     */
    public static void set(HttpServletResponse response, String key, String value, boolean remember) {
        int age = remember ? COOKIE_MAX_AGE : -1;
        set(response, key, value, null, COOKIE_PATH, age, true);
    }


    /**
     * 销毁 cookie
     *
     * @param request
     * @param response
     * @param key
     */
    public static void destroy(HttpServletRequest request, HttpServletResponse response, String key) {
        String cookie = get(request, key);
        if (cookie != null) {
            set(response, key, "", null, COOKIE_PATH, 0, true);
        }
    }

    /**
     * 保存
     *
     * @param response
     * @param key
     * @param value
     * @param maxAge
     */
    private static void set(HttpServletResponse response, String key, String value, String domain, String path, int maxAge, boolean httpOnly) {
        Cookie cookie = new Cookie(key, value);
        if (domain != null) {
            cookie.setDomain(domain);
        }
        cookie.setPath(path);
        cookie.setMaxAge(maxAge);
        cookie.setHttpOnly(httpOnly);
        response.addCookie(cookie);
    }


}
