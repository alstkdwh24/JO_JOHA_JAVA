package com.example.trip.config;

import com.example.trip.service.myUser.MyUserDetailServiceImpl;
import com.example.trip.service.myUser.MyUserDetails;
import com.example.trip.util.JwtTokenUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private MyUserDetailServiceImpl myUserDetailServiceImpl; // 사용자 정보를 로드하는 서비스

    @Autowired
    private JwtTokenUtil jwtTokenUtil; // JWT 토큰 유틸리티 클래스

    // 필터링 로직은 정의
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
    throws ServletException, IOException {

        //Authorization 헤더에서 JWT 토큰을 가져옴
        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        //AUthorization 헤더가 존재하고 'Bearer '로 시작하는지 확인
        if(authorizationHeader !=null && authorizationHeader.startsWith("Bearer ")){

            // 'Bearer ' 다음의 JWT 토큰 부분만 추출
            jwt = authorizationHeader.substring(7);

            // JWT 토큰에서 이메일 추출
            username = jwtTokenUtil.extractUsername(jwt);
        }

        //username이 존재하고, 현재 SecurityContext에 인증 정보가 없는 경우
        if(username !=null && SecurityContextHolder.getContext().getAuthentication() == null){

            //이메일을 이용해 사용자 정보를 로드
            MyUserDetails myUserDetails = (MyUserDetails) myUserDetailServiceImpl.loadUserByUsername(username);

            // JWT 토큰이 유효한지 검증

            if(jwtTokenUtil.validateToken(jwt, myUserDetails)){

                // 유효한 경우, 인증 객체를 생성하여 SecurityContext에 설정
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(myUserDetails, null, myUserDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }

        //다음 필터(UsernamePasswordAuthenticationFilter )로 요청을 전달하여 로그인 요청
chain.doFilter(request, response);
    }
}
