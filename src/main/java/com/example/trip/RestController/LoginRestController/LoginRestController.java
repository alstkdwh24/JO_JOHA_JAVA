package com.example.trip.RestController.LoginRestController;

import com.example.trip.commendVO.JoJoHaVO;
import com.example.trip.record.JwtVO;
import com.example.trip.service.login.LoginService;
import com.example.trip.util.TripTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginRestController {

    @Autowired
    @Qualifier("loginService")
    private LoginService loginService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TripTokenProvider tripTokenProvider;

    public LoginRestController(AuthenticationManager authenticationManager, TripTokenProvider tripTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.tripTokenProvider = tripTokenProvider;
    }


    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> request){

           String token   = loginService.authenticateAndGenerateToken(request.get("username"), request.get("password"));

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.get("username"),
                        request.get("password")
                )

        ) ;           String jwtToken= String.valueOf(tripTokenProvider.jwtTokenGenerator(authentication));

        Map<String, String> response=new HashMap<>();
        response.put("token",jwtToken);
        return response;


    }
}
