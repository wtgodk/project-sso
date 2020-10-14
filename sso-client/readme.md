# 客户端集成

1.  java-web后端集成（使用sso-server登陆页面）

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
2.  App集成（使用客户端登陆页面，java-Demo）
    
    扩展TokenSsoFilter.java过滤器重写以下方法（非强制，该方式为了在全局异常处理可以捕获到抛出的异常）
    
    
       public class CustomTokenSsoFilter extends TokenSsoFilter {
           private HandlerExceptionResolver resolver;
           public CustomTokenSsoFilter(HandlerExceptionResolver resolver) {
               this.resolver = resolver;
           }
           public CustomTokenSsoFilter(TokenHandler tokenHandler, HandlerExceptionResolver resolver) {
               super(tokenHandler);
               this.resolver = resolver;
           }
           @Override
           protected void exception(ServletRequest request, ServletResponse response, RuntimeException e) {
               resolver.resolveException((HttpServletRequest) request, (HttpServletResponse)response, null,e);
           }
       }



    配置过滤器(该过滤器仅会拦截ajax请求，对于页面请求不会拦截)
    
    
        @Autowired
        @Qualifier("handlerExceptionResolver")
        private HandlerExceptionResolver resolver;
        
        @Bean
        public FilterRegistrationBean<TokenSsoFilter> tokenSsoFilter(){
            FilterRegistrationBean<TokenSsoFilter> authenticationFilter = new FilterRegistrationBean<>();
            TokenSsoFilter cookieSsoFilter = new CustomTokenSsoFilter(new DefaultTokenHandler(),resolver);
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

 
    登录页面处理（java-web-html）
        
        
        <script type="application/javascript" charset="UTF-8">
           // jquery form 提交，登录成功将token保存到cookie(根据实际情况选择保存位置)
           $("#submit").on("click",function(){
              var option ={
                  success:function (data) {
                    console.log(data);
                    if(data.code==0){
                        cookieSave("token",data.data.key);
                        // 登录成功跳转
                        window.location.href="/index";
                    }else{
                        alert("登陆失败")
                    }
                  }
              };
              $("#from").ajaxSubmit(option);
        
          })


    全局ajax控制：
            
            
        <script type="application/javascript" charset="UTF-8">
            // ajax发送前，获取保存的token，放到请求头中一并发送
            $(document).ajaxSend(function(event, jqxhr, settings) {
               jqxhr.setRequestHeader("token", $.cookie("token")) ;
            });
            // ajax请求成功，定义指定result.code为未登录状态（此处为全局异常返回结果，定义为302，根据自身情况定义，设置），失败则跳转到指定页面
            $(document).ajaxSuccess(function(event, xhr, settings) {
               var result = xhr.responseJSON;
                if(result.code==302){
                    window.location.href="/login";
                }
            });
    
        </script>