package be.achieveit.snhinschrijvingen.model;

import jakarta.persistence.*;

@Entity
@Table(name = "study_programs")
public class StudyProgram {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true, length = 20)
    private String code;
    
    @Column(nullable = false, length = 200)
    private String name;
    
    @Column(name = "study_year", nullable = false)
    private Integer year;
    
    @ManyToOne
    @JoinColumn(name = "domain_id")
    private StudyDomain domain;
    
    @ManyToOne
    @JoinColumn(name = "orientation_id")
    private StudyOrientation orientation;
    
    @Column(name = "display_order", nullable = false)
    private Integer displayOrder;
    
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    public StudyProgram() {
    }

    public StudyProgram(String code, String name, Integer year, StudyDomain domain, 
                       StudyOrientation orientation, Integer displayOrder) {
        this.code = code;
        this.name = name;
        this.year = year;
        this.domain = domain;
        this.orientation = orientation;
        this.displayOrder = displayOrder;
        this.isActive = true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public StudyDomain getDomain() {
        return domain;
    }

    public void setDomain(StudyDomain domain) {
        this.domain = domain;
    }

    public StudyOrientation getOrientation() {
        return orientation;
    }

    public void setOrientation(StudyOrientation orientation) {
        this.orientation = orientation;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
}
