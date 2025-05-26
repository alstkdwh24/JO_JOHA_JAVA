package com.example.trip.RestController.LoginRestController;

import com.example.trip.commendVO.JoJoHaVO;
import com.example.trip.service.login.LoginService;
import com.example.trip.service.myUser.MyUserDetailServiceImpl;
import com.example.trip.service.myUser.MyUserDetails;
import com.example.trip.service.myUser.MyUserService;
import com.example.trip.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/login")


public class LoginRestController {

    @Autowired
    @Qualifier("myUserService")
    private MyUserService myUserService;

    @Autowired
    private MyUserDetailServiceImpl myUserDetailServiceImpl;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @GetMapping("/login")
    public ResponseEntity<?> login(@RequestBody JoJoHaVO vo) {
        UsernamePasswordAuthenticationToken authenticationToken= new UsernamePasswordAuthenticationToken(vo.getUsername(), vo.getPassword());

        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();

        String jwtToken = jwtTokenUtil.generateToken(myUserDetails);

        return ResponseEntity.ok(Collections.singletonMap("token", "Bearer " + jwtToken
        ));

    }
    @PostMapping("/join")
    public ResponseEntity<?> join( @RequestBody JoJoHaVO vo) {
        try {
            // 서비스에서 사용자 등록 로직 처리
            int users = myUserService.joins(vo);

            // HTTP 201(Created) 상태와 함께 사용자 객체 반환
            return ResponseEntity.status(HttpStatus.CREATED).body(users);

        } catch (IllegalArgumentException e) {
            // 유효성 문제 발생 시 에러 응답 처리
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("error", e.getMessage()));
        }
    }


}