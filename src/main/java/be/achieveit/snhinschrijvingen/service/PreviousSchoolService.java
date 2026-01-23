package be.achieveit.snhinschrijvingen.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

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
    
    // School ID to name mapping
    // TODO: Move to database table in future
    private static final Map<String, String> SCHOOL_NAME_MAPPING = new HashMap<>();
    
    static {
        // Basisonderwijs
        SCHOOL_NAME_MAPPING.put("101", "BAMO Moorsele");
        SCHOOL_NAME_MAPPING.put("102", "Basisschool Wevelgem");
        SCHOOL_NAME_MAPPING.put("103", "Centrumschool Zwevegem");
        SCHOOL_NAME_MAPPING.put("104", "Damiaanschool Kortrijk");
        SCHOOL_NAME_MAPPING.put("105", "De Gulleboom Gullegem");
        SCHOOL_NAME_MAPPING.put("106", "De Waterlelie Moorsele");
        SCHOOL_NAME_MAPPING.put("107", "Dobbelsteen XL Heule");
        SCHOOL_NAME_MAPPING.put("108", "Het Open Groene Marke");
        SCHOOL_NAME_MAPPING.put("109", "Kinderland Don Bosco");
        SCHOOL_NAME_MAPPING.put("110", "Sint-Amandscollege Zuid Kortrijk");
        SCHOOL_NAME_MAPPING.put("111", "Sint-Pauluscollege Wevelgem");
        SCHOOL_NAME_MAPPING.put("112", "VBS De Watermolen Heule");
        SCHOOL_NAME_MAPPING.put("113", "VBS Sint-Eloois-Winkel");
        SCHOOL_NAME_MAPPING.put("114", "VBS Sint-Katharina");
        SCHOOL_NAME_MAPPING.put("115", "VBS Spes Nostra Heule");
        SCHOOL_NAME_MAPPING.put("116", "VBS St. Vincentius Bissegem");
        SCHOOL_NAME_MAPPING.put("117", "Wijnbergschool Wevelgem");
        
        // Secundair onderwijs
        SCHOOL_NAME_MAPPING.put("1", "Atheneum Kortrijk");
        SCHOOL_NAME_MAPPING.put("11", "Prizma Middenschool Lendelede");
        SCHOOL_NAME_MAPPING.put("12", "Sint-Aloysiuscollege Menen");
        SCHOOL_NAME_MAPPING.put("13", "Sint-Pauluscollege Wevelgem");
        SCHOOL_NAME_MAPPING.put("15", "Spes Nostra Kuurne");
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
        return SCHOOL_NAME_MAPPING.getOrDefault(schoolId, schoolId);
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
     * Gets all available schools with their names.
     * 
     * @return Map of school ID to name
     */
    public Map<String, String> getAllSchools() {
        return new HashMap<>(SCHOOL_NAME_MAPPING);
    }
}
