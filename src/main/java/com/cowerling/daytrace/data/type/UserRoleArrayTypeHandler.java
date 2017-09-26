package com.cowerling.daytrace.data.type;

import com.cowerling.daytrace.domain.user.UserRole;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;

import java.sql.*;
import java.util.Arrays;
import java.util.stream.Collectors;

@MappedJdbcTypes(JdbcType.VARCHAR)
public class UserRoleArrayTypeHandler extends BaseTypeHandler<UserRole[]> {
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, UserRole[] parameter, JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(i, Arrays.stream(parameter).map(UserRole::name).collect(Collectors.joining(" ")));
    }

    @Override
    public UserRole[] getNullableResult(ResultSet resultSet, String columnName) throws SQLException {
        throw new SQLException();
    }

    @Override
    public UserRole[] getNullableResult(ResultSet resultSet, int columnIndex) throws SQLException {
        throw new SQLException();
    }

    @Override
    public UserRole[] getNullableResult(CallableStatement callableStatement, int columnIndex) throws SQLException {
        throw new SQLException();
    }
}
