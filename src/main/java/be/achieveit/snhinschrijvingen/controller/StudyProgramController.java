package be.achieveit.snhinschrijvingen.controller;

import be.achieveit.snhinschrijvingen.model.Registration;
import be.achieveit.snhinschrijvingen.model.StudyDomain;
import be.achieveit.snhinschrijvingen.model.StudyOrientation;
import be.achieveit.snhinschrijvingen.model.StudyProgram;
import be.achieveit.snhinschrijvingen.service.RegistrationService;
import be.achieveit.snhinschrijvingen.service.StudyProgramService;
import be.achieveit.snhinschrijvingen.service.WizardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/inschrijving")
public class StudyProgramController {
    
    private static final Logger logger = LoggerFactory.getLogger(StudyProgramController.class);
    
    private final StudyProgramService studyProgramService;
    private final RegistrationService registrationService;
    private final WizardService wizardService;
    
    public StudyProgramController(StudyProgramService studyProgramService,
                                 RegistrationService registrationService,
                                 WizardService wizardService) {
        this.studyProgramService = studyProgramService;
        this.registrationService = registrationService;
        this.wizardService = wizardService;
    }
    
    @GetMapping("/study-program")
    public String showStudyProgramSelection(@RequestParam("id") UUID registrationId, Model model) {
        Optional<Registration> registration = registrationService.findById(registrationId);
        
        if (registration.isEmpty()) {
            return "redirect:/inschrijving/start";
        }
        
        model.addAttribute("registrationId", registrationId.toString());
        model.addAttribute("availableYears", studyProgramService.getAvailableYears());
        model.addAttribute("wizardSteps", wizardService.getWizardSteps(2));
        
        return "study-program";
    }
    
    @GetMapping("/study-program/programs")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getStudyPrograms(@RequestParam("year") Integer year) {
        List<StudyProgram> programs = studyProgramService.getStudyProgramsByYear(year);
        
        Map<String, Object> response = new HashMap<>();
        response.put("year", year);
        response.put("hasGrouping", year >= 3);
        
        if (year < 3) {
            // Simple list for years 1 and 2
            response.put("programs", programs.stream()
                .map(this::mapProgram)
                .collect(Collectors.toList()));
        } else {
            // Grouped by domain and orientation for years 3-6
            Map<String, Map<String, List<Map<String, Object>>>> grouped = new LinkedHashMap<>();
            
            programs.stream()
                .filter(p -> p.getDomain() != null)
                .collect(Collectors.groupingBy(
                    p -> p.getDomain().getName(),
                    LinkedHashMap::new,
                    Collectors.toList()
                ))
                .forEach((domainName, domainPrograms) -> {
                    Map<String, List<Map<String, Object>>> orientationMap = new LinkedHashMap<>();
                    
                    domainPrograms.stream()
                        .collect(Collectors.groupingBy(
                            p -> p.getOrientation() != null ? p.getOrientation().getName() : "Onbekend",
                            LinkedHashMap::new,
                            Collectors.toList()
                        ))
                        .forEach((orientationName, orientationPrograms) -> {
                            orientationMap.put(orientationName, orientationPrograms.stream()
                                .map(this::mapProgram)
                                .collect(Collectors.toList()));
                        });
                    
                    grouped.put(domainName, orientationMap);
                });
            
            response.put("groupedPrograms", grouped);
            
            // Add domain colors
            List<StudyDomain> domains = studyProgramService.getDomainsForYear(year);
            Map<String, String> domainColors = new HashMap<>();
            domains.forEach(d -> domainColors.put(d.getName(), studyProgramService.getDomainColor(d.getCode())));
            response.put("domainColors", domainColors);
        }
        
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/study-program")
    public String saveStudyProgram(@RequestParam("id") UUID registrationId,
                                  @RequestParam("studyProgramId") Long studyProgramId) {
        Optional<Registration> registrationOpt = registrationService.findById(registrationId);
        Optional<StudyProgram> programOpt = studyProgramService.getStudyProgramByCode(studyProgramId.toString());
        
        if (registrationOpt.isEmpty()) {
            return "redirect:/inschrijving/start";
        }
        
        // Save study program to registration (you'll need to add this field to Registration entity)
        // For now, just move to next step
        
        return "redirect:/inschrijving/next-step?id=" + registrationId;
    }
    
    private Map<String, Object> mapProgram(StudyProgram program) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", program.getId());
        map.put("code", program.getCode());
        map.put("name", program.getName());
        map.put("year", program.getYear());
        
        if (program.getDomain() != null) {
            map.put("domain", program.getDomain().getName());
            map.put("domainCode", program.getDomain().getCode());
        }
        
        if (program.getOrientation() != null) {
            map.put("orientation", program.getOrientation().getName());
        }
        
        return map;
    }
}
