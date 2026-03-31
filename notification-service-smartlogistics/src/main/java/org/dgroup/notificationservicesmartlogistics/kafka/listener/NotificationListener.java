package org.dgroup.notificationservicesmartlogistics.kafka.listener;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dgroup.commonevents.UserRegisteredEvent;
import org.dgroup.commonevents.VerificationEmailEvent;
import org.dgroup.notificationservicesmartlogistics.service.EmailService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationListener {

    private final EmailService emailService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "user-registered", groupId = "notification-service-group")
    public void onUserRegistered(String message) {
        try {
            UserRegisteredEvent event =
                    objectMapper.readValue(message, UserRegisteredEvent.class);

            log.info("Received UserRegisteredEvent: {}", event.getEmail());

            emailService.sendEmail(
                    event.getEmail(),
                    "Welcome to Our Music Shop!",
                    "Hello " + event.getFirstName() + " " + event.getLastName() + ",\n\n" +
                            "Thank you for registering with us!\n\n" +
                            "Enjoy your journey with music 🎵"
            );

        } catch (Exception e) {
            log.error("Error processing UserRegisteredEvent", e);
        }
    }

    @KafkaListener(topics = "email-verification", groupId = "notification-service-group")
    public void onVerificationEmail(String message) {
        try {
            VerificationEmailEvent event =
                    objectMapper.readValue(message, VerificationEmailEvent.class);

            emailService.sendEmail(
                    event.getEmail(),
                    "Please verify your email",
                    "Hello " + event.getFirstName() + " " + event.getLastName() + ",\n\n" +
                            "Your verification code:\n\n" +
                            event.getToken()
            );

        } catch (Exception e) {
            log.error("Error processing VerificationEmailEvent", e);
        }
    }
}