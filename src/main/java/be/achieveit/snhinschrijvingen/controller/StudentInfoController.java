package be.achieveit.snhinschrijvingen.controller;

import be.achieveit.snhinschrijvingen.dto.StudentForm;
import be.achieveit.snhinschrijvingen.model.Address;
import be.achieveit.snhinschrijvingen.model.Registration;
import be.achieveit.snhinschrijvingen.service.GenderService;
import be.achieveit.snhinschrijvingen.service.NationalityService;
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
public class StudentInfoController {

    private static final Logger logger = LoggerFactory.getLogger(StudentInfoController.class);

    private final WizardService wizardService;
    private final RegistrationService registrationService;
    private final NationalityService nationalityService;
    private final GenderService genderService;

    public StudentInfoController(
            final WizardService wizardService,
            final RegistrationService registrationService,
            final NationalityService nationalityService,
            final GenderService genderService) {
        this.wizardService = wizardService;
        this.registrationService = registrationService;
        this.nationalityService = nationalityService;
        this.genderService = genderService;
    }

    @GetMapping("/leerling-info/{id}")
    public String showStudentInfo(@PathVariable UUID id, Model model) {
        // Registration ID should be provided
        if (id == null) {
            logger.warn("No registration ID provided for student-info");
            return "redirect:/inschrijving/start";
        }

        Optional<Registration> registrationOpt = registrationService.findById(id);
        if (registrationOpt.isEmpty()) {
            logger.warn("Registration not found: {}", id);
            return "redirect:/inschrijving/start";
        }

        Registration registration = registrationOpt.get();

        // Wizard steps (1 active, none completed yet)
        model.addAttribute("wizardSteps", wizardService.getWizardSteps(1, List.of(), id.toString()));

        // Add registration to model
        model.addAttribute("registration", registration);
        model.addAttribute("registrationId", id);

        // Form data - pre-fill from database or use test data
        StudentForm studentForm = new StudentForm();
        
        if (registration.getStudentFirstname() != null) {
            // Load from database
            studentForm.setVoornaamLeerling(registration.getStudentFirstname());
            studentForm.setNaamLeerling(registration.getStudentLastname());
            
            // Map address from Registration entity
            if (registration.getStudentAddress() != null) {
                Address dbAddress = registration.getStudentAddress();
                studentForm.getAddress().setStreet(dbAddress.getStreet());
                studentForm.getAddress().setHouseNumber(dbAddress.getHouseNumber());
                studentForm.getAddress().setBox(dbAddress.getBox());
                studentForm.getAddress().setPostalCode(dbAddress.getPostalCode());
                studentForm.getAddress().setCity(dbAddress.getCity());
                studentForm.getAddress().setCountry(dbAddress.getCountry());
            }
            
            studentForm.setGsm(registration.getStudentGsm());
            studentForm.setGeslacht(registration.getStudentGeslacht());
            studentForm.setRijksregisternummer(registration.getStudentRijksregisternummer());
            studentForm.setGeboortedatum(registration.getStudentGeboortedatum());
            studentForm.setGeboorteplaats(registration.getStudentGeboorteplaats());
            studentForm.setNationaliteit(registration.getStudentNationaliteit());
        } else {
            // TEMPORARY: Pre-fill with test data
            studentForm.setVoornaamLeerling("Jan");
            studentForm.setNaamLeerling("Janssens");
            studentForm.getAddress().setStreet("Teststraat");
            studentForm.getAddress().setHouseNumber("123");
            studentForm.getAddress().setPostalCode("8500");
            studentForm.getAddress().setCity("Kortrijk");
            studentForm.getAddress().setCountry("België");
            studentForm.setGsm("0476123456");
            studentForm.setGeslacht("M");
            studentForm.setRijksregisternummer("050101-123-45");
            studentForm.setGeboortedatum(java.time.LocalDate.of(2005, 1, 1));
            studentForm.setGeboorteplaats("Kortrijk");
            studentForm.setNationaliteit("BE");
        }

        model.addAttribute("studentForm", studentForm);
        model.addAttribute("nationaliteiten", nationalityService.getAllNationalities());
        model.addAttribute("genderOptions", genderService.getAllGenderOptions());

        return "student-info";
    }

    @PostMapping("/leerling-info/{id}")
    public String processStudentInfo(
            @PathVariable UUID id,
            @ModelAttribute StudentForm studentForm,
            Model model) {

        logger.info("Processing student info for registration: {}", id);

        Optional<Registration> registrationOpt = registrationService.findById(id);
        if (registrationOpt.isEmpty()) {
            logger.error("Registration not found: {}", id);
            return "redirect:/inschrijving/start";
        }

        Registration registration = registrationOpt.get();

        // Update registration with all student info
        registration.setStudentFirstname(studentForm.getVoornaamLeerling());
        registration.setStudentLastname(studentForm.getNaamLeerling());
        
        // Map Address object directly
        if (studentForm.getAddress() != null) {
            Address address = new Address();
            address.setStreet(studentForm.getAddress().getStreet());
            address.setHouseNumber(studentForm.getAddress().getHouseNumber());
            address.setBox(studentForm.getAddress().getBox());
            address.setPostalCode(studentForm.getAddress().getPostalCode());
            address.setCity(studentForm.getAddress().getCity());
            address.setCountry(studentForm.getAddress().getCountry());
            registration.setStudentAddress(address);
        }
        
        registration.setStudentGsm(studentForm.getGsm());
        registration.setStudentGeslacht(studentForm.getGeslacht());
        registration.setStudentRijksregisternummer(studentForm.getRijksregisternummer());
        registration.setStudentGeboortedatum(studentForm.getGeboortedatum());
        registration.setStudentGeboorteplaats(studentForm.getGeboorteplaats());
        registration.setStudentNationaliteit(studentForm.getNationaliteit());
        
        // Update current step
        registration.setCurrentStep("STUDY_PROGRAM");

        registrationService.updateRegistration(registration);

        logger.info("Student info saved for registration: {}", id);

        // Navigate to study program selection (next step in wizard)
        return "redirect:/inschrijving/studierichting/" + id;
    }
}
