package com.example.trip.config;


import com.example.trip.service.myUser.MyUserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration //Spring 설정 클래스임
@EnableWebSecurity //Spring Security를 활성화
public class SecurityConfig {
    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    private MyUserDetailServiceImpl MyUserDetailServiceImpl; // 사용자 정보를 로드하는 서비스

    //HTTP 보안을 설정을 구성하는 메서드
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests(authorizeRequests -> /* HTTP 요청에 대한 보안 규칙을 정의*/
                authorizeRequests.requestMatchers("/**").permitAll() // 모든 요청을 허용);
                        .anyRequest().authenticated() // 그 외의 모든 요청은 인증이 필요합니다.

        )
        // .httpBasic(withDefaults()) // HTTP Basic 인증을 사용합니다.-> 주로 간단한 테스트에 이용됩니다. Jwt토큰 인증을 이용하면 없어도 됨니다.
        .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class) //JWT 필터 추가
                .cors(cors -> cors.disable()) // CORS 설정 비활성화
                .csrf(csrf -> csrf.disable());
        return http.build();
    }

    // Spring Security 의 인증 관리자. 사용자의 인증을 관리
    @Bean
    public AuthenticationManager authenticationManagerBean( HttpSecurity http) throws Exception{
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder.userDetailsService(MyUserDetailServiceImpl) //사용자 정보를 로드
                .passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }

    // 비밀번호 인코더를 정의
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // BCryptPasswordEncoder를 반환. 이는 비밀번호를 암호화하는 데 사용됨
    }

}
