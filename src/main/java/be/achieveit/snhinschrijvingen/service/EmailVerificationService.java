package be.achieveit.snhinschrijvingen.service;

import be.achieveit.snhinschrijvingen.model.Registration;
import jakarta.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailVerificationService {

    private static final Logger logger = LoggerFactory.getLogger(EmailVerificationService.class);

    @Value("${app.base-url:http://localhost:8080}")
    private String baseUrl;

    private final RegistrationService registrationService;
    private final EmailService emailService;

    public EmailVerificationService(final RegistrationService registrationService, final EmailService emailService) {
        this.registrationService = registrationService;
        this.emailService = emailService;
    }

    /**
     * Send verification email via EmailService
     * @param registration The registration to send verification email for
     */
    public void sendVerificationEmail(Registration registration) {
        String verificationLink = buildVerificationLink(registration);

        try {
            emailService.sendVerificationEmail(
                registration.getEmail(), 
                verificationLink
            );
            
            logger.info("Verificatie email succesvol verstuurd naar: {}", registration.getEmail());
        } catch (MessagingException e) {
            logger.error("Fout bij versturen verificatie email naar {}: {}", registration.getEmail(), e.getMessage());
            
            // Fallback: Log naar console voor development/debugging
            logger.warn("=".repeat(80));
            logger.warn("EMAIL NIET VERSTUURD - VERIFICATION LINK (Fallback):");
            logger.warn("Email: {}", registration.getEmail());
            logger.warn("Link: {}", verificationLink);
            logger.warn("=".repeat(80));
        }
    }

    /**
     * Build verification link in format: {baseUrl}/inschrijving/verify/{id}/{emailHash}
     */
    private String buildVerificationLink(Registration registration) {
        return String.format("%s/inschrijving/verify/%s/%s",
                baseUrl,
                registration.getId(),
                registration.getEmailHash());
    }
}
