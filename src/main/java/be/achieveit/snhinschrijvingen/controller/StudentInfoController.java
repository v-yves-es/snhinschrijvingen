package be.achieveit.snhinschrijvingen.controller;

import be.achieveit.snhinschrijvingen.dto.StudentForm;
import be.achieveit.snhinschrijvingen.model.Nationality;
import be.achieveit.snhinschrijvingen.model.Registration;
import be.achieveit.snhinschrijvingen.model.SchoolYear;
import be.achieveit.snhinschrijvingen.service.NationalityService;
import be.achieveit.snhinschrijvingen.service.RegistrationService;
import be.achieveit.snhinschrijvingen.service.SchoolYearService;
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
    private final SchoolYearService schoolYearService;

    public StudentInfoController(
            final WizardService wizardService,
            final RegistrationService registrationService,
            final NationalityService nationalityService,
            final SchoolYearService schoolYearService) {
        this.wizardService = wizardService;
        this.registrationService = registrationService;
        this.nationalityService = nationalityService;
        this.schoolYearService = schoolYearService;
    }

    @GetMapping("/student-info")
    public String showStudentInfo(@RequestParam(required = false) UUID id, Model model) {
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
        model.addAttribute("wizardSteps", wizardService.getWizardSteps(1, List.of()));

        // Add registration to model
        model.addAttribute("registration", registration);
        model.addAttribute("registrationId", id);

        // Add school year for header
        SchoolYear registrationSchoolYear = schoolYearService.getRegistrationSchoolYear();
        model.addAttribute("schoolYearDescription", registrationSchoolYear.getDescription());

        // Form data - pre-fill from database or use test data
        StudentForm studentForm = new StudentForm();
        
        if (registration.getStudentFirstname() != null) {
            // Load from database
            studentForm.setVoornaamLeerling(registration.getStudentFirstname());
            studentForm.setNaamLeerling(registration.getStudentLastname());
            studentForm.setAdres(registration.getStudentAdres());
            studentForm.setPostnummer(registration.getStudentPostnummer());
            studentForm.setGemeente(registration.getStudentGemeente());
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
            studentForm.setAdres("Teststraat 123");
            studentForm.setPostnummer("8500");
            studentForm.setGemeente("Kortrijk");
            studentForm.setGsm("0476123456");
            studentForm.setGeslacht("M");
            studentForm.setRijksregisternummer("050101-123-45");
            studentForm.setGeboortedatum(java.time.LocalDate.of(2005, 1, 1));
            studentForm.setGeboorteplaats("Kortrijk");
            studentForm.setNationaliteit("BE");
        }

        model.addAttribute("studentForm", studentForm);
        model.addAttribute("nationaliteiten", nationalityService.getAllNationalities());

        return "student-info";
    }

    @PostMapping("/student-info")
    public String processStudentInfo(
            @RequestParam UUID id,
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
        registration.setStudentAdres(studentForm.getAdres());
        registration.setStudentPostnummer(studentForm.getPostnummer());
        registration.setStudentGemeente(studentForm.getGemeente());
        registration.setStudentGsm(studentForm.getGsm());
        registration.setStudentGeslacht(studentForm.getGeslacht());
        registration.setStudentRijksregisternummer(studentForm.getRijksregisternummer());
        registration.setStudentGeboortedatum(studentForm.getGeboortedatum());
        registration.setStudentGeboorteplaats(studentForm.getGeboorteplaats());
        registration.setStudentNationaliteit(studentForm.getNationaliteit());

        registrationService.updateRegistration(registration);

        logger.info("Student info saved for registration: {}", id);

        // Navigate to study program selection (next step in wizard)
        return "redirect:/inschrijving/study-program?id=" + id;
    }
}
