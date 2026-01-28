package be.achieveit.snhinschrijvingen.controller;

import be.achieveit.snhinschrijvingen.model.Registration;
import be.achieveit.snhinschrijvingen.model.RegistrationStatus;
import be.achieveit.snhinschrijvingen.model.StudyProgram;
import be.achieveit.snhinschrijvingen.repository.StudyProgramRepository;
import be.achieveit.snhinschrijvingen.service.RegistrationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Controller
@RequestMapping("/inschrijving")
public class RegistrationController {

    private static final Logger logger = LoggerFactory.getLogger(RegistrationController.class);

    private final RegistrationService registrationService;
    private final StudyProgramRepository studyProgramRepository;

    public RegistrationController(final RegistrationService registrationService,
                                   final StudyProgramRepository studyProgramRepository) {
        this.registrationService = registrationService;
        this.studyProgramRepository = studyProgramRepository;
    }

    /**
     * Handle email verification link
     * URL format: /inschrijving/verify/{registrationId}/{emailHash}
     */
    @GetMapping("/verify/{registrationId}/{emailHash}")
    public String verifyEmail(
            @PathVariable UUID registrationId,
            @PathVariable String emailHash,
            HttpServletRequest request,
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

        // Check if there are any OTHER verified registrations (excluding current one that just got verified)
        long verifiedCount = allRegistrations.stream()
                .filter(r -> r.getEmailStatus() == be.achieveit.snhinschrijvingen.model.EmailStatus.VERIFIED)
                .count();

        // Find pending registration (could be current or another one)
        Optional<Registration> pendingRegistration = allRegistrations.stream()
                .filter(r -> r.getStatus() == RegistrationStatus.PENDING)
                .findFirst();

        // Decision logic:
        // Only go DIRECTLY to student-info if:
        // 1. This is the VERY FIRST verified registration (count == 1)
        // 2. Total registrations is 1 (no history)
        // 3. Current step is still EMAIL_VERIFICATION or STUDENT_INFO (hasn't progressed yet)
        boolean isFirstTimeVerification = verifiedCount == 1 
                && allRegistrations.size() == 1
                && ("EMAIL_VERIFICATION".equals(registration.getCurrentStep()) 
                    || "STUDENT_INFO".equals(registration.getCurrentStep()));
        
        if (isFirstTimeVerification) {
            // First time verification, user hasn't progressed yet -> go directly to student-info
            logger.info("First verification for email: {}, redirecting to student-info", email);
            
            // Store verified email in session
            HttpSession session = request.getSession();
            session.setAttribute("verifiedEmail", email);
            
            return "redirect:/inschrijving/leerling-info/" + registrationId;
        }

        // Show overview in all other cases:
        // - User has verified before (multiple registrations)
        // - User has already progressed beyond step 1
        // - User is returning to a pending registration
        logger.info("Showing overview for email: {} (verified count: {}, total: {}, current step: {})", 
                email, verifiedCount, allRegistrations.size(), registration.getCurrentStep());
        
        // Build map of study program names for each registration
        Map<UUID, String> studyProgramNames = new HashMap<>();
        for (Registration reg : allRegistrations) {
            if (reg.getSelectedStudyProgramId() != null) {
                Optional<StudyProgram> program = studyProgramRepository.findById(reg.getSelectedStudyProgramId());
                program.ifPresent(p -> studyProgramNames.put(reg.getId(), p.getName()));
            }
        }
        
        // Store verified email in session for security
        HttpSession session = request.getSession();
        session.setAttribute("verifiedEmail", email);
        
        model.addAttribute("email", email);
        model.addAttribute("registrations", allRegistrations);
        model.addAttribute("pendingRegistration", pendingRegistration.orElse(null));
        model.addAttribute("currentRegistrationId", registrationId);
        model.addAttribute("studyProgramNames", studyProgramNames);

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
    
    /**
     * Show overview for a specific registration's email
     */
    @GetMapping("/overzicht/{registrationId}")
    public String showOverviewForRegistration(@PathVariable UUID registrationId, 
                                               HttpServletRequest request,
                                               Model model) {
        Optional<Registration> registrationOpt = registrationService.findById(registrationId);

        if (registrationOpt.isEmpty()) {
            return "redirect:/inschrijving/start";
        }

        Registration registration = registrationOpt.get();
        String email = registration.getEmail();

        // Get all registrations for this email
        List<Registration> allRegistrations = registrationService.findRegistrationsByEmail(email);

        // Find pending registration
        Optional<Registration> pendingRegistration = allRegistrations.stream()
                .filter(r -> r.getStatus() == RegistrationStatus.PENDING)
                .findFirst();

        // Build map of study program names for each registration
        Map<UUID, String> studyProgramNames = new HashMap<>();
        for (Registration reg : allRegistrations) {
            if (reg.getSelectedStudyProgramId() != null) {
                Optional<StudyProgram> program = studyProgramRepository.findById(reg.getSelectedStudyProgramId());
                program.ifPresent(p -> studyProgramNames.put(reg.getId(), p.getName()));
            }
        }

        // Store verified email in session for security
        HttpSession session = request.getSession();
        session.setAttribute("verifiedEmail", email);

        // Add attributes for template
        model.addAttribute("email", email);
        model.addAttribute("registrations", allRegistrations);
        model.addAttribute("pendingRegistration", pendingRegistration.orElse(null));
        model.addAttribute("currentRegistrationId", registrationId);
        model.addAttribute("studyProgramNames", studyProgramNames);

        return "registrations-overview";
    }
    
    /**
     * Start new registration (email from session - secure)
     */
    @GetMapping("/nieuw")
    public String startNewRegistration(HttpServletRequest request) {
        // Get verified email from session
        HttpSession session = request.getSession(false);
        if (session == null) {
            logger.warn("No session found when trying to start new registration");
            return "redirect:/inschrijving/start";
        }
        
        String email = (String) session.getAttribute("verifiedEmail");
        if (email == null || email.isEmpty()) {
            logger.warn("No verified email in session when trying to start new registration");
            return "redirect:/inschrijving/start";
        }
        
        // Create new registration with already verified email
        Registration registration = registrationService.createRegistration(email);
        
        // Mark email as verified since we're coming from overview
        registration.setEmailStatus(be.achieveit.snhinschrijvingen.model.EmailStatus.VERIFIED);
        registrationService.updateRegistration(registration);
        
        logger.info("Starting new registration for verified email from session, registration ID: {}", registration.getId());
        
        // Redirect directly to student-info
        return "redirect:/inschrijving/leerling-info/" + registration.getId();
    }
    
    /**
     * Delete a pending registration
     */
    @GetMapping("/verwijderen/{registrationId}")
    public String deleteRegistration(@PathVariable UUID registrationId, 
                                      HttpServletRequest request) {
        // Get verified email from session for security
        HttpSession session = request.getSession(false);
        if (session == null) {
            logger.warn("No session found when trying to delete registration");
            return "redirect:/inschrijving/start";
        }
        
        String sessionEmail = (String) session.getAttribute("verifiedEmail");
        if (sessionEmail == null || sessionEmail.isEmpty()) {
            logger.warn("No verified email in session when trying to delete registration");
            return "redirect:/inschrijving/start";
        }
        
        // Get registration
        Optional<Registration> registrationOpt = registrationService.findById(registrationId);
        if (registrationOpt.isEmpty()) {
            logger.warn("Registration not found for deletion: {}", registrationId);
            return "redirect:/inschrijving/start";
        }
        
        Registration registration = registrationOpt.get();
        
        // Security check: verify email matches
        if (!sessionEmail.equals(registration.getEmail())) {
            logger.warn("Email mismatch when trying to delete registration. Session: {}, Registration: {}", 
                    sessionEmail, registration.getEmail());
            return "redirect:/inschrijving/start";
        }
        
        // Only allow deletion of PENDING registrations
        if (registration.getStatus() != RegistrationStatus.PENDING) {
            logger.warn("Attempt to delete non-pending registration: {}, status: {}", 
                    registrationId, registration.getStatus());
            return "redirect:/inschrijving/overzicht/" + registrationId;
        }
        
        // Delete the registration
        registrationService.deleteRegistration(registrationId);
        logger.info("Deleted pending registration: {}", registrationId);
        
        // Check if there are other registrations for this email
        List<Registration> remainingRegistrations = registrationService.findRegistrationsByEmail(sessionEmail);
        if (remainingRegistrations.isEmpty()) {
            // No more registrations, go back to start
            return "redirect:/inschrijving/start";
        }
        
        // Go back to overview (use first remaining registration ID)
        return "redirect:/inschrijving/overzicht/" + remainingRegistrations.get(0).getId();
    }
}
