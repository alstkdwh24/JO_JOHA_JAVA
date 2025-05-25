package com.example.trip.commendVO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data

public class JoJoHaVO  {

    private String id;
    private String username;
    @JsonIgnore
    private String password;
    private String email;
    private boolean enabled;
    private String role;
    private String createdAt;
    private String updatedAt;

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Data
    public static class UserBase {
        private String id;
        private String username;
        @JsonIgnore
        private String password;
        private String email;
        private boolean enabled;
        private String role;
        private String createdAt;
        private String updatedAt;
    }




    @Data
    @Builder
    public static class UserSearchByUsernameCondition {
        private String username;
    }

}