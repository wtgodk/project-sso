# 客户端集成

1. java-web后端集成

    配置CookieSsoFilter过滤器(spring boot 配置方式)
        
        @Bean
        public FilterRegistrationBean<CookieSsoFilter> cookieSsoFilter(){
            FilterRegistrationBean<CookieSsoFilter> authenticationFilter = new FilterRegistrationBean<>();
            CookieSsoFilter cookieSsoFilter = new CookieSsoFilter();
            cookieSsoFilter.setKey("sso_cookie_name");
            cookieSsoFilter.setCrossDomain(true);
            authenticationFilter.setFilter(cookieSsoFilter);
            Map<String, String> initParameters = Maps.newHashMap();
            // sso-server服务地址 http://IP:PORT/
            initParameters.put("ssoServerUrl",ssoServer);
            // sso-server服务登出地址
            initParameters.put("ssoLogoutUrl",logoutPath);
            // sso-server服务登陆地址
            initParameters.put(ssoLoginUrlKey, ssoLoginUrl);
            // 客户端 ID
            initParameters.put("appId", appId);
            // 客户端服务地址
            initParameters.put("ssoClientUrl", ssoClientUrl);
            // 排除路径（不受登录控制的路径）
            initParameters.put("excludedPaths", "/index,/main");
            authenticationFilter.setInitParameters(initParameters);
            authenticationFilter.setOrder(0);
            return authenticationFilter;
        }
        
    退出登录
        /**
        *  @param request HttpServletRequest
        *  @param path 登陆成功跳转路径，最终跳转路径为 ssoClientUrl + @param path
        */
        cn.godk.sso.LogoutHelper.logout(HttpServletRequest request,String path);