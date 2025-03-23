package org.example.itheima.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 使用明确的前后端分离路径
        registry.addResourceHandler("/static/qrcode/**")
                .addResourceLocations("file:C:/Users/ASUS/Desktop/tea/tea-e-commerce/file/qrcode/"); // 绝对路径
    }
    //C:\Users\ASUS\Desktop\tea-vue\tea-e-commerce-vue\src\assets\images\background.jpg
}
