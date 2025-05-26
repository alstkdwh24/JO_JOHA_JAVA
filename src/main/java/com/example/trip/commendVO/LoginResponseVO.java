package com.example.trip.commendVO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponseVO {
    // Getter, Setter 추가
    private String message;
    private int code;
    private String jwtToken;
}
