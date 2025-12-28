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

    @Column(name = "student_adres")
    private String studentAdres;

    @Column(name = "student_postnummer")
    private String studentPostnummer;

    @Column(name = "student_gemeente")
    private String studentGemeente;

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

    public String getStudentAdres() {
        return studentAdres;
    }

    public void setStudentAdres(String studentAdres) {
        this.studentAdres = studentAdres;
    }

    public String getStudentPostnummer() {
        return studentPostnummer;
    }

    public void setStudentPostnummer(String studentPostnummer) {
        this.studentPostnummer = studentPostnummer;
    }

    public String getStudentGemeente() {
        return studentGemeente;
    }

    public void setStudentGemeente(String studentGemeente) {
        this.studentGemeente = studentGemeente;
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
}
