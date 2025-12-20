package com.projecth.hms.auth.service;

import com.projecth.hms.auth.dto.LoginRequest;
import com.projecth.hms.auth.dto.LoginResponse;
import com.projecth.hms.auth.dto.RegisterRequest;
import com.projecth.hms.auth.dto.RegisterResponse;
import com.projecth.hms.user.entity.User;
import com.projecth.hms.user.repository.UserRepository;
import com.projecth.hms.shared.enums.Role;
import com.projecth.hms.shared.enums.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
@RequiredArgsConstructor
@Service

public class AuthService {

    private final UserRepository userRepository;
   public RegisterResponse registerUser(RegisterRequest registerRequest){
       if (userRepository.existsByEmail(registerRequest.getEmail())){
           throw new RuntimeException("User email already exists "+registerRequest.getEmail());
       }

       User user =User.builder()
               .email(registerRequest.getEmail())
               .password(registerRequest.getPassword())
               .phone(registerRequest.getPhone())
               .createdAt(LocalDateTime.now())
               .updatedAt(LocalDateTime.now())
               .role(Role.ADMIN)
               .status(UserStatus.ACTIVE)
               .build();
       User savedUser = userRepository.save(user);
       return RegisterResponse.builder()
               .id(savedUser.getId())
               .email(savedUser.getEmail())
               .phone(savedUser.getPhone())
               .role(savedUser.getRole().name())
               .status(savedUser.getStatus().name())
               .createdAt(savedUser.getCreatedAt())
               .build();

   }

   public LoginResponse login(LoginRequest loginRequest){
       User user = userRepository.findByEmail(loginRequest.getEmail())
               .orElseThrow(() -> new RuntimeException("User not found "+loginRequest.getEmail()));

            if (!user.getPassword().equals(loginRequest.getPassword())) {
           throw  new RuntimeException("Invalid email or password");
       }

       LoginResponse loginResponse = new LoginResponse();
       loginResponse.setUserId(user.getId());
       loginResponse.setEmail(user.getEmail());
       loginResponse.setRole(user.getRole());
       loginResponse.setStatus(user.getStatus());

       return loginResponse;
   }

}
