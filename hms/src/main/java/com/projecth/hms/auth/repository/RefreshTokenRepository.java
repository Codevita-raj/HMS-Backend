package com.projecth.hms.auth.repository;

import com.projecth.hms.auth.entity.RefreshToken;
import com.projecth.hms.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);

    List<RefreshToken> findAllByUserAndRevokedFalse(User user);
    void deleteByUserId(Long userId);

   Optional<RefreshToken> findByTokenAndRevokedFalse(String token);
}

