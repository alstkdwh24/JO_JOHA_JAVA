package com.example.trip.commendVO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JoJoHaVO {
    private String id;
    private String pw;
    private String nickname;
    private String email;
    private String name;
}
