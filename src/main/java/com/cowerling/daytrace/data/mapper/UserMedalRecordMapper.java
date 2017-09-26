package com.cowerling.daytrace.data.mapper;

import com.cowerling.daytrace.domain.user.UserMedal;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

public interface UserMedalRecordMapper {
    @Select("SELECT name FROM t_user_medal_record LEFT OUTER JOIN t_user_medal ON t_user_medal_record.medal = t_user_medal.id WHERE user_id = #{userId}")
    List<UserMedal> selectUserMedalsByUserId(Long userId);

    @Delete("DELETE FROM t_user_medal_record WHERE user_id = #{userId} AND medal = (SELECT id FROM t_user_medal WHERE name = #{medal})")
    int deleteUserMedalsByUserId(@Param("userId") Long userId, @Param("medal") UserMedal medal);

    @Insert("INSERT INTO t_user_medal_record (user_id, medal, acquire_time) VALUES (#{userId}, (SELECT id FROM t_user_medal WHERE name = #{medal}), #{acquireTime})")
    int insertUserMedalByUserId(@Param("userId") Long userId, @Param("medal") UserMedal medal, @Param("acquireTime") Date acquireTime);
}
