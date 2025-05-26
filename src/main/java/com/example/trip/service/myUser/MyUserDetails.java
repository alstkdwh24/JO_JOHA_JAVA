package com.example.trip.service.myUser;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data

public class MyUserDetails implements UserDetails {


    private  Integer id;

    private String username;

    private String password;

    private String nickname;

    private String email;

    private  String name;

    private Collection<? extends GrantedAuthority> authorities; // 사용자의 권한 목록

    private boolean enabled; // 계정의 활성화 여부



    // 컬렉션 값이 저장되는 컬럼 이름
    @Builder.Default

    private Set<String> roles = new HashSet<>(); // 역할 저장 필드 초기화

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role ->(GrantedAuthority)()->"ROLE_" + role)
                .collect(Collectors.toSet()); // 역할에 "ROLE_" 접두사 추가)
    }

    // 계정의 만료 여부
    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    // 계정의 잠금 여부
    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    // 자격 증명의 만료 여부
    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }
}