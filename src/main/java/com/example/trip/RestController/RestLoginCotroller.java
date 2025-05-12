package com.example.trip.RestController;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;

@RestController

public class RestLoginCotroller {

    @Value("${kakao.rest-api-key}") // 환경 변수 또는 application.properties에서 REST API 키를 읽어옴
    private String clientId;

    @Value("${kakao.redirect-uri}") // 환경 변수 또는 application.properties에서 Redirect URI를 읽어옴
    private String redirectUri;

    @GetMapping("/mains")
    public RedirectView kakaoAuth(@RequestParam("code") String code) {
        String tokenUrl = "https://kauth.kakao.com/oauth/token";

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", clientId);
        params.add("redirect_uri", redirectUri);
        params.add("code", code);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        RestTemplate restTemplate = new RestTemplate();
        try {
            // POST 요청을 보내고 응답을 받음
            ResponseEntity<String> response =
                    restTemplate.postForEntity(tokenUrl, request, String.class);

            System.out.println("Access Token Response: " + response.getBody());
            return new RedirectView("/main");
        } catch (Exception e) {
            e.printStackTrace();
            // 에러 처리
            return new RedirectView("/main");
        }
    }
    // @PostMapping("/jo-joha/join")
    // public ResponseEntity<ArrayList<KakaoVO>> postJoin(){
    // int saveJoin = RestLoginControllerService.saveJoin();
    // }


    // 액세스 토큰 처리 (필요 시 저장하거나 사용)

    // 인증 성공 후 메인 페이지로 리다이렉트


}
