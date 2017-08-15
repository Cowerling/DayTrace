package com.cowerling.daytrace.data.mapper;

import com.cowerling.daytrace.domain.user.User;
import org.apache.ibatis.annotations.*;

public interface UserMapper {
    @Select("SELECT t_user.id, t_user.name, password, email, t_user_role.name AS role, register_date, photo FROM t_user LEFT OUTER JOIN t_user_role ON t_user.role = t_user_role.id WHERE t_user.name = #{name}")
    @ResultMap("com.cowerling.daytrace.data.mapper.UserMapper.userResult")
    User selectUserByName(String name);

    @Insert("INSERT INTO t_user(name, password, email, role, register_date, photo) VALUES(#{name}, #{password}, #{email}, (SELECT id FROM t_user_role WHERE name = #{userRole}), #{registerDate}, #{photo})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertUser(User user);
}
