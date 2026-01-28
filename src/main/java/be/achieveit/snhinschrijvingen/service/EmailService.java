package be.achieveit.snhinschrijvingen.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 * Email service voor het versturen van emails via Mailtrap (development/demo) of SMTP (productie).
 * 
 * Mailtrap Setup:
 * 1. Ga naar https://mailtrap.io en registreer (gratis account)
 * 2. Maak een inbox aan
 * 3. Kopieer SMTP credentials naar application.properties
 * 4. Alle emails worden "gevangen" in Mailtrap inbox (niet echt verstuurd)
 */
@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender mailSender;

    @Value("${app.email.from}")
    private String fromEmail;

    @Value("${app.email.from-name}")
    private String fromName;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * Verstuur email verificatie link naar nieuwe inschrijving.
     * 
     * @param toEmail Ontvanger email adres
     * @param verificationLink Volledige verificatie link (bijv. http://localhost:8080/inschrijving/verify/id/hash)
     * @throws MessagingException Als email niet verstuurd kon worden
     */
    public void sendVerificationEmail(String toEmail, String verificationLink) throws MessagingException {
        String subject = "Bevestig uw email adres - SPES Nostra Heule";
        
        String htmlContent = buildVerificationEmailHtml(verificationLink);
        
        sendHtmlEmail(toEmail, subject, htmlContent);
        
        logger.info("Verificatie email verstuurd naar: {}", toEmail);
    }

    /**
     * Verstuur bevestiging email na voltooide inschrijving.
     * 
     * @param toEmail Ontvanger email adres
     * @param studentName Naam van de ingeschreven leerling
     * @param registrationNumber Inschrijvingsnummer
     * @throws MessagingException Als email niet verstuurd kon worden
     */
    public void sendRegistrationConfirmationEmail(String toEmail, String studentName, String registrationNumber) throws MessagingException {
        String subject = "Inschrijving ontvangen - SPES Nostra Heule";
        
        String htmlContent = buildConfirmationEmailHtml(studentName, registrationNumber);
        
        sendHtmlEmail(toEmail, subject, htmlContent);
        
        logger.info("Bevestiging email verstuurd naar: {} voor inschrijving {}", toEmail, registrationNumber);
    }

    /**
     * Algemene methode om HTML email te versturen.
     */
    private void sendHtmlEmail(String to, String subject, String htmlContent) throws MessagingException {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            
            helper.setFrom(fromEmail, fromName);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true); // true = HTML
            
            mailSender.send(message);
        } catch (Exception e) {
            logger.error("Fout bij versturen email naar {}: {}", to, e.getMessage());
            throw new MessagingException("Kon email niet versturen", e);
        }
    }

    /**
     * Bouw HTML content voor verificatie email.
     */
    private String buildVerificationEmailHtml(String verificationLink) {
        return """
            <!DOCTYPE html>
            <html lang="nl">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <style>
                    body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }
                    .container { max-width: 600px; margin: 0 auto; padding: 20px; }
                    .header { background-color: #c92617; color: white; padding: 20px; text-align: center; }
                    .content { background-color: #f9f9f9; padding: 30px; border: 1px solid #ddd; }
                    .button { 
                        display: inline-block; 
                        padding: 12px 30px; 
                        background-color: #c92617; 
                        color: white; 
                        text-decoration: none; 
                        border-radius: 5px; 
                        margin: 20px 0;
                    }
                    .button:hover { background-color: #a51d13; }
                    .link { color: #c92617; word-break: break-all; text-decoration: none; }
                    .footer { text-align: center; padding: 20px; font-size: 12px; color: #777; }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>SPES Nostra Heule</h1>
                        <p>Email Verificatie</p>
                    </div>
                    <div class="content">
                        <h2>Verifieer uw emailadres</h2>
                        <p>Beste ouder/voogd,</p>
                        <p>Bedankt voor uw interesse om uw kind in te schrijven bij SPES Nostra Heule.</p>
                        <p>Klik op onderstaande link om uw emailadres te verifiëren en verder te gaan met de inschrijving:</p>
                        <div style="text-align: center;">
                            <a href="%s" class="button">Email Verifiëren</a>
                        </div>
                        <p style="margin-top: 20px; font-size: 14px; color: #666;">
                            Of kopieer deze link naar uw browser:<br>
                            <a href="%s" class="link">%s</a>
                        </p>
                        <p style="margin-top: 30px; font-size: 14px;">
                            Deze link is uniek en wordt gebruikt om:
                        </p>
                        <ul style="font-size: 14px;">
                            <li>Uw emailadres te verifiëren</li>
                            <li>Verder te gaan met een bestaande inschrijving</li>
                            <li>Nieuwe inschrijvingen te starten</li>
                        </ul>
                        <p style="margin-top: 30px;">
                            Met vriendelijke groeten,<br>
                            <strong>SPES Nostra Heule</strong>
                        </p>
                    </div>
                    <div class="footer">
                        <p>SPES Nostra Heule - Kortrijk</p>
                        <p>Dit is een automatisch gegenereerde email. Gelieve niet te antwoorden.</p>
                    </div>
                </div>
            </body>
            </html>
            """.formatted(verificationLink, verificationLink, verificationLink);
    }

    /**
     * Bouw HTML content voor bevestiging email.
     */
    private String buildConfirmationEmailHtml(String studentName, String registrationNumber) {
        return """
            <!DOCTYPE html>
            <html lang="nl">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <style>
                    body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }
                    .container { max-width: 600px; margin: 0 auto; padding: 20px; }
                    .header { background-color: #c92617; color: white; padding: 20px; text-align: center; }
                    .content { background-color: #f9f9f9; padding: 30px; border: 1px solid #ddd; }
                    .info-box { background-color: white; padding: 15px; border-left: 4px solid #c92617; margin: 20px 0; }
                    .footer { text-align: center; padding: 20px; font-size: 12px; color: #777; }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>✓ Inschrijving Ontvangen</h1>
                        <p>SPES Nostra Heule</p>
                    </div>
                    <div class="content">
                        <h2>Beste ouder(s)/voogd,</h2>
                        <p>Uw inschrijving voor <strong>%s</strong> is succesvol ontvangen.</p>
                        
                        <h3>Volgende stappen:</h3>
                        <ul>
                            <li>Onze administratie zal uw aanvraag verwerken</li>
                            <li>U ontvangt binnenkort verdere info per e-mail</li>
                            <li>Bij vragen kunt u contact opnemen met ons secretariaat</li>
                        </ul>
                        
                        <p style="margin-top: 30px;">Met vriendelijke groet,<br>
                        <strong>SPES Nostra Heule</strong></p>
                    </div>
                    <div class="footer">
                        <p><strong>Contact:</strong></p>
                        <p>SPES Nostra Heule - Kortrijk</p>
                        <p>Tel: 056 35 39 53 | Email: info@snh.be</p>
                        <p style="margin-top: 15px;">Dit is een automatisch gegenereerde email.</p>
                    </div>
                </div>
            </body>
            </html>
            """.formatted(studentName, registrationNumber);
    }
}
