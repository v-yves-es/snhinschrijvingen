# SNH Inschrijvingen - TODO List

**Laatst bijgewerkt:** 18 januari 2026 17:12  
**Status:** Fase 2 - VOLTOOID! Alle 10 wizard stappen geïmplementeerd + Critical bugfixes

---

## 📋 Inhoudsopgave

1. [Prioriteiten Overzicht](#prioriteiten-overzicht)
2. [Fase 2: Wizard Voltooiing](#fase-2-wizard-voltooiing)
3. [Fase 3: Email Integratie](#fase-3-email-integratie)
4. [Fase 4: Validatie & Error Handling](#fase-4-validatie--error-handling)
5. [Fase 5: Testing](#fase-5-testing)
6. [Fase 6: Security](#fase-6-security)
7. [Fase 7: Production Ready](#fase-7-production-ready)
8. [Nice to Have Features](#nice-to-have-features)
9. [Technical Debt](#technical-debt)

---

## Prioriteiten Overzicht

### 🔴 Hoge Prioriteit (Must Have)
- [x] Wizard stappen 1-10 implementeren - **VOLTOOID!**
- [x] URL's vertalen naar Nederlands - **VOLTOOID!**
- [x] Path variables ipv query parameters - **VOLTOOID!**
- [x] URL encoding bugfixes - **VOLTOOID!**
- [x] Field name consistency - **VOLTOOID!**
- [ ] Email integratie (echte verzending)
- [ ] Server-side validatie
- [ ] Error handling & logging
- [ ] Production database setup

### 🟡 Gemiddelde Prioriteit (Should Have)
- [ ] Unit tests
- [ ] Integration tests
- [ ] Admin dashboard
- [ ] Email templates (HTML)
- [ ] PDF generatie (bevestiging)

### 🟢 Lage Prioriteit (Nice to Have)
- [ ] Multi-language support
- [ ] Dark mode
- [ ] File upload (documenten)
- [ ] Email notificaties naar school
- [ ] Analytics dashboard

---

## Fase 2: Wizard Voltooiing

### 🐛 Critical Bugfixes - 18 januari 2026 17:12

**Opgelost tijdens sessie:**
- [x] **Email verificatie link fout:** Redirect naar oude URL structuur gefixed
  - `RegistrationController.verifyEmail()`: `student-info?id=` → `leerling-info/{id}`
  - `RegistrationController.continueRegistration()`: Alle 10 stappen toegevoegd aan switch statement
- [x] **URL encoding probleem:** `${registrationId}` → `__${registrationId}__` in 10 templates
  - Alle form actions gefixed met correcte Thymeleaf syntax
  - Alle navigation fragment calls gefixed
  - Alle href links gefixed (study-program, submission)
- [x] **Null registration ID:** PreviousSchoolController gebruikte `id` ipv `registrationId`
  - Model attribute naam gecorrigeerd naar `registrationId`
  - Navigation link syntax gefixed naar `__${registrationId}__`
- [x] **Field name mismatch in templates:** submission.html en confirmation.html
  - `voornaam` → `studentFirstname`
  - `naam` → `studentLastname`
  - `geboortedatum` → `studentGeboortedatum`
  - `geslacht` → `studentGeslacht`
  - `rijksregisternummer` → `studentRijksregisternummer`
  - `nationaliteit` → `studentNationaliteit`
  - `gsmNummer`/`thuistaal` → `studentGsm`
  - Null-safe date formatting toegevoegd
  - Dubbel GSM veld verwijderd

**Impact:** Volledige wizard flow werkt nu correct van start tot bevestiging!

---

### ✅ Stap 1: Algemeen (Student Info) - VOLTOOID

**Status:** ✅ Completed  
**URL:** `/inschrijving/leerling-info/{id}`

**Geïmplementeerd:**
- [x] StudentForm DTO aangemaakt
- [x] Controller endpoint met path variable
- [x] Template: `student-info.html`
- [x] Form sectie: Leerling (voornaam, naam, rijksregisternummer, geboortedatum, geboorteplaats, geslacht, nationaliteit)
- [x] Form sectie: Domicilieadres (Address object hergebruik)
- [x] Registration entity uitgebreid met Address embedded object
- [x] Service methods: `updateStudentInfo()`
- [x] Client-side validatie
- [x] Flatpickr datepicker integratie (Nederlands)
- [x] Pre-fill bij herladen
- [x] "Kopieer domicilieadres" knop bij relaties

---

### ✅ Stap 2: Studierichting (Study Program) - VOLTOOID

**Status:** ✅ Completed  
**URL:** `/inschrijving/studierichting/{id}`  
**AJAX Endpoint:** `/inschrijving/studierichting/programmas`

**Geïmplementeerd:**
- [x] StudyProgramForm DTO aangemaakt
- [x] Controller endpoints met path variable
- [x] Template: `study-program.html`
- [x] Database entities: StudyDomain, StudyOrientation, StudyProgram
- [x] Services: StudyProgramService, StudyDomainService, StudyOrientationService
- [x] Select2 jaar dropdown (1-6)
- [x] Dynamische richtingen weergave per jaar (AJAX)
- [x] Jaar 1-2: Platte lijst met radiobuttons
- [x] Jaar 3-6: Gegroepeerd per domein/oriëntatie met kleurcodering
- [x] Data initialisatie (100+ studierichtingen)
- [x] Extra info textarea bij twijfel
- [x] Nederlandse URL

---

### ✅ Stap 3: Vorige School (Previous School) - VOLTOOID

**Status:** ✅ Completed  
**URL:** `/inschrijving/vorige-school/{id}`

**Geïmplementeerd:**
- [x] PreviousSchoolForm DTO aangemaakt
- [x] Controller endpoint met path variable
- [x] Template: `previous-school.html`
- [x] Select2 dropdown met zoekfunctie
- [x] Gegroepeerde schoollijst (17 basis + 5 secundair)
- [x] "Anders" functionaliteit met dynamisch tekstveld
- [x] Jaar vorige school (radio buttons)
- [x] Gevolgde richting (text input)
- [x] Toestemming gegevensuitwisseling (ja/nee)
- [x] Nederlandse URL

---

### ✅ Stap 4: Relaties (Relations) - VOLTOOID

**Status:** ✅ Completed  
**URL:** `/inschrijving/relaties/{id}`

**Geïmplementeerd:**
- [x] RelationForm DTO met Address objects
- [x] Relation entity (JPA one-to-many)
- [x] Controller endpoint met path variable
- [x] Template: `relations.html`
- [x] Select2 dropdown voor relatietype
- [x] 9 relatietypes (vader, moeder, plus-, voogd, groot-, pleeg-)
- [x] Minimaal 1, maximaal 2 relaties
- [x] Dynamisch toevoegen/verwijderen tweede relatie
- [x] "Kopieer domicilieadres" functionaliteit
- [x] Volledig gevalideerd (incl. verborgen velden skippen)
- [x] Nederlandse URL

---

### ✅ Stap 5: Huisarts (Doctor) - VOLTOOID

**Status:** ✅ Completed  
**URL:** `/inschrijving/huisarts/{id}`

**Geïmplementeerd:**
- [x] DoctorForm DTO aangemaakt
- [x] Controller endpoint met path variable
- [x] Template: `doctor.html`
- [x] Naam huisarts (text input, verplicht)
- [x] Telefoonnummer (tel input, optioneel)
- [x] Registration entity uitgebreid (doctorName, doctorPhone)
- [x] Client-side validatie
- [x] Pre-fill bij herladen
- [x] Nederlandse URL

---

### ✅ Stap 6: Zorgvraag (Care Needs) - VOLTOOID

**Status:** ✅ Completed  
**URL:** `/inschrijving/zorgvraag/{id}`

**Geïmplementeerd:**
- [x] CareNeedsForm DTO aangemaakt
- [x] Controller endpoint met path variable
- [x] Template: `care-needs.html`
- [x] Wensen klassamenstelling (text input)
- [x] Bijzondere zorgvraag (J/N radio buttons)
- [x] Conditionele sectie bij "WEL zorgvraag":
  - [x] Privacyverklaring (notice box)
  - [x] Medische zorg (7 vragen)
  - [x] Sociaal-emotioneel functioneren
  - [x] Leren en studeren
  - [x] Externe ondersteuning
- [x] Registration entity uitgebreid (11 velden)
- [x] JavaScript toggle voor conditionele zichtbaarheid
- [x] Nederlandse URL

---

### ✅ Stap 7: Privacy (Privacy Consents) - VOLTOOID

**Status:** ✅ Completed  
**URL:** `/inschrijving/privacy/{id}`

**Geïmplementeerd:**
- [x] PrivacyForm DTO aangemaakt
- [x] Controller endpoint met path variable
- [x] Template: `privacy.html`
- [x] 4 toestemmingsvragen (radio buttons, verplicht):
  - [x] Foto's/filmfragmenten
  - [x] Studieresultaten vorige school
  - [x] Oud-leerling gegevens bewaren
  - [x] Gegevens hoger onderwijs
- [x] Registration entity uitgebreid (4 velden)
- [x] Client-side validatie (required)
- [x] Nederlandse URL

---

### ✅ Stap 8: Laptop - VOLTOOID

**Status:** ✅ Completed  
**URL:** `/inschrijving/laptop/{id}`

**Geïmplementeerd:**
- [x] LaptopForm DTO aangemaakt
- [x] Controller endpoint met path variable
- [x] Template: `laptop.html`
- [x] Signpost laptop merk (text input, optioneel)
- [x] Laptopproject informatie box met:
  - [x] Info voor leerlingen zonder laptop
  - [x] Prijsinformatie (30 + 3x50 euro)
  - [x] Eigendomsregeling
  - [x] Voordelen schoollaptops (6 bullets)
  - [x] PDF download link
- [x] Registration entity uitgebreid (laptopBrand)
- [x] Nederlandse URL

---

### ✅ Stap 9: Schoolrekening (School Account) - VOLTOOID

**Status:** ✅ Completed  
**URL:** `/inschrijving/schoolrekening/{id}`

**Geïmplementeerd:**
- [x] SchoolAccountForm DTO aangemaakt
- [x] Controller endpoint met path variable
- [x] Template: `school-account.html`
- [x] Financieel gesprek gewenst (J/N radio buttons, verplicht)
- [x] Uitleg over financiële problemen (text-muted)
- [x] Registration entity uitgebreid (financialSupportRequest)
- [x] Client-side validatie
- [x] Nederlandse URL

---

### ✅ Stap 10: Verzenden (Submission & Confirmation) - VOLTOOID

**Status:** ✅ Completed  
**URLs:**  
- `/inschrijving/verzenden/{id}` (overzicht + ondertekening)
- `/inschrijving/bevestiging/{id}` (confirmation page)

**Geïmplementeerd:**
- [x] SubmissionForm DTO aangemaakt (3 velden)
- [x] SubmissionController met 3 endpoints
- [x] Template: `submission.html`
  - [x] Read-only overzicht ALLE ingevoerde data (9 secties)
  - [x] Summary styling (gray boxes met blue border)
  - [x] Ondertekening sectie met juridische tekst
  - [x] Naam ouder/voogd (text input, verplicht)
  - [x] Schoolreglement akkoord (checkbox, verplicht)
  - [x] Bijkomende info (checkbox, optioneel)
  - [x] "Inschrijving voltooien" button
- [x] Template: `confirmation.html`
  - [x] Geen wizard steps
  - [x] Success indicator (groene checkmark)
  - [x] Email bevestigingsmelding
  - [x] Ingediend op datum/tijd
  - [x] Inschrijvingsnummer (UUID)
  - [x] Read-only samenvatting belangrijkste data
  - [x] "Terug naar home" button
- [x] Registration entity uitgebreid:
  - [x] signatureName
  - [x] schoolRegulationAgreement
  - [x] additionalInfoRequest
  - [x] submissionDate (auto-filled)
- [x] CurrentStep = "SUBMITTED"
- [x] Nederlandse URLs

---

## Fase 2 SAMENVATTING - ✅ VOLLEDIG VOLTOOID!

**Alle 10 wizard stappen zijn geïmplementeerd:**

1. ✅ Leerling info (student-info)
2. ✅ Studierichting (study-program)
3. ✅ Vorige school (previous-school)
4. ✅ Relaties (relations)
5. ✅ Huisarts (doctor)
6. ✅ Zorgvraag (care-needs)
7. ✅ Privacy (privacy)
8. ✅ Laptop (laptop)
9. ✅ Schoolrekening (school-account)
10. ✅ Verzenden + Bevestiging (submission + confirmation)

**Additionele verbeteringen:**
- [x] Alle URLs vertaald naar Nederlands
- [x] Query parameters vervangen door path variables
- [x] Validatie verbeterd (verborgen velden skippen)
- [x] Address refactoring (embedded object)
- [x] Kopieer domicilieadres functionaliteit
- [x] Conditionele zorgvraag sectie
- [x] Complete read-only overview bij submission
- [x] Professional confirmation page

---
- [x] Minimum 1 relatie verplicht voor doorgaan naar volgende stap
- [x] Wizard navigatie (vorige/volgende)
- [x] Client-side validatie op alle velden
- [x] Pre-fill bij herladen
- [x] Responsive layout (Bootstrap grid)

**Herbruikbare Componenten:**
- [x] Address fragment (`fragments/address-fields.html`)
  - Unified single version (geen simple/detailed meer)
  - Gebruikt door student-info EN relations
  - Aparte velden: Straat (8 col) + Nummer (4 col)
  - Postcode (4 col) + Gemeente (8 col)
  - Land (standaard: België)
  - Validatie: postcode (4 cijfers), alle velden verplicht
- [x] Address model class (`model/Address.java`)
  - POJO met fields: street, houseNumber, postalCode, city, country
  - Gebruikt in StudentForm en RelationForm

**JavaScript Validatie:**
- [x] Dynamische validatie voor tweede relatie (form-validation.js)
- [x] Event listeners voor toevoegen/verwijderen relatie
- [x] Select2 validatie integratie
- [x] Radio button validatie (indien nodig)

**Database & Persistence:**
- [x] Address class in model package
- [x] StudentForm aangepast naar Address object (ipv aparte velden)
- [x] Controller mapping voor address conversie (Registration ↔ StudentForm)
- [ ] RelationForm persistence (nog te implementeren in database)
- [ ] Registration entity uitbreiden met relaties

---

### Stap 5: Contactgegevens Ouders (Uit originele planning - kan herordenen)

**Prioriteit:** 🔴 Hoog  
**Status:** ❌ Not Started

**Opmerking:** Adresgegevens zijn nu verplaatst naar stap 1 (Algemeen/Student Info)

---

### Stap 6: Huisarts (Uit originele planning - kan herordenen)

**Prioriteit:** 🔴 Hoog  
**Status:** ❌ Not Started

**Te implementeren:**
- [ ] DoctorForm DTO aanmaken
- [ ] Controller endpoint: `/inschrijving/huisarts`
- [ ] Template: `huisarts.html`
- [ ] Velden:
  - Naam huisarts
  - Praktijk naam (optioneel)
  - Telefoon
  - Adres (straat, nr, postcode, gemeente)
- [ ] Doctor entity aanmaken (relatie met Registration)
- [ ] Service methods voor doctor management
- [ ] Validatie:
  - Alle velden verplicht
  - Telefoon format

**Database Changes:**
```sql
CREATE TABLE doctors (
    id UUID PRIMARY KEY,
    registration_id UUID NOT NULL,
    name VARCHAR(255),
    practice_name VARCHAR(255),
    phone VARCHAR(50),
    street VARCHAR(255),
    house_number VARCHAR(50),
    postal_code VARCHAR(10),
    city VARCHAR(255),
    FOREIGN KEY (registration_id) REFERENCES registrations(id)
);
```

**Afhankelijkheden:**
- Ouders (stap 4) moet voltooid zijn (of herbepalen volgorde)

---

### Stap 7: Medische Informatie (Uit originele planning - kan herordenen)

**Prioriteit:** 🔴 Hoog  
**Status:** ❌ Not Started

**Te implementeren:**
- [ ] MedicalInfoForm DTO aanmaken
- [ ] Controller endpoint: `/inschrijving/medisch`
- [ ] Template: `medisch.html`
- [ ] Velden:
  - Medicatie (ja/nee radio)
    - Indien ja: welke medicatie (textarea)
  - Allergieën (ja/nee radio)
    - Indien ja: welke allergieën (textarea)
  - Dieet restricties (ja/nee radio)
    - Indien ja: welke restricties (textarea)
  - Bijzonderheden (textarea, optioneel)
  - Toestemming foto's (ja/nee checkbox)
  - Toestemming uitstappen (ja/nee checkbox)
- [ ] MedicalInfo entity aanmaken
- [ ] Service methods voor medical info
- [ ] Conditional fields (show/hide based on radio)

**Database Changes:**
```sql
CREATE TABLE medical_info (
    id UUID PRIMARY KEY,
    registration_id UUID NOT NULL,
    has_medication BOOLEAN,
    medication_details TEXT,
    has_allergies BOOLEAN,
    allergy_details TEXT,
    has_diet_restrictions BOOLEAN,
    diet_details TEXT,
    additional_info TEXT,
    photo_permission BOOLEAN,
    trip_permission BOOLEAN,
    FOREIGN KEY (registration_id) REFERENCES registrations(id)
);
```

**JavaScript:**
```javascript
// Conditional field display
document.querySelectorAll('input[type="radio"]').forEach(radio => {
    radio.addEventListener('change', function() {
        const detailField = this.closest('.form-group').querySelector('.detail-field');
        detailField.style.display = this.value === 'yes' ? 'block' : 'none';
    });
});
```

---

### Stap 7: School Info & Voorkeuren (Uit originele planning - kan herordenen)

**Prioriteit:** 🔴 Hoog  
**Status:** ❌ Not Started

**Te implementeren:**
- [ ] SchoolPreferencesForm DTO aanmaken
- [ ] Controller endpoint: `/inschrijving/voorkeuren`
- [ ] Template: `voorkeuren.html`
- [ ] Velden:
  - Vorige school (indien van toepassing)
  - Gewenst studierichtingen (dropdown/multiselect)
  - Taal thuissituatie (dropdown)
  - Broers/zussen op school (ja/nee)
    - Indien ja: naam + klas
  - Bijzondere voorkeuren (textarea)
  - Hoe van school gehoord (dropdown)
- [ ] SchoolPreferences entity aanmaken
- [ ] Service methods
- [ ] Studierichtingen configuratie (database of config file)

**Database Changes:**
```sql
CREATE TABLE school_preferences (
    id UUID PRIMARY KEY,
    registration_id UUID NOT NULL,
    previous_school VARCHAR(255),
    preferred_direction VARCHAR(255),
    home_language VARCHAR(100),
    has_siblings BOOLEAN,
    siblings_info TEXT,
    special_preferences TEXT,
    how_heard VARCHAR(255),
    FOREIGN KEY (registration_id) REFERENCES registrations(id)
);
```

---

### Stap 8: Overzicht & Bevestiging (Uit originele planning)

**Prioriteit:** 🔴 Hoog  
**Status:** ❌ Not Started

**Te implementeren:**
- [ ] Controller endpoint: `/inschrijving/overzicht`
- [ ] Template: `overzicht.html`
- [ ] Overzicht tonen van ALLE ingevoerde gegevens:
  - Student info (stap 1: Algemeen)
  - Study program (stap 2: Richting)
  - ... (overige stappen)
- [ ] "Bewerken" links bij elke sectie
- [ ] Checkbox: "Ik bevestig dat de gegevens correct zijn"
- [ ] Button: "Inschrijving voltooien"
- [ ] POST endpoint: `/inschrijving/voltooien`
- [ ] Status update: PENDING → COMPLETED
- [ ] Redirect naar bevestigingspagina

**Template Structuur:**
```html
<div class="overview-section">
    <h3>Student Info</h3>
    <div class="info-grid">
        <div class="info-item">
            <label>Naam:</label>
            <span th:text="${registration.studentFirstname + ' ' + registration.studentLastname}">
        </div>
        <!-- More fields... -->
    </div>
    <a th:href="@{/inschrijving/student-info(id=${registration.id})}" class="btn btn--outline">
        ✏️ Bewerken
    </a>
</div>
```

**Service Method:**
```java
@Transactional
public void completeRegistration(UUID id) {
    Registration registration = findById(id)
        .orElseThrow(() -> new RegistrationNotFoundException(id));
    
    registration.setStatus(RegistrationStatus.COMPLETED);
    registration.setCurrentStep("COMPLETED");
    
    registrationRepository.save(registration);
    
    logger.info("Registration completed: {}", id);
}
```

---

### Bevestigingspagina

**Prioriteit:** 🔴 Hoog  
**Status:** ❌ Not Started

**Te implementeren:**
- [ ] Controller endpoint: `/inschrijving/bevestiging/{id}`
- [ ] Template: `bevestiging.html`
- [ ] Succes bericht met confetti animatie
- [ ] Referentienummer tonen (registration ID)
- [ ] Datum van inschrijving
- [ ] Volgende stappen uitleggen
- [ ] Download PDF button (fase 2)
- [ ] Email bevestiging (automatisch verzonden)

**Template:**
```html
<div class="success-container">
    <div class="success-icon">✅</div>
    <h1>Inschrijving Voltooid!</h1>
    <p class="success-message">
        Uw inschrijving is succesvol ontvangen.
    </p>
    
    <div class="reference-box">
        <label>Referentienummer:</label>
        <strong th:text="${registration.id}">REF-123456</strong>
    </div>
    
    <div class="next-steps">
        <h3>Volgende stappen:</h3>
        <ol>
            <li>U ontvangt een bevestigingsmail</li>
            <li>De school neemt contact op binnen 5 werkdagen</li>
            <li>Bewaar uw referentienummer</li>
        </ol>
    </div>
    
    <button class="btn btn--primary" onclick="window.print()">
        📄 Afdrukken
    </button>
</div>
```

---

## Fase 3: Email Integratie

### Email Service Implementatie

**Prioriteit:** 🔴 Hoog  
**Status:** ❌ Not Started

**Te implementeren:**
- [ ] JavaMailSender dependency toevoegen aan pom.xml
- [ ] Email configuratie in application.properties
- [ ] EmailService interface aanmaken
- [ ] EmailServiceImpl implementatie
- [ ] HTML email templates met Thymeleaf
- [ ] Error handling voor email failures

**Dependencies:**
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-mail</artifactId>
</dependency>
```

**Configuration:**
```properties
# Email Configuration
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=${EMAIL_USERNAME}
spring.mail.password=${EMAIL_PASSWORD}
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
spring.mail.properties.mail.smtp.starttls.required=true

# Email sender
app.email.from=noreply@snh.be
app.email.from-name=SPES Nostra Heule
```

**Service Interface:**
```java
public interface EmailService {
    void sendVerificationEmail(String to, String verificationLink);
    void sendConfirmationEmail(Registration registration);
    void sendNotificationToSchool(Registration registration);
}
```

---

### Email Templates

**Prioriteit:** 🔴 Hoog  
**Status:** ❌ Not Started

**Te implementeren:**
- [ ] Verification email template (HTML)
- [ ] Confirmation email template (HTML)
- [ ] School notification template (HTML)
- [ ] Email styling (inline CSS)
- [ ] Logo en branding in emails

**Template Locaties:**
```
src/main/resources/templates/email/
├── verification.html
├── confirmation.html
└── school-notification.html
```

**Verification Email:**
```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <style>
        /* Inline styles for email compatibility */
    </style>
</head>
<body>
    <div class="email-container">
        <div class="header">
            <img src="..." alt="SNH Logo">
            <h1>Email Verificatie</h1>
        </div>
        
        <div class="content">
            <p>Beste ouder/voogd,</p>
            <p>Klik op onderstaande link om uw email te verifiëren:</p>
            <a th:href="${verificationLink}" class="button">
                Verifieer Email
            </a>
        </div>
        
        <div class="footer">
            <p>SPES Nostra Heule</p>
        </div>
    </div>
</body>
</html>
```

---

### Email Testing

**Prioriteit:** 🟡 Gemiddeld  
**Status:** ❌ Not Started

**Te implementeren:**
- [ ] Unit tests voor EmailService
- [ ] Mock email sending in tests
- [ ] Integration tests met test SMTP server (GreenMail)
- [ ] Email preview functionality (development)

**Test Setup:**
```java
@SpringBootTest
@AutoConfigureMockMvc
class EmailServiceTest {
    
    @Autowired
    private EmailService emailService;
    
    @MockBean
    private JavaMailSender mailSender;
    
    @Test
    void sendVerificationEmail_ShouldSendEmail() {
        // Test implementation
    }
}
```

---

## Fase 4: Validatie & Error Handling

### ✅ Client-side Validatie - VOLTOOID

**Prioriteit:** 🔴 Hoog  
**Status:** ✅ Completed (28 december 2025)

**Geïmplementeerd:**
- [x] Real-time form validatie systeem (form-validation.js)
- [x] Custom Nederlandse foutmeldingen
- [x] Visual feedback (rood/groen borders + foutmeldingen)
- [x] Validation rules per field type:
  - [x] Email format validatie
  - [x] Rijksregisternummer validatie (YYMMDD-XXX-XX)
  - [x] Postcode validatie (4 cijfers)
  - [x] Phone validatie (Belgisch formaat +32/0XXXXXXXXX)
  - [x] MinLength/MaxLength validatie
- [x] Support voor alle field types:
  - [x] Text inputs
  - [x] Email inputs
  - [x] Tel inputs
  - [x] Select dropdowns
  - [x] Select2 dropdowns (met custom event handling)
  - [x] Radio buttons (inclusief dynamisch gegenereerde)
  - [x] Checkboxes
  - [x] Textareas
  - [x] Date inputs (Flatpickr)
- [x] Browser native validatie uitgeschakeld (novalidate attribuut)
- [x] Validatie triggering:
  - [x] On blur (na eerste interactie met veld)
  - [x] On input (real-time tijdens typen, alleen als touched)
  - [x] On change (dropdowns en radio buttons)
  - [x] On submit (alle velden, scroll naar eerste fout)
  - [x] Select2 events: select, unselect, clear, change
- [x] Visual feedback systeem:
  - [x] Rode border (#dc3545) bij fout
  - [x] Groene border (#28a745) bij correct
  - [x] Blauwe focus border (#80bdff) bij focus
  - [x] Foutmeldingen onder velden (invalid-feedback)
  - [x] Auto-scroll naar eerste fout bij submit
  - [x] Focus op fout veld (text) of open dropdown (Select2)
- [x] Select2 specifieke implementatie:
  - [x] Validatie na selectie/deselectie in select2-init.js
  - [x] Rode/groene border op Select2 container
  - [x] Foutmelding verbergt direct bij correcte selectie
  - [x] Global function: window.validateSelect2Field()
  - [x] Werkt voor ALLE Select2 dropdowns automatisch
- [x] Radio button specifieke implementatie:
  - [x] Validatie van hele groep (niet per individuele button)
  - [x] Preventie van dubbele validatie bij submit (Set tracking)
  - [x] Rode/groene state op alle buttons in groep
  - [x] Foutmelding "Maak een keuze" onder groep
  - [x] Support voor dynamisch gegenereerde buttons (name="program")
  - [x] Special handling voor form-radio en form-check layouts
- [x] Submit button gedrag:
  - [x] Altijd enabled (gebruiker kan altijd submitten)
  - [x] Submit toont alle validatiefouten tegelijk
  - [x] Geen frustratie over "waarom werkt button niet"
- [x] Styling en UX:
  - [x] Focus states voor alle veldtypes
  - [x] Consistent kleurgebruik (rood/groen/blauw)
  - [x] Smooth transitions (0.2s ease)
  - [x] Form-check-input validation styling
  - [x] Button disabled state styling (grijs, opacity 0.6)

**Validatie gedrag:**
1. **Page load**: Geen validatie (schoon scherm)
2. **Eerste interactie**: Veld wordt "touched"
3. **Real-time**: Validatie bij typen (als touched)
4. **Correctie**: Foutmelding verdwijnt direct bij fix
5. **Submit**: Alle fouten tegelijk + scroll naar eerste

**JavaScript bestanden:**
- `/static/js/form-validation.js` - Validatie engine
- `/static/js/select2-init.js` - Select2 validatie integratie

**CSS bestanden:**
- `/static/css/snh-style.css` - Validation state styling
- `/static/css/select2-custom.css` - Select2 validation styling

---

### Server-Side Validatie

**Prioriteit:** 🔴 Hoog  
**Status:** ❌ Not Started

**Te implementeren:**
- [ ] JSR-380 Bean Validation annotations
- [ ] Custom validators
- [ ] @Valid in controller methods
- [ ] BindingResult error handling
- [ ] Error messages in Nederlands
- [ ] Validation messages configuration

**Bean Validation:**
```java
public class StudentForm {
    
    @NotBlank(message = "Voornaam is verplicht")
    @Size(min = 2, max = 100, message = "Voornaam moet tussen 2 en 100 karakters zijn")
    private String voornaamLeerling;
    
    @NotBlank(message = "Naam is verplicht")
    @Size(min = 2, max = 100, message = "Naam moet tussen 2 en 100 karakters zijn")
    private String naamLeerling;
    
    @NotNull(message = "Geboortedatum is verplicht")
    @Past(message = "Geboortedatum moet in het verleden liggen")
    private LocalDate geboortedatum;
    
    @Pattern(regexp = "\\d{11}", message = "Rijksregisternummer moet 11 cijfers bevatten")
    private String rijksregisternummer;
}
```

**Controller:**
```java
@PostMapping("/student-info")
public String processStudentInfo(
        @Valid @ModelAttribute StudentForm form,
        BindingResult result,
        Model model) {
    
    if (result.hasErrors()) {
        model.addAttribute("wizardSteps", ...);
        return "student-info";
    }
    
    // Process form...
}
```

---

### Custom Validators

**Prioriteit:** 🟡 Gemiddeld  
**Status:** ❌ Not Started

**Te implementeren:**
- [ ] Email domain validator (school emails blocken)
- [ ] Rijksregisternummer validator (checksum)
- [ ] Postcode validator (per land)
- [ ] Phone number validator (BE/NL formats)
- [ ] Age validator (min/max leeftijd)

**Example:**
```java
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RijksregisternummerValidator.class)
public @interface ValidRijksregisternummer {
    String message() default "Ongeldig rijksregisternummer";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

public class RijksregisternummerValidator 
        implements ConstraintValidator<ValidRijksregisternummer, String> {
    
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true; // Use @NotNull for required check
        }
        
        // Implement checksum validation
        return validateChecksum(value);
    }
}
```

---

### Error Handling

**Prioriteit:** 🔴 Hoog  
**Status:** ❌ Not Started

**Te implementeren:**
- [ ] Global exception handler (@ControllerAdvice)
- [ ] Custom exception classes
- [ ] Error page templates
- [ ] Logging van errors
- [ ] User-friendly error messages

**Exception Handler:**
```java
@ControllerAdvice
public class GlobalExceptionHandler {
    
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    
    @ExceptionHandler(RegistrationNotFoundException.class)
    public String handleRegistrationNotFound(
            RegistrationNotFoundException ex,
            Model model) {
        
        logger.error("Registration not found: {}", ex.getMessage());
        model.addAttribute("error", "Inschrijving niet gevonden");
        return "error/404";
    }
    
    @ExceptionHandler(Exception.class)
    public String handleGenericException(Exception ex, Model model) {
        logger.error("Unexpected error", ex);
        model.addAttribute("error", "Er is een fout opgetreden");
        return "error/500";
    }
}
```

**Custom Exceptions:**
```java
public class RegistrationNotFoundException extends RuntimeException {
    public RegistrationNotFoundException(UUID id) {
        super("Registration not found: " + id);
    }
}

public class EmailVerificationException extends RuntimeException {
    public EmailVerificationException(String message) {
        super(message);
    }
}

public class InvalidEmailHashException extends RuntimeException {
    public InvalidEmailHashException() {
        super("Invalid email verification hash");
    }
}
```

---

### Error Pages

**Prioriteit:** 🟡 Gemiddeld  
**Status:** ✅ Completed

**Geïmplementeerd:**
- [x] 404 error page
- [x] 500 error page
- [x] Generic error page
- [x] Custom error styling (consistent met applicatie thema)
- [x] CustomErrorController voor error handling

**Templates:**
```
src/main/resources/templates/
├── error.html (generic error)
└── error/
    ├── 404.html
    └── 500.html
```

---

## Fase 5: Testing

### Unit Tests

**Prioriteit:** 🟡 Gemiddeld  
**Status:** ❌ Not Started

**Te implementeren:**
- [ ] RegistrationService tests
- [ ] EmailVerificationService tests
- [ ] WizardService tests
- [ ] Controller tests (MockMvc)
- [ ] Repository tests (@DataJpaTest)
- [ ] Validator tests

**Example:**
```java
@ExtendWith(MockitoExtension.class)
class RegistrationServiceTest {
    
    @Mock
    private RegistrationRepository repository;
    
    @InjectMocks
    private RegistrationService service;
    
    @Test
    void createRegistration_ShouldCreateWithPendingStatus() {
        // Arrange
        String email = "test@example.com";
        
        // Act
        Registration result = service.createRegistration(email);
        
        // Assert
        assertThat(result.getEmail()).isEqualTo(email);
        assertThat(result.getStatus()).isEqualTo(RegistrationStatus.PENDING);
        assertThat(result.getEmailStatus()).isEqualTo(EmailStatus.UNVERIFIED);
    }
    
    @Test
    void hashEmail_ShouldReturnSha256Hash() {
        // Test implementation
    }
}
```

---

### Integration Tests

**Prioriteit:** 🟡 Gemiddeld  
**Status:** ❌ Not Started

**Te implementeren:**
- [ ] Complete wizard flow test
- [ ] Email verification flow test
- [ ] Multi-registration scenario test
- [ ] Database integration tests
- [ ] API endpoint tests

**Example:**
```java
@SpringBootTest
@AutoConfigureMockMvc
class RegistrationFlowIntegrationTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private RegistrationRepository repository;
    
    @Test
    @Transactional
    void completeRegistrationFlow_ShouldCreateAndVerifyRegistration() throws Exception {
        // 1. Submit email
        mockMvc.perform(post("/inschrijving/start")
                .param("emailadres", "test@example.com"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/inschrijving/email-sent"));
        
        // 2. Verify registration created
        List<Registration> registrations = 
            repository.findByEmailOrderByCreatedAtDesc("test@example.com");
        assertThat(registrations).hasSize(1);
        
        Registration registration = registrations.get(0);
        
        // 3. Verify email
        mockMvc.perform(get("/inschrijving/verify/{id}/{hash}",
                registration.getId(),
                registration.getEmailHash()))
                .andExpect(status().is3xxRedirection());
        
        // 4. Check email status updated
        Registration updated = repository.findById(registration.getId()).get();
        assertThat(updated.getEmailStatus()).isEqualTo(EmailStatus.VERIFIED);
    }
}
```

---

### Test Coverage

**Prioriteit:** 🟢 Laag  
**Status:** ❌ Not Started

**Te implementeren:**
- [ ] JaCoCo plugin configureren
- [ ] Minimum coverage: 80%
- [ ] Coverage report generatie
- [ ] CI/CD integratie

**pom.xml:**
```xml
<plugin>
    <groupId>org.jacoco</groupId>
    <artifactId>jacoco-maven-plugin</artifactId>
    <version>0.8.10</version>
    <executions>
        <execution>
            <goals>
                <goal>prepare-agent</goal>
            </goals>
        </execution>
        <execution>
            <id>report</id>
            <phase>test</phase>
            <goals>
                <goal>report</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

---

## Fase 6: Security

### Link Expiration

**Prioriteit:** 🟡 Gemiddeld  
**Status:** ❌ Not Started

**Te implementeren:**
- [ ] Expiration timestamp in Registration entity
- [ ] Validation in verification controller
- [ ] Expired link error page
- [ ] Resend link functionality

**Implementation:**
```java
// Registration entity
@Column(name = "verification_expires_at")
private LocalDateTime verificationExpiresAt;

// RegistrationService
public Registration createRegistration(String email) {
    Registration registration = new Registration();
    // ... set other fields
    registration.setVerificationExpiresAt(
        LocalDateTime.now().plusHours(24)
    );
    return save(registration);
}

// RegistrationController
public String verifyEmail(...) {
    Optional<Registration> regOpt = 
        registrationService.verifyEmailAndGetRegistration(id, hash);
    
    if (regOpt.isEmpty()) {
        return "error/invalid-link";
    }
    
    Registration reg = regOpt.get();
    if (reg.getVerificationExpiresAt().isBefore(LocalDateTime.now())) {
        model.addAttribute("expired", true);
        return "error/expired-link";
    }
    
    // Continue...
}
```

---

### Rate Limiting

**Prioriteit:** 🟡 Gemiddeld  
**Status:** ❌ Not Started

**Te implementeren:**
- [ ] Bucket4j dependency
- [ ] Rate limit configuratie
- [ ] Rate limit op email sending
- [ ] Rate limit op form submissions
- [ ] 429 Too Many Requests handling

**Implementation:**
```java
@Component
public class RateLimitInterceptor implements HandlerInterceptor {
    
    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();
    
    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) throws Exception {
        
        String key = getClientIP(request);
        Bucket bucket = buckets.computeIfAbsent(key, k -> 
            createBucket()
        );
        
        if (bucket.tryConsume(1)) {
            return true;
        } else {
            response.setStatus(429);
            return false;
        }
    }
    
    private Bucket createBucket() {
        Bandwidth limit = Bandwidth.simple(10, Duration.ofMinutes(1));
        return Bucket4j.builder().addLimit(limit).build();
    }
}
```

---

### CSRF Protection

**Prioriteit:** 🔴 Hoog  
**Status:** ❌ Not Started

**Te implementeren:**
- [ ] Spring Security dependency
- [ ] CSRF token configuration
- [ ] CSRF tokens in forms
- [ ] CSRF validation

**Configuration:**
```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
            )
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll()
            );
        
        return http.build();
    }
}
```

**Template:**
```html
<form method="post">
    <input type="hidden" th:name="${_csrf.parameterName}" th:value="${_csrf.token}">
    <!-- form fields -->
</form>
```

---

### Input Sanitization

**Prioriteit:** 🔴 Hoog  
**Status:** ❌ Not Started

**Te implementeren:**
- [ ] OWASP Java HTML Sanitizer
- [ ] Sanitization in services
- [ ] XSS protection
- [ ] SQL injection protection (al gedaan via JPA)

---

## Fase 7: Production Ready

### Production Database

**Prioriteit:** 🔴 Hoog  
**Status:** ❌ Not Started

**Te implementeren:**
- [ ] PostgreSQL/MySQL dependency
- [ ] Database schema creation scripts
- [ ] Migration scripts (Flyway/Liquibase)
- [ ] Database backup strategy
- [ ] Connection pooling configuratie

**PostgreSQL Setup:**
```xml
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <scope>runtime</scope>
</dependency>
```

**application-prod.properties:**
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/snh_registrations
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

---

### Flyway Migration

**Prioriteit:** 🔴 Hoog  
**Status:** ❌ Not Started

**Te implementeren:**
- [ ] Flyway dependency
- [ ] Migration scripts voor alle tables
- [ ] Baseline migration
- [ ] Version control voor migrations

**Migration Example:**
```sql
-- V1__create_registrations_table.sql
CREATE TABLE registrations (
    id UUID PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    email_hash VARCHAR(255) NOT NULL UNIQUE,
    school_year VARCHAR(255) NOT NULL,
    status VARCHAR(50) NOT NULL,
    current_step VARCHAR(100),
    email_status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    student_firstname VARCHAR(255),
    student_lastname VARCHAR(255)
);

CREATE INDEX idx_registrations_email ON registrations(email);
CREATE INDEX idx_registrations_status ON registrations(status);
```

---

### Logging & Monitoring

**Prioriteit:** 🟡 Gemiddeld  
**Status:** ❌ Not Started

**Te implementeren:**
- [ ] Logback configuratie
- [ ] Log levels per environment
- [ ] Structured logging (JSON)
- [ ] Log aggregation (ELK stack)
- [ ] Application metrics (Micrometer)
- [ ] Health checks
- [ ] Actuator endpoints

**logback-spring.xml:**
```xml
<configuration>
    <springProfile name="prod">
        <appender name="FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
            <file>logs/snh-registrations.log</file>
            <encoder class="net.logstash.logback.encoder.LogstashEncoder"/>
        </appender>
        
        <root level="INFO">
            <appender-ref ref="FILE"/>
        </root>
    </springProfile>
</configuration>
```

---

### Deployment

**Prioriteit:** 🔴 Hoog  
**Status:** ❌ Not Started

**Te implementeren:**
- [ ] Docker containerization
- [ ] Docker Compose setup
- [ ] Kubernetes manifests (optioneel)
- [ ] CI/CD pipeline (GitHub Actions)
- [ ] Environment-specific configurations
- [ ] Secrets management

**Dockerfile:**
```dockerfile
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY target/snhinschrijvingen-*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
```

**docker-compose.yml:**

```yaml
version: '3.8'

services:
  app:
    build: ..
    ports:
      - "8080:8080"
    environment:
      - SPRING_PROFILES_ACTIVE=prod
      - DB_HOST=db
    depends_on:
      - db

  db:
    image: postgres:15
    environment:
      - POSTGRES_DB=snh_registrations
      - POSTGRES_USER=${DB_USER}
      - POSTGRES_PASSWORD=${DB_PASSWORD}
    volumes:
      - postgres-data:/var/lib/postgresql/data

volumes:
  postgres-data:
```

---

### Performance Optimization

**Prioriteit:** 🟡 Gemiddeld  
**Status:** ❌ Not Started

**Te implementeren:**
- [ ] Database indexing strategie
- [ ] Query optimization
- [ ] Caching strategie (Spring Cache)
- [ ] Connection pooling (HikariCP)
- [ ] Static resource compression
- [ ] CDN voor static assets

---

## Nice to Have Features

### Admin Dashboard

**Prioriteit:** 🟢 Laag  
**Status:** ❌ Not Started

**Features:**
- [ ] Admin login/authentication
- [ ] Registrations overview table
- [ ] Search & filter registrations
- [ ] Export to Excel/CSV
- [ ] Status management
- [ ] Statistics dashboard
- [ ] Email resend functionality

**Tech Stack:**
- Spring Security voor authentication
- Thymeleaf voor admin templates
- Apache POI voor Excel export

---

### PDF Generation

**Prioriteit:** 🟢 Laag  
**Status:** ❌ Not Started

**Features:**
- [ ] PDF bevestiging na voltooiing
- [ ] PDF met alle registratie gegevens
- [ ] School logo en branding
- [ ] Download button op bevestigingspagina

**Tech Stack:**
- Flying Saucer (HTML to PDF)
- iText PDF
- Thymeleaf voor PDF templates

---

### File Uploads

**Prioriteit:** 🟢 Laag  
**Status:** ❌ Not Started

**Features:**
- [ ] Upload identity documents
- [ ] Upload medical certificates
- [ ] File validation (type, size)
- [ ] Storage strategy (S3/local)
- [ ] File preview

---

### Multi-Language Support

**Prioriteit:** 🟢 Laag  
**Status:** ❌ Not Started

**Features:**
- [ ] i18n configuration
- [ ] Message bundles (nl, fr, en)
- [ ] Language switcher
- [ ] URL-based locale detection

---

### Email Notifications naar School

**Prioriteit:** 🟢 Laag  
**Status:** ❌ Not Started

**Features:**
- [ ] Email naar school bij nieuwe inschrijving
- [ ] Daily digest van nieuwe inschrijvingen
- [ ] Configureerbare email templates
- [ ] School admin email configuratie

---

## Technical Debt

### Code Improvements

**Prioriteit:** 🟢 Laag  
**Status:** ❌ Not Started

**Te doen:**
- [ ] Extract magic strings naar constants
- [ ] Reduce code duplication in controllers
- [ ] Improve error messages
- [ ] Add JavaDoc comments
- [ ] Refactor large methods
- [ ] Extract business logic from controllers

---

### Documentation Updates

**Prioriteit:** 🟡 Gemiddeld  
**Status:** ❌ Not Started

**Te doen:**
- [ ] API documentation (Swagger/OpenAPI)
- [ ] Architecture decision records (ADRs)
- [ ] Deployment guide
- [ ] User manual
- [ ] Admin manual
- [ ] Troubleshooting guide

---

### Performance Testing

**Prioriteit:** 🟢 Laag  
**Status:** ❌ Not Started

**Te doen:**
- [ ] Load testing (JMeter/Gatling)
- [ ] Stress testing
- [ ] Database query optimization
- [ ] Memory profiling
- [ ] Response time benchmarks

---

## Checklist voor Go-Live

### Pre-Production Checklist

- [ ] Alle wizard stappen geïmplementeerd
- [ ] Email verzending werkt
- [ ] Database migraties getest
- [ ] Server-side validatie compleet
- [ ] Error handling compleet
- [ ] Security audit uitgevoerd
- [ ] Performance testing uitgevoerd
- [ ] Backup strategie geïmplementeerd
- [ ] Monitoring setup
- [ ] SSL certificaat geïnstalleerd
- [ ] DNS configuratie
- [ ] GDPR compliance check
- [ ] Privacy policy toegevoegd
- [ ] Terms & conditions toegevoegd
- [ ] Contact informatie correct
- [ ] School logo's en branding
- [ ] Testing op verschillende browsers
- [ ] Mobile responsiveness getest
- [ ] Accessibility audit
- [ ] User acceptance testing
- [ ] Training voor school personeel

---

## Prioriteit Matrix

| Fase | Prioriteit | Geschatte Tijd | Status |
|------|-----------|----------------|---------|
| Wizard Stappen 3-8 | 🔴 Hoog | 3-4 weken | ❌ |
| Email Integratie | 🔴 Hoog | 1 week | ❌ |
| Validatie & Errors | 🔴 Hoog | 2 weken | ❌ |
| Production DB | 🔴 Hoog | 1 week | ❌ |
| Security | 🟡 Gemiddeld | 1 week | ❌ |
| Testing | 🟡 Gemiddeld | 2 weken | ❌ |
| Deployment | 🔴 Hoog | 1 week | ❌ |
| Admin Dashboard | 🟢 Laag | 2 weken | ❌ |
| PDF Generation | 🟢 Laag | 1 week | ❌ |
| Multi-language | 🟢 Laag | 1 week | ❌ |

**Totale geschatte tijd tot go-live:** 10-12 weken

---

## Volgende Sprint Planning

### Sprint 1 (Week 1-2): Database & Persistentie ✅ VOLTOOID
- [x] Stap 1: Student Info (Algemeen) - VOLTOOID
- [x] Stap 2: Vorige School - VOLTOOID
- [x] Stap 3: Study Program Selection (Richting) - VOLTOOID
- [x] Select2 integratie met custom styling
- [ ] Study program selectie opslaan in Registration entity
- [x] Stap 4: Relaties (contactgegevens ouders/voogd) - VOLTOOID
- [x] Herbruikbaar Address fragment aangemaakt
- [x] Address model class aangemaakt
- [ ] Relation entities implementeren (database persistence)

### Sprint 2 (Week 3-4): Wizard Voltooiing Deel 1
- [ ] Stap 5: Te bepalen (mogelijk huisarts of medische info)

### Sprint 3 (Week 5-6): Wizard Voltooiing Deel 2
- [ ] Stap 6: Huisarts
- [ ] Stap 7: Medische info

### Sprint 4 (Week 7-8): Wizard Voltooiing Deel 3  
- [ ] Stap 8: Voorkeuren
- [ ] Stap 9: Overzicht & bevestiging

### Sprint 5 (Week 9-10): Email & Validatie
- [ ] Email integratie
- [ ] Server-side validatie
- [ ] Error handling

### Sprint 5 (Week 9-10): Email & Validatie
- [ ] Email integratie
- [ ] Server-side validatie
- [ ] Error handling

### Sprint 6 (Week 11-12): Testing & Security
- [ ] Unit tests
- [ ] Integration tests
- [ ] Security implementatie

### Sprint 6 (Week 11-12): Testing & Security
- [ ] Unit tests
- [ ] Integration tests
- [ ] Security implementatie

### Sprint 7 (Week 13-14): Production Ready
- [ ] Database setup
- [ ] Deployment
- [ ] Monitoring
- [ ] Go-live!

---

**End of TODO List**

**Opmerking:** Deze TODO lijst is een levend document en wordt bijgewerkt naarmate het project vordert. Prioriteiten kunnen wijzigen op basis van feedback en requirements.
