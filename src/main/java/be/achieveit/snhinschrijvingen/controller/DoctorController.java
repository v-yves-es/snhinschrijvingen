package be.achieveit.snhinschrijvingen.controller;

import be.achieveit.snhinschrijvingen.dto.DoctorForm;
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
public class DoctorController {
    
    private final RegistrationService registrationService;
    private final WizardService wizardService;
    
    public DoctorController(RegistrationService registrationService, WizardService wizardService) {
        this.registrationService = registrationService;
        this.wizardService = wizardService;
    }
    
    @GetMapping("/huisarts/{id}")
    public String showDoctor(@PathVariable("id") UUID registrationId, Model model) {
        Registration registration = registrationService.findById(registrationId)
                .orElseThrow(() -> new RuntimeException("Registration not found"));
        
        // Create form and pre-fill if data exists
        DoctorForm form = new DoctorForm();
        if (registration.getDoctorName() != null) {
            form.setDoctorName(registration.getDoctorName());
            form.setDoctorPhone(registration.getDoctorPhone());
        }
        
        model.addAttribute("doctorForm", form);
        model.addAttribute("registrationId", registrationId);
        model.addAttribute("wizardSteps", wizardService.getWizardSteps(5, List.of(1, 2, 3, 4), registrationId.toString()));
        
        return "doctor";
    }
    
    @PostMapping("/huisarts/{id}")
    public String processDoctor(
            @PathVariable("id") UUID registrationId,
            @ModelAttribute DoctorForm doctorForm,
            Model model) {
        
        Registration registration = registrationService.findById(registrationId)
                .orElseThrow(() -> new RuntimeException("Registration not found"));
        
        // Update registration with doctor information
        registration.setDoctorName(doctorForm.getDoctorName());
        registration.setDoctorPhone(doctorForm.getDoctorPhone());
        
        // Update current step to next step
        registration.setCurrentStep("CARE_NEEDS");
        
        registrationService.updateRegistration(registration);
        
        // Redirect to next step (Care Needs)
        return "redirect:/inschrijving/zorgvraag/" + registrationId;
    }
}
