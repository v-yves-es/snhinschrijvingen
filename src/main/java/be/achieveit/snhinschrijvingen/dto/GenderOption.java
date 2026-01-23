package be.achieveit.snhinschrijvingen.dto;

/**
 * DTO representing a gender option for radio buttons.
 */
public class GenderOption {
    private String code;
    private String label;
    private Integer sortOrder;
    
    public GenderOption(String code, String label, Integer sortOrder) {
        this.code = code;
        this.label = label;
        this.sortOrder = sortOrder;
    }
    
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    public String getLabel() {
        return label;
    }
    
    public void setLabel(String label) {
        this.label = label;
    }
    
    public Integer getSortOrder() {
        return sortOrder;
    }
    
    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }
}
