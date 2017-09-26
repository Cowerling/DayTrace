package com.cowerling.daytrace.data.mapper;

import com.cowerling.daytrace.domain.user.User;
import com.cowerling.daytrace.domain.user.UserRole;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface UserMapper {
    @Select("SELECT t_user.id, t_user.name, password, email, t_user_role.name AS role, register_date, photo FROM t_user LEFT OUTER JOIN t_user_role ON t_user.role = t_user_role.id WHERE t_user.name = #{name}")
    @ResultMap("com.cowerling.daytrace.data.mapper.UserMapper.userResult")
    User selectUserByName(String name);

    @Select("SELECT t_user.name, email, t_user_role.name AS role, register_date, photo FROM t_user LEFT OUTER JOIN t_user_role ON t_user.role = t_user_role.id WHERE t_user_role.name = ANY(string_to_array(#{userRoles}, ' ')) ORDER BY t_user.name")
    @ResultMap("com.cowerling.daytrace.data.mapper.UserMapper.userResult")
    List<User> selectUsersByRole(@Param("userRoles") UserRole[] userRoles, RowBounds rowBounds);

    @Select("SELECT COUNT(*) FROM t_user LEFT OUTER JOIN t_user_role ON t_user.role = t_user_role.id WHERE t_user_role.name = ANY(string_to_array(#{userRoles}, ' '))")
    Long selectUserCountByRole(@Param("userRoles") UserRole[] userRoles);

    @Insert("INSERT INTO t_user(name, password, email, role, register_date, photo) VALUES(#{name}, #{password}, #{email}, (SELECT id FROM t_user_role WHERE name = #{userRole}), #{registerDate}, #{photo})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertUser(User user);

    @Update("UPDATE t_user SET password = #{password}, email = #{email}, role = (SELECT id FROM t_user_role WHERE name = #{userRole}), photo = #{photo} WHERE id = #{id}")
    void updateUser(User user);

    @Update("UPDATE t_user SET email = #{email}, photo = #{photo} WHERE id = #{id}")
    void updateUserById(@Param("id") Long id, @Param("email") String email, @Param("photo") String photo);
}
