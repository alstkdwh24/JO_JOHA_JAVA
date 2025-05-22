package com.example.trip.commendVO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JoJoHaVO {
    private String username;
    private String password;
    private String nickname;
    private String email;
    private String name;
    private Set<String> roles;  // 권한을 여러 개 포함할 수 있도록 Set으로 변경
}
