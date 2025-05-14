package com.example.trip.RestController;

import com.example.trip.commendVO.KakaoVO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/kakao")
@CrossOrigin(origins = "http://localhost:8081", allowCredentials = "true") // 특정 도메인에 대해 허용
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
    @PostMapping(path = "/token", consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Map<String, Object
            >> getKakaoToken(@RequestBody KakaoVO kakaoVO) {

        // 1. RestTemplate 초기화
        RestTemplate restTemplate = new RestTemplate();

        // 2. 요청 URL 및 헤더 설정
        String tokenUrl = "https://kauth.kakao.com/oauth/token";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // 3. 요청 데이터 추가
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code"); // 고정 값
        params.add("client_id", clientId);              // 클라이언트 ID
        params.add("redirect_uri", redirectUri);        // 리다이렉트 URI
        params.add("code", kakaoVO.getAuthorizationCodes()); // 요청 코드

        // 4. HTTP 요청 전송
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(tokenUrl, request, String.class);
            System.out.println(1);

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonResponse = objectMapper.readTree(response.getBody());
            Map<String, Object> result = new HashMap<>();
            result.put("access_token", jsonResponse.get("access_token").asText());
            result.put("refresh_token", jsonResponse.get("refresh_token").asText());
            result.put("id_token", jsonResponse.get("id_token").asText());
            result.put("expires_in", jsonResponse.get("expires_in").asInt());
            return ResponseEntity.ok(result);

        } catch (Exception e) {
            // 6. 실패 결과 반환
            return ResponseEntity.badRequest().body(Map.of("error", "Error during Kakao token request: " + e.getMessage()));
        }
    }



}
