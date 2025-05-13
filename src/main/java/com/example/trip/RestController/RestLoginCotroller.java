package com.example.trip.RestController;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class RestLoginCotroller {

    @Value("${kakao.rest-api-key}") // 환경 변수 또는 application.properties에서 REST API 키를 읽어옴
    private String clientId;

    @Value("${kakao.redirect-uri}") // 환경 변수 또는 application.properties에서 Redirect URI를 읽어옴
    private String redirectUri;


    // @PostMapping("kakao/auth")
    // public ResponseEntity

    // @PostMapping("/jo-joha/join")
    // public ResponseEntity<ArrayList<KakaoVO>> postJoin(){
    // int saveJoin = RestLoginControllerService.saveJoin();
    // }


    // 액세스 토큰 처리 (필요 시 저장하거나 사용)

    // 인증 성공 후 메인 페이지로 리다이렉트


}
