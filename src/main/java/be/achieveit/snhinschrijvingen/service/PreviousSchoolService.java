package be.achieveit.snhinschrijvingen.service;

import be.achieveit.snhinschrijvingen.dto.SchoolOption;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service for handling previous school related data and mappings.
 */
@Service
public class PreviousSchoolService {

    // School year code to readable text mapping
    private static final Map<String, String> SCHOOL_YEAR_MAPPING = new HashMap<>();
    
    static {
        // Basisonderwijs
        SCHOOL_YEAR_MAPPING.put("B5", "5de leerjaar basisonderwijs");
        SCHOOL_YEAR_MAPPING.put("B6", "6de leerjaar basisonderwijs");
        
        // Secundair onderwijs
        SCHOOL_YEAR_MAPPING.put("S1", "1ste jaar secundair onderwijs");
        SCHOOL_YEAR_MAPPING.put("S2", "2de jaar secundair onderwijs");
        SCHOOL_YEAR_MAPPING.put("S3", "3de jaar secundair onderwijs");
        SCHOOL_YEAR_MAPPING.put("S4", "4de jaar secundair onderwijs");
        SCHOOL_YEAR_MAPPING.put("S5", "5de jaar secundair onderwijs");
        SCHOOL_YEAR_MAPPING.put("S6", "6de jaar secundair onderwijs");
    }
    
    // School options with categories
    private static final List<SchoolOption> SCHOOLS = new ArrayList<>();
    
    static {
        // Basisonderwijs
        SCHOOLS.add(new SchoolOption("101", "BAMO Moorsele", "Basisonderwijs"));
        SCHOOLS.add(new SchoolOption("102", "Basisschool Wevelgem", "Basisonderwijs"));
        SCHOOLS.add(new SchoolOption("103", "Centrumschool Zwevegem", "Basisonderwijs"));
        SCHOOLS.add(new SchoolOption("104", "Damiaanschool Kortrijk", "Basisonderwijs"));
        SCHOOLS.add(new SchoolOption("105", "De Gulleboom Gullegem", "Basisonderwijs"));
        SCHOOLS.add(new SchoolOption("106", "De Waterlelie Moorsele", "Basisonderwijs"));
        SCHOOLS.add(new SchoolOption("107", "Dobbelsteen XL Heule", "Basisonderwijs"));
        SCHOOLS.add(new SchoolOption("108", "Het Open Groene Marke", "Basisonderwijs"));
        SCHOOLS.add(new SchoolOption("109", "Kinderland Don Bosco", "Basisonderwijs"));
        SCHOOLS.add(new SchoolOption("110", "Sint-Amandscollege Zuid Kortrijk", "Basisonderwijs"));
        SCHOOLS.add(new SchoolOption("111", "Sint-Pauluscollege Wevelgem", "Basisonderwijs"));
        SCHOOLS.add(new SchoolOption("112", "VBS De Watermolen Heule", "Basisonderwijs"));
        SCHOOLS.add(new SchoolOption("113", "VBS Sint-Eloois-Winkel", "Basisonderwijs"));
        SCHOOLS.add(new SchoolOption("114", "VBS Sint-Katharina", "Basisonderwijs"));
        SCHOOLS.add(new SchoolOption("115", "VBS Spes Nostra Heule", "Basisonderwijs"));
        SCHOOLS.add(new SchoolOption("116", "VBS St. Vincentius Bissegem", "Basisonderwijs"));
        SCHOOLS.add(new SchoolOption("117", "Wijnbergschool Wevelgem", "Basisonderwijs"));
        
        // Secundair onderwijs
        SCHOOLS.add(new SchoolOption("1", "Atheneum Kortrijk", "Secundair onderwijs"));
        SCHOOLS.add(new SchoolOption("11", "Prizma Middenschool Lendelede", "Secundair onderwijs"));
        SCHOOLS.add(new SchoolOption("12", "Sint-Aloysiuscollege Menen", "Secundair onderwijs"));
        SCHOOLS.add(new SchoolOption("13", "Sint-Pauluscollege Wevelgem", "Secundair onderwijs"));
        SCHOOLS.add(new SchoolOption("15", "Spes Nostra Kuurne", "Secundair onderwijs"));
        
        // Andere
        SCHOOLS.add(new SchoolOption("0", "➕ Andere school (niet in lijst)", "Andere"));
    }
    
