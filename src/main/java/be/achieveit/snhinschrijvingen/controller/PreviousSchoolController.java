package be.achieveit.snhinschrijvingen.controller;

import be.achieveit.snhinschrijvingen.dto.PreviousSchoolForm;
import be.achieveit.snhinschrijvingen.model.Registration;
import be.achieveit.snhinschrijvingen.service.PreviousSchoolService;
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
    private final PreviousSchoolService previousSchoolService;
    
    public PreviousSchoolController(RegistrationService registrationService, 
                                   WizardService wizardService,
                                   PreviousSchoolService previousSchoolService) {
        this.registrationService = registrationService;
        this.wizardService = wizardService;
        this.previousSchoolService = previousSchoolService;
    }
    
    @GetMapping("/vorige-school/{id}")
    public String showPreviousSchoolForm(@PathVariable("id") UUID id, Model model) {
        Optional<Registration> registrationOpt = registrationService.findById(id);
        
        if (registrationOpt.isEmpty()) {
            logger.warn("Registration not found for ID: {}", id);
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
        model.addAttribute("registrationId", id);
        model.addAttribute("wizardSteps", wizardService.getWizardSteps(3, List.of(1, 2)));
        
        // Add school options grouped by category
        model.addAttribute("schoolsByCategory", previousSchoolService.getSchoolsByCategory());
        model.addAttribute("schoolYearsByCategory", previousSchoolService.getSchoolYearsByCategory());
        
        return "previous-school";
    }
    
    @PostMapping("/vorige-school/{id}")
    public String submitPreviousSchoolForm(
            @PathVariable("id") UUID id,
            @ModelAttribute PreviousSchoolForm form) {
        
        Optional<Registration> registrationOpt = registrationService.findById(id);
        
        if (registrationOpt.isEmpty()) {
            logger.error("Registration not found for ID: {}", id);
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
        
        logger.info("Previous school info saved for registration: {}", id);
        
        // Redirect to next wizard step (Relations)
        return "redirect:/inschrijving/relaties/" + id;
    }
}
