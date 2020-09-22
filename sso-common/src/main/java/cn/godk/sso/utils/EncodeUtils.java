package cn.godk.sso.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * copy from arango db  url encode
 *
 * @author wt
 * @program project-sso
 * @create 2020-09-21  13:43
 */
public final class EncodeUtils {

    public static String encodeURL(final String value) throws UnsupportedEncodingException {
        return URLEncoder.encode(value, "UTF-8").replaceAll("\\+", "%20").replaceAll("\\%21", "!")
                .replaceAll("\\%27", "'").replaceAll("\\%28", "(").replaceAll("\\%29", ")").replaceAll("\\%7E", "~");
    }

}