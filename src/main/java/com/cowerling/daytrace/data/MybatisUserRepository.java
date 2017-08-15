package com.cowerling.daytrace.data;

import com.cowerling.daytrace.data.mapper.UserMapper;
import com.cowerling.daytrace.data.mapper.UserMedalRecordMapper;
import com.cowerling.daytrace.data.mapper.UserOperationRecordMapper;
import com.cowerling.daytrace.data.mapper.UserProfileMapper;
import com.cowerling.daytrace.domain.user.User;
import com.cowerling.daytrace.domain.user.UserMedal;
import com.cowerling.daytrace.domain.user.UserOperationRecord;
import com.cowerling.daytrace.domain.user.UserProfile;
import com.cowerling.daytrace.web.exception.DuplicateUserException;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

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
