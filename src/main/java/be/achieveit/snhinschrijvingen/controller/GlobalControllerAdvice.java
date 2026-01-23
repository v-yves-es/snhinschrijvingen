package be.achieveit.snhinschrijvingen.controller;

import be.achieveit.snhinschrijvingen.model.SchoolYear;
import be.achieveit.snhinschrijvingen.service.SchoolYearService;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * Global controller advice that adds common model attributes available to all views.
 */
@ControllerAdvice
public class GlobalControllerAdvice {

    private final SchoolYearService schoolYearService;

    public GlobalControllerAdvice(final SchoolYearService schoolYearService) {
        this.schoolYearService = schoolYearService;
    }

    /**
     * Adds the current registration school year description to all views.
     * This is used in the header to display "Inschrijvingen [schooljaar]".
     */
    @ModelAttribute("schoolYearDescription")
    public String schoolYearDescription() {
        SchoolYear registrationSchoolYear = schoolYearService.getRegistrationSchoolYear();
        return registrationSchoolYear.getDescription();
    }
}
