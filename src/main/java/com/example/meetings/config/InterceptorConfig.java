package com.example.meetings.config;

import com.example.meetings.filter.JWTInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        System.out.println("***********************************************************");
        System.out.println(registry.toString());
        System.out.println("***********************************************************");
        registry.addInterceptor(new JWTInterceptor())
                //拦截
                .addPathPatterns("/**")
                //放行register
                .excludePathPatterns("/user/login", "/user/register","/wx/subRegToAdmins");
        ///subRegToAdmins
    }
}