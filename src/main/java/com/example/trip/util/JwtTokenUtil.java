package com.example.trip.util;

import com.example.trip.service.myUser.MyUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtil {

    @Value("${jwt.secret}")
    private String secretKeyString;

    private SecretKey secretKey;

    @PostConstruct
    public void init() {
        if (secretKeyString == null || secretKeyString.isEmpty()) {
            throw new RuntimeException("secret.key 를 읽어올 수 없습니다.");
        }
        // String -> SecretKey 변환
        secretKey = Keys.hmacShaKeyFor(secretKeyString.getBytes(StandardCharsets.UTF_8));
        System.out.println("Secret key loaded successfully: " + secretKeyString);
    }

    // JWT 토큰에서 이메일 추출
    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    // JWT 토큰에서 만료시간 추출
    public Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }

    // JWT 토큰에서 특정 클레인 추출
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // JWT 토큰에서 모든 클레인 추출
    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // JWT 토큰 만료 확인
    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    // 사용자 정보를 기반으로 JWT 토큰 생성
// 사용자 이름만으로 JWT 토큰 생성
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }


    // 클레임과 주제를 기반으로 JWT 토큰 생성
    private String createToken(Map<String, Object> claims, String subject){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 24시간 유효
                .signWith(secretKey)  // SecretKey 객체 사용
                .compact();
    }

    // JWT 토큰 유효성 검증
    public Boolean validateToken(String token, MyUserDetails myUserDetails){
        final String username = extractUsername(token);
        return (username.equals(myUserDetails.getUsername()) && !isTokenExpired(token));
    }

}
