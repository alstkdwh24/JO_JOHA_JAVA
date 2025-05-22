package com.example.trip.config;

import com.example.trip.util.TripTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor

public class JwtFilter extends OncePerRequestFilter {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";

    private final TripTokenProvider tripTokenProvider;

    @Override
    protected void doFilterInternal (HttpServletRequest request, HttpServletResponse response, FilterChain filterChain )throws IOException, ServletException {

        // 1. Request Header에서 JWT를 받아옵니다.
        String jwt = resolveToken(request);

        //2. validateToken 메서드를 통해 유효성을 검사합니다.
        //정상 토큰이면 해당 토큰으로 Authentication 객체를 만들어 SecurityContext에 저장합니다.
        if(StringUtils.hasText(jwt) && tripTokenProvider.validateToken(jwt)){
            Authentication authentication = tripTokenProvider.getAuthentication(jwt);
        }

        filterChain.doFilter(request, response);
    }
    //Request Header에서 JWT를 꺼내는 메서드입니다.
    private String resolveToken(HttpServletRequest request){
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)){
            return bearerToken.split(" ")[1].trim();
        }
        return null;

    }
}
