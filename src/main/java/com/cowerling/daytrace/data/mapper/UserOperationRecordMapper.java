package com.cowerling.daytrace.data.mapper;

import com.cowerling.daytrace.domain.user.UserOperationRecord;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserOperationRecordMapper {
    @Select("SELECT t_user_operation.id, user_id, t_user_operation.name AS operation, time, message FROM t_user_operation_record LEFT OUTER JOIN t_user_operation ON t_user_operation_record.operation = t_user_operation.id WHERE user_id = #{userId} ORDER BY time DESC LIMIT 10")
    @ResultMap("com.cowerling.daytrace.data.mapper.UserOperationRecordMapper.userOperationRecordResult")
    List<UserOperationRecord> selectUserOperationRecordsByUserId(Long userId);

    @Insert("INSERT INTO t_user_operation_record(user_id, operation, time, message) VALUES(#{userId}, (SELECT id FROM t_user_operation WHERE name = #{userOperation}), #{time}, #{message})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertUserOperationRecord(UserOperationRecord userOperationRecord);
}
