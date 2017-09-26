package com.cowerling.daytrace.data;

import com.cowerling.daytrace.domain.user.*;
import com.cowerling.daytrace.web.exception.DuplicateUserException;
import com.cowerling.daytrace.web.exception.UserNotFoundException;

import java.util.Date;
import java.util.List;
import java.util.Set;

public interface UserRepository {
    User findUserByName(String name);
    List<User> findUsersByRole(UserRole[] userRoles, int offset, int limit);
    Long findUserCountByRole(UserRole[] userRoles);
    void saveUser(User user) throws DuplicateUserException;
    void updateUser(User user);
    void updateUserById(Long id, String email, String photo);

    UserProfile findUserProfileByUserId(Long userId);
    UserProfile findUserProfileByUserName(String userName);
    void saveUserProfile(UserProfile userProfile);
    void updateUserProfileByUserId(Long userId, String alias, UserGender gender, Date birthday, String phone, String brief);

    List<UserMedal> findUserMedalsByUserId(Long userId);
    void saveUserMedalsByUserId(Long userId, Set<UserMedal> medals);
    void removeUserMedalsByUserId(Long userId, Set<UserMedal> medals);
    void updateUserMedalsByUserId(Long userId, Set<UserMedal> removeMedals, Set<UserMedal> addMedals);

    List<UserOperationRecord> findUserOperationRecordsByUserId(Long userId);
    void saveUserOperationRecord(UserOperationRecord userOperationRecord);
}
