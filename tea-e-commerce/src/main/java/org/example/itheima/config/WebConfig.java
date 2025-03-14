package org.example.itheima.config;

import org.example.itheima.interceptors.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//配置类
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private LoginInterceptor loginInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //登录注册不拦截
        registry.addInterceptor(new LoginInterceptor())
                //.excludePathPatterns("/user/login", "/user/register", "/pay", "/notify");
                .excludePathPatterns("/**");
    }
}
