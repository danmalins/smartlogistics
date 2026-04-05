package org.dgroup.userservicesmartlogistics.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class VerificationRateLimitService {

    private final StringRedisTemplate redisTemplate;

    private static final long RESEND_COOLDOWN_SECONDS = 60;

    public void checkRateLimit(String email) {
        String key = buildKey(email);

        Boolean success = redisTemplate.opsForValue().setIfAbsent(
                key,
                "BLOCKED",
                RESEND_COOLDOWN_SECONDS,
                TimeUnit.SECONDS
        );

        if (Boolean.FALSE.equals(success))
            throw new RuntimeException("Too many requests. Try again in 60 seconds.");
    }

    private String buildKey(String email) {
        return "verification:resend:" + email;
    }
}
