package be.achieveit.snhinschrijvingen.controller;

import be.achieveit.snhinschrijvingen.dto.CareNeedsForm;
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
public class CareNeedsController {
    
    private final RegistrationService registrationService;
    private final WizardService wizardService;
    
    public CareNeedsController(RegistrationService registrationService, WizardService wizardService) {
        this.registrationService = registrationService;
        this.wizardService = wizardService;
    }
    
    @GetMapping("/zorgvraag/{id}")
    public String showCareNeeds(@PathVariable UUID id, Model model) {
        Registration registration = registrationService.findById(id)
                .orElseThrow(() -> new RuntimeException("Registration not found"));
        
        // Create form and pre-fill if data exists
        CareNeedsForm form = new CareNeedsForm();
        if (registration.getClassCompositionWishes() != null) {
            form.setClassCompositionWishes(registration.getClassCompositionWishes());
            form.setHasCareNeeds(registration.getHasCareNeeds());
            form.setMedicalCareDetails(registration.getMedicalCareDetails());
            form.setReceivingTreatment(registration.getReceivingTreatment());
            form.setDoctorContactInfo(registration.getDoctorContactInfo());
            form.setTakesMedication(registration.getTakesMedication());
            form.setSchoolExpectations(registration.getSchoolExpectations());
            form.setLessonInfluence(registration.getLessonInfluence());
            form.setClbConsultPermission(registration.getClbConsultPermission());
            form.setSocialEmotionalInfo(registration.getSocialEmotionalInfo());
            form.setLearningDifficulties(registration.getLearningDifficulties());
            form.setExternalSupport(registration.getExternalSupport());
        }
        
        model.addAttribute("careNeedsForm", form);
        model.addAttribute("registrationId", id);
        model.addAttribute("wizardSteps", wizardService.getWizardSteps(6, List.of(1, 2, 3, 4, 5), id.toString()));
        
        return "care-needs";
    }
    
    @PostMapping("/zorgvraag/{id}")
    public String processCareNeeds(
            @PathVariable UUID id,
            @ModelAttribute CareNeedsForm careNeedsForm,
            Model model) {
        
        Registration registration = registrationService.findById(id)
                .orElseThrow(() -> new RuntimeException("Registration not found"));
        
        // Update registration with care needs information
        registration.setClassCompositionWishes(careNeedsForm.getClassCompositionWishes());
        registration.setHasCareNeeds(careNeedsForm.getHasCareNeeds());
        registration.setMedicalCareDetails(careNeedsForm.getMedicalCareDetails());
        registration.setReceivingTreatment(careNeedsForm.getReceivingTreatment());
        registration.setDoctorContactInfo(careNeedsForm.getDoctorContactInfo());
        registration.setTakesMedication(careNeedsForm.getTakesMedication());
        registration.setSchoolExpectations(careNeedsForm.getSchoolExpectations());
        registration.setLessonInfluence(careNeedsForm.getLessonInfluence());
        registration.setClbConsultPermission(careNeedsForm.getClbConsultPermission());
        registration.setSocialEmotionalInfo(careNeedsForm.getSocialEmotionalInfo());
        registration.setLearningDifficulties(careNeedsForm.getLearningDifficulties());
        registration.setExternalSupport(careNeedsForm.getExternalSupport());
        
        // Update current step to next step
        registration.setCurrentStep("PRIVACY");
        
        registrationService.updateRegistration(registration);
        
        // Redirect to next step (Privacy)
        return "redirect:/inschrijving/privacy/" + id;
    }
}
