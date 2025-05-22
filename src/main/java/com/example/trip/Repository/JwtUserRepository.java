package com.example.trip.Repository;

import com.example.trip.commendVO.JoJoHaVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JwtUserRepository extends JpaRepository<User,Long> {
    Optional<JoJoHaVO> findByUsername(String username);
}
