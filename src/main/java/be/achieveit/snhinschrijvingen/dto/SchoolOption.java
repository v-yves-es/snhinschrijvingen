package be.achieveit.snhinschrijvingen.dto;

/**
 * DTO representing a school option for dropdowns.
 */
public class SchoolOption {
    private String id;
    private String name;
    private String category;
    
    public SchoolOption(String id, String name, String category) {
        this.id = id;
        this.name = name;
        this.category = category;
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
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
}
