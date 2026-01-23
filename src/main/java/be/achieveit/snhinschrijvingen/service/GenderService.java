package be.achieveit.snhinschrijvingen.service;

import be.achieveit.snhinschrijvingen.dto.GenderOption;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service for handling gender related data and mappings.
 */
@Service
public class GenderService {

    // Gender options with sort order
    private static final List<GenderOption> GENDERS = new ArrayList<>();
    
    static {
        GENDERS.add(new GenderOption("M", "Man", 1));
        GENDERS.add(new GenderOption("V", "Vrouw", 2));
        GENDERS.add(new GenderOption("A", "Andere", 3));
        GENDERS.add(new GenderOption("X", "Dat zeg ik liever niet", 4));
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
        return GENDERS.stream()
                .filter(g -> g.getCode().equals(genderCode))
                .map(GenderOption::getLabel)
                .findFirst()
                .orElse(genderCode);
    }
    
    /**
     * Gets all available genders with their text (legacy method).
     * 
     * @return Map of gender code to text
     * @deprecated Use {@link #getAllGenderOptions()} instead for structured data
     */
    @Deprecated
    public Map<String, String> getAllGenders() {
        return GENDERS.stream()
                .collect(Collectors.toMap(
                        GenderOption::getCode,
                        GenderOption::getLabel,
                        (existing, replacement) -> existing,
                        LinkedHashMap::new
                ));
    }
    
    /**
     * Gets all available genders as structured GenderOption objects.
     * Genders are already sorted by sortOrder.
     * 
     * @return List of GenderOption objects
     */
    public List<GenderOption> getAllGenderOptions() {
        return new ArrayList<>(GENDERS);
    }
}
