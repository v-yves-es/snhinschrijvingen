package be.achieveit.snhinschrijvingen.dto;

/**
 * Form DTO for the Relations wizard step
 * Handles up to 2 relations (parents/guardians)
 */
public class RelationsForm {
    
    private RelationDTO relation1;
    private RelationDTO relation2;
    private boolean showRelation2;
    
    public RelationsForm() {
        this.relation1 = new RelationDTO();
        this.relation2 = new RelationDTO();
        this.showRelation2 = false;
    }
    
    // Getters and Setters
    
    public RelationDTO getRelation1() {
        return relation1;
    }
    
    public void setRelation1(RelationDTO relation1) {
        this.relation1 = relation1;
    }
    
    public RelationDTO getRelation2() {
        return relation2;
    }
    
    public void setRelation2(RelationDTO relation2) {
        this.relation2 = relation2;
    }
    
    public boolean isShowRelation2() {
        return showRelation2;
    }
    
    public void setShowRelation2(boolean showRelation2) {
        this.showRelation2 = showRelation2;
    }
    
    /**
     * Check if at least one relation is filled
     */
    public boolean hasAtLeastOneRelation() {
        return isRelation1Filled() || isRelation2Filled();
    }
    
    private boolean isRelation1Filled() {
        return relation1 != null && 
               relation1.getRelationType() != null && 
               !relation1.getRelationType().isEmpty();
    }
    
    private boolean isRelation2Filled() {
        return relation2 != null && 
               relation2.getRelationType() != null && 
               !relation2.getRelationType().isEmpty();
    }
}
