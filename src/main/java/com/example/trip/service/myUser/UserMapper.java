package com.example.trip.service.myUser;

import com.example.trip.commendVO.JoJoHaVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    JoJoHaVO.UserBase findByUsername(JoJoHaVO.UserSearchByUsernameCondition param);

}
