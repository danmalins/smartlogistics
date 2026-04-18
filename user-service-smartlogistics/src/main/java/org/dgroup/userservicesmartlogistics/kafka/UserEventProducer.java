package org.dgroup.userservicesmartlogistics.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dgroup.userservicesmartlogistics.event.UserRegisteredEvent;
import org.dgroup.userservicesmartlogistics.event.VerificationEmailEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void publishUserRegistered(UserRegisteredEvent event) throws JsonProcessingException {
        log.info("Publishing UserRegisteredEvent for email={}", event.getEmail());
        kafkaTemplate.send("user-registered", objectMapper.writeValueAsString(event));
    }

    public void publishVerificationEmail(VerificationEmailEvent event) {
        log.info("Publishing VerificationEmailEvent for email={}", event.getEmail());
        kafkaTemplate.send("email-verification", event.getEmail(), event);
    }
}
