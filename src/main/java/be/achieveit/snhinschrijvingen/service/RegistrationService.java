package be.achieveit.snhinschrijvingen.service;

import be.achieveit.snhinschrijvingen.model.EmailStatus;
import be.achieveit.snhinschrijvingen.model.Registration;
import be.achieveit.snhinschrijvingen.model.RegistrationStatus;
import be.achieveit.snhinschrijvingen.repository.RegistrationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Year;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RegistrationService {

    private static final Logger logger = LoggerFactory.getLogger(RegistrationService.class);

    private final RegistrationRepository registrationRepository;

    public RegistrationService(final RegistrationRepository registrationRepository) {
        this.registrationRepository = registrationRepository;
    }

    @Transactional
    public Registration createRegistration(String email) {
        Registration registration = new Registration();
        registration.setEmail(email.toLowerCase().trim());
        registration.setEmailHash(hashEmail(email));
        registration.setSchoolYear(getCurrentSchoolYear());
        registration.setStatus(RegistrationStatus.PENDING);
        registration.setEmailStatus(EmailStatus.UNVERIFIED);
        registration.setCurrentStep("EMAIL_VERIFICATION");

        Registration saved = registrationRepository.save(registration);
        logger.info("Created new registration with ID: {} for email: {}", saved.getId(), email);

        return saved;
    }

    @Transactional
    public Optional<Registration> verifyEmailAndGetRegistration(UUID registrationId, String emailHash) {
        Optional<Registration> registrationOpt = registrationRepository.findByIdAndEmailHash(registrationId, emailHash);

        if (registrationOpt.isPresent()) {
            Registration registration = registrationOpt.get();
            if (registration.getEmailStatus() == EmailStatus.UNVERIFIED) {
                registration.setEmailStatus(EmailStatus.VERIFIED);
                registration.setCurrentStep("STUDENT_INFO");
                registrationRepository.save(registration);
                logger.info("Email verified for registration ID: {}", registrationId);
            }
        }

        return registrationOpt;
    }

    public List<Registration> findRegistrationsByEmail(String email) {
        return registrationRepository.findByEmailOrderByCreatedAtDesc(email.toLowerCase().trim());
    }

    public Optional<Registration> findPendingRegistrationByEmail(String email) {
        return registrationRepository.findFirstByEmailAndStatusOrderByCreatedAtDesc(
                email.toLowerCase().trim(),
                RegistrationStatus.PENDING
        );
    }

    public Optional<Registration> findById(UUID id) {
        return registrationRepository.findById(id);
    }

    @Transactional
    public Registration updateRegistration(Registration registration) {
        return registrationRepository.save(registration);
    }

    @Transactional
    public void updateStudentInfo(UUID registrationId, String firstname, String lastname) {
        Optional<Registration> regOpt = registrationRepository.findById(registrationId);
        if (regOpt.isPresent()) {
            Registration registration = regOpt.get();
            registration.setStudentFirstname(firstname);
            registration.setStudentLastname(lastname);
            registration.setCurrentStep("COMPLETED"); // Or next step
            registrationRepository.save(registration);
            logger.info("Updated student info for registration ID: {}", registrationId);
        }
    }

    /**
     * Hash email address using SHA-256
     */
    public String hashEmail(String email) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(email.toLowerCase().trim().getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            logger.error("Error hashing email", e);
            throw new RuntimeException("Error hashing email", e);
        }
    }

    /**
     * Get current school year in format "2025-2026"
     */
    private String getCurrentSchoolYear() {
        int currentYear = Year.now().getValue();
        int currentMonth = java.time.LocalDate.now().getMonthValue();

        // School year typically starts in September
        if (currentMonth >= 9) {
            return currentYear + "-" + (currentYear + 1);
        } else {
            return (currentYear - 1) + "-" + currentYear;
        }
    }
}
