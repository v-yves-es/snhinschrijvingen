package be.achieveit.snhinschrijvingen.service;

import be.achieveit.snhinschrijvingen.dto.RelationTypeOption;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service for handling relation related data and mappings.
 */
@Service
public class RelationService {

    // Relation type options with sort order
    private static final List<RelationTypeOption> RELATION_TYPES = new ArrayList<>();
    
    static {
        RELATION_TYPES.add(new RelationTypeOption("13", "Vader", 1));
        RELATION_TYPES.add(new RelationTypeOption("14", "Moeder", 2));
        RELATION_TYPES.add(new RelationTypeOption("5", "Plusvader", 3));
        RELATION_TYPES.add(new RelationTypeOption("6", "Plusmoeder", 4));
        RELATION_TYPES.add(new RelationTypeOption("2", "Voogd", 5));
        RELATION_TYPES.add(new RelationTypeOption("9", "Grootvader", 6));
        RELATION_TYPES.add(new RelationTypeOption("10", "Grootmoeder", 7));
        RELATION_TYPES.add(new RelationTypeOption("7", "Pleegvader", 8));
        RELATION_TYPES.add(new RelationTypeOption("8", "Pleegmoeder", 9));
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
        return RELATION_TYPES.stream()
                .filter(rt -> rt.getId().equals(relationTypeId))
                .map(RelationTypeOption::getName)
                .findFirst()
                .orElse(relationTypeId);
    }
    
    /**
     * Gets all available relation types with their names (legacy method).
     * 
     * @return Map of relation type ID to name
     * @deprecated Use {@link #getAllRelationTypeOptions()} instead for structured data
     */
    @Deprecated
    public Map<String, String> getAllRelationTypes() {
        return RELATION_TYPES.stream()
                .collect(Collectors.toMap(
                        RelationTypeOption::getId,
                        RelationTypeOption::getName,
                        (existing, replacement) -> existing,
                        LinkedHashMap::new
                ));
    }
    
    /**
     * Gets all available relation types as structured RelationTypeOption objects.
     * Types are already sorted by sortOrder.
     * 
     * @return List of RelationTypeOption objects
     */
    public List<RelationTypeOption> getAllRelationTypeOptions() {
        return new ArrayList<>(RELATION_TYPES);
    }
}
