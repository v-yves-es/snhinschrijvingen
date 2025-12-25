package be.achieveit.snhinschrijvingen.controller;

import be.achieveit.snhinschrijvingen.dto.StudentForm;
import be.achieveit.snhinschrijvingen.model.Nationaliteit;
import be.achieveit.snhinschrijvingen.service.WizardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/inschrijving")
public class StudentInfoController {

    private final WizardService wizardService;

    public StudentInfoController(final WizardService wizardService) {
        this.wizardService = wizardService;
    }

    @GetMapping("/student-info")
    public String showStudentInfo(Model model) {
        model.addAttribute("studentForm", new StudentForm());
        model.addAttribute("wizardSteps", wizardService.getWizardSteps(4, List.of(1, 2, 3)));
        model.addAttribute("nationaliteiten", getNationaliteiten());
        return "student-info";
    }

    @PostMapping("/student-info")
    public String processStudentInfo(@ModelAttribute StudentForm studentForm, Model model) {
        // Process and save student info
        // Redirect to next step
        return "redirect:/inschrijving/huisarts";
    }

    private List<Nationaliteit> getNationaliteiten() {
        List<Nationaliteit> nationaliteiten = new ArrayList<>();
        nationaliteiten.add(new Nationaliteit("1", "Belg"));
        nationaliteiten.add(new Nationaliteit("4", "Congolees"));
        nationaliteiten.add(new Nationaliteit("7", "Frans"));
        nationaliteiten.add(new Nationaliteit("16", "Marokkaans"));
        nationaliteiten.add(new Nationaliteit("8", "Nederlands"));
        nationaliteiten.add(new Nationaliteit("13", "Spaans"));
        nationaliteiten.add(new Nationaliteit("0", "Anders"));
        return nationaliteiten;
    }
}
