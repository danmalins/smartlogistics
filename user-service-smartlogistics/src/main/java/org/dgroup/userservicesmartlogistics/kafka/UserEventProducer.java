package org.dgroup.userservicesmartlogistics.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dgroup.commonevents.UserRegisteredEvent;
import org.dgroup.commonevents.VerificationEmailEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishUserRegistered(UserRegisteredEvent event) {
        log.info("Publishing UserRegisteredEvent for email={}", event.getEmail());
        kafkaTemplate.send("user-registered", event);
    }

    public void publishVerificationEmail(VerificationEmailEvent event) {
        log.info("Publishing VerificationEmailEvent for email={}", event.getEmail());
        kafkaTemplate.send("email-verification", event.getEmail(), event);
    }
}
