package com.example.trip.service.myUser;

import com.example.trip.commendVO.JoJoHaVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.context.annotation.Lazy;

@Lazy
@Mapper
public interface MyUserMapper{


    int joins(JoJoHaVO user);

    JoJoHaVO findByUsernameName(String username);


    JoJoHaVO findByUsername(String username);
}
