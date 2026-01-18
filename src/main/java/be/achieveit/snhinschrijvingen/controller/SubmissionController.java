package be.achieveit.snhinschrijvingen.controller;

import be.achieveit.snhinschrijvingen.dto.SubmissionForm;
import be.achieveit.snhinschrijvingen.model.Registration;
import be.achieveit.snhinschrijvingen.service.RegistrationService;
import be.achieveit.snhinschrijvingen.service.WizardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Controller
@RequestMapping("/inschrijving")
public class SubmissionController {
    
    private final RegistrationService registrationService;
    private final WizardService wizardService;
    
    public SubmissionController(RegistrationService registrationService, WizardService wizardService) {
        this.registrationService = registrationService;
        this.wizardService = wizardService;
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
        
        model.addAttribute("submissionForm", form);
        model.addAttribute("registration", registration);
        model.addAttribute("registrationId", id);
        model.addAttribute("wizardSteps", wizardService.getWizardSteps(10));
        
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
        
        registrationService.updateRegistration(registration);
        
        // Redirect to confirmation page
        return "redirect:/inschrijving/bevestiging/" + id;
    }
    
    @GetMapping("/bevestiging/{id}")
    public String showConfirmation(@PathVariable UUID id, Model model) {
        Registration registration = registrationService.findById(id)
                .orElseThrow(() -> new RuntimeException("Registration not found"));
        
        model.addAttribute("registration", registration);
        
        return "confirmation";
    }
}
