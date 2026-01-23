package be.achieveit.snhinschrijvingen.controller;

import be.achieveit.snhinschrijvingen.dto.RelationsForm;
import be.achieveit.snhinschrijvingen.model.Address;
import be.achieveit.snhinschrijvingen.model.Registration;
import be.achieveit.snhinschrijvingen.model.Relation;
import be.achieveit.snhinschrijvingen.repository.RelationRepository;
import be.achieveit.snhinschrijvingen.service.RegistrationService;
import be.achieveit.snhinschrijvingen.service.WizardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/inschrijving")
public class RelationsController {
    
    private final RegistrationService registrationService;
    private final WizardService wizardService;
    private final RelationRepository relationRepository;
    
    public RelationsController(RegistrationService registrationService, 
                              WizardService wizardService,
                              RelationRepository relationRepository) {
        this.registrationService = registrationService;
        this.wizardService = wizardService;
        this.relationRepository = relationRepository;
    }
    
    @GetMapping("/relaties/{id}")
    public String showRelations(@PathVariable("id") UUID registrationId, Model model) {
        Registration registration = registrationService.findById(registrationId)
                .orElseThrow(() -> new RuntimeException("Registration not found"));
        
        // Load existing relations from database
        List<Relation> existingRelations = relationRepository.findByRegistrationIdOrderByRelationOrderAsc(registrationId);
        
        // Create form
        RelationsForm form = new RelationsForm();
        
        // Pre-fill form with existing data
        if (!existingRelations.isEmpty()) {
            Relation relation1 = existingRelations.get(0);
            form.getRelation1().setRelationType(relation1.getRelationType());
            form.getRelation1().setFirstName(relation1.getFirstName());
            form.getRelation1().setLastName(relation1.getLastName());
            form.getRelation1().setPhone(relation1.getPhone());
            form.getRelation1().setEmail(relation1.getEmail());
            
            // Map Address to AddressDTO
            if (relation1.getAddress() != null) {
                form.getRelation1().getAddress().setStreet(relation1.getAddress().getStreet());
                form.getRelation1().getAddress().setHouseNumber(relation1.getAddress().getHouseNumber());
                form.getRelation1().getAddress().setBox(relation1.getAddress().getBox());
                form.getRelation1().getAddress().setPostalCode(relation1.getAddress().getPostalCode());
                form.getRelation1().getAddress().setCity(relation1.getAddress().getCity());
                form.getRelation1().getAddress().setCountry(relation1.getAddress().getCountry());
            }
        }
        
        if (existingRelations.size() > 1) {
            Relation relation2 = existingRelations.get(1);
            form.setShowRelation2(true);
            form.getRelation2().setRelationType(relation2.getRelationType());
            form.getRelation2().setFirstName(relation2.getFirstName());
            form.getRelation2().setLastName(relation2.getLastName());
            form.getRelation2().setPhone(relation2.getPhone());
            form.getRelation2().setEmail(relation2.getEmail());
            
            // Map Address to AddressDTO
            if (relation2.getAddress() != null) {
                form.getRelation2().getAddress().setStreet(relation2.getAddress().getStreet());
                form.getRelation2().getAddress().setHouseNumber(relation2.getAddress().getHouseNumber());
                form.getRelation2().getAddress().setBox(relation2.getAddress().getBox());
                form.getRelation2().getAddress().setPostalCode(relation2.getAddress().getPostalCode());
                form.getRelation2().getAddress().setCity(relation2.getAddress().getCity());
                form.getRelation2().getAddress().setCountry(relation2.getAddress().getCountry());
            }
        }
        
        model.addAttribute("relationsForm", form);
        model.addAttribute("registrationId", registrationId);
        model.addAttribute("registration", registration);
        model.addAttribute("wizardSteps", wizardService.getWizardSteps(4));
        
        return "relations";
    }
    
    @PostMapping("/relaties/{id}")
    public String processRelations(
            @PathVariable("id") UUID registrationId,
            @ModelAttribute RelationsForm relationsForm,
            Model model) {
        
        Registration registration = registrationService.findById(registrationId)
                .orElseThrow(() -> new RuntimeException("Registration not found"));
        
        // Validate at least one relation is filled
        if (!relationsForm.hasAtLeastOneRelation()) {
            model.addAttribute("error", "Vul minstens één relatie in");
            model.addAttribute("relationsForm", relationsForm);
            model.addAttribute("registrationId", registrationId);
            model.addAttribute("registration", registration);
            model.addAttribute("wizardSteps", wizardService.getWizardSteps(4));
            return "relations";
        }
        
        // Delete existing relations
        List<Relation> existingRelations = relationRepository.findByRegistrationIdOrderByRelationOrderAsc(registrationId);
        relationRepository.deleteAll(existingRelations);
        
        // Save relation 1
        Relation relation1 = new Relation();
        relation1.setRegistration(registration);
        relation1.setRelationOrder(1);
        relation1.setRelationType(relationsForm.getRelation1().getRelationType());
        relation1.setFirstName(relationsForm.getRelation1().getFirstName());
        relation1.setLastName(relationsForm.getRelation1().getLastName());
        relation1.setPhone(relationsForm.getRelation1().getPhone());
        relation1.setEmail(relationsForm.getRelation1().getEmail());
        
        // Map AddressDTO to Address
        Address address1 = new Address();
        address1.setStreet(relationsForm.getRelation1().getAddress().getStreet());
        address1.setHouseNumber(relationsForm.getRelation1().getAddress().getHouseNumber());
        address1.setBox(relationsForm.getRelation1().getAddress().getBox());
        address1.setPostalCode(relationsForm.getRelation1().getAddress().getPostalCode());
        address1.setCity(relationsForm.getRelation1().getAddress().getCity());
        address1.setCountry(relationsForm.getRelation1().getAddress().getCountry() != null 
            ? relationsForm.getRelation1().getAddress().getCountry() : "België");
        relation1.setAddress(address1);
        
        relationRepository.save(relation1);
        
        // Save relation 2 if provided
        if (relationsForm.isShowRelation2() && relationsForm.getRelation2() != null 
            && relationsForm.getRelation2().getFirstName() != null 
            && !relationsForm.getRelation2().getFirstName().isEmpty()) {
            
            Relation relation2 = new Relation();
            relation2.setRegistration(registration);
            relation2.setRelationOrder(2);
            relation2.setRelationType(relationsForm.getRelation2().getRelationType());
            relation2.setFirstName(relationsForm.getRelation2().getFirstName());
            relation2.setLastName(relationsForm.getRelation2().getLastName());
            relation2.setPhone(relationsForm.getRelation2().getPhone());
            relation2.setEmail(relationsForm.getRelation2().getEmail());
            
            // Map AddressDTO to Address
            Address address2 = new Address();
            address2.setStreet(relationsForm.getRelation2().getAddress().getStreet());
            address2.setHouseNumber(relationsForm.getRelation2().getAddress().getHouseNumber());
            address2.setBox(relationsForm.getRelation2().getAddress().getBox());
            address2.setPostalCode(relationsForm.getRelation2().getAddress().getPostalCode());
            address2.setCity(relationsForm.getRelation2().getAddress().getCity());
            address2.setCountry(relationsForm.getRelation2().getAddress().getCountry() != null 
                ? relationsForm.getRelation2().getAddress().getCountry() : "België");
            relation2.setAddress(address2);
            
            relationRepository.save(relation2);
        }
        
        // Update current step
        registration.setCurrentStep("RELATIONS");
        registrationService.updateRegistration(registration);
        
        // Redirect to next step (Doctor)
        return "redirect:/inschrijving/huisarts/" + registrationId;
    }
}