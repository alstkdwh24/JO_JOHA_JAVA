package com.example.trip.commendVO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GoogleVO {
   private String access_Token;
    private String refresh_Token;
    private String expires_In;
    private String id_Token;
    private String token_Type;
    private String scope;
    private String AuthorizationCodes; // 클라이언트로부터 전달받은 인증 코드
}
