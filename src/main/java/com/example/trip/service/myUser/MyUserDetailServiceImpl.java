package com.example.trip.service.myUser;

import com.example.trip.commendVO.JoJoHaVO;
import com.example.trip.service.login.LoginMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

//사용자 정보를 데이터베이스에서 로드하고, 이를 CustomUserDetails 로 변환하는 역할
@Service
@Slf4j
public class MyUserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper UserMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //데이터베이스에서 이메일을 기반으로 사용자 정보를 조회
        JoJoHaVO.UserBase user = UserMapper.findByUsername(JoJoHaVO.UserSearchByUsernameCondition.builder()
                .username(username)
                .build());
        if (user == null) {
            // 사용자가 없을 경우 예외처리
            throw new UsernameNotFoundException(username + " 이메일로 가입된 사용자가 없습니다");
        }

        //사용자의 권한 목록을 생성
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("user"));

        // CustomUserDetials 객체를 생성하여 반환

        return new MyUserDetails(user.getId(), user.getUsername(), user.getEmail(), user.getPassword(), authorities, user.isEnabled());
    }
}