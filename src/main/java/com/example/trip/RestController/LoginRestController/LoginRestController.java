package com.example.trip.RestController.LoginRestController;

import com.example.trip.commendVO.JoJoHaVO;
import com.example.trip.service.login.LoginService;
import com.example.trip.service.myUser.MyUserDetails;
import com.example.trip.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor

public class LoginRestController {

    @Autowired
    @Qualifier("loginService")
    private LoginService loginService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @GetMapping("/login")
    public ResponseEntity<?>
    login(@RequestBody JoJoHaVO body) {
        UsernamePasswordAuthenticationToken authenticationToken= new UsernamePasswordAuthenticationToken(body.getUsername(), body.getPassword());

        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();

        String jwtToken = jwtTokenUtil.generateToken(myUserDetails);

        return ResponseEntity.ok(Collections.singletonMap("token", "Bearer " + jwtToken
        ));

    }


}