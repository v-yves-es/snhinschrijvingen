# SNH Inschrijvingen - Applicatie Status

**Versie:** 0.0.1-SNAPSHOT  
**Laatst bijgewerkt:** 18 januari 2026 17:12  
**Spring Boot:** 3.5.9  
**Java:** 25  
**Status:** ✅ Fase 2 VOLTOOID - Alle 10 wizard stappen geïmplementeerd + Critical bugfixes  

---

## 📋 Inhoudsopgave

1. [Project Overzicht](#project-overzicht)
2. [Technologie Stack](#technologie-stack)
3. [Architectuur](#architectuur)
4. [Database Model](#database-model)
5. [Geïmplementeerde Features](#geïmplementeerde-features)
6. [Frontend Componenten](#frontend-componenten)
7. [Backend Services](#backend-services)
8. [API Endpoints](#api-endpoints)
9. [Wizard Flow](#wizard-flow)
10. [Validatie Systeem](#validatie-systeem)
11. [Alert Systeem](#alert-systeem)
12. [Navigatie Systeem](#navigatie-systeem)
13. [Testing](#testing)
14. [Documentatie](#documentatie)

---

## Project Overzicht

### Doel
Online inschrijvingssysteem voor SPES Nostra Heule, waarmee ouders/voogden hun kinderen kunnen inschrijven voor het nieuwe schooljaar via een wizard-gebaseerde interface.

### Status
✅ **Fase 1 voltooid**: Email verificatie systeem en basis wizard structuur  
✅ **Fase 2 voltooid**: Volledige wizard implementatie (10 stappen) + Critical bugfixes  
🚧 **Fase 3 voorbereid**: Email integratie & server-side validatie

---

## Technologie Stack

### Backend
- **Framework:** Spring Boot 3.5.9
- **Java:** 25
- **Database:** H2 (in-memory, development)
- **ORM:** Spring Data JPA / Hibernate
- **Template Engine:** Thymeleaf
- **Build Tool:** Maven

### Frontend
- **Template Engine:** Thymeleaf
- **CSS:** Custom (SNH branding)
- **JavaScript:** Vanilla JS (form validation, alerts)
- **Select2:** 4.1.0-rc.0 (advanced dropdowns)
- **Flatpickr:** Datepicker library (Nederlands)
- **Fonts:** Montserrat, Cervo (custom)
- **Icons:** SVG inline

### Dependencies (Key)
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>runtime</scope>
</dependency>
```

---

## Architectuur

### Lagenstructuur

```
snhinschrijvingen/
├── model/              # JPA Entities & Enums
├── repository/         # Spring Data Repositories
├── service/           # Business Logic Layer
├── controller/        # Web Controllers (MVC)
└── dto/               # Data Transfer Objects
```

### Package Structuur

```
be.achieveit.snhinschrijvingen/
├── SnhinschrijvingenApplication.java
├── controller/
│   ├── CustomErrorController.java
│   ├── EmailVerificationController.java
│   ├── RegistrationController.java
│   ├── StudentInfoController.java
│   ├── StudyProgramController.java
│   ├── PreviousSchoolController.java
│   ├── RelationsController.java
│   ├── DoctorController.java
│   ├── CareNeedsController.java
│   ├── PrivacyController.java
│   ├── LaptopController.java
│   ├── SchoolAccountController.java
│   └── SubmissionController.java
├── dto/
│   ├── EmailForm.java
│   ├── StudentForm.java
│   ├── StudyProgramForm.java
│   ├── PreviousSchoolForm.java
│   ├── RelationForm.java
│   ├── DoctorForm.java
│   ├── CareNeedsForm.java
│   ├── PrivacyForm.java
│   ├── LaptopForm.java
│   ├── SchoolAccountForm.java
│   └── SubmissionForm.java
├── model/
│   ├── Registration.java
│   ├── Relation.java (JPA entity)
│   ├── Address.java (embedded)
│   ├── RegistrationStatus.java
│   ├── EmailStatus.java
│   ├── Nationaliteit.java
│   ├── SchoolYear.java
│   ├── WizardStep.java
│   ├── StudyDomain.java
│   ├── StudyOrientation.java
│   └── StudyProgram.java
├── repository/
│   ├── RegistrationRepository.java
│   ├── RelationRepository.java
│   ├── SchoolYearRepository.java
│   ├── NationalityRepository.java
│   ├── StudyDomainRepository.java
│   ├── StudyOrientationRepository.java
│   └── StudyProgramRepository.java
├── service/
│   ├── RegistrationService.java
│   ├── EmailVerificationService.java
│   ├── SchoolYearService.java
│   ├── NationalityService.java
│   ├── WizardService.java
│   ├── StudyProgramService.java
│   ├── StudyDomainService.java
│   └── StudyOrientationService.java
├── config/
│   └── DataInitializer.java
└── initializer/
    └── StudyProgramInitializer.java
```

---

## Database Model

### Entity: Registration

**Tabel:** `registrations`

| Veld | Type | Constraints | Beschrijving |
|------|------|------------|--------------|
| `id` | UUID | PRIMARY KEY | Uniek inschrijvings-ID |
| `email` | String | NOT NULL | Emailadres ouder/voogd |
| `email_hash` | String | UNIQUE | Unieke hash voor verificatie |
| `email_status` | Enum | NOT NULL | PENDING, VERIFIED, EXPIRED |
| `verification_sent_at` | LocalDateTime | | Tijdstip verificatie mail |
| `verified_at` | LocalDateTime | | Tijdstip verificatie |
| `registration_status` | Enum | NOT NULL | DRAFT, SUBMITTED, PROCESSING, COMPLETED, CANCELLED |
| `current_step` | String | | Huidige wizard stap |
| `created_at` | LocalDateTime | NOT NULL | Aanmaak tijdstip |
| `updated_at` | LocalDateTime | | Laatste wijziging |
| **Leerling gegevens** ||||
| `voornaam` | String(100) | | Voornaam leerling |
| `naam` | String(100) | | Achternaam leerling |
| `geboortedatum` | LocalDate | | Geboortedatum |
| `geboorteplaats` | String(100) | | Geboorteplaats |
| `geslacht` | String(1) | | M/V |
| `rijksregisternummer` | String(15) | | Rijksregisternummer |
| `nationaliteit` | String(50) | | Nationaliteit |
| `thuistaal` | String(50) | | Thuistaal |
| `gsm_nummer` | String(20) | | GSM-nummer leerling |
| **Domicilieadres (embedded Address)** ||||
| `student_address_*` | Address | | Embedded adres object |
| **Studierichting** ||||
| `selected_study_year` | Integer | | Gekozen leerjaar (1-6) |
| `selected_study_program_id` | Long | FK | ID van gekozen studierichting |
| `study_program_extra_info` | String(500) | | Extra info bij twijfel |
| **Vorige school** ||||
| `vorige_school` | String(200) | | Naam vorige school |
| `vorige_school_anders` | String(200) | | Andere school (vrij veld) |
| `vorige_school_jaar` | String(20) | | Jaar vorige school |
| `richting_vorige_school` | String(200) | | Gevolgde richting |
| `toestemming_vorige_school` | String(1) | | J/N toestemming contact |
| **Huisarts** ||||
| `doctor_name` | String(200) | | Naam huisarts |
| `doctor_phone` | String(20) | | Telefoon huisarts |
| **Zorgvraag** ||||
| `class_composition_wishes` | String(500) | | Wensen klassamenstelling |
| `has_care_needs` | String(1) | | J/N bijzondere zorgvraag |
| `medical_care_details` | String(1000) | | Medische zorg details |
| `receiving_treatment` | String(1) | | J/N in behandeling |
| `doctor_contact_info` | String(500) | | Contactinfo behandelend arts |
| `takes_medication` | String(1) | | J/N medicatie |
| `school_expectations` | String(1000) | | Verwachtingen school |
| `lesson_influence` | String(1000) | | Invloed op lessen |
| `clb_consult_permission` | String(1) | | J/N CLB toestemming |
| `social_emotional_info` | String(1000) | | Sociaal-emotionele info |
| `learning_difficulties` | String(1000) | | Leermoeilijkheden |
| `external_support` | String(500) | | Externe hulpverlening |
| **Privacy toestemmingen** ||||
| `photo_video_consent` | String(1) | | J/N foto's/video's |
| `study_results_consent` | String(1) | | J/N studieresultaten delen |
| `alumni_data_consent` | String(1) | | J/N oud-leerling data |
| `higher_education_consent` | String(1) | | J/N hoger onderwijs |
| **Laptop** ||||
| `laptop_brand` | String(200) | | Merk Signpost laptop |
| **Schoolrekening** ||||
| `financial_support_request` | String(1) | | J/N financieel gesprek |
| **Ondertekening** ||||
| `signature_name` | String(200) | | Naam ondertekenaar |
| `school_regulation_agreement` | String(1) | | J akkoord schoolreglement |
| `additional_info_request` | String(1) | | J bijkomende info gewenst |
| `submission_date` | LocalDateTime | | Ingediend op |

### Entity: Relation

**Tabel:** `relations`

| Veld | Type | Constraints | Beschrijving |
|------|------|------------|--------------|
| `id` | Long | PRIMARY KEY | Auto-increment ID |
| `registration_id` | UUID | FK | Link naar registration |
| `relation_type` | String(50) | NOT NULL | Type relatie |
| `first_name` | String(100) | NOT NULL | Voornaam |
| `last_name` | String(100) | NOT NULL | Achternaam |
| `phone` | String(20) | NOT NULL | Telefoonnummer |
| `email` | String(200) | NOT NULL | Email |
| **Adres (embedded Address)** ||||
| `address_*` | Address | | Embedded adres object |
| `email_hash` | String | NOT NULL, UNIQUE | SHA-256 hash van email |
| `school_year` | String | NOT NULL | Schooljaar (bijv. "2025-2026") |
| `status` | Enum | NOT NULL | PENDING / COMPLETED |
| `current_step` | String | | Huidige wizard stap |
| `email_status` | Enum | NOT NULL | UNVERIFIED / VERIFIED / ERROR |
| `created_at` | LocalDateTime | NOT NULL | Aanmaakdatum |
| `updated_at` | LocalDateTime | | Laatste wijziging |
| `student_firstname` | String | | Voornaam leerling |
| `student_lastname` | String | | Achternaam leerling |
| `student_adres` | String | | Domicilieadres (straat + nummer) |
| `student_postnummer` | String | | Postcode |
| `student_gemeente` | String | | Gemeente/stad |
| `student_gsm` | String | | GSM nummer leerling |
| `student_geslacht` | String | | Geslacht (M/V/X/O) |
| `student_rijksregisternummer` | String | | Rijksregisternummer |
| `student_geboortedatum` | LocalDate | | Geboortedatum |
| `student_geboorteplaats` | String | | Geboorteplaats |
| `student_nationaliteit` | String | | Nationaliteit (ISO code) |
| `vorige_school_adres` | String | | Naam vorige school |
| `vorige_school_jaar` | String | | Jaar in vorige school |
| `richting_vorige_school` | String | | Gevolgde richting |
| `toestemming_vorige_school` | String | | Toestemming gegevensuitwisseling |

### Entity: Address

**Package:** `model.Address`  
**Type:** POJO (niet-persistent, gebruikt in forms)

| Veld | Type | Beschrijving |
|------|------|--------------|
| `street` | String | Straatnaam |
| `houseNumber` | String | Huisnummer (+bus optioneel) |
| `postalCode` | String | Postcode (4 cijfers) |
| `city` | String | Gemeente/stad |
| `country` | String | Land (standaard: België) |

**Gebruikt in:**
- StudentForm (domicilieadres leerling)
- RelationForm (adres relatie/ouder)

### Entity: StudyDomain

**Tabel:** `study_domains`

| Veld | Type | Constraints | Beschrijving |
|------|------|------------|--------------|
| `id` | Long | PRIMARY KEY | Uniek domein-ID |
| `name` | String | NOT NULL, UNIQUE | Domeinnaam (NL) |
| `color` | String | NOT NULL | Hex kleurcode |

**Domeinen:**
- Economie (#FF6B35)
- Maatschappij & Welzijn (#28A745)
- STEM (#007BFF)
- Talen (#FFC107)

### Entity: StudyOrientation

**Tabel:** `study_orientations`

| Veld | Type | Constraints | Beschrijving |
|------|------|------------|--------------|
| `id` | Long | PRIMARY KEY | Uniek oriëntatie-ID |
| `name` | String | NOT NULL, UNIQUE | Oriëntatienaam (NL) |

**Oriëntaties:**
- Doorstroom
- Doorstroom/arbeidsmarkt
- Arbeidsmarkt

### Entity: StudyProgram

**Tabel:** `study_programs`

| Veld | Type | Constraints | Beschrijving |
|------|------|------------|--------------|
| `id` | Long | PRIMARY KEY | Uniek programma-ID |
| `name` | String | NOT NULL | Naam studierichting |
| `jaar` | Integer | NOT NULL | Leerjaar (1-6) |
| `domain_id` | Long | FK | Studiedomein (nullable voor jaar 1-2) |
| `orientation_id` | Long | FK | Studioriëntatie (nullable voor jaar 1-2) |

**Data:**
- 100+ studierichtingen voor jaar 1-6
- Jaar 1-2: platte lijst
- Jaar 3-6: gegroepeerd per domein/oriëntatie

### Entity: SchoolYear

**Tabel:** `school_years`

| Veld | Type | Constraints | Beschrijving |
|------|------|------------|--------------|
| `id` | String | PRIMARY KEY | Schooljaar ID (bijv. "2025-2026") |
| `description` | String | NOT NULL | Beschrijving schooljaar |
| `start_date` | LocalDate | NOT NULL | Startdatum (01/09) |
| `end_date` | LocalDate | NOT NULL | Einddatum (31/08) |

### Entity: Nationality

**Tabel:** `nationalities`

| Veld | Type | Constraints | Beschrijving |
|------|------|------------|--------------|
| `code` | String | PRIMARY KEY | ISO country code |
| `name_nl` | String | NOT NULL | Nederlandse naam |
| `name_fr` | String | NOT NULL | Franse naam |

### Entity: StudyDomain

**Tabel:** `study_domains`

| Veld | Type | Constraints | Beschrijving |
|------|------|------------|--------------|
| `id` | Long | PRIMARY KEY | Uniek domein ID |
| `name` | String | NOT NULL | Naam van het domein |
| `color` | String | | Hexadecimale kleur code |
| `display_order` | Integer | | Volgorde in UI |

**Voorbeelden:**
- Economie en organisatie (#E85C28)
- Maatschappij en welzijn (#6AAB41)
- STEM (#0088FF)
- Taal en cultuur (#E63888)

### Entity: StudyOrientation

**Tabel:** `study_orientations`

| Veld | Type | Constraints | Beschrijving |
|------|------|------------|--------------|
| `id` | Long | PRIMARY KEY | Uniek oriëntatie ID |
| `name` | String | NOT NULL | Naam van de oriëntatie |
| `display_order` | Integer | | Volgorde in UI |

**Voorbeelden:**
- Doorstroom
- Doorstroom/arbeidsmarkt
- Arbeidsmarkt

### Entity: StudyProgram

**Tabel:** `study_programs`

| Veld | Type | Constraints | Beschrijving |
|------|------|------------|--------------|
| `code` | String | PRIMARY KEY | Unieke code (bijv. "1A-LAT") |
| `name` | String | NOT NULL | Naam van de richting |
| `study_year` | Integer | NOT NULL | Jaar (1-6) |
| `domain_id` | Long | FOREIGN KEY | Link naar study_domains (NULL voor jaar 1-2) |
| `orientation_id` | Long | FOREIGN KEY | Link naar study_orientations (NULL voor jaar 1-2) |
| `display_order` | Integer | | Volgorde in UI |
| `is_active` | Boolean | DEFAULT true | Actief in huidig schooljaar |

### Enums

**RegistrationStatus:**
- `PENDING` - Inschrijving in uitvoering
- `COMPLETED` - Inschrijving voltooid

**EmailStatus:**
- `UNVERIFIED` - Email nog niet geverifieerd
- `VERIFIED` - Email geverifieerd
- `ERROR` - Fout bij emailverzending

### Repository Queries

```java
// Alle registraties voor een email (nieuwste eerst)
List<Registration> findByEmailOrderByCreatedAtDesc(String email);

// Pending registraties voor een email
List<Registration> findByEmailAndStatusOrderByCreatedAtDesc(
    String email, 
    RegistrationStatus status
);

// Verificatie met ID + hash
Optional<Registration> findByIdAndEmailHash(UUID id, String emailHash);

// Eerste pending registratie
Optional<Registration> findFirstByEmailAndStatusOrderByCreatedAtDesc(
    String email, 
    RegistrationStatus status
);

// Check of hash bestaat
boolean existsByEmailHash(String emailHash);
```

---

## Geïmplementeerde Features

### ✅ Email Verificatie Systeem

**Functionaliteit:**
- Email invoer met validatie
- Automatische generatie van verificatielink
- SHA-256 email hashing voor security
- Console output van verificatielink (klaar voor echte email integratie)
- Email status tracking

**Flow:**
1. Ouder voert email in → POST `/inschrijving/start`
2. Systeem maakt Registration aan (PENDING, UNVERIFIED)
3. Verificatielink wordt gegenereerd en gelogd
4. Email sent confirmation pagina
5. Ouder klikt op link → GET `/inschrijving/verify/{id}/{hash}`
6. Email wordt geverifieerd → VERIFIED
7. Routing naar juiste pagina

**Security:**
- Email wordt gehashed met SHA-256
- Link format: `/verify/{UUID}/{SHA256-hash}`
- Validatie vereist both ID én hash match
- Email niet zichtbaar in URL

### ✅ Multi-Registratie Ondersteuning

**Functionaliteit:**
- Meerdere inschrijvingen per emailadres mogelijk
- Overzichtspagina toont alle registraties
- Onderscheid tussen PENDING en COMPLETED
- Resume capability voor onvoltooide inschrijvingen

**Registrations Overview:**
- Tabel met alle registraties
- Status badges (✅ Voltooid, ⏳ Lopend)
- "Inschrijving afwerken" knop voor pending
- "Nieuwe leerling inschrijven" knop

### ✅ Wizard Structuur

**Huidige Stappen:**
1. Algemeen - Student Info (GEÏMPLEMENTEERD)
2. Richting - Studiekeuze (GEÏMPLEMENTEERD)
3-10. *(Nog te implementeren)*

**Pre-Wizard Stappen:**
- Email Verificatie (voltooid)
- Email Sent Info (voltooid)

**Wizard Features:**
- Visuele stappenbalk bovenaan
- Actieve stap highlighted
- Voltooide stappen met vinkje
- Responsief design

### ✅ Student Info Formulier (Wizard Stap 1: Algemeen)

**Velden:**
- **Leerling sectie:**
  - Voornaam en familienaam (50/50 layout)
  - Rijksregisternummer (100%)
  - Geboortedatum en geboorteplaats (50/50)
  - Geslacht (M/V radio buttons) en nationaliteit (50/50)
- **Domicilieadres sectie:**
  - Straat en nummer (100%)
  - Postcode (25%) en gemeente/stad (75%)
  - GSM nummer (100%)
- **Vorige school sectie:**
  - Naam en adres vorige school (radio buttons met "Anders" optie)
  - Jaar in vorige school (radio buttons met "Anders" optie)
  - Gevolgde richting (text input)
  - Toestemming gegevensuitwisseling (Ja/Nee radio buttons)

**Integratie:**
- Gekoppeld aan Registration via UUID
- Pre-fill bij herladen
- Updates worden opgeslagen
- Navigatie naar vorige/volgende stap

### ✅ Schooljaar Berekening

**Automatisch:**
```java
// September-december: volgend schooljaar
currentMonth >= 9 ? currentYear + "-" + (currentYear + 1)

// Januari-augustus: huidig schooljaar  
: (currentYear - 1) + "-" + currentYear

// December 2025 → "2025-2026"
// September 2026 → "2026-2027"
```

**SchoolYearService:**
- Beheert schooljaren in database
- `getCurrentRegistrationSchoolYear()` - automatisch berekening
- `getSchoolYearDescription(id)` - beschrijving ophalen
- Gebruikt in header voor dynamische titel

### ✅ Nationaliteiten Beheer

**Functionaliteit:**
- Database tabel met nationaliteiten (code, name_nl, name_fr)
- NationalityService voor data management
- Automatische initialisatie bij opstarten (DataInitializer)
- Gebruikt in student-info dropdown

**Geïnitialiseerde Nationaliteiten:**
- België, Nederland, Frankrijk, Duitsland, Groot-Brittannië, Spanje, Italië, Polen, Roemenië, Turkije, Marokko, Andere

### ✅ Study Programs Beheer

**Functionaliteit:**
- Database tabellen voor studierichtingen management
- Hiërarchische structuur: Jaar → Domein → Oriëntatie → Programma
- Data voor alle jaren (1-6) geladen bij startup
- Kleurcodering per domein
- Dynamische filtering op basis van gekozen jaar

**Database Structuur:**
- **StudyDomain:** Hoofdrichtingen (Economie, Maatschappij, STEM, Taal)
- **StudyOrientation:** Types (Doorstroom, Doorstroom/arbeidsmarkt, Arbeidsmarkt)
- **StudyProgram:** Specifieke richtingen per jaar

**Services:**
- StudyProgramService: Programma's ophalen per jaar
- StudyDomainService: Domeinen beheren
- StudyOrientationService: Oriëntaties beheren

### ✅ Study Program Selection (Wizard Stap 2: Richting)

**Functionaliteit:**
- Dropdown voor jaarselectie (1ste-6de)
- Dynamische weergave van richtingen op basis van jaar
- Voor jaar 1-2: Eenvoudige radiobutton lijst
- Voor jaar 3-6: Gegroepeerd per domein en oriëntatie
  - Domeinnaam in kleur
  - Oriëntatie in vet
  - Richtingen als radiobuttons
- Responsive layout met Bootstrap grid
- AJAX laden van richtingen (future enhancement mogelijk)

**Kleuren per domein:**
- Economie en organisatie: #E85C28 (oranje)
- Maatschappij en welzijn: #6AAB41 (groen)
- STEM: #0088FF (blauw)
- Taal en cultuur: #E63888 (roze)

### ✅ Error Pages

**Custom Error Handling:**
- Generic error page (`error.html`)
- 404 Not Found page (`error/404.html`)
- 500 Internal Server Error page (`error/500.html`)
- CustomErrorController voor routing
- Consistent thema met applicatie design
- User-friendly foutmeldingen in Nederlands

---

## Frontend Componenten

### CSS Framework

**Locatie:** `src/main/resources/static/css/snh-style.css`

**Features:**
- ✅ Custom branding (snh Red: #c92617)
- ✅ Form styling met labels en inputs
- ✅ Button variants (primary, secondary, outline)
- ✅ Alert componenten (8 types)
- ✅ Wizard navigation
- ✅ Card layouts
- ✅ Responsive design
- ✅ Form validation states (valid/invalid/focus)

**Button Types:**

| Class | Style | Gebruik |
|-------|-------|---------|
| `.btn--primary` | Rood, prominent | Hoofdacties (Volgende, Verzenden) |
| `.btn--secondary` | Grijs, solid | Terug/Annuleren |
| `.btn--outline` | Border only | Vorige stap in wizard |

**Alert Types:**

| Class | Kleur | Gebruik |
|-------|-------|---------|
| `.alert-primary` | Blauw | Algemene info |
| `.alert-success` | Groen | Succesmeldingen |
| `.alert-danger` | Rood | Errors |
| `.alert-warning` | Geel | Waarschuwingen |
| `.alert-info` | Lichtblauw | Informatief |
| `.alert-secondary` | Grijs | Minder belangrijk |
| `.alert-light` | Wit | Subtiel |
| `.alert-dark` | Donker | Contrast |

### JavaScript Modules

#### 1. Form Validation (`form-validation.js`)

**Features:**
- Real-time validatie op blur event
- Focus state (blauwe border)
- Valid state (groene border + vinkje)
- Invalid state (rode border + foutmelding)
- Bootstrap-compatible classes
- Automatische activatie op forms met `data-validate="true"`

**Usage:**
```html
<form data-validate="true">
    <input type="email" required>
    <div class="invalid-feedback">Vul een geldig emailadres in</div>
</form>
```

#### 2. Alert System (`alerts.js`)

**Features:**
- Dismissible alerts met close button
- Fade out animatie
- Automatic cleanup (removes from DOM)
- Event listeners op alle `.alert-dismissible`

**Usage:**
```html
<div class="alert alert-warning alert-dismissible fade show">
    Waarschuwing
    <button type="button" class="close" aria-label="Close">
        <span>&times;</span>
    </button>
</div>
```

### Thymeleaf Fragments

**Locatie:** `src/main/resources/templates/fragments/`

#### 1. Layout Fragment (`layout.html`)

**Purpose:** Base template voor alle pagina's

**Features:**
- HTML head met meta tags
- CSS includes (snh-style.css)
- JavaScript includes (form-validation.js, alerts.js)
- Main content placeholder
- Consistent page structure

**Usage:**
```html
<html th:replace="~{fragments/layout :: layout(~{::title}, ~{::section})}">
```

#### 2. Wizard Fragment (`wizard.html`)

**Purpose:** Wizard stappenbalk

**Parameters:**
- `steps` - Lijst van WizardStep objecten
- `currentStep` - Nummer van actieve stap

**Features:**
- Dynamische stap rendering
- Active, completed, pending states
- Responsive design
- SVG checkmark voor voltooide stappen

**Usage:**
```html
<div th:replace="~{fragments/wizard :: wizard(${wizardSteps})}"></div>
```

#### 3. Navigation Fragment (`navigation.html`)

**Purpose:** Herbruikbare navigatie buttons

**Fragments:**

**A) Form Navigation (met Vorige + Volgende):**
```html
<div th:replace="~{fragments/navigation :: 
     navigation(@{/previous-url}, 'Vorige', 'Volgende', true)}">
</div>
```

**Parameters:**
- `backUrl` - URL voor terug button
- `backLabel` - Tekst voor terug button
- `nextLabel` - Tekst voor submit button  
- `showBack` - Boolean om terug button te tonen

**B) Simple Back Button:**
```html
<div th:replace="~{fragments/navigation :: 
     backButton(@{/back-url}, 'Terug')}">
</div>
```

**Parameters:**
- `backUrl` - URL voor terug button
- `backLabel` - Tekst voor button

---

## Backend Services

### RegistrationService

**Locatie:** `be.achieveit.snhinschrijvingen.service.RegistrationService`

**Verantwoordelijkheden:**
- CRUD operaties voor Registration entities
- Email hashing (SHA-256)
- Schooljaar berekening
- Business logic voor registraties

**Key Methods:**

```java
// Nieuwe registratie aanmaken
Registration createRegistration(String email)

// Email verifiëren en registratie ophalen
Optional<Registration> verifyEmailAndGetRegistration(UUID id, String hash)

// Alle registraties voor een email
List<Registration> findRegistrationsByEmail(String email)

// Pending registratie voor een email
Optional<Registration> findPendingRegistrationByEmail(String email)

// Student info updaten
void updateStudentInfo(UUID id, String firstname, String lastname)

// Email hashen (SHA-256)
String hashEmail(String email)

// Registratie updaten
Registration updateRegistration(Registration registration)

// Registratie ophalen via ID
Optional<Registration> findById(UUID id)
```

**Transactioneel:**
- `@Transactional` op alle write operaties
- Automatische rollback bij exceptions

### EmailVerificationService

**Locatie:** `be.achieveit.snhinschrijvingen.service.EmailVerificationService`

**Verantwoordelijkheden:**
- Verificatie emails verzenden (console output)
- Verificatielinks genereren
- Email templates

**Key Methods:**

```java
// Verificatie email "verzenden"
void sendVerificationEmail(Registration registration)

// Verificatielink bouwen
String buildVerificationLink(Registration registration)

// Email template loggen
void logEmailTemplate(String email, String link)
```

**Configuration:**
```properties
app.base-url=http://localhost:8080
```

**Link Format:**
```
{base-url}/inschrijving/verify/{registrationId}/{emailHash}
```

**Console Output:**
```
================================================================================
EMAIL VERIFICATION LINK FOR: user@example.com
Registration ID: 74b6ba25-8855-4055-a91d-f44c5a09299c
Link: http://localhost:8080/inschrijving/verify/...
================================================================================

╔════════════════════════════════════════════════════════════════╗
║                    EMAIL VERIFICATION                          ║
╚════════════════════════════════════════════════════════════════╝

To: user@example.com
Subject: Verifieer uw emailadres - SPES Nostra Heule

[Email body...]
```

### WizardService

**Locatie:** `be.achieveit.snhinschrijvingen.service.WizardService`

**Verantwoordelijkheden:**
- Wizard stappen genereren
- Status management (active, completed, pending)

**Key Methods:**

```java
// Wizard stappen ophalen met status
List<WizardStep> getWizardSteps(int currentStep, List<Integer> completedSteps)
```

**Wizard Step Model:**
```java
public class WizardStep {
    private int number;        // Stap nummer
    private String label;      // Stap label
    private boolean active;    // Is actieve stap
    private boolean completed; // Is voltooid
}
```

---

## API Endpoints

### Email Verificatie Endpoints

| Method | Endpoint | Controller | Beschrijving |
|--------|----------|-----------|--------------|
| GET | `/inschrijving/start` | EmailVerificationController | Email invoer formulier |
| POST | `/inschrijving/start` | EmailVerificationController | Email versturen + registratie aanmaken |
| GET | `/inschrijving/email-sent` | EmailVerificationController | Bevestigingspagina na email |
| GET | `/inschrijving/verify/{id}/{hash}` | RegistrationController | Email verifiëren + routing |

### Registratie Endpoints

| Method | Endpoint | Controller | Beschrijving |
|--------|----------|-----------|--------------|
| GET | `/inschrijving/continue/{id}` | RegistrationController | Hervatten van registratie |

### Wizard Endpoints

| Method | Endpoint | Controller | Beschrijving |
|--------|----------|-----------|--------------|
| GET | `/inschrijving/student-info?id={id}` | StudentInfoController | Student info formulier |
| POST | `/inschrijving/student-info` | StudentInfoController | Student info opslaan |
| GET | `/inschrijving/richting?id={id}` | StudyProgramController | Studie richting selectie |
| POST | `/inschrijving/richting` | StudyProgramController | Studierichting opslaan |

### Request/Response Examples

**POST /inschrijving/start:**
```http
POST /inschrijving/start HTTP/1.1
Content-Type: application/x-www-form-urlencoded

emailadres=test@example.com
```

Response: Redirect to `/inschrijving/email-sent`

**GET /inschrijving/verify/{id}/{hash}:**
```http
GET /inschrijving/verify/74b6ba25-8855-4055-a91d-f44c5a09299c/973dfe46... HTTP/1.1
```

Response: 
- First time: Redirect to `/inschrijving/student-info?id={id}`
- Has registrations: Show `registrations-overview.html`

---

## Wizard Flow

### Pre-Wizard (Email Verificatie)

```
┌─────────────────────────────────────────────────────────────┐
│ 1. Email Invoer                                             │
│    GET  /inschrijving/start                                 │
│    POST /inschrijving/start                                 │
│         ↓                                                   │
│    • Registration created (PENDING, UNVERIFIED)             │
│    • Email hash generated                                   │
│    • Verification link logged                               │
└─────────────────────────────────────────────────────────────┘
                            ↓
┌─────────────────────────────────────────────────────────────┐
│ 2. Email Sent Bevestiging                                   │
│    GET /inschrijving/email-sent                             │
│         ↓                                                   │
│    • Success alert                                          │
│    • Instructies                                            │
│    • Troubleshooting tips                                   │
└─────────────────────────────────────────────────────────────┘
                            ↓
┌─────────────────────────────────────────────────────────────┐
│ 3. Email Verificatie (via link uit console)                │
│    GET /inschrijving/verify/{id}/{hash}                     │
│         ↓                                                   │
│    • ID + hash validation                                   │
│    • Email status: UNVERIFIED → VERIFIED                    │
│    • Current step: EMAIL_VERIFICATION → STUDENT_INFO        │
│         ↓                                                   │
│    ROUTING DECISION:                                        │
│    ├─ First registration? → student-info                    │
│    └─ Has registrations?  → registrations-overview          │
└─────────────────────────────────────────────────────────────┘
```

### Wizard Stappen (In Development)

```
Stap 1: Algemeen - Student Info (✅ GEÏMPLEMENTEERD)
    ├─ Leerling gegevens (naam, geboortedatum, nationaliteit, etc.)
    ├─ Domicilieadres
    ├─ Vorige school informatie
    └─ Navigatie: Volgende (geen Vorige op eerste stap)

Stap 2: Richting - Study Program Selection (✅ GEÏMPLEMENTEERD)
    ├─ Jaarselectie (dropdown 1-6)
    ├─ Dynamische richtingen weergave
    │   ├─ Jaar 1-2: Platte lijst met radiobuttons
    │   └─ Jaar 3-6: Gegroepeerd per domein/oriëntatie
    └─ Navigatie: Vorige (→ student-info) | Volgende

Stap 3-10: (🚧 TODO)
    └─ Zie TODO.md voor details
```

---

## Validatie Systeem

### Client-Side Validatie ✅

**Status:** ✅ Volledig geïmplementeerd (28 december 2025)  
**Locatie:** `src/main/resources/static/js/form-validation.js`

**Core Features:**
- ✅ Automatische activatie op alle forms met required fields
- ✅ Real-time validatie (na eerste interactie)
- ✅ Nederlandse foutmeldingen
- ✅ Visuele feedback (rood/groen borders + foutmeldingen)
- ✅ Auto-scroll naar eerste fout bij submit
- ✅ Browser native validatie uitgeschakeld (novalidate)

**Ondersteunde Field Types:**
- ✅ Text inputs (voornaam, naam, straat, etc.)
- ✅ Email inputs (met format validatie)
- ✅ Tel inputs (Belgisch telefoonformaat)
- ✅ Select dropdowns (standaard HTML)
- ✅ Select2 dropdowns (met custom events)
- ✅ Radio buttons (inclusief dynamische)
- ✅ Checkboxes
- ✅ Textareas
- ✅ Date inputs (Flatpickr)

**Validatie Rules:**
```javascript
validators = {
    required: 'Dit veld is verplicht',
    email: 'Voer een geldig e-mailadres in',
    phone: 'Voer een geldig telefoonnummer in',
    rijksregisternummer: 'Voer een geldig rijksregisternummer in (YYMMDD-XXX-XX)',
    postalCode: 'Voer een geldige postcode in (4 cijfers)',
    minLength: 'Te kort, minimaal {length} karakters',
    maxLength: 'Te lang, maximaal {length} karakters'
}
```

**Validatie Gedrag:**
1. **Page Load:** Geen validatie (schoon scherm)
2. **Eerste Blur:** Veld wordt "touched", validatie begint
3. **Real-time:** Validatie tijdens typen (alleen als touched)
4. **Correctie:** Foutmelding verdwijnt direct bij fix
5. **Submit:** Alle velden valideren, toon alle fouten, scroll naar eerste

**Visual States:**

```css
/* Normal state */
.form-control {
    border: 1px solid #ccc;
}

/* Focus state - blue */
.form-control:focus {
    border-color: #80bdff;
    box-shadow: 0 0 0 0.2rem rgba(0, 123, 255, 0.25);
}

/* Valid state - green */
.form-control.is-valid {
    border-color: #28a745;
}

/* Invalid state - red */
.form-control.is-invalid {
    border-color: #dc3545;
}

/* Error message */
.invalid-feedback {
    display: block;
    color: #dc3545;
    font-size: 0.875rem;
    margin-top: 0.25rem;
}
```

**Select2 Integratie:**

Select2 dropdowns krijgen speciale behandeling via `select2-init.js`:

```javascript
// Automatisch event handlers na initialisatie
$this.on('select2:select select2:unselect select2:clear change', function() {
    this.dataset.touched = 'true';
    if (typeof window.validateSelect2Field === 'function') {
        window.validateSelect2Field(this);
    }
});
```

Features:
- ✅ Validatie bij selectie/deselectie
- ✅ Rode/groene border op Select2 container
- ✅ Foutmelding verbergt direct bij correcte selectie
- ✅ Werkt voor ALLE Select2 dropdowns automatisch

**Radio Button Validatie:**

Speciale handling voor radio button groepen:

```javascript
// Valideer hele groep, niet per button
if (field.type === 'radio') {
    const radioGroup = document.querySelectorAll(`input[name="${field.name}"]`);
    const isChecked = Array.from(radioGroup).some(radio => radio.checked);
    // Alle buttons in groep krijgen dezelfde state
}
```

Features:
- ✅ Validatie van hele groep (niet per individuele button)
- ✅ Preventie van dubbele validatie bij submit
- ✅ Rode/groene state op alle buttons in groep
- ✅ Foutmelding onder groep (niet per button)
- ✅ Support voor dynamisch gegenereerde buttons (study-program)

**Submit Button Gedrag:**

```javascript
// Button blijft ALTIJD enabled
// Bij submit: validatie toont alle fouten
form.addEventListener('submit', function(event) {
    let formIsValid = true;
    fields.forEach(field => {
        if (!validateField(field, true)) {
            formIsValid = false;
        }
    });
    
    if (!formIsValid) {
        event.preventDefault();
        // Scroll naar eerste fout
        const firstInvalid = form.querySelector('.is-invalid');
        firstInvalid.scrollIntoView({ behavior: 'smooth' });
    }
});
```

**Implementatie per Pagina:**

| Pagina | Status | Validatie Fields |
|--------|--------|------------------|
| email-verification.html | ✅ | Email (format) |
| student-info.html | ✅ | Voornaam, naam, RRN, geboortedatum, adres, GSM, geslacht (radio), nationaliteit (Select2) |
| previous-school.html | ✅ | Vorige school (Select2 + anders), schooljaar (Select2), richting, toestemming (radio) |
| study-program.html | ✅ | Jaar (Select2), richting (radio dynamisch), extra info (optioneel) |

**Configuratie:**

Forms activeren validatie automatisch via:
- `data-validate="true"` attribuut, OF
- `class="needs-validation"`, OF
- Aanwezigheid van `required` velden (auto-detect)

```html
<form data-validate="true" novalidate>
    <!-- Browser validatie uitgeschakeld -->
    <!-- Custom validatie actief -->
</form>
```

---

### Server-Side Validatie

**Status:** 🚧 TODO

**Planned:**
- JSR-380 Bean Validation
- `@Valid` annotations on controller methods
- Custom validators
- Error messages in Dutch

---

## Alert Systeem

### Beschikbare Alert Types

**Locatie:** `src/main/resources/static/css/snh-style.css`

| Type | Class | Gebruik | Voorbeeld |
|------|-------|---------|-----------|
| Primary | `.alert-primary` | Algemene info | Standaard meldingen |
| Success | `.alert-success` | Succesberichten | ✅ Email verzonden |
| Danger | `.alert-danger` | Fouten | ❌ Fout opgetreden |
| Warning | `.alert-warning` | Waarschuwingen | ⚠️ Let op! |
| Info | `.alert-info` | Informatief | ℹ️ Belangrijk |
| Secondary | `.alert-secondary` | Secundair | Minder belangrijk |
| Light | `.alert-light` | Subtiel | Lichte achtergrond |
| Dark | `.alert-dark` | Donker | Voor contrast |

### Alert Features

**Basic Alert:**
```html
<div class="alert alert-info fade show" role="alert">
    <strong>ℹ️ Info:</strong> Dit is een informatief bericht.
</div>
```

**Dismissible Alert:**
```html
<div class="alert alert-warning alert-dismissible fade show" role="alert">
    <strong>⚠️ Waarschuwing!</strong> Dit bericht is sluitbaar.
    <button type="button" class="close" aria-label="Close">
        <span aria-hidden="true">&times;</span>
    </button>
</div>
```

**Alert met Link:**
```html
<div class="alert alert-info fade show" role="alert">
    Meer info? Bekijk onze <a href="#" class="alert-link">FAQ</a>.
</div>
```

**Alert met Icoon:**
```html
<div class="alert alert-info fade show" role="alert">
    <div class="alert-icon">
        <svg width="24" height="24">...</svg>
        <div class="alert-icon-content">
            <strong>Belangrijk:</strong><br>
            Bericht inhoud hier.
        </div>
    </div>
</div>
```

### JavaScript Functionaliteit

**Locatie:** `src/main/resources/static/js/alerts.js`

**Features:**
- Automatic close button handling
- Fade out animatie (150ms)
- DOM cleanup na sluiten
- Toegankelijkheid (ARIA labels)

---

## Navigatie Systeem

### Fragment: navigation.html

**Locatie:** `src/main/resources/templates/fragments/navigation.html`

### Option 1: Form Navigation (Wizard)

**Voor wizard pagina's met form submit:**

```html
<div th:replace="~{fragments/navigation :: 
     navigation(@{/vorige-url}, 'Vorige', 'Volgende', true)}">
</div>
```

**Parameters:**
- `backUrl` - URL voor terug button
- `backLabel` - Label voor terug button (default: "Vorige")
- `nextLabel` - Label voor submit button (default: "Volgende")
- `showBack` - Boolean om terug button te tonen (default: true)

**Renders:**
```html
<div class="form-actions">
    <div class="btn-group">
        <a href="/vorige-url" class="btn btn--outline">
            ← Vorige
        </a>
        <button type="submit" class="btn btn--primary">
            Volgende →
        </button>
    </div>
</div>
```

### Option 2: Simple Back Button

**Voor info pagina's zonder form:**

```html
<div th:replace="~{fragments/navigation :: 
     backButton(@{/terug-url}, 'Terug naar vorige')}">
</div>
```

**Parameters:**
- `backUrl` - URL voor terug button
- `backLabel` - Label voor button (default: "Terug")

**Renders:**
```html
<div class="form-actions">
    <form action="/terug-url" method="get">
        <button type="submit" class="btn btn--secondary">
            ← Terug naar vorige
        </button>
    </form>
</div>
```

---

## Testing

### Manual Testing

**Email Verificatie Flow:**
```bash
# 1. Start applicatie
./mvnw spring-boot:run

# 2. Open browser
http://localhost:8080/inschrijving/start

# 3. Vul email in
test@example.com

# 4. Check console voor verificatie link
# Link format: /verify/{UUID}/{hash}

# 5. Open verificatie link in browser
# First time: Redirects naar student-info
# Has registrations: Shows overview

# 6. Vul student info in en submit

# 7. Zie registrations overview

# 8. Test "Nieuwe leerling inschrijven"

# 9. Gebruik dezelfde verificatie link opnieuw
# Shows overview met alle registraties
```

### Database Inspection (H2 Console)

**Access:**
```
URL: http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:testdb
Username: sa
Password: (empty)
```

**Queries:**
```sql
-- Alle registraties
SELECT * FROM registrations;

-- Registraties voor specifiek email
SELECT * FROM registrations 
WHERE email = 'test@example.com' 
ORDER BY created_at DESC;

-- Pending registraties
SELECT * FROM registrations 
WHERE status = 'PENDING';

-- Verified emails
SELECT * FROM registrations 
WHERE email_status = 'VERIFIED';
```

---

## Documentatie

### Bestaande Documentatie

| Bestand | Inhoud |
|---------|--------|
| `ALERTS.md` | Alert componenten documentatie |
| `FORM_VALIDATION.md` | Form validatie systeem |
| `REGISTRATION_FLOW.md` | Email verificatie flow details |
| `THYMELEAF_STRUCTURE.md` | Thymeleaf templates structuur |
| `VALIDATION_COMPLETE.md` | Validatie implementatie |
| `HELP.md` | Spring Boot help guide |
| `APPLICATION_STATUS.md` | **Dit document** |
| `TODO.md` | Nog te implementeren features |

### Demo Pagina's

**Locatie:** `src/main/resources/static/`

| Pagina | URL | Inhoud |
|--------|-----|--------|
| `alerts-demo.html` | `/alerts-demo.html` | Alle alert types en voorbeelden |
| `buttons-demo.html` | `/buttons-demo.html` | Alle button types en navigatie patterns |
| `validation-demo.html` | `/validation-demo.html` | Form validatie demonstratie |

---

## Project Structure

```
snhinschrijvingen/
├── src/
│   ├── main/
│   │   ├── java/be/achieveit/snhinschrijvingen/
│   │   │   ├── SnhinschrijvingenApplication.java
│   │   │   ├── controller/
│   │   │   │   ├── EmailVerificationController.java
│   │   │   │   ├── RegistrationController.java
│   │   │   │   └── StudentInfoController.java
│   │   │   ├── dto/
│   │   │   │   ├── EmailForm.java
│   │   │   │   └── StudentForm.java
│   │   │   ├── model/
│   │   │   │   ├── Registration.java
│   │   │   │   ├── RegistrationStatus.java
│   │   │   │   ├── EmailStatus.java
│   │   │   │   ├── Nationaliteit.java
│   │   │   │   └── WizardStep.java
│   │   │   ├── repository/
│   │   │   │   └── RegistrationRepository.java
│   │   │   └── service/
│   │   │       ├── RegistrationService.java
│   │   │       ├── EmailVerificationService.java
│   │   │       └── WizardService.java
│   │   └── resources/
│   │       ├── static/
│   │       │   ├── css/
│   │       │   │   └── snh-style.css
│   │       │   ├── js/
│   │       │   │   ├── form-validation.js
│   │       │   │   └── alerts.js
│   │       │   ├── alerts-demo.html
│   │       │   ├── buttons-demo.html
│   │       │   └── validation-demo.html
│   │       ├── templates/
│   │       │   ├── fragments/
│   │       │   │   ├── layout.html
│   │       │   │   ├── wizard.html
│   │       │   │   └── navigation.html
│   │       │   ├── email-verification.html
│   │       │   ├── email-sent.html
│   │       │   ├── registrations-overview.html
│   │       │   └── student-info.html
│   │       └── application.properties
│   └── test/
│       └── java/be/achieveit/snhinschrijvingen/
│           └── SnhinschrijvingenApplicationTests.java
├── target/
├── pom.xml
├── mvnw
├── mvnw.cmd
├── ALERTS.md
├── FORM_VALIDATION.md
├── REGISTRATION_FLOW.md
├── THYMELEAF_STRUCTURE.md
├── VALIDATION_COMPLETE.md
├── HELP.md
├── APPLICATION_STATUS.md (✅ Dit document)
└── TODO.md
```

---

## Configuration

### application.properties

```properties
# Application
spring.application.name=snhinschrijvingen
app.base-url=http://localhost:8080

# H2 Database
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# H2 Console
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

# JPA/Hibernate
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=false

# Thymeleaf
spring.thymeleaf.cache=false
spring.thymeleaf.prefix=classpath:/templates/
spring.thymeleaf.suffix=.html

# Logging
logging.level.be.achieveit.snhinschrijvingen=INFO
logging.level.org.springframework.web=INFO
```

---

## Build & Run

### Maven Commands

```bash
# Clean build
./mvnw clean package

# Run application
./mvnw spring-boot:run

# Run without tests
./mvnw spring-boot:run -DskipTests

# Build JAR
./mvnw clean package

# Run JAR
java -jar target/snhinschrijvingen-0.0.1-SNAPSHOT.jar
```

### Development Mode

```bash
# Start with auto-reload (using spring-boot-devtools)
./mvnw spring-boot:run
```

---

## URLs (Localhost)

| URL | Beschrijving |
|-----|--------------|
| `http://localhost:8080` | Home (redirect naar start) |
| `http://localhost:8080/inschrijving/start` | Email verificatie start |
| `http://localhost:8080/inschrijving/email-sent` | Email sent bevestiging |
| `http://localhost:8080/inschrijving/verify/{id}/{hash}` | Email verificatie link |
| `http://localhost:8080/inschrijving/leerling-info/{id}` | Stap 1: Leerling info |
| `http://localhost:8080/inschrijving/studierichting/{id}` | Stap 2: Studierichting |
| `http://localhost:8080/inschrijving/studierichting/programmas?year={n}` | AJAX: Studierichtingen per jaar |
| `http://localhost:8080/inschrijving/vorige-school/{id}` | Stap 3: Vorige school |
| `http://localhost:8080/inschrijving/relaties/{id}` | Stap 4: Relaties/contactpersonen |
| `http://localhost:8080/inschrijving/huisarts/{id}` | Stap 5: Huisarts |
| `http://localhost:8080/inschrijving/zorgvraag/{id}` | Stap 6: Zorgvraag |
| `http://localhost:8080/inschrijving/privacy/{id}` | Stap 7: Privacy toestemmingen |
| `http://localhost:8080/inschrijving/laptop/{id}` | Stap 8: Laptop |
| `http://localhost:8080/inschrijving/schoolrekening/{id}` | Stap 9: Schoolrekening |
| `http://localhost:8080/inschrijving/verzenden/{id}` | Stap 10: Overzicht & verzenden |
| `http://localhost:8080/inschrijving/bevestiging/{id}` | Bevestigingspagina (na submit) |
| `http://localhost:8080/alerts-demo.html` | Alert componenten demo |
| `http://localhost:8080/buttons-demo.html` | Button types demo |
| `http://localhost:8080/validation-demo.html` | Form validatie demo |
| `http://localhost:8080/h2-console` | H2 Database console |

---

## Git Repository

**Repository:** `snhinschrijvingen`  
**Branch:** `main`

### Important Files

- All Java code in English
- Templates in Dutch (user-facing)
- Documentation in Dutch
- Comments in English (code) / Dutch (documentation)

---

## Contact & Support

**School:** SPES Nostra Heule  
**Developer:** AchieveIT  
**Last Updated:** 26 december 2025

---

---

## 💡 Thymeleaf Best Practices & Lessons Learned

### URL Expressies met Variabelen

**FOUT - Literal substitution zonder escaping:**
```html
<!-- Dit wordt niet geïnterpreteerd en blijft letterlijk ${registrationId} -->
th:action="@{/inschrijving/leerling-info/${registrationId}}"
<!-- Resultaat: /inschrijving/leerling-info/$%7BregistrationId%7D -->
```

**CORRECT - Inline expressie met dubbele underscores:**
```html
<!-- Thymeleaf evalueert de expressie en vervangt deze met de waarde -->
th:action="@{/inschrijving/leerling-info/__${registrationId}__}"
<!-- Resultaat: /inschrijving/leerling-info/a76fea67-026e-43bc-9d66-2baa7680917f -->
```

**Alternatieve syntaxen:**
```html
<!-- Met explicit parameter (verbose): -->
th:action="@{/inschrijving/leerling-info/{id}(id=${registrationId})}"

<!-- Met literal substitution (minder leesbaar): -->
th:action="@{|/inschrijving/leerling-info/${registrationId}|}"

<!-- Onze gekozen syntax (clean en duidelijk): -->
th:action="@{/inschrijving/leerling-info/__${registrationId}__}"
```

### Model Attribute Naming Consistency

**Probleem:** Inconsistente attribute namen tussen controller en template
```java
// Controller:
model.addAttribute("id", registrationId);  // ❌ FOUT

// Template:
${registrationId}  // Geeft null!
```

**Oplossing:** Gebruik dezelfde naam overal
```java
// Controller:
model.addAttribute("registrationId", registrationId);  // ✅ CORRECT

// Template:
${registrationId}  // Werkt perfect!
```

### Entity Field Name Mapping

**Probleem:** Template gebruikt veldnamen die niet bestaan in entity
```html
<!-- Template: -->
${registration.voornaam}  <!-- ❌ Property not found! -->

<!-- Registration entity heeft: -->
private String studentFirstname;  <!-- Niet "voornaam" -->
```

**Oplossing:** Exacte veldnamen gebruiken
```html
<!-- Template: -->
${registration.studentFirstname}  <!-- ✅ CORRECT -->
```

### Null-Safe Expressions

**FOUT - Kan NPE geven:**
```html
${#temporals.format(registration.studentGeboortedatum, 'dd/MM/yyyy')}
```

**CORRECT - Null-safe met ternary:**
```html
${registration.studentGeboortedatum != null ? 
  #temporals.format(registration.studentGeboortedatum, 'dd/MM/yyyy') : '-'}
```

**Of met elvis operator:**
```html
${registration.studentFirstname ?: '-'}
```

### Fragment Calls met URL Parameters

**Syntax voor navigation fragment:**
```html
<!-- FOUT: -->
th:replace="~{fragments/navigation :: navigation(@{/inschrijving/vorige/{id}(id=${registrationId})}, ...)}"

<!-- CORRECT: -->
th:replace="~{fragments/navigation :: navigation(@{/inschrijving/vorige/__${registrationId}__}, ...)}"
```

### Lessons Learned Checklist

- ✅ **Altijd `__${var}__` gebruiken** voor variabelen in `@{...}` URL expressies
- ✅ **Consistente naming** tussen controller model attributes en template variabelen
- ✅ **Exact field names** uit entity gebruiken in templates
- ✅ **Null-safe expressions** met ternary of elvis operator
- ✅ **Test alle URLs** na refactoring van URL structuur
- ✅ **Gebruik dezelfde pattern** overal (maintenance!)

---

## Status Summary

### ✅ Completed - FASE 2 VOLLEDIG VOLTOOID!

**Core Systeem:**
- [x] Email verificatie systeem
- [x] Multi-registratie ondersteuning
- [x] Database model (Registration, Relation, Address, StudyProgram entities)
- [x] Services (Registration, EmailVerification, Wizard, SchoolYear, Nationality, StudyProgram)
- [x] Repositories (Spring Data JPA)

**Alle 10 Wizard Stappen:**
- [x] **Stap 1:** Leerling Info (`/inschrijving/leerling-info/{id}`)
- [x] **Stap 2:** Studierichting (`/inschrijving/studierichting/{id}`)
- [x] **Stap 3:** Vorige School (`/inschrijving/vorige-school/{id}`)
- [x] **Stap 4:** Relaties (`/inschrijving/relaties/{id}`)
- [x] **Stap 5:** Huisarts (`/inschrijving/huisarts/{id}`)
- [x] **Stap 6:** Zorgvraag (`/inschrijving/zorgvraag/{id}`)
- [x] **Stap 7:** Privacy (`/inschrijving/privacy/{id}`)
- [x] **Stap 8:** Laptop (`/inschrijving/laptop/{id}`)
- [x] **Stap 9:** Schoolrekening (`/inschrijving/schoolrekening/{id}`)
- [x] **Stap 10:** Verzenden + Bevestiging (`/inschrijving/verzenden/{id}` + `/bevestiging/{id}`)

**URL Herstructurering:**
- [x] Alle URLs vertaald naar Nederlands
- [x] Query parameters vervangen door path variables
- [x] RESTful URL structuur
- [x] Thymeleaf URL encoding gefixed (`__${var}__` syntax)
- [x] Email verificatie redirects gefixed
- [x] Navigation links consistency

**Critical Bugfixes (18 jan 2026):**
- [x] **Email verificatie link:** Redirect naar nieuwe URL structuur
- [x] **URL encoding:** 10 templates gefixed met `__${registrationId}__`
- [x] **Null registration ID:** Model attribute naam gecorrigeerd
- [x] **Field name mismatch:** Template velden aligned met Registration entity

**Frontend Componenten:**
- [x] **Select2 integratie (4.1.0-rc.0):**
  - Custom styling met SNH branding (#c92617)
  - Optgroup styling voor categorieën
  - Nederlandse vertalingen
  - Zoekfunctionaliteit
  - data-no-scroll attribuut voor kleine datasets
  - Blauwe focus border (#80bdff)
  - Validatie integratie
  
- [x] **Client-side validatie systeem (volledig):**
  - Real-time validatie (form-validation.js)
  - Nederlandse foutmeldingen
  - Support voor alle field types (text, email, tel, select, select2, radio, checkbox, textarea, date)
  - Validatie rules: required, email, phone, rijksregisternummer, postcode, min/max length
  - Visual feedback: rood/groen borders, foutmeldingen
  - Auto-scroll naar eerste fout bij submit
  - **Verborgen velden skippen** (window.getComputedStyle check)
  - Select2 validatie met custom events
  - Radio button groepsvalidatie
  
- [x] Alert systeem (8 types)
- [x] Navigatie fragment (herbruikbaar)
- [x] Button system (primary, secondary, outline)
- [x] Thymeleaf fragments (layout, wizard, navigation, address-fields)
- [x] CSS framework (custom branding met domeinkleuren)
- [x] Demo pagina's (alerts, buttons, validation)
- [x] Flatpickr datepicker integratie (Nederlands, maandag start)

**Database & Data:**
- [x] Study programs database met 100+ richtingen
- [x] Study domains met kleuren (4 domeinen)
- [x] Study orientations (3 types)
- [x] Nationaliteiten lijst
- [x] Schooljaren beheer

**Herbruikbare Componenten:**
- [x] Address fragment (`fragments/address-fields.html`)
- [x] Address model class (embedded entity)
- [x] Gebruikt door student-info EN relations
- [x] "Kopieer domicilieadres" functionaliteit in relations

**Specifieke Features per Stap:**

**Stap 1 - Leerling Info:**
- Alle persoonlijke gegevens leerling
- Embedded Address object voor domicilieadres
- GSM en email validatie
- Flatpickr datepicker

**Stap 2 - Studierichting:**
- Dynamische filtering per leerjaar (AJAX)
- Gegroepeerde weergave jaar 3-6 (domeinen/oriëntaties)
- Platte lijst jaar 1-2
- Kleurcodering per domein

**Stap 3 - Vorige School:**
- Select2 dropdown met 22+ scholen
- Gegroepeerd (basis/secundair/anders)
- "Anders" optie met vrij tekstveld
- Toestemming gegevensuitwisseling

**Stap 4 - Relaties:**
- Relation JPA entity (one-to-many)
- Min 1, max 2 relaties
- 9 relatietypes (vader, moeder, plus-, voogd, groot-, pleeg-)
- Dynamisch toevoegen/verwijderen
- "Kopieer domicilieadres" knop
- Volledig gevalideerd

**Stap 5 - Huisarts:**
- Naam (verplicht)
- Telefoonnummer (optioneel)

**Stap 6 - Zorgvraag:**
- Wensen klassamenstelling
- Bijzondere zorgvraag (J/N)
- **Conditionele sectie** met JavaScript toggle:
  - Privacyverklaring
  - Medische zorg (7 vragen)
  - Sociaal-emotioneel
  - Leren en studeren
  - Externe ondersteuning

**Stap 7 - Privacy:**
- 4 toestemmingsvragen (alle verplicht):
  - Foto's/filmfragmenten
  - Studieresultaten delen
  - Oud-leerling data
  - Hoger onderwijs

**Stap 8 - Laptop:**
- Signpost laptop merk (optioneel)
- Laptopproject info box met:
  - Prijsinformatie
  - Eigendomsregeling
  - Voordelen (6 bullets)
  - PDF download link

**Stap 9 - Schoolrekening:**
- Financieel gesprek gewenst (J/N, verplicht)
- Uitleg financiële problemen

**Stap 10 - Verzenden + Bevestiging:**
- **Read-only overzicht** alle ingevoerde data (9 secties)
- **Ondertekening sectie:**
  - Juridische tekst (B.W. artikels)
  - Naam ouder/voogd (verplicht)
  - Schoolreglement checkbox (verplicht)
  - Bijkomende info checkbox (optioneel)
- "Inschrijving voltooien" button
- **Bevestigingspagina:**
  - Geen wizard steps
  - Success indicator (groene checkmark)
  - Email bevestigingsmelding
  - Submission datum/tijd + UUID
  - Read-only samenvatting
  - "Terug naar home" button

### 🚧 In Progress
- Geen - Fase 2 is volledig afgerond inclusief bugfixes!

### 📋 To Do (Fase 3)
- Email integratie (echte verzending)
- Server-side validatie
- Error handling & logging
- Testing (unit, integration)
- Production database (PostgreSQL/MySQL)
- Deployment configuratie
- Admin dashboard

---

## 🐛 Recente Bugfixes (18 januari 2026)

### Email Verificatie Link Fout
**Probleem:** Email verificatie link redirected naar oude URL met query parameters  
**Oplossing:** 
- `RegistrationController.verifyEmail()` aangepast: `redirect:/inschrijving/leerling-info/{id}`
- `RegistrationController.continueRegistration()` uitgebreid met alle 10 wizard stappen

### URL Encoding Probleem
**Probleem:** URLs werden gerenderd als `/inschrijving/leerling-info/$%7BregistrationId%7D`  
**Oorzaak:** Thymeleaf variabelen in `@{...}` expressies niet correct escaped  
**Oplossing:** Alle templates gefixed met `__${registrationId}__` syntax
- ✅ 10 form actions gefixed
- ✅ 6 navigation fragment calls gefixed  
- ✅ 2 href links gefixed

**Gefixte templates:**
1. student-info.html
2. study-program.html  
3. previous-school.html
4. relations.html
5. doctor.html
6. care-needs.html
7. privacy.html
8. laptop.html
9. school-account.html
10. submission.html

### Null Registration ID Probleem
**Probleem:** Van `/vorige-school/{id}` naar volgende ging naar `/vorige-school/null`  
**Oorzaak:** PreviousSchoolController gebruikte `model.addAttribute("id", id)` ipv `"registrationId"`  
**Oplossing:** Model attribute naam gecorrigeerd + navigation link syntax gefixed

### Field Name Mismatch
**Probleem:** Templates gebruikten veldnamen die niet bestaan in Registration entity  
**Voorbeeld:** `${registration.voornaam}` → Property not found error  
**Oplossing:** Alle velden in submission.html en confirmation.html gecorrigeerd:
- `voornaam` → `studentFirstname`
- `naam` → `studentLastname`  
- `geboortedatum` → `studentGeboortedatum`
- `geslacht` → `studentGeslacht`
- `rijksregisternummer` → `studentRijksregisternummer`
- `nationaliteit` → `studentNationaliteit`
- `gsmNummer` → `studentGsm`
- Null-safe date formatting toegevoegd
- Dubbel GSM veld verwijderd
- Label verduidelijkt: "E-mailadres ouder/voogd", "GSM leerling"

**Impact:** Volledige wizard flow werkt nu van start tot bevestiging zonder errors!

---

**End of Document**
