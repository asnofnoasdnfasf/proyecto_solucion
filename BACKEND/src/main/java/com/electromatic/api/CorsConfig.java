package com.electromatic.api;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    private final ApiAuthorizationInterceptor authorization;
    public CorsConfig(ApiAuthorizationInterceptor authorization){this.authorization=authorization;}
    @Override public void addInterceptors(InterceptorRegistry registry){registry.addInterceptor(authorization).addPathPatterns("/api/**");}
    @Override public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**").allowedOriginPatterns("http://localhost:4200", "http://127.0.0.1:4200")
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                .allowedHeaders("*");
    }
}
