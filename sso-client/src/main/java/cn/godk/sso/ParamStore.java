package cn.godk.sso;

/**
 * 参数仓库 地址
 *
 * @author wt
 * @program project-sso
 * @create 2020-10-13  10:49
 */
public class ParamStore {

    public static String tokenKey;
    /**
     * 服务ID
     */
    public static String appId;
    /**
     * sso 地址
     */
    public static String ssoServer;
    /**
     * sso 登陆页面地址
     */
    public static String ssoLoginUrl;
    /**
     * 退出登录地址
     */
    public static String logoutPath;
    /**
     * 排除路径
     */
    public static String excludedPaths;
    /**
     * 客户端 地址
     */
    public static String ssoClientUrl;
}
