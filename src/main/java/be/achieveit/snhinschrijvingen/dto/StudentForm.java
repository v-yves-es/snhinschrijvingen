package be.achieveit.snhinschrijvingen.dto;

import be.achieveit.snhinschrijvingen.model.Address;

import java.time.LocalDate;

public class StudentForm {
    private String voornaamLeerling;
    private String naamLeerling;
    private Address address;
    private String gsm;
    private String geslacht;
    private String rijksregisternummer;
    private LocalDate geboortedatum;
    private String geboorteplaats;
    private String nationaliteit;
    
    // Vorige school fields
    private String vorigeSchoolAdres;
    private String vorigeSchoolAdresAnders;
    private String vorigeSchoolJaar;
    private String vorigeSchoolJaarAnders;
    private String richtingVorigeSchool;
    private String toestemmingVorigeSchool;

    public StudentForm() {
        this.address = new Address();
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
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

    public String getVorigeSchoolAdres() {
        return vorigeSchoolAdres;
    }

    public void setVorigeSchoolAdres(String vorigeSchoolAdres) {
        this.vorigeSchoolAdres = vorigeSchoolAdres;
    }

    public String getVorigeSchoolAdresAnders() {
        return vorigeSchoolAdresAnders;
    }

    public void setVorigeSchoolAdresAnders(String vorigeSchoolAdresAnders) {
        this.vorigeSchoolAdresAnders = vorigeSchoolAdresAnders;
    }

    public String getVorigeSchoolJaar() {
        return vorigeSchoolJaar;
    }

    public void setVorigeSchoolJaar(String vorigeSchoolJaar) {
        this.vorigeSchoolJaar = vorigeSchoolJaar;
    }

    public String getVorigeSchoolJaarAnders() {
        return vorigeSchoolJaarAnders;
    }

    public void setVorigeSchoolJaarAnders(String vorigeSchoolJaarAnders) {
        this.vorigeSchoolJaarAnders = vorigeSchoolJaarAnders;
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
