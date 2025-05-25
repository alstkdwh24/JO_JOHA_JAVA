package com.example.trip.util;

import com.example.trip.service.myUser.MyUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtil {

    @Value("${jwt.secret}")//secret.key 파일의 고정된 비밀 키 사용

    private String secretKey;

    @PostConstruct
    public void init() {
        if (secretKey == null || secretKey.isEmpty()) {
            throw new RuntimeException("secret.key 를 읽어올 수 없습니다.");
        }
        System.out.println("Secret key loaded successfully: " + secretKey);
    }

    // JWT 토큰에서 이메일을 추출
    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject); //subject를 이메일로 사용
    }

    //JWT 토큰에서 만료시간을 추출
    public Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }

    //JWT 토큰에서 특정 클레인을 추출
    public <T> T extractClaim(String token, Function<Claims, T>claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    //JWT 토큰에서 모든 클레임을 추출
    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder().setSigningKey(secretKey.getBytes()).build().parseClaimsJws(token).getBody();
    }

    //JWT 토큰에서 만료되었는지 확인
    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    // 사용자 정보를 기반으로 JWT 토큰 생성
    public String generateToken(MyUserDetails myUserDetails){
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, myUserDetails.getUsername());
    }

    //클래임과 주제를 기반으로 JWT 토큰 생성
    private String createToken(Map<String, Object> claims, String subject){
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+ 1000 * 60 * 60* 24)) //24시간 동안 유효
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes()).compact();
    }

    //JWT 토큰의 유효성 검증
    public Boolean validateToken(String token, MyUserDetails myUserDetails){
        final String username = extractUsername(token);
        return (username.equals(myUserDetails.getUsername()) && !isTokenExpired(token));
    }

}
