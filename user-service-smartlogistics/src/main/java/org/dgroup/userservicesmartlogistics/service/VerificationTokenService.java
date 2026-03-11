package org.dgroup.userservicesmartlogistics.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.dgroup.userservicesmartlogistics.model.User;
import org.dgroup.userservicesmartlogistics.model.VerificationToken;
import org.dgroup.userservicesmartlogistics.repository.VerificationTokenRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class VerificationTokenService {
    private final VerificationTokenRepository tokenRepository;

    // TTL токена, наприклад 24 години (можна винести в properties)
    private final Duration TOKEN_TTL = Duration.ofHours(24);

    public VerificationToken createTokenForUser(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken vt = VerificationToken.builder()
                .token(token)
                .user(user)
                .expiryDate(LocalDateTime.now().plus(TOKEN_TTL))
                .build();
        return tokenRepository.save(vt);
    }

    public Optional<VerificationToken> findByToken(String token) {
        return tokenRepository.findByToken(token);
    }

    public void deleteToken(String token) {
        tokenRepository.deleteByToken(token);
    }
}
