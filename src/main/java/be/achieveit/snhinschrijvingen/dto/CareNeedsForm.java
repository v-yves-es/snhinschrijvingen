package be.achieveit.snhinschrijvingen.dto;

public class CareNeedsForm {
    
    // Wensen klassamenstelling
    private String classCompositionWishes;
    
    // Bijzondere zorgvraag
    private String hasCareNeeds; // "N" or "J"
    
    // Medische zorg
    private String medicalCareDetails;
    private String receivingTreatment; // "J" or "N"
    private String doctorContactInfo;
    private String takesMedication; // "J" or "N"
    private String schoolExpectations;
    private String lessonInfluence;
    private String clbConsultPermission; // "J" or "N"
    
    // Sociaal-emotioneel
    private String socialEmotionalInfo;
    
    // Leren en studeren
    private String learningDifficulties;
    
    // Externe ondersteuning
    private String externalSupport;
    
    public CareNeedsForm() {
        this.hasCareNeeds = "N"; // Default to no care needs
    }
    
    // Getters and Setters
    
    public String getClassCompositionWishes() {
        return classCompositionWishes;
    }
    
    public void setClassCompositionWishes(String classCompositionWishes) {
        this.classCompositionWishes = classCompositionWishes;
    }
    
    public String getHasCareNeeds() {
        return hasCareNeeds;
    }
    
    public void setHasCareNeeds(String hasCareNeeds) {
        this.hasCareNeeds = hasCareNeeds;
    }
    
    public String getMedicalCareDetails() {
        return medicalCareDetails;
    }
    
    public void setMedicalCareDetails(String medicalCareDetails) {
        this.medicalCareDetails = medicalCareDetails;
    }
    
    public String getReceivingTreatment() {
        return receivingTreatment;
    }
    
    public void setReceivingTreatment(String receivingTreatment) {
        this.receivingTreatment = receivingTreatment;
    }
    
    public String getDoctorContactInfo() {
        return doctorContactInfo;
    }
    
    public void setDoctorContactInfo(String doctorContactInfo) {
        this.doctorContactInfo = doctorContactInfo;
    }
    
    public String getTakesMedication() {
        return takesMedication;
    }
    
    public void setTakesMedication(String takesMedication) {
        this.takesMedication = takesMedication;
    }
    
    public String getSchoolExpectations() {
        return schoolExpectations;
    }
    
    public void setSchoolExpectations(String schoolExpectations) {
        this.schoolExpectations = schoolExpectations;
    }
    
    public String getLessonInfluence() {
        return lessonInfluence;
    }
    
    public void setLessonInfluence(String lessonInfluence) {
        this.lessonInfluence = lessonInfluence;
    }
    
    public String getClbConsultPermission() {
        return clbConsultPermission;
    }
    
    public void setClbConsultPermission(String clbConsultPermission) {
        this.clbConsultPermission = clbConsultPermission;
    }
    
    public String getSocialEmotionalInfo() {
        return socialEmotionalInfo;
    }
    
    public void setSocialEmotionalInfo(String socialEmotionalInfo) {
        this.socialEmotionalInfo = socialEmotionalInfo;
    }
    
    public String getLearningDifficulties() {
        return learningDifficulties;
    }
    
    public void setLearningDifficulties(String learningDifficulties) {
        this.learningDifficulties = learningDifficulties;
    }
    
    public String getExternalSupport() {
        return externalSupport;
    }
    
    public void setExternalSupport(String externalSupport) {
        this.externalSupport = externalSupport;
    }
}
