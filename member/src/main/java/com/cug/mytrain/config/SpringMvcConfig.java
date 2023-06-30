package com.cug.mytrain.config;

import com.cug.mytrain.interceptor.LogInterceptor;
import com.cug.mytrain.interceptor.MemberInterceptor;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 每个模块自定义使用哪些拦截器
 */
@Configuration
public class SpringMvcConfig implements WebMvcConfigurer {

    //开启日志流水号拦截器
    @Resource
    LogInterceptor logInterceptor;

    @Resource
    MemberInterceptor memberInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(logInterceptor);

        //把拦截器加进来
        // 路径不要包含context-path
        registry.addInterceptor(memberInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/hello",
                        "/member/send-code",
                        "/member/login"
                );
    }
}
