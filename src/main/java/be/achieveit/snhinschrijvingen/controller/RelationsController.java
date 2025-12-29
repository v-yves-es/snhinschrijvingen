package be.achieveit.snhinschrijvingen.controller;

import be.achieveit.snhinschrijvingen.dto.RelationsForm;
import be.achieveit.snhinschrijvingen.model.Registration;
import be.achieveit.snhinschrijvingen.service.RegistrationService;
import be.achieveit.snhinschrijvingen.service.WizardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/inschrijving")
public class RelationsController {
    
    private final RegistrationService registrationService;
    private final WizardService wizardService;
    
    public RelationsController(RegistrationService registrationService, WizardService wizardService) {
        this.registrationService = registrationService;
        this.wizardService = wizardService;
    }
    
    @GetMapping("/relations")
    public String showRelations(@RequestParam("id") UUID registrationId, Model model) {
        Registration registration = registrationService.findById(registrationId)
                .orElseThrow(() -> new RuntimeException("Registration not found"));
        
        // Create form (for now empty, will add persistence later)
        RelationsForm form = new RelationsForm();
        
        model.addAttribute("relationsForm", form);
        model.addAttribute("registrationId", registrationId);
        model.addAttribute("wizardSteps", wizardService.getWizardSteps(4)); // Step 4 = Relations
        
        return "relations";
    }
    
    @PostMapping("/relations")
    public String processRelations(
            @RequestParam("id") UUID registrationId,
            @ModelAttribute RelationsForm relationsForm,
            Model model) {
        
        Registration registration = registrationService.findById(registrationId)
                .orElseThrow(() -> new RuntimeException("Registration not found"));
        
        // Validate at least one relation is filled
        if (!relationsForm.hasAtLeastOneRelation()) {
            model.addAttribute("error", "Vul minstens één relatie in");
            model.addAttribute("relationsForm", relationsForm);
            model.addAttribute("registrationId", registrationId);
            model.addAttribute("wizardSteps", wizardService.getWizardSteps(4));
            return "relations";
        }
        
        // TODO: Persist relations data to database
        // For now, just update current step
        registration.setCurrentStep("RELATIONS");
        registrationService.updateRegistration(registration);
        
        // Redirect to next step (TBD)
        return "redirect:/inschrijving/overview?id=" + registrationId;
    }
}
