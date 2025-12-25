package be.achieveit.snhinschrijvingen.model;

import jakarta.persistence.*;

@Entity
@Table(name = "nationalities")
public class Nationality {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String code; // e.g., "BE", "NL", "FR"

    @Column(nullable = false, length = 255)
    private String nameNl; // Dutch name

    @Column(length = 255)
    private String nameFr; // French name

    @Column(length = 255)
    private String nameEn; // English name

    @Column(name = "display_order")
    private Integer displayOrder = 999;

    public Nationality() {
    }

    public Nationality(String code, String nameNl) {
        this.code = code;
        this.nameNl = nameNl;
    }

    // Getters and Setters
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

    public String getNameNl() {
        return nameNl;
    }

    public void setNameNl(String nameNl) {
        this.nameNl = nameNl;
    }

    public String getNameFr() {
        return nameFr;
    }

    public void setNameFr(String nameFr) {
        this.nameFr = nameFr;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }
}
