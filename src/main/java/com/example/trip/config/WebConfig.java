package com.example.trip.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**").allowedOrigins("http://localhost:8081") // 명시적으로 도메인 설정
//                .allowedMethods("GET", "POST", "PUT", "DELETE")
//                .allowedHeaders("*")
//                .exposedHeaders("Authorization") // 클라이언트가 확인할 수 있는 특정 헤더 설정(필요한 경우 추가)
//                .allowCredentials(true); // 자격 증명 허용
//
//    }
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.addExposedHeader("Authorization"); // 클라이언트가 확인 가능한 헤더 설정

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }


}
