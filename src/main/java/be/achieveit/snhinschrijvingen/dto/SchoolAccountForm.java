package be.achieveit.snhinschrijvingen.dto;

public class SchoolAccountForm {
    
    private String bankAccountIban;
    private String bankAccountHolder;
    private String financialSupportRequest; // "J" or "N"
    
    public SchoolAccountForm() {
        // Default to "N" (No)
        this.financialSupportRequest = "N";
    }
    
    // Getters and Setters
    
    public String getBankAccountIban() {
        return bankAccountIban;
    }
    
    public void setBankAccountIban(String bankAccountIban) {
        this.bankAccountIban = bankAccountIban;
    }
    
    public String getBankAccountHolder() {
        return bankAccountHolder;
    }
    
    public void setBankAccountHolder(String bankAccountHolder) {
        this.bankAccountHolder = bankAccountHolder;
    }
    
    public String getFinancialSupportRequest() {
        return financialSupportRequest;
    }
    
    public void setFinancialSupportRequest(String financialSupportRequest) {
        this.financialSupportRequest = financialSupportRequest;
    }
}
