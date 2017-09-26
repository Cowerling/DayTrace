package com.cowerling.daytrace.data.mapper;

import com.cowerling.daytrace.domain.user.UserGender;
import com.cowerling.daytrace.domain.user.UserProfile;
import org.apache.ibatis.annotations.*;

import java.util.Date;

public interface UserProfileMapper {
    @Select("SELECT t_user_profile.id, alias, t_user_gender.name AS gender, birthday, phone, brief, user_id FROM t_user_profile LEFT OUTER JOIN t_user_gender ON t_user_profile.gender = t_user_gender.id WHERE user_id = #{userId}")
    @ResultMap("com.cowerling.daytrace.data.mapper.UserProfileMapper.userProfileResult")
    UserProfile selectUserProfileByUserId(Long userId);

    @Select("SELECT t_user_profile.id, alias, t_user_gender.name AS gender, birthday, phone, brief, user_id FROM t_user_profile LEFT OUTER JOIN t_user_gender ON t_user_profile.gender = t_user_gender.id LEFT OUTER JOIN t_user ON t_user_profile.user_id = t_user.id WHERE t_user.name = #{userName}")
    @ResultMap("com.cowerling.daytrace.data.mapper.UserProfileMapper.userProfileResult")
    UserProfile selectUserProfileByUserName(String userName);

    @Insert("INSERT INTO t_user_profile(alias, gender, birthday, phone, user_id) VALUES(#{alias}, (SELECT id FROM t_user_gender WHERE name = #{userGender}), #{birthday}, #{phone}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertUserProfile(UserProfile userProfile);

    @Update("UPDATE t_user_profile SET alias = #{alias}, gender = (SELECT id FROM t_user_gender WHERE name = #{gender}), birthday = #{birthday}, phone = #{phone}, brief = #{brief} WHERE user_id = #{userId}")
    void updateUserProfileByUserId(@Param("userId") Long userId, @Param("alias") String alias, @Param("gender") UserGender gender, @Param("birthday") Date birthday, @Param("phone") String phone, @Param("brief") String brief);
}
