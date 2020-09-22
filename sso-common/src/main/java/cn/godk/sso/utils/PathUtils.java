package cn.godk.sso.utils;

/**
 * @author wt
 * @program project-sso
 * @create 2020-09-22  14:40
 */
public class PathUtils {

    private static final String PATH_END = "/";

    /**
     * 路径补齐  路径强制以 / 结尾
     *
     * @param path
     * @return
     */
    public static String pathCompletion(String path) {
        if (path != null && !"".equals(path)) {
            return path.endsWith(PATH_END) ? path : path + PATH_END;
        }
        return PATH_END;
    }
}
