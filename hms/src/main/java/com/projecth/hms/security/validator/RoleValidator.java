package com.projecth.hms.security.validator;

import com.projecth.hms.shared.enums.Role;
import com.projecth.hms.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class RoleValidator {

//    public void requireAdmin(User currentUser) {
//        if (currentUser.getRole() != Role.ADMIN) {
//            throw new RuntimeException("Only ADMIN can perform this action");
//        }
//    }

    public void validateAdminCreation(User currentUser, Role roleToCreate) {
        if (roleToCreate == Role.ADMIN && currentUser.getRole() != Role.ADMIN) {
            throw new RuntimeException("Only ADMIN can create another ADMIN");
        }
    }
}

