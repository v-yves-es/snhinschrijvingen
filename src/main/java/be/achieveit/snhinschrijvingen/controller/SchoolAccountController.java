package be.achieveit.snhinschrijvingen.controller;

import be.achieveit.snhinschrijvingen.dto.SchoolAccountForm;
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
public class SchoolAccountController {
    
    private final RegistrationService registrationService;
    private final WizardService wizardService;
    
    public SchoolAccountController(RegistrationService registrationService, WizardService wizardService) {
        this.registrationService = registrationService;
        this.wizardService = wizardService;
    }
    
    @GetMapping("/schoolrekening/{id}")
    public String showSchoolAccount(@PathVariable UUID id, Model model) {
        Registration registration = registrationService.findById(id)
                .orElseThrow(() -> new RuntimeException("Registration not found"));
        
        // Create form and pre-fill if data exists
        SchoolAccountForm form = new SchoolAccountForm();
        if (registration.getBankAccountIban() != null) {
            form.setBankAccountIban(registration.getBankAccountIban());
        }
        if (registration.getBankAccountHolder() != null) {
            form.setBankAccountHolder(registration.getBankAccountHolder());
        }
        if (registration.getFinancialSupportRequest() != null) {
            form.setFinancialSupportRequest(registration.getFinancialSupportRequest());
        }
        
        model.addAttribute("schoolAccountForm", form);
        model.addAttribute("registrationId", id);
        model.addAttribute("wizardSteps", wizardService.getWizardSteps(9, List.of(1, 2, 3, 4, 5, 6, 7, 8), id.toString()));
        
        return "school-account";
    }
    
    @PostMapping("/schoolrekening/{id}")
    public String processSchoolAccount(
            @PathVariable UUID id,
            @ModelAttribute SchoolAccountForm schoolAccountForm,
            Model model) {
        
        Registration registration = registrationService.findById(id)
                .orElseThrow(() -> new RuntimeException("Registration not found"));
        
        // Update registration with bank account and financial support data
        registration.setBankAccountIban(schoolAccountForm.getBankAccountIban());
        registration.setBankAccountHolder(schoolAccountForm.getBankAccountHolder());
        registration.setFinancialSupportRequest(schoolAccountForm.getFinancialSupportRequest());
        
        // Update current step to next step
        registration.setCurrentStep("SUBMISSION");
        
        registrationService.updateRegistration(registration);
        
        // Redirect to next step (Submission/Overview)
        return "redirect:/inschrijving/verzenden/" + id;
    }
}
