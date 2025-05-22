package com.example.trip.util;

import com.example.trip.Repository.JwtUserRepository;
import com.example.trip.commendVO.JoJoHaVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service

public class CustomUserDetailsService implements UserDetailsService{

    @Autowired
    private JwtUserRepository jwtUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        JoJoHaVO user=jwtUserRepository.findByUsername(username) .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        List<GrantedAuthority> authorities = user.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role)) // 각 역할에 "ROLE_" 접두어 추가
                .collect(Collectors.toList());



        return User.withUsername(user.getUsername()).password(user.getPassword()).authorities(authorities).build();
    }
}