    /**
     * Maps a school year code to its readable text.
     * 
     * @param yearCode The year code (e.g., "B6", "S1")
     * @return The readable text or the code itself if not found
     */
    public String getSchoolYearText(String yearCode) {
        if (yearCode == null || yearCode.isEmpty()) {
            return null;
        }
        return SCHOOL_YEAR_MAPPING.getOrDefault(yearCode, yearCode);
    }
    
    /**
     * Maps a school ID to its name.
     * Returns null for ID "0" (custom school) so the custom name can be used instead.
     * 
     * @param schoolId The school ID (e.g., "101", "1")
     * @return The school name, null for "0" (custom school), or the ID itself if not found
     */
    public String getSchoolName(String schoolId) {
        if (schoolId == null || schoolId.isEmpty()) {
            return null;
        }
        // Return null for "0" (custom school) so fallback to vorigeSchoolAnders works
        if ("0".equals(schoolId)) {
            return null;
        }
        return SCHOOLS.stream()
                .filter(school -> school.getId().equals(schoolId))
                .map(SchoolOption::getName)
                .findFirst()
                .orElse(schoolId);
    }
    
    /**
     * Gets all available school year codes with their text.
     * 
     * @return Map of year code to text
     */
    public Map<String, String> getAllSchoolYears() {
        return new HashMap<>(SCHOOL_YEAR_MAPPING);
    }
    
    /**
     * Gets all available schools with their names (legacy method).
     * 
     * @return Map of school ID to name
     * @deprecated Use {@link #getAllSchoolOptions()} instead for structured data
     */
    @Deprecated
    public Map<String, String> getAllSchools() {
        return SCHOOLS.stream()
                .collect(Collectors.toMap(
                        SchoolOption::getId,
                        SchoolOption::getName,
                        (existing, replacement) -> existing,
                        HashMap::new
                ));
    }
    
    /**
     * Gets all available schools as structured SchoolOption objects.
     * Schools are already sorted by category and name.
     * 
     * @return List of SchoolOption objects
     */
    public List<SchoolOption> getAllSchoolOptions() {
        return new ArrayList<>(SCHOOLS);
    }
    
    /**
     * Gets schools grouped by category.
     * 
     * @return Map with category as key and list of schools as value
     */
    public Map<String, List<SchoolOption>> getSchoolsByCategory() {
        return SCHOOLS.stream()
                .collect(Collectors.groupingBy(
                        SchoolOption::getCategory,
                        LinkedHashMap::new,
                        Collectors.toList()
                ));
    }
    
    /**
     * Gets school year options grouped by category.
     * 
     * @return Map with category as key and map of year codes to labels as value
     */
    public Map<String, Map<String, String>> getSchoolYearsByCategory() {
        Map<String, Map<String, String>> result = new LinkedHashMap<>();
        
        Map<String, String> basisonderwijs = new LinkedHashMap<>();
        basisonderwijs.put("B5", "5de leerjaar basisonderwijs");
        basisonderwijs.put("B6", "6de leerjaar basisonderwijs");
        result.put("Basisonderwijs", basisonderwijs);
        
        Map<String, String> secundair = new LinkedHashMap<>();
        secundair.put("S1", "1ste jaar secundair onderwijs");
        secundair.put("S2", "2de jaar secundair onderwijs");
        secundair.put("S3", "3de jaar secundair onderwijs");
        secundair.put("S4", "4de jaar secundair onderwijs");
        secundair.put("S5", "5de jaar secundair onderwijs");
        secundair.put("S6", "6de jaar secundair onderwijs");
        result.put("Secundair onderwijs", secundair);
        
        return result;
    }
}
