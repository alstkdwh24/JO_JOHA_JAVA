package com.example.trip.service.myUser;

import com.example.trip.commendVO.AlertResponseVO;
import com.example.trip.commendVO.JoJoHaVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

//사용자 정보를 데이터베이스에서 로드하고, 이를 CustomUserDetails 로 변환하는 역할
@Service("myUserService")
@Slf4j
public class MyUserDetailServiceImpl implements UserDetailsService, MyUserService {

    @Autowired
    private MyUserMapper myUserMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        JoJoHaVO user = myUserMapper.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
    }

    public int joins(JoJoHaVO vo) {
      if(vo.getPassword() == null || vo.getPassword().isBlank()){
          throw new IllegalArgumentException("비밀번호는 필수 입력 항목입니다.");

      }


        String encodedPassword = passwordEncoder.encode(vo.getPassword());
JoJoHaVO user= JoJoHaVO.builder()
        .username(vo.getUsername())
        .password(encodedPassword)
        .email(vo.getEmail())
        .nickname(vo.getNickname())
        .name(vo.getName())
        .roles(vo.getRoles()) // 기본 역할을 USER로 설정
        .build();

      // 데이터베이스에 저장

      return myUserMapper.joins(user); // 저장된 사용자 반환

  }
}