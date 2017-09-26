package com.cowerling.daytrace.data;

import com.cowerling.daytrace.data.mapper.UserMapper;
import com.cowerling.daytrace.data.mapper.UserMedalRecordMapper;
import com.cowerling.daytrace.data.mapper.UserOperationRecordMapper;
import com.cowerling.daytrace.data.mapper.UserProfileMapper;
import com.cowerling.daytrace.domain.user.*;
import com.cowerling.daytrace.web.exception.DuplicateUserException;
import com.cowerling.daytrace.web.exception.UserNotFoundException;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class MybatisUserRepository implements UserRepository {
    private static final String UNIQUE_CONSTRAINT_NAME  = "uc_name";
    private static final String UNIQUE_CONSTRAINT_EMAIL  = "uc_email";

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    private SqlSession currentSession() {
        return sqlSessionFactory.openSession();
    }

    @Override
    public User findUserByName(String name) {
        SqlSession sqlSession = currentSession();

        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            return userMapper.selectUserByName(name);
        } finally {
            sqlSession.close();
        }
    }

    @Override
    public List<User> findUsersByRole(UserRole[] userRoles, int offset, int limit) {
        SqlSession sqlSession = currentSession();

        try {
            RowBounds rowBounds = new RowBounds(offset, limit);
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            return userMapper.selectUsersByRole(userRoles, rowBounds);
        } finally {
            sqlSession.close();
        }
    }

    @Override
    public Long findUserCountByRole(UserRole[] userRoles) {
        SqlSession sqlSession = currentSession();

        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            return userMapper.selectUserCountByRole(userRoles);
        } finally {
            sqlSession.close();
        }
    }

    @Override
    public void saveUser(User user) throws DuplicateUserException {
        SqlSession sqlSession = currentSession();

        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            userMapper.insertUser(user);
            sqlSession.commit();
        } catch (Exception e) {
            if (e.getMessage().contains(UNIQUE_CONSTRAINT_NAME)) {
                throw new DuplicateUserException("Duplicate name");
            } else if (e.getMessage().contains(UNIQUE_CONSTRAINT_EMAIL)) {
                throw new DuplicateUserException("Duplicate email");
            }
            else {
                throw e;
            }
        } finally {
            sqlSession.close();
        }
    }

    @Override
    public void updateUser(User user) {
        SqlSession sqlSession = currentSession();

        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            userMapper.updateUser(user);
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }

    @Override
    public void updateUserById(Long id, String email, String photo) {
        SqlSession sqlSession = currentSession();

        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            userMapper.updateUserById(id, email, photo);
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }

    @Override
    public UserProfile findUserProfileByUserId(Long userId) {
        SqlSession sqlSession = currentSession();

        try {
            UserProfileMapper userProfileMapper = sqlSession.getMapper(UserProfileMapper.class);
            return userProfileMapper.selectUserProfileByUserId(userId);
        } finally {
            sqlSession.close();
        }
    }

    @Override
    public UserProfile findUserProfileByUserName(String userName) {
        SqlSession sqlSession = currentSession();

        try {
            UserProfileMapper userProfileMapper = sqlSession.getMapper(UserProfileMapper.class);
            return userProfileMapper.selectUserProfileByUserName(userName);
        } finally {
            sqlSession.close();
        }
    }

    @Override
    public void saveUserProfile(UserProfile userProfile) {
        SqlSession sqlSession = currentSession();

        try {
            UserProfileMapper userProfileMapper = sqlSession.getMapper(UserProfileMapper.class);
            userProfileMapper.insertUserProfile(userProfile);
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }

    @Override
    public void updateUserProfileByUserId(Long userId, String alias, UserGender gender, Date birthday, String phone, String brief) {
        SqlSession sqlSession = currentSession();

        try {
            UserProfileMapper userProfileMapper = sqlSession.getMapper(UserProfileMapper.class);
            userProfileMapper.updateUserProfileByUserId(userId, alias, gender, birthday, phone, brief);
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }

    @Override
    public List<UserMedal> findUserMedalsByUserId(Long userId) {
        SqlSession sqlSession = currentSession();

        try {
            UserMedalRecordMapper userMedalRecordMapper = sqlSession.getMapper(UserMedalRecordMapper.class);
            return userMedalRecordMapper.selectUserMedalsByUserId(userId);
        } finally {
            sqlSession.close();
        }
    }

    @Override
    public void saveUserMedalsByUserId(Long userId, Set<UserMedal> medals) {
        SqlSession sqlSession = currentSession();

        try {
            UserMedalRecordMapper userMedalRecordMapper = sqlSession.getMapper(UserMedalRecordMapper.class);

            Date date = new Date();

            for (UserMedal medal : medals) {
                userMedalRecordMapper.insertUserMedalByUserId(userId, medal, date);
            }

            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }

    @Override
    public void removeUserMedalsByUserId(Long userId, Set<UserMedal> medals) {
        SqlSession sqlSession = currentSession();

        try {
            UserMedalRecordMapper userMedalRecordMapper = sqlSession.getMapper(UserMedalRecordMapper.class);
            for (UserMedal medal : medals) {
                userMedalRecordMapper.deleteUserMedalsByUserId(userId, medal);
            }
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }

    @Override
    public void updateUserMedalsByUserId(Long userId, Set<UserMedal> removeMedals, Set<UserMedal> addMedals) {
        removeUserMedalsByUserId(userId, removeMedals);
        saveUserMedalsByUserId(userId, addMedals);
    }

    @Override
    public List<UserOperationRecord> findUserOperationRecordsByUserId(Long userId) {
        SqlSession sqlSession = currentSession();

        try {
            UserOperationRecordMapper userOperationRecordMapper = sqlSession.getMapper(UserOperationRecordMapper.class);
            return userOperationRecordMapper.selectUserOperationRecordsByUserId(userId);
        } finally {
            sqlSession.close();
        }
    }

    @Override
    public void saveUserOperationRecord(UserOperationRecord userOperationRecord) {
        SqlSession sqlSession = currentSession();

        try {
            UserOperationRecordMapper userOperationRecordMapper = sqlSession.getMapper(UserOperationRecordMapper.class);
            userOperationRecordMapper.insertUserOperationRecord(userOperationRecord);
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }
}
