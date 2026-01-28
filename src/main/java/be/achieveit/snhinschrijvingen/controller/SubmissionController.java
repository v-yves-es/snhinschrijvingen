package be.achieveit.snhinschrijvingen.controller;

import be.achieveit.snhinschrijvingen.dto.SubmissionForm;
import be.achieveit.snhinschrijvingen.model.Nationality;
import be.achieveit.snhinschrijvingen.model.Registration;
import be.achieveit.snhinschrijvingen.model.StudyProgram;
import be.achieveit.snhinschrijvingen.repository.NationalityRepository;
import be.achieveit.snhinschrijvingen.repository.StudyProgramRepository;
import be.achieveit.snhinschrijvingen.service.*;
import jakarta.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/inschrijving")
public class SubmissionController {
    
    private static final Logger logger = LoggerFactory.getLogger(SubmissionController.class);
    
    private final RegistrationService registrationService;
    private final WizardService wizardService;
    private final StudyProgramRepository studyProgramRepository;
    private final NationalityRepository nationalityRepository;
    private final PreviousSchoolService previousSchoolService;
    private final RelationService relationService;
    private final GenderService genderService;
    private final EmailService emailService;
    
    public SubmissionController(
            RegistrationService registrationService, 
            WizardService wizardService,
            StudyProgramRepository studyProgramRepository,
            NationalityRepository nationalityRepository,
            PreviousSchoolService previousSchoolService,
            RelationService relationService,
            GenderService genderService,
            EmailService emailService) {
        this.registrationService = registrationService;
        this.wizardService = wizardService;
        this.studyProgramRepository = studyProgramRepository;
        this.nationalityRepository = nationalityRepository;
        this.previousSchoolService = previousSchoolService;
        this.relationService = relationService;
        this.genderService = genderService;
        this.emailService = emailService;
    }
    
    @GetMapping("/verzenden/{id}")
    public String showSubmission(@PathVariable UUID id, Model model) {
        Registration registration = registrationService.findById(id)
                .orElseThrow(() -> new RuntimeException("Registration not found"));
        
        // Create form and pre-fill if data exists
        SubmissionForm form = new SubmissionForm();
        if (registration.getSignatureName() != null) {
            form.setSignatureName(registration.getSignatureName());
            form.setSchoolRegulationAgreement(registration.getSchoolRegulationAgreement());
            form.setAdditionalInfoRequest(registration.getAdditionalInfoRequest());
        }
        
        // Get full study program details if selected
        StudyProgram selectedProgram = null;
        if (registration.getSelectedStudyProgramId() != null) {
            selectedProgram = studyProgramRepository.findById(registration.getSelectedStudyProgramId()).orElse(null);
        }
        
        // Get nationality full name
        String nationalityFullName = null;
        if (registration.getStudentNationaliteit() != null) {
            Optional<Nationality> nationality = nationalityRepository.findByCode(registration.getStudentNationaliteit());
            nationalityFullName = nationality.map(Nationality::getNameNl).orElse(registration.getStudentNationaliteit());
        }
        
        // Map previous school ID to name using service
        String previousSchoolName = previousSchoolService.getSchoolName(registration.getVorigeSchool());
        
        // Map previous school year code to readable text using service
        String previousSchoolYearText = previousSchoolService.getSchoolYearText(registration.getVorigeSchoolJaar());
        
        model.addAttribute("submissionForm", form);
        model.addAttribute("registration", registration);
        model.addAttribute("registrationId", id);
        model.addAttribute("selectedProgram", selectedProgram);
        model.addAttribute("nationalityFullName", nationalityFullName);
        model.addAttribute("previousSchoolName", previousSchoolName);
        model.addAttribute("previousSchoolYearText", previousSchoolYearText);
        model.addAttribute("relationService", relationService);
        model.addAttribute("genderService", genderService);
        model.addAttribute("wizardSteps", wizardService.getWizardSteps(10, List.of(1, 2, 3, 4, 5, 6, 7, 8, 9), id.toString()));
        
        return "submission";
    }
    
    @PostMapping("/verzenden/{id}")
    public String processSubmission(
            @PathVariable UUID id,
            @ModelAttribute SubmissionForm submissionForm,
            Model model) {
        
        Registration registration = registrationService.findById(id)
                .orElseThrow(() -> new RuntimeException("Registration not found"));
        
        // Update registration with submission data
        registration.setSignatureName(submissionForm.getSignatureName());
        registration.setSchoolRegulationAgreement(submissionForm.getSchoolRegulationAgreement());
        registration.setAdditionalInfoRequest(submissionForm.getAdditionalInfoRequest());
        registration.setSubmissionDate(LocalDateTime.now());
        registration.setCurrentStep("SUBMITTED");
        registration.setStatus(be.achieveit.snhinschrijvingen.model.RegistrationStatus.COMPLETED);
        
        registrationService.updateRegistration(registration);
        
        // Send confirmation email
        String studentName = registration.getStudentFirstname() + " " + registration.getStudentLastname();
        String registrationNumber = "SNH-" + registration.getSchoolYear() + "-" + 
                                    String.format("%06d", registration.getId().toString().hashCode() & 0xFFFFFF);
        
        try {
            emailService.sendRegistrationConfirmationEmail(
                registration.getEmail(),
                studentName,
                registrationNumber
            );
            logger.info("Bevestigingsmail verstuurd naar {} voor inschrijving {}", registration.getEmail(), registrationNumber);
        } catch (MessagingException e) {
            logger.error("Kon bevestigingsmail niet versturen naar {}: {}", registration.getEmail(), e.getMessage());
            // Continue anyway - registration is saved, email is not critical at this point
        }
        
        // Redirect to confirmation page
        return "redirect:/inschrijving/bevestiging/" + id;
    }
    
    @GetMapping("/bevestiging/{id}")
    public String showConfirmation(@PathVariable UUID id, Model model) {
        Registration registration = registrationService.findById(id)
                .orElseThrow(() -> new RuntimeException("Registration not found"));
        
        model.addAttribute("registration", registration);
        
        // Add study program name
        if (registration.getSelectedStudyProgramId() != null) {
            Optional<StudyProgram> studyProgram = studyProgramRepository.findById(registration.getSelectedStudyProgramId());
            studyProgram.ifPresent(program -> model.addAttribute("studyProgramName", program.getName()));
        }
        
        return "confirmation";
    }
}
