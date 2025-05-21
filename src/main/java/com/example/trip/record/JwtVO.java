package com.example.trip.record;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

//record 데이터의 불변성이 필요한 경우

@Builder
public record JwtVO(String grantType,
String accessToken, String refreshToken, long accessTokenExpiresIn
) {}


