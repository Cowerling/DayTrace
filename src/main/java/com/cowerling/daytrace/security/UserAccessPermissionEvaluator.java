package com.cowerling.daytrace.security;

import com.cowerling.daytrace.data.UserRepository;
import com.cowerling.daytrace.domain.user.User;
import com.cowerling.daytrace.domain.user.UserRole;
import com.cowerling.daytrace.domain.user.form.UserEditForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class UserAccessPermissionEvaluator implements PermissionEvaluator {
    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean hasPermission(Authentication authentication, Object target, Object permission) {
        if (target instanceof String && permission instanceof User) {
            User user = userRepository.findUserByName((String) target);
            if (user == null) {
                return false;
            }

            User loginUser = (User) permission;

            if (user.getUserRole().ordinal() > loginUser.getUserRole().ordinal()) {
                return true;
            } else {
                return false;
            }
        } else if (target instanceof UserEditForm && permission instanceof User) {
            User user = userRepository.findUserByName(((UserEditForm) target).getOriginName());
            if (user == null) {
                return false;
            }

            User loginUser = (User) permission;

            if (user.getUserRole().ordinal() > loginUser.getUserRole().ordinal()) {
                return true;
            } else {
                return false;
            }
        }

        throw new UnsupportedOperationException("hasPermission not supported for object <" + target + "> and permission <" + permission + ">");
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        throw new UnsupportedOperationException();
    }
}
