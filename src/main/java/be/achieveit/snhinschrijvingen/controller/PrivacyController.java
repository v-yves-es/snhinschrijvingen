package be.achieveit.snhinschrijvingen.controller;

import be.achieveit.snhinschrijvingen.dto.PrivacyForm;
import be.achieveit.snhinschrijvingen.model.Registration;
import be.achieveit.snhinschrijvingen.service.RegistrationService;
import be.achieveit.snhinschrijvingen.service.WizardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/inschrijving")
public class PrivacyController {
    
    private final RegistrationService registrationService;
    private final WizardService wizardService;
    
    public PrivacyController(RegistrationService registrationService, WizardService wizardService) {
        this.registrationService = registrationService;
        this.wizardService = wizardService;
    }
    
    @GetMapping("/privacy/{id}")
    public String showPrivacy(@PathVariable UUID id, Model model) {
        Registration registration = registrationService.findById(id)
                .orElseThrow(() -> new RuntimeException("Registration not found"));
        
        // Create form and pre-fill if data exists
        PrivacyForm form = new PrivacyForm();
        if (registration.getPhotoVideoConsent() != null) {
            form.setPhotoVideoConsent(registration.getPhotoVideoConsent());
            form.setStudyResultsConsent(registration.getStudyResultsConsent());
            form.setAlumniDataConsent(registration.getAlumniDataConsent());
            form.setHigherEducationConsent(registration.getHigherEducationConsent());
        }
        
        model.addAttribute("privacyForm", form);
        model.addAttribute("registrationId", id);
        model.addAttribute("wizardSteps", wizardService.getWizardSteps(7, List.of(1, 2, 3, 4, 5, 6), id.toString()));
        
        return "privacy";
    }
    
    @PostMapping("/privacy/{id}")
    public String processPrivacy(
            @PathVariable UUID id,
            @ModelAttribute PrivacyForm privacyForm,
            Model model) {
        
        Registration registration = registrationService.findById(id)
                .orElseThrow(() -> new RuntimeException("Registration not found"));
        
        // Update registration with privacy consents
        registration.setPhotoVideoConsent(privacyForm.getPhotoVideoConsent());
        registration.setStudyResultsConsent(privacyForm.getStudyResultsConsent());
        registration.setAlumniDataConsent(privacyForm.getAlumniDataConsent());
        registration.setHigherEducationConsent(privacyForm.getHigherEducationConsent());
        
        // Update current step to next step
        registration.setCurrentStep("LAPTOP");
        
        registrationService.updateRegistration(registration);
        
        // Redirect to next step (Laptop)
        return "redirect:/inschrijving/laptop/" + id;
    }
}
