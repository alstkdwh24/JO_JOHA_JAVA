package com.example.trip.service.myUser;

import com.example.trip.commendVO.JoJoHaVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MyUserMapper{
    JoJoHaVO findByUsername(String username);

    int joins(JoJoHaVO user);

}
