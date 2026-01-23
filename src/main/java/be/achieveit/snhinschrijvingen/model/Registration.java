package be.achieveit.snhinschrijvingen.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "registrations")
public class Registration {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String email;

    @Column(name = "email_hash", nullable = false, unique = true)
    private String emailHash;

    @Column(name = "school_year", nullable = false)
    private String schoolYear;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RegistrationStatus status = RegistrationStatus.PENDING;

    @Column(name = "current_step")
    private String currentStep = "EMAIL_VERIFICATION";

    @Enumerated(EnumType.STRING)
    @Column(name = "email_status", nullable = false)
    private EmailStatus emailStatus = EmailStatus.UNVERIFIED;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Student information
    @Column(name = "student_firstname")
    private String studentFirstname;

    @Column(name = "student_lastname")
    private String studentLastname;

    // Student domicile address (embedded)
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "street", column = @Column(name = "student_street")),
        @AttributeOverride(name = "houseNumber", column = @Column(name = "student_house_number")),
        @AttributeOverride(name = "box", column = @Column(name = "student_box")),
        @AttributeOverride(name = "postalCode", column = @Column(name = "student_postal_code")),
        @AttributeOverride(name = "city", column = @Column(name = "student_city")),
        @AttributeOverride(name = "country", column = @Column(name = "student_country"))
    })
    private Address studentAddress;

    @Column(name = "student_gsm")
    private String studentGsm;

    @Column(name = "student_geslacht")
    private String studentGeslacht;

    @Column(name = "student_rijksregisternummer")
    private String studentRijksregisternummer;

    @Column(name = "student_geboortedatum")
    private java.time.LocalDate studentGeboortedatum;

    @Column(name = "student_geboorteplaats")
    private String studentGeboorteplaats;

    @Column(name = "student_nationaliteit")
    private String studentNationaliteit;

    // Study program selection
    @Column(name = "selected_study_year")
    private Integer selectedStudyYear;

    @Column(name = "selected_study_program_id")
    private Long selectedStudyProgramId;

    @Column(name = "study_program_extra_info")
    private String studyProgramExtraInfo;

    // Previous school information
    @Column(name = "vorige_school")
    private String vorigeSchool;

    @Column(name = "vorige_school_anders")
    private String vorigeSchoolAnders;

    @Column(name = "vorige_school_jaar")
    private String vorigeSchoolJaar;

    @Column(name = "richting_vorige_school")
    private String richtingVorigeSchool;

    @Column(name = "toestemming_vorige_school")
    private String toestemmingVorigeSchool;
    
    // Doctor information
    @Column(name = "doctor_name")
    private String doctorName;
    
    @Column(name = "doctor_phone")
    private String doctorPhone;
    
    // Care needs information
    @Column(name = "class_composition_wishes", length = 500)
    private String classCompositionWishes;
    
    @Column(name = "has_care_needs")
    private String hasCareNeeds;
    
    @Column(name = "medical_care_details", length = 1000)
    private String medicalCareDetails;
    
    @Column(name = "receiving_treatment")
    private String receivingTreatment;
    
    @Column(name = "doctor_contact_info", length = 500)
    private String doctorContactInfo;
    
    @Column(name = "takes_medication")
    private String takesMedication;
    
    @Column(name = "school_expectations", length = 1000)
    private String schoolExpectations;
    
    @Column(name = "lesson_influence", length = 1000)
    private String lessonInfluence;
    
    @Column(name = "clb_consult_permission")
    private String clbConsultPermission;
    
    @Column(name = "social_emotional_info", length = 1000)
    private String socialEmotionalInfo;
    
    @Column(name = "learning_difficulties", length = 1000)
    private String learningDifficulties;
    
    @Column(name = "external_support", length = 500)
    private String externalSupport;
    
    // Privacy consents
    @Column(name = "photo_video_consent")
    private String photoVideoConsent;
    
    @Column(name = "study_results_consent")
    private String studyResultsConsent;
    
    @Column(name = "alumni_data_consent")
    private String alumniDataConsent;
    
    @Column(name = "higher_education_consent")
    private String higherEducationConsent;
    
    // Laptop information
    @Column(name = "laptop_brand", length = 200)
    private String laptopBrand;
    
    // School account / financial support
    @Column(name = "bank_account_iban", length = 34)
    private String bankAccountIban;
    
    @Column(name = "bank_account_holder", length = 200)
    private String bankAccountHolder;
    
    @Column(name = "financial_support_request")
    private String financialSupportRequest;
    
    // Submission / signature
    @Column(name = "signature_name", length = 200)
    private String signatureName;
    
    @Column(name = "school_regulation_agreement")
    private String schoolRegulationAgreement;
    
    @Column(name = "additional_info_request")
    private String additionalInfoRequest;
    
    @Column(name = "submission_date")
    private java.time.LocalDateTime submissionDate;
    
    // Relations (one-to-many with Relation entity)
    @OneToMany(mappedBy = "registration", cascade = CascadeType.ALL, orphanRemoval = true)
    private java.util.List<Relation> relations = new java.util.ArrayList<>();

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmailHash() {
        return emailHash;
    }

    public void setEmailHash(String emailHash) {
        this.emailHash = emailHash;
    }

    public String getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(String schoolYear) {
        this.schoolYear = schoolYear;
    }

    public RegistrationStatus getStatus() {
        return status;
    }

    public void setStatus(RegistrationStatus status) {
        this.status = status;
    }

    public String getCurrentStep() {
        return currentStep;
    }

    public void setCurrentStep(String currentStep) {
        this.currentStep = currentStep;
    }

    public EmailStatus getEmailStatus() {
        return emailStatus;
    }

    public void setEmailStatus(EmailStatus emailStatus) {
        this.emailStatus = emailStatus;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getStudentFirstname() {
        return studentFirstname;
    }

    public void setStudentFirstname(String studentFirstname) {
        this.studentFirstname = studentFirstname;
    }

    public String getStudentLastname() {
        return studentLastname;
    }

    public void setStudentLastname(String studentLastname) {
        this.studentLastname = studentLastname;
    }

    public Address getStudentAddress() {
        if (studentAddress == null) {
            studentAddress = new Address();
        }
        return studentAddress;
    }

    public void setStudentAddress(Address studentAddress) {
        this.studentAddress = studentAddress;
    }

    public String getStudentGsm() {
        return studentGsm;
    }

    public void setStudentGsm(String studentGsm) {
        this.studentGsm = studentGsm;
    }

    public String getStudentGeslacht() {
        return studentGeslacht;
    }

    public void setStudentGeslacht(String studentGeslacht) {
        this.studentGeslacht = studentGeslacht;
    }

    public String getStudentRijksregisternummer() {
        return studentRijksregisternummer;
    }

    public void setStudentRijksregisternummer(String studentRijksregisternummer) {
        this.studentRijksregisternummer = studentRijksregisternummer;
    }

    public java.time.LocalDate getStudentGeboortedatum() {
        return studentGeboortedatum;
    }

    public void setStudentGeboortedatum(java.time.LocalDate studentGeboortedatum) {
        this.studentGeboortedatum = studentGeboortedatum;
    }

    public String getStudentGeboorteplaats() {
        return studentGeboorteplaats;
    }

    public void setStudentGeboorteplaats(String studentGeboorteplaats) {
        this.studentGeboorteplaats = studentGeboorteplaats;
    }

    public String getStudentNationaliteit() {
        return studentNationaliteit;
    }

    public void setStudentNationaliteit(String studentNationaliteit) {
        this.studentNationaliteit = studentNationaliteit;
    }

    public Integer getSelectedStudyYear() {
        return selectedStudyYear;
    }

    public void setSelectedStudyYear(Integer selectedStudyYear) {
        this.selectedStudyYear = selectedStudyYear;
    }

    public Long getSelectedStudyProgramId() {
        return selectedStudyProgramId;
    }

    public void setSelectedStudyProgramId(Long selectedStudyProgramId) {
        this.selectedStudyProgramId = selectedStudyProgramId;
    }

    public String getStudyProgramExtraInfo() {
        return studyProgramExtraInfo;
    }

    public void setStudyProgramExtraInfo(String studyProgramExtraInfo) {
        this.studyProgramExtraInfo = studyProgramExtraInfo;
    }

    public String getVorigeSchool() {
        return vorigeSchool;
    }

    public void setVorigeSchool(String vorigeSchool) {
        this.vorigeSchool = vorigeSchool;
    }

    public String getVorigeSchoolAnders() {
        return vorigeSchoolAnders;
    }

    public void setVorigeSchoolAnders(String vorigeSchoolAnders) {
        this.vorigeSchoolAnders = vorigeSchoolAnders;
    }

    public String getVorigeSchoolJaar() {
        return vorigeSchoolJaar;
    }

    public void setVorigeSchoolJaar(String vorigeSchoolJaar) {
        this.vorigeSchoolJaar = vorigeSchoolJaar;
    }

    public String getRichtingVorigeSchool() {
        return richtingVorigeSchool;
    }

    public void setRichtingVorigeSchool(String richtingVorigeSchool) {
        this.richtingVorigeSchool = richtingVorigeSchool;
    }

    public String getToestemmingVorigeSchool() {
        return toestemmingVorigeSchool;
    }

    public void setToestemmingVorigeSchool(String toestemmingVorigeSchool) {
        this.toestemmingVorigeSchool = toestemmingVorigeSchool;
    }
    
    public java.util.List<Relation> getRelations() {
        return relations;
    }
    
    public void setRelations(java.util.List<Relation> relations) {
        this.relations = relations;
    }
    
    public void addRelation(Relation relation) {
        relations.add(relation);
        relation.setRegistration(this);
    }
    
    public void removeRelation(Relation relation) {
        relations.remove(relation);
        relation.setRegistration(null);
    }
    
    public String getDoctorName() {
        return doctorName;
    }
    
    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }
    
    public String getDoctorPhone() {
        return doctorPhone;
    }
    
    public void setDoctorPhone(String doctorPhone) {
        this.doctorPhone = doctorPhone;
    }
    
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
    
    public String getLaptopBrand() {
        return laptopBrand;
    }
    
    public void setLaptopBrand(String laptopBrand) {
        this.laptopBrand = laptopBrand;
    }
    
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
    
    public java.time.LocalDateTime getSubmissionDate() {
        return submissionDate;
    }
    
    public void setSubmissionDate(java.time.LocalDateTime submissionDate) {
        this.submissionDate = submissionDate;
    }
}
