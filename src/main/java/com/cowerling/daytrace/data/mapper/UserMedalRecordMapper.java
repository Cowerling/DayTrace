package com.cowerling.daytrace.data.mapper;

import com.cowerling.daytrace.domain.user.UserMedal;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserMedalRecordMapper {
    @Select("SELECT name FROM t_user_medal_record LEFT OUTER JOIN t_user_medal ON t_user_medal_record.medal = t_user_medal.id WHERE user_id = #{userId}")
    List<UserMedal> selectUserMedalsByUserId(Long userId);
}
