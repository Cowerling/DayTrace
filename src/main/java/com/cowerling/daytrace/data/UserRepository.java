package com.cowerling.daytrace.data;

import com.cowerling.daytrace.domain.user.User;
import com.cowerling.daytrace.domain.user.UserMedal;
import com.cowerling.daytrace.domain.user.UserOperationRecord;
import com.cowerling.daytrace.domain.user.UserProfile;
import com.cowerling.daytrace.web.exception.DuplicateUserException;

import java.util.List;

public interface UserRepository {
    User findUserByName(String name);
    void saveUser(User user) throws DuplicateUserException;

    UserProfile findUserProfileByUserId(Long userId);
    void saveUserProfile(UserProfile userProfile);

    List<UserMedal> findUserMedalsByUserId(Long userId);

    List<UserOperationRecord> findUserOperationRecordsByUserId(Long userId);
    void saveUserOperationRecord(UserOperationRecord userOperationRecord);
}
