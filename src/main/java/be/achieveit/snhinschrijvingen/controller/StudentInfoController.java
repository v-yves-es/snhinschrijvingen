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

        // Form data
        StudentForm studentForm = new StudentForm();
        // Pre-fill if data exists
        if (registration.getStudentFirstname() != null) {
            studentForm.setVoornaamLeerling(registration.getStudentFirstname());
        }
        if (registration.getStudentLastname() != null) {
            studentForm.setNaamLeerling(registration.getStudentLastname());
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

        // Update registration with student info
        registrationService.updateStudentInfo(
                id,
                studentForm.getVoornaamLeerling(),
                studentForm.getNaamLeerling()
        );

        // TODO: Navigate to next step in wizard
        // For now, redirect back to overview
        Optional<Registration> regOpt = registrationService.findById(id);
        if (regOpt.isPresent()) {
            String email = regOpt.get().getEmail();
            return "redirect:/inschrijving/verify/" + id + "/" + 
                   registrationService.hashEmail(email);
        }

        return "redirect:/inschrijving/start";
    }
}
