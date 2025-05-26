package com.example.trip.commendVO;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class JoJoHaVO  {

    private String username;

    // 비밀번호 필수 검증
    private String password;

    private String email;

    private String nickname;
    private String name;
    private String roles;

}