package be.achieveit.snhinschrijvingen.controller;

import be.achieveit.snhinschrijvingen.model.Registration;
import be.achieveit.snhinschrijvingen.model.RegistrationStatus;
import be.achieveit.snhinschrijvingen.service.RegistrationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/inschrijving")
public class RegistrationController {

    private static final Logger logger = LoggerFactory.getLogger(RegistrationController.class);

    private final RegistrationService registrationService;

    public RegistrationController(final RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    /**
     * Handle email verification link
     * URL format: /inschrijving/verify/{registrationId}/{emailHash}
     */
    @GetMapping("/verify/{registrationId}/{emailHash}")
    public String verifyEmail(
            @PathVariable UUID registrationId,
            @PathVariable String emailHash,
            Model model) {

        logger.info("Email verification attempt for registration ID: {}", registrationId);

        // Verify email and get registration
        Optional<Registration> verifiedRegistration = 
                registrationService.verifyEmailAndGetRegistration(registrationId, emailHash);

        if (verifiedRegistration.isEmpty()) {
            logger.warn("Invalid verification link for registration ID: {}", registrationId);
            model.addAttribute("error", "Ongeldige verificatielink");
            return "error";
        }

        Registration registration = verifiedRegistration.get();
        String email = registration.getEmail();

        // Get all registrations for this email
        List<Registration> allRegistrations = registrationService.findRegistrationsByEmail(email);

        // Check if there are any completed registrations
        boolean hasCompletedRegistrations = allRegistrations.stream()
                .anyMatch(r -> r.getStatus() == RegistrationStatus.COMPLETED);

        // Find pending registration (could be current or another one)
        Optional<Registration> pendingRegistration = allRegistrations.stream()
                .filter(r -> r.getStatus() == RegistrationStatus.PENDING)
                .findFirst();

        // Decision logic:
        // 1. If this is the first registration ever (no completed ones) -> go to student-info
        // 2. If there are existing registrations -> show overview
        if (!hasCompletedRegistrations && allRegistrations.size() == 1) {
            // First time registration, go directly to student-info
            logger.info("First registration for email: {}, redirecting to student-info", email);
            return "redirect:/inschrijving/leerling-info/" + registrationId;
        }

        // Show overview of all registrations
        model.addAttribute("email", email);
        model.addAttribute("registrations", allRegistrations);
        model.addAttribute("pendingRegistration", pendingRegistration.orElse(null));
        model.addAttribute("currentRegistrationId", registrationId);

        return "registrations-overview";
    }

    /**
     * Continue with specific registration
     */
    @GetMapping("/continue/{registrationId}")
    public String continueRegistration(@PathVariable UUID registrationId) {
        Optional<Registration> registrationOpt = registrationService.findById(registrationId);

        if (registrationOpt.isEmpty()) {
            return "redirect:/inschrijving/start";
        }

        Registration registration = registrationOpt.get();
        String currentStep = registration.getCurrentStep();

        // Route to appropriate step based on current step
        return switch (currentStep) {
            case "EMAIL_VERIFICATION" -> "redirect:/inschrijving/start";
            case "STUDENT_INFO" -> "redirect:/inschrijving/leerling-info/" + registrationId;
            case "STUDY_PROGRAM" -> "redirect:/inschrijving/studierichting/" + registrationId;
            case "PREVIOUS_SCHOOL" -> "redirect:/inschrijving/vorige-school/" + registrationId;
            case "RELATIONS" -> "redirect:/inschrijving/relaties/" + registrationId;
            case "DOCTOR" -> "redirect:/inschrijving/huisarts/" + registrationId;
            case "CARE_NEEDS" -> "redirect:/inschrijving/zorgvraag/" + registrationId;
            case "PRIVACY" -> "redirect:/inschrijving/privacy/" + registrationId;
            case "LAPTOP" -> "redirect:/inschrijving/laptop/" + registrationId;
            case "SCHOOL_ACCOUNT" -> "redirect:/inschrijving/schoolrekening/" + registrationId;
            case "SUBMISSION" -> "redirect:/inschrijving/verzenden/" + registrationId;
            case "SUBMITTED" -> "redirect:/inschrijving/bevestiging/" + registrationId;
            default -> "redirect:/inschrijving/leerling-info/" + registrationId;
        };
    }
}
