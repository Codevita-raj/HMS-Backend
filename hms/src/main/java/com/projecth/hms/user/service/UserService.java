package com.projecth.hms.user.service;

import com.projecth.hms.shared.utill.SecurityUtil;
import com.projecth.hms.user.entity.User;
import com.projecth.hms.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getCurrentUser() {
        String email = SecurityUtil.getCurrentUserEmail();
        if (email == null) {
            throw new RuntimeException("Unauthenticated");
        }
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
    public User getById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

}

