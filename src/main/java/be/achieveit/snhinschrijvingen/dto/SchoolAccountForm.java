package be.achieveit.snhinschrijvingen.dto;

public class SchoolAccountForm {
    
    private String financialSupportRequest; // "J" or "N"
    
    public SchoolAccountForm() {
        // Default to "N" (No)
        this.financialSupportRequest = "N";
    }
    
    // Getters and Setters
    
    public String getFinancialSupportRequest() {
        return financialSupportRequest;
    }
    
    public void setFinancialSupportRequest(String financialSupportRequest) {
        this.financialSupportRequest = financialSupportRequest;
    }
}
