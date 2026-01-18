package be.achieveit.snhinschrijvingen.dto;

public class SubmissionForm {
    
    private String signatureName; // Naam ouder/voogd
    private String schoolRegulationAgreement; // "J" checkbox
    private String additionalInfoRequest; // "J" checkbox (optioneel)
    
    public SubmissionForm() {
    }
    
    // Getters and Setters
    
    public String getSignatureName() {
        return signatureName;
    }
    
    public void setSignatureName(String signatureName) {
        this.signatureName = signatureName;
    }
    
    public String getSchoolRegulationAgreement() {
        return schoolRegulationAgreement;
    }
    
    public void setSchoolRegulationAgreement(String schoolRegulationAgreement) {
        this.schoolRegulationAgreement = schoolRegulationAgreement;
    }
    
    public String getAdditionalInfoRequest() {
        return additionalInfoRequest;
    }
    
    public void setAdditionalInfoRequest(String additionalInfoRequest) {
        this.additionalInfoRequest = additionalInfoRequest;
    }
}
