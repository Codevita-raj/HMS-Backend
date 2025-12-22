package com.projecth.hms.auth.service;

import com.projecth.hms.auth.dto.LoginRequest;
import com.projecth.hms.auth.dto.LoginResponse;
import com.projecth.hms.shared.utill.JwtUtil;
import com.projecth.hms.user.entity.User;
import com.projecth.hms.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service

public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

//   public RegisterResponse registerUser(RegisterRequest registerRequest){
//       if (userRepository.existsByEmail(registerRequest.getEmail())){
//           throw new RuntimeException("User email already exists "+registerRequest.getEmail());
//       }
//
//       User user =User.builder()
//               .email(registerRequest.getEmail())
//               .password(passwordEncoder.encode(registerRequest.getPassword()))
//               .createdAt(LocalDateTime.now())
//               .updatedAt(LocalDateTime.now())
//               .role(Role.ADMIN)
//               .status(UserStatus.ACTIVE)
//               .build();
//       User savedUser = userRepository.save(user);
//       return RegisterResponse.builder()
//               .id(savedUser.getId())
//               .email(savedUser.getEmail())
//               .role(savedUser.getRole().name())
//               .status(savedUser.getStatus().name())
//               .createdAt(savedUser.getCreatedAt())
//               .build();
//
//   }

   public LoginResponse login(LoginRequest loginRequest){
       User user = userRepository.findByEmail(loginRequest.getEmail())
               .orElseThrow(() -> new RuntimeException("User not found "+loginRequest.getEmail()));

            if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
           throw  new RuntimeException("Invalid email or password");
       }

            String token = jwtUtil.generateToken(
                    user.getId(),
                    user.getEmail(),
                    user.getRole().name()
            );
       LoginResponse loginResponse = new LoginResponse();
       loginResponse.setUserId(user.getId());
       loginResponse.setEmail(user.getEmail());
       loginResponse.setRole(user.getRole());
       loginResponse.setStatus(user.getStatus());
       loginResponse.setToken(token);

       return loginResponse;
   }

}
