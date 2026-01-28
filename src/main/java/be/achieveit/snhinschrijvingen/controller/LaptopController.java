package be.achieveit.snhinschrijvingen.controller;

import be.achieveit.snhinschrijvingen.dto.LaptopForm;
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
public class LaptopController {
    
    private final RegistrationService registrationService;
    private final WizardService wizardService;
    
    public LaptopController(RegistrationService registrationService, WizardService wizardService) {
        this.registrationService = registrationService;
        this.wizardService = wizardService;
    }
    
    @GetMapping("/laptop/{id}")
    public String showLaptop(@PathVariable UUID id, Model model) {
        Registration registration = registrationService.findById(id)
                .orElseThrow(() -> new RuntimeException("Registration not found"));
        
        // Create form and pre-fill if data exists
        LaptopForm form = new LaptopForm();
        if (registration.getLaptopBrand() != null) {
            form.setLaptopBrand(registration.getLaptopBrand());
        }
        
        model.addAttribute("laptopForm", form);
        model.addAttribute("registrationId", id);
        model.addAttribute("wizardSteps", wizardService.getWizardSteps(8, List.of(1, 2, 3, 4, 5, 6, 7), id.toString()));
        
        return "laptop";
    }
    
    @PostMapping("/laptop/{id}")
    public String processLaptop(
            @PathVariable UUID id,
            @ModelAttribute LaptopForm laptopForm,
            Model model) {
        
        Registration registration = registrationService.findById(id)
                .orElseThrow(() -> new RuntimeException("Registration not found"));
        
        // Update registration with laptop information
        registration.setLaptopBrand(laptopForm.getLaptopBrand());
        
        // Update current step to next step
        registration.setCurrentStep("SCHOOL_ACCOUNT");
        
        registrationService.updateRegistration(registration);
        
        // Redirect to next step (School Account)
        return "redirect:/inschrijving/schoolrekening/" + id;
    }
}
