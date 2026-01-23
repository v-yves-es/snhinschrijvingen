package be.achieveit.snhinschrijvingen.dto;

/**
 * DTO representing a relation type option for dropdowns.
 */
public class RelationTypeOption {
    private String id;
    private String name;
    private Integer sortOrder;
    
    public RelationTypeOption(String id, String name, Integer sortOrder) {
        this.id = id;
        this.name = name;
        this.sortOrder = sortOrder;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Integer getSortOrder() {
        return sortOrder;
    }
    
    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }
}
