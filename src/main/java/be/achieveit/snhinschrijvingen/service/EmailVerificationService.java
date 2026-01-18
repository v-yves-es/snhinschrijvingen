package be.achieveit.snhinschrijvingen.service;

import be.achieveit.snhinschrijvingen.model.Registration;
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

    public EmailVerificationService(final RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    /**
     * Send verification email (currently prints to console)
     * @param registration The registration to send verification email for
     */
    public void sendVerificationEmail(Registration registration) {
        String verificationLink = buildVerificationLink(registration);

        logger.info("=".repeat(80));
        logger.info("EMAIL VERIFICATION LINK FOR: {}", registration.getEmail());
        logger.info("Registration ID: {}", registration.getId());
        logger.info("Link: {}", verificationLink);
        logger.info("=".repeat(80));

        // TODO: Implement actual email sending
        // For now, we just log the link to console
        logEmailTemplate(registration.getEmail(), verificationLink);
    }

    /**
     * Build verification link with registration ID and email hash
     */
    private String buildVerificationLink(Registration registration) {
        return String.format("%s/inschrijving/verify/%s/%s",
                baseUrl,
                registration.getId(),
                registration.getEmailHash());
    }

    /**
     * Log email template to console
     */
    private void logEmailTemplate(String email, String verificationLink) {
        String emailBody = String.format("""
                
                ╔════════════════════════════════════════════════════════════════╗
                ║                    EMAIL VERIFICATION                          ║
                ╚════════════════════════════════════════════════════════════════╝
                
                To: %s
                Subject: Verifieer uw emailadres - SPES Nostra Heule
                
                Beste ouder/voogd,
                
                Bedankt voor uw interesse om uw kind in te schrijven bij 
                SPES Nostra Heule.
                
                Klik op onderstaande link om uw emailadres te verifiëren en 
                verder te gaan met de inschrijving:
                
                %s
                
                Deze link is uniek en wordt gebruikt om:
                • Uw emailadres te verifiëren
                • Verder te gaan met een bestaande inschrijving
                • Nieuwe inschrijvingen te starten
                
                Met vriendelijke groeten,
                SPES Nostra Heule
                
                ════════════════════════════════════════════════════════════════
                
                """, email, verificationLink);

        logger.info(emailBody);
    }
}
