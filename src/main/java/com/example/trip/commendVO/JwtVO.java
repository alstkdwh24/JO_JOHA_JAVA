package com.example.trip.commendVO;

import lombok.*;

//record 데이터의 불변성이 필요한 경우

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
public class JwtVO {
    private final String grantType;
    private final String accessToken;
    private final String refreshToken;
    private final String token;
    private final long accessTokenExpiresIn;

}


