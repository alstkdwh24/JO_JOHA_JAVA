package com.example.trip.RestController.LoginRestController;

import com.example.trip.commendVO.AlertResponseVO;
import com.example.trip.commendVO.JoJoHaVO;
import com.example.trip.commendVO.LoginResponseVO;
import com.example.trip.service.login.LoginService;
import com.example.trip.service.myUser.MyUserDetailServiceImpl;
import com.example.trip.service.myUser.MyUserDetails;
import com.example.trip.service.myUser.MyUserMapper;
import com.example.trip.service.myUser.MyUserService;
import com.example.trip.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/login")
public class LoginRestController {

    @Autowired
    @Qualifier("myUserService")
    private MyUserService myUserService;

    @Autowired
    public LoginRestController(PasswordEncoder passwordEncoder,
                               AuthenticationManager authenticationManager,
                               JwtTokenUtil jwtTokenUtil) {
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MyUserMapper myUserMapper;

    @Autowired
    private MyUserDetailServiceImpl myUserDetailServiceImpl;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;


    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody JoJoHaVO vo) {


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

    @GetMapping("/sameId")
    public ResponseEntity<?> sameId(@RequestParam("username") String username) {
        JoJoHaVO existUser = myUserMapper.findByUsernameName(username);
        if (existUser != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new AlertResponseVO("이미 존재하는 사용자입니다.", 409));
        } else {
            return ResponseEntity.ok(new AlertResponseVO("사용 가능한 아이디입니다.", 200));
        }
    }

    @PostMapping("/joJoLogin")
    public ResponseEntity<?> joJoLogin(@RequestBody JoJoHaVO vo) {
        String username = vo.getUsername();
        String password = vo.getPassword();

        try {
            // 인증 토큰 생성
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(username, password);

            // 인증 수행 (이 단계에서 이미 비밀번호 검증이 이루어짐)
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // JWT 토큰 생성
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String jwtToken = jwtTokenUtil.generateToken(userDetails.getUsername());

            // 인증이 성공했다면 바로 토큰 반환
            return ResponseEntity.ok(new LoginResponseVO("로그인 성공", 200, jwtToken));

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new AlertResponseVO("아이디 또는 비밀번호가 올바르지 않습니다.", 401));
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new AlertResponseVO("존재하지 않는 아이디입니다.", 401));
        }
    }
}