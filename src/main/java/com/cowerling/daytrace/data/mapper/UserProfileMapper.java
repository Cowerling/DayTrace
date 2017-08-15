package com.cowerling.daytrace.data.mapper;

import com.cowerling.daytrace.domain.user.UserProfile;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

public interface UserProfileMapper {
    @Select("SELECT t_user_profile.id, alias, t_user_gender.name AS gender, birthday, phone, user_id FROM t_user_profile LEFT OUTER JOIN t_user_gender ON t_user_profile.gender = t_user_gender.id WHERE user_id = #{userId}")
    @ResultMap("com.cowerling.daytrace.data.mapper.UserProfileMapper.userProfileResult")
    UserProfile selectUserProfileByUserId(Long userId);

    @Insert("INSERT INTO t_user_profile(alias, gender, birthday, phone, user_id) VALUES(#{alias}, (SELECT id FROM t_user_gender WHERE name = #{userGender}), #{birthday}, #{phone}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertUserProfile(UserProfile userProfile);
}
