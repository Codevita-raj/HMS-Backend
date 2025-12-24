package com.projecth.hms.auth.service;

import com.projecth.hms.auth.dto.LoginRequest;
import com.projecth.hms.auth.dto.LoginResponse;
import com.projecth.hms.shared.utill.JwtUtil;
import com.projecth.hms.auth.entity.RefreshToken;
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
    private final RefreshTokenService refreshTokenService;

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

       RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);

       LoginResponse loginResponse = new LoginResponse();
       loginResponse.setUserId(user.getId());
       loginResponse.setEmail(user.getEmail());
       loginResponse.setRole(user.getRole());
       loginResponse.setStatus(user.getStatus());
       loginResponse.setTokenType(loginResponse.getTokenType());
       loginResponse.setToken(token);
       loginResponse.setRefreshToken(refreshToken.getToken());

       return loginResponse;
   }

}
