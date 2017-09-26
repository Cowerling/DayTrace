package com.cowerling.daytrace.data.type;

import com.cowerling.daytrace.domain.user.UserMedal;
import com.cowerling.daytrace.domain.user.UserRole;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.stream.Collectors;

@MappedJdbcTypes(JdbcType.VARCHAR)
public class UserMedalArrayTypeHandler extends BaseTypeHandler<UserMedal[]> {
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, UserMedal[] parameter, JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(i, Arrays.stream(parameter).map(UserMedal::name).collect(Collectors.joining(" ")));
    }

    @Override
    public UserMedal[] getNullableResult(ResultSet resultSet, String columnName) throws SQLException {
        throw new SQLException();
    }

    @Override
    public UserMedal[] getNullableResult(ResultSet resultSet, int columnIndex) throws SQLException {
        throw new SQLException();
    }

    @Override
    public UserMedal[] getNullableResult(CallableStatement callableStatement, int columnIndex) throws SQLException {
        throw new SQLException();
    }
}
