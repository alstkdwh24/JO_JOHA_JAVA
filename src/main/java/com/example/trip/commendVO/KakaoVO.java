package com.example.trip.commendVO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KakaoVO {
    private String authorizationCodes; // 클라이언트로부터 전달받은 인증 코드

    // 필요시 추가 필드
    private String grantType;          // 기본적으로 "authorization_code" 사용
    private String clientId;           // Kakao REST API 키
private String accessToken;

}
