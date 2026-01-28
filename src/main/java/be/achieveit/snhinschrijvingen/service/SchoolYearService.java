package be.achieveit.snhinschrijvingen.service;

import be.achieveit.snhinschrijvingen.model.SchoolYear;
import be.achieveit.snhinschrijvingen.repository.SchoolYearRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class SchoolYearService {

    private static final Logger logger = LoggerFactory.getLogger(SchoolYearService.class);

    private final SchoolYearRepository schoolYearRepository;

    public SchoolYearService(SchoolYearRepository schoolYearRepository) {
        this.schoolYearRepository = schoolYearRepository;
    }

    /**
     * Get the current active school year (the one currently running).
     */
    public Optional<SchoolYear> getCurrentSchoolYear() {
        LocalDate now = LocalDate.now();
        return schoolYearRepository.findByStartDateLessThanEqualAndEndDateGreaterThanEqual(now, now);
    }

    /**
     * Get the registration school year (next school year after the current one).
     * This is the school year for which students are registering.
     */
    public SchoolYear getRegistrationSchoolYear() {
        Optional<SchoolYear> currentOpt = getCurrentSchoolYear();
        
        if (currentOpt.isPresent()) {
            SchoolYear current = currentOpt.get();
            // Extract year from current school year (e.g., "2024-2025" -> 2024)
            String[] parts = current.getId().split("-");
            int startYear = Integer.parseInt(parts[0]);
            
            // Calculate next school year
            String nextSchoolYearId = (startYear + 1) + "-" + (startYear + 2);
            
            Optional<SchoolYear> nextOpt = schoolYearRepository.findById(nextSchoolYearId);
            // If next year doesn't exist, create it
            return nextOpt.orElseGet(() -> createNextSchoolYear(current));
        } else {
            // If no current school year, create default based on current date
            logger.warn("No current school year found, creating default");
            return createDefaultSchoolYear();
        }
    }

    /**
     * Create the next school year based on the current one.
     */
    private SchoolYear createNextSchoolYear(SchoolYear current) {
        String[] parts = current.getId().split("-");
        int startYear = Integer.parseInt(parts[0]) + 1;
        int endYear = startYear + 1;
        
        String id = startYear + "-" + endYear;
        String description = "Schooljaar " + id;
        LocalDate startDate = LocalDate.of(startYear, 9, 1);
        LocalDate endDate = LocalDate.of(endYear, 8, 31);
        
        SchoolYear newSchoolYear = new SchoolYear(id, description, startDate, endDate);
        SchoolYear saved = schoolYearRepository.save(newSchoolYear);
        
        logger.info("Created new school year: {}", id);
        return saved;
    }

    /**
     * Create a default school year based on current date.
     */
    private SchoolYear createDefaultSchoolYear() {
        LocalDate now = LocalDate.now();
        int currentYear = now.getYear();
        int startYear = now.getMonthValue() >= 9 ? currentYear + 1 : currentYear;
        int endYear = startYear + 1;
        
        String id = startYear + "-" + endYear;
        String description = "Schooljaar " + id;
        LocalDate startDate = LocalDate.of(startYear, 9, 1);
        LocalDate endDate = LocalDate.of(endYear, 8, 31);
        
        SchoolYear schoolYear = new SchoolYear(id, description, startDate, endDate);
        SchoolYear saved = schoolYearRepository.save(schoolYear);
        
        logger.info("Created default school year: {}", id);
        return saved;
    }

    @Transactional
    public SchoolYear save(SchoolYear schoolYear) {
        return schoolYearRepository.save(schoolYear);
    }

    public Optional<SchoolYear> findById(String id) {
        return schoolYearRepository.findById(id);
    }
}
