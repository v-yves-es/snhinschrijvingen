package be.achieveit.snhinschrijvingen.dto;

public class PrivacyForm {
    
    private String photoVideoConsent; // "J" or "N"
    private String studyResultsConsent; // "J" or "N"
    private String alumniDataConsent; // "J" or "N"
    private String higherEducationConsent; // "J" or "N"
    
    public PrivacyForm() {
        // Default to "J" (Yes) for all consents
        this.photoVideoConsent = "J";
        this.studyResultsConsent = "J";
        this.alumniDataConsent = "J";
        this.higherEducationConsent = "J";
    }
    
    // Getters and Setters
    
    public String getPhotoVideoConsent() {
        return photoVideoConsent;
    }
    
    public void setPhotoVideoConsent(String photoVideoConsent) {
        this.photoVideoConsent = photoVideoConsent;
    }
    
    public String getStudyResultsConsent() {
        return studyResultsConsent;
    }
    
    public void setStudyResultsConsent(String studyResultsConsent) {
        this.studyResultsConsent = studyResultsConsent;
    }
    
    public String getAlumniDataConsent() {
        return alumniDataConsent;
    }
    
    public void setAlumniDataConsent(String alumniDataConsent) {
        this.alumniDataConsent = alumniDataConsent;
    }
    
    public String getHigherEducationConsent() {
        return higherEducationConsent;
    }
    
    public void setHigherEducationConsent(String higherEducationConsent) {
        this.higherEducationConsent = higherEducationConsent;
    }
}
