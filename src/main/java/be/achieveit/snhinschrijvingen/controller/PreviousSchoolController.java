package be.achieveit.snhinschrijvingen.controller;

import be.achieveit.snhinschrijvingen.dto.PreviousSchoolForm;
import be.achieveit.snhinschrijvingen.model.Registration;
import be.achieveit.snhinschrijvingen.service.RegistrationService;
import be.achieveit.snhinschrijvingen.service.WizardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/inschrijving")
public class PreviousSchoolController {
    
    private static final Logger logger = LoggerFactory.getLogger(PreviousSchoolController.class);
    
    private final RegistrationService registrationService;
    private final WizardService wizardService;
    
    public PreviousSchoolController(RegistrationService registrationService, WizardService wizardService) {
        this.registrationService = registrationService;
        this.wizardService = wizardService;
    }
    
    @GetMapping("/previous-school")
    public String showPreviousSchoolForm(@RequestParam("id") UUID registrationId, Model model) {
        Optional<Registration> registrationOpt = registrationService.findById(registrationId);
        
        if (registrationOpt.isEmpty()) {
            logger.warn("Registration not found for ID: {}", registrationId);
            return "redirect:/inschrijving/start";
        }
        
        Registration registration = registrationOpt.get();
        
        // Create form and populate with existing data if available
        PreviousSchoolForm form = new PreviousSchoolForm();
        form.setVorigeSchool(registration.getVorigeSchool());
        form.setVorigeSchoolAnders(registration.getVorigeSchoolAnders());
        form.setVorigeSchoolJaar(registration.getVorigeSchoolJaar());
        form.setRichtingVorigeSchool(registration.getRichtingVorigeSchool());
        form.setToestemmingVorigeSchool(registration.getToestemmingVorigeSchool());
        
        model.addAttribute("previousSchoolForm", form);
        model.addAttribute("registrationId", registrationId);
        model.addAttribute("wizardSteps", wizardService.getWizardSteps(3, List.of(1, 2)));
        
        return "previous-school";
    }
    
    @PostMapping("/previous-school")
    public String submitPreviousSchoolForm(
            @RequestParam("id") UUID registrationId,
            @ModelAttribute PreviousSchoolForm form) {
        
        Optional<Registration> registrationOpt = registrationService.findById(registrationId);
        
        if (registrationOpt.isEmpty()) {
            logger.error("Registration not found for ID: {}", registrationId);
            return "redirect:/inschrijving/start";
        }
        
        Registration registration = registrationOpt.get();
        
        // Update registration with previous school data
        registration.setVorigeSchool(form.getVorigeSchool());
        registration.setVorigeSchoolAnders(form.getVorigeSchoolAnders());
        registration.setVorigeSchoolJaar(form.getVorigeSchoolJaar());
        registration.setRichtingVorigeSchool(form.getRichtingVorigeSchool());
        registration.setToestemmingVorigeSchool(form.getToestemmingVorigeSchool());
        
        registrationService.updateRegistration(registration);
        
        logger.info("Previous school info saved for registration: {}", registrationId);
        
        // TODO: Redirect to next wizard step (step 4)
        return "redirect:/inschrijving/student-info?id=" + registrationId;
    }
}
