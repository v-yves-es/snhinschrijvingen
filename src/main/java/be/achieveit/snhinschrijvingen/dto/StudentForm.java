package be.achieveit.snhinschrijvingen.dto;

import java.time.LocalDate;

public class StudentForm {
    private String voornaamLeerling;
    private String naamLeerling;
    private String adres;
    private String postnummer;
    private String gemeente;
    private String gsm;
    private String geslacht;
    private String rijksregisternummer;
    private LocalDate geboortedatum;
    private String geboorteplaats;
    private String nationaliteit;

    public StudentForm() {
    }

    public String getVoornaamLeerling() {
        return voornaamLeerling;
    }

    public void setVoornaamLeerling(String voornaamLeerling) {
        this.voornaamLeerling = voornaamLeerling;
    }

    public String getNaamLeerling() {
        return naamLeerling;
    }

    public void setNaamLeerling(String naamLeerling) {
        this.naamLeerling = naamLeerling;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public String getPostnummer() {
        return postnummer;
    }

    public void setPostnummer(String postnummer) {
        this.postnummer = postnummer;
    }

    public String getGemeente() {
        return gemeente;
    }

    public void setGemeente(String gemeente) {
        this.gemeente = gemeente;
    }

    public String getGsm() {
        return gsm;
    }

    public void setGsm(String gsm) {
        this.gsm = gsm;
    }

    public String getGeslacht() {
        return geslacht;
    }

    public void setGeslacht(String geslacht) {
        this.geslacht = geslacht;
    }

    public String getRijksregisternummer() {
        return rijksregisternummer;
    }

    public void setRijksregisternummer(String rijksregisternummer) {
        this.rijksregisternummer = rijksregisternummer;
    }

    public LocalDate getGeboortedatum() {
        return geboortedatum;
    }

    public void setGeboortedatum(LocalDate geboortedatum) {
        this.geboortedatum = geboortedatum;
    }

    public String getGeboorteplaats() {
        return geboorteplaats;
    }

    public void setGeboorteplaats(String geboorteplaats) {
        this.geboorteplaats = geboorteplaats;
    }

    public String getNationaliteit() {
        return nationaliteit;
    }

    public void setNationaliteit(String nationaliteit) {
        this.nationaliteit = nationaliteit;
    }
}
