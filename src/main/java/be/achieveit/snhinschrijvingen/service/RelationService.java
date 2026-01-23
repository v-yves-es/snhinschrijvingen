package be.achieveit.snhinschrijvingen.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Service for handling relation related data and mappings.
 */
@Service
public class RelationService {

    // Relation type ID to readable text mapping
    private static final Map<String, String> RELATION_TYPE_MAPPING = new HashMap<>();
    
    static {
        RELATION_TYPE_MAPPING.put("13", "Vader");
        RELATION_TYPE_MAPPING.put("14", "Moeder");
        RELATION_TYPE_MAPPING.put("5", "Plusvader");
        RELATION_TYPE_MAPPING.put("6", "Plusmoeder");
        RELATION_TYPE_MAPPING.put("2", "Voogd");
        RELATION_TYPE_MAPPING.put("9", "Grootvader");
        RELATION_TYPE_MAPPING.put("10", "Grootmoeder");
        RELATION_TYPE_MAPPING.put("7", "Pleegvader");
        RELATION_TYPE_MAPPING.put("8", "Pleegmoeder");
    }
    
    /**
     * Maps a relation type ID to its readable text.
     * 
     * @param relationTypeId The relation type ID (e.g., "13", "14")
     * @return The readable text or the ID itself if not found
     */
    public String getRelationTypeName(String relationTypeId) {
        if (relationTypeId == null || relationTypeId.isEmpty()) {
            return null;
        }
        return RELATION_TYPE_MAPPING.getOrDefault(relationTypeId, relationTypeId);
    }
    
    /**
     * Gets all available relation types with their names.
     * 
     * @return Map of relation type ID to name
     */
    public Map<String, String> getAllRelationTypes() {
        return new HashMap<>(RELATION_TYPE_MAPPING);
    }
}
