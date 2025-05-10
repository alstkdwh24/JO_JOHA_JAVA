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
    private String id;
    private String nickname;
    private String email;
    private String profile_image;
    private String thumbnail_image;
    private String access_token;
    private String refresh_token;
    private String token_type;
    private String expires_in;
    private String scope;

}
