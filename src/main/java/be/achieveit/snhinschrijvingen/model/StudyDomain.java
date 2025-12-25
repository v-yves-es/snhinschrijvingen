package be.achieveit.snhinschrijvingen.model;

import jakarta.persistence.*;

@Entity
@Table(name = "study_domains")
public class StudyDomain {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true, length = 10)
    private String code;
    
    @Column(nullable = false, length = 100)
    private String name;
    
    @Column(name = "display_order", nullable = false)
    private Integer displayOrder;
    
    @Column(name = "applicable_from_year", nullable = false)
    private Integer applicableFromYear;

    public StudyDomain() {
    }

    public StudyDomain(String code, String name, Integer displayOrder, Integer applicableFromYear) {
        this.code = code;
        this.name = name;
        this.displayOrder = displayOrder;
        this.applicableFromYear = applicableFromYear;
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

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public Integer getApplicableFromYear() {
        return applicableFromYear;
    }

    public void setApplicableFromYear(Integer applicableFromYear) {
        this.applicableFromYear = applicableFromYear;
    }
}
