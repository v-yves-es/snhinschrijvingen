package be.achieveit.snhinschrijvingen.service;

import be.achieveit.snhinschrijvingen.model.EmailStatus;
import be.achieveit.snhinschrijvingen.model.Registration;
import be.achieveit.snhinschrijvingen.model.RegistrationStatus;
import be.achieveit.snhinschrijvingen.model.SchoolYear;
import be.achieveit.snhinschrijvingen.repository.RegistrationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RegistrationService {

    private static final Logger logger = LoggerFactory.getLogger(RegistrationService.class);

    private final RegistrationRepository registrationRepository;
    private final SchoolYearService schoolYearService;

    public RegistrationService(
            final RegistrationRepository registrationRepository,
            final SchoolYearService schoolYearService) {
        this.registrationRepository = registrationRepository;
        this.schoolYearService = schoolYearService;
    }

    @Transactional
    public Registration createRegistration(String email) {
        String normalizedEmail = email.toLowerCase().trim();
        String emailHash = hashEmail(normalizedEmail);
        
        // Check if there's already a registration (any status) with UNVERIFIED email for this email hash
        Optional<Registration> existingUnverified = registrationRepository
                .findFirstByEmailHashAndEmailStatusOrderByCreatedAtDesc(emailHash, EmailStatus.UNVERIFIED);
        
        if (existingUnverified.isPresent()) {
            Registration registration = existingUnverified.get();
            logger.info("Found existing unverified registration with ID: {} for email: {}", 
                    registration.getId(), normalizedEmail);
            return registration;
        }
        
        // Check if there are ANY registrations (verified or completed) for this email hash
        // If yes, we should NOT create a new one, but find a pending one OR create without unique email_hash
        List<Registration> existingRegistrations = registrationRepository.findByEmail(normalizedEmail);
        
        if (!existingRegistrations.isEmpty()) {
            // Email was used before, find if there's a pending registration
            Optional<Registration> pendingReg = existingRegistrations.stream()
                    .filter(r -> r.getStatus() == RegistrationStatus.PENDING)
                    .findFirst();
            
            if (pendingReg.isPresent()) {
                logger.info("Found existing pending registration with ID: {} for email: {}", 
                        pendingReg.get().getId(), normalizedEmail);
                return pendingReg.get();
            }
            
            // All registrations are completed, create new one
            // But we need to handle the unique email_hash constraint
            // Solution: Delete the email_hash from old COMPLETED registrations first
            // OR: Change database schema to allow multiple registrations per email
            // For now: we'll create a new registration (email_hash will need to be nullable or removed from unique constraint)
        }
        
        // Create new registration
        Registration registration = new Registration();
        registration.setEmail(normalizedEmail);
        registration.setEmailHash(emailHash);
        
        // Get registration school year (next year after current)
        SchoolYear registrationSchoolYear = schoolYearService.getRegistrationSchoolYear();
        registration.setSchoolYear(registrationSchoolYear.getId());
        
        registration.setStatus(RegistrationStatus.PENDING);
        registration.setEmailStatus(EmailStatus.UNVERIFIED);
        registration.setCurrentStep("EMAIL_VERIFICATION");

        Registration saved = registrationRepository.save(registration);
        logger.info("Created new registration with ID: {} for email: {} for school year: {}", 
                saved.getId(), normalizedEmail, registrationSchoolYear.getId());

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
    public void updateCurrentStep(UUID registrationId, String stepName) {
        Optional<Registration> registrationOpt = registrationRepository.findById(registrationId);
        if (registrationOpt.isPresent()) {
            Registration registration = registrationOpt.get();
            registration.setCurrentStep(stepName);
            registrationRepository.save(registration);
            logger.info("Updated current step for registration ID: {} to {}", registrationId, stepName);
        }
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
     * Delete a registration (only PENDING registrations can be deleted)
     */
    @Transactional
    public void deleteRegistration(UUID registrationId) {
        Optional<Registration> registrationOpt = registrationRepository.findById(registrationId);
        if (registrationOpt.isPresent()) {
            Registration registration = registrationOpt.get();
            if (registration.getStatus() == RegistrationStatus.PENDING) {
                registrationRepository.delete(registration);
                logger.info("Deleted pending registration ID: {}", registrationId);
            } else {
                logger.warn("Attempted to delete non-pending registration ID: {}, status: {}", 
                        registrationId, registration.getStatus());
                throw new IllegalStateException("Only pending registrations can be deleted");
            }
        } else {
            logger.warn("Attempted to delete non-existent registration ID: {}", registrationId);
        }
    }
}
