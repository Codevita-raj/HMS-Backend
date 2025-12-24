package com.projecth.hms.auth.service;

import com.projecth.hms.auth.dto.RefreshTokenRequest;
import com.projecth.hms.auth.dto.RefreshTokenResponse;
import com.projecth.hms.auth.entity.RefreshToken;
import com.projecth.hms.auth.repository.RefreshTokenRepository;
import com.projecth.hms.shared.exception.RefreshTokenException;
import com.projecth.hms.shared.utill.JwtUtil;
import com.projecth.hms.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private static final long REFRESH_TOKEN_EXPIRY_DAYS = 7;

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtil jwtUtil;

    public RefreshToken createRefreshToken(User user) {

        RefreshToken refreshToken = RefreshToken.builder()
                .token(UUID.randomUUID().toString())
                .user(user)
                .expiryTime(Instant.now().plus(REFRESH_TOKEN_EXPIRY_DAYS, ChronoUnit.DAYS))
                .revoked(false)
                .createdAt(LocalDateTime.now())
                .build();

        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshTokenResponse refreshAccessToken(String token) {

        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new RefreshTokenException("Invalid refresh token ,refreshAccessToken"));

        if (refreshToken.isRevoked()) {
            throw new RefreshTokenException("Refresh token revoked");
        }

        if (refreshToken.getExpiryTime().isBefore(Instant.now())) {
            throw new RefreshTokenException("Refresh token expired");
        }


        refreshToken.setRevoked(true);
        refreshToken.setLastUsedAt(LocalDateTime.now());
        refreshTokenRepository.save(refreshToken);
        User user = refreshToken.getUser();
        RefreshToken newRefreshToken = createRefreshToken(user);

        String newAccessToken = jwtUtil.generateToken(
                user.getId(),
                user.getEmail(),
                user.getRole().name()
        );

        return new RefreshTokenResponse(
                newAccessToken,
                newRefreshToken.getToken()
        );
    }
    public void revokeByToken(String token) {

        RefreshToken refreshToken = refreshTokenRepository
                .findByTokenAndRevokedFalse(token)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        refreshToken.setRevoked(true);
        refreshTokenRepository.save(refreshToken);
    }

    public void revokeAllUserTokens(User user) {
        List<RefreshToken> tokens =
                refreshTokenRepository.findAllByUserAndRevokedFalse(user);

        tokens.forEach(t -> t.setRevoked(true));
        refreshTokenRepository.saveAll(tokens);
    }
}


