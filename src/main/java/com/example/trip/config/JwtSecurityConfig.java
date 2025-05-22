package com.example.trip.config;

import com.example.trip.util.TripTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//직접만든 TokenProvider 와 JwtFilter를 SecurityConfig에 등록하기 위한 설정
@RequiredArgsConstructor

public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    private final TripTokenProvider tripTokenProvider;

    @Override
    public void configure(HttpSecurity http) {
        JwtFilter customFilter = new JwtFilter(tripTokenProvider);
        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
