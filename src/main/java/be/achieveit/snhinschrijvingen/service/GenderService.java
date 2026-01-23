package be.achieveit.snhinschrijvingen.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Service for handling gender related data and mappings.
 */
@Service
public class GenderService {

    // Gender code to readable text mapping
    private static final Map<String, String> GENDER_MAPPING = new HashMap<>();
    
    static {
        GENDER_MAPPING.put("M", "Man");
        GENDER_MAPPING.put("V", "Vrouw");
        GENDER_MAPPING.put("A", "Andere");
        GENDER_MAPPING.put("X", "Dat zeg ik liever niet");
    }
    
    /**
     * Maps a gender code to its readable text.
     * 
     * @param genderCode The gender code (e.g., "M", "V", "A", "X")
     * @return The readable text or the code itself if not found
     */
    public String getGenderText(String genderCode) {
        if (genderCode == null || genderCode.isEmpty()) {
            return null;
        }
        return GENDER_MAPPING.getOrDefault(genderCode, genderCode);
    }
    
    /**
     * Gets all available genders with their text.
     * 
     * @return Map of gender code to text
     */
    public Map<String, String> getAllGenders() {
        return new HashMap<>(GENDER_MAPPING);
    }
}
