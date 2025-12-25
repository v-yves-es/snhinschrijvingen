# SNH Inschrijvingen - Applicatie Status

**Versie:** 0.0.1-SNAPSHOT  
**Laatst bijgewerkt:** 25 december 2025  
**Spring Boot:** 3.5.9  
**Java:** 25  

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
🚧 **Fase 2 in ontwikkeling**: Volledige wizard implementatie

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
│   ├── EmailVerificationController.java
│   ├── RegistrationController.java
│   └── StudentInfoController.java
├── dto/
│   ├── EmailForm.java
│   └── StudentForm.java
├── model/
│   ├── Registration.java
│   ├── RegistrationStatus.java
│   ├── EmailStatus.java
│   ├── Nationaliteit.java
│   └── WizardStep.java
├── repository/
│   └── RegistrationRepository.java
└── service/
    ├── RegistrationService.java
    ├── EmailVerificationService.java
    └── WizardService.java
```

---

## Database Model

### Entity: Registration

**Tabel:** `registrations`

| Veld | Type | Constraints | Beschrijving |
|------|------|------------|--------------|
| `id` | UUID | PRIMARY KEY | Uniek inschrijvings-ID |
| `email` | String | NOT NULL | Emailadres ouder/voogd |
| `email_hash` | String | NOT NULL, UNIQUE | SHA-256 hash van email |
| `school_year` | String | NOT NULL | Schooljaar (bijv. "2025-2026") |
| `status` | Enum | NOT NULL | PENDING / COMPLETED |
| `current_step` | String | | Huidige wizard stap |
| `email_status` | Enum | NOT NULL | UNVERIFIED / VERIFIED / ERROR |
| `created_at` | LocalDateTime | NOT NULL | Aanmaakdatum |
| `updated_at` | LocalDateTime | | Laatste wijziging |
| `student_firstname` | String | | Voornaam leerling |
| `student_lastname` | String | | Achternaam leerling |

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
1. Email Verificatie (pre-wizard)
2. Email Sent Info (pre-wizard)
3. *(Placeholder)*
4. Student Info (GEÏMPLEMENTEERD)
5-10. *(Nog te implementeren)*

**Wizard Features:**
- Visuele stappenbalk bovenaan
- Actieve stap highlighted
- Voltooide stappen met vinkje
- Responsief design

### ✅ Student Info Formulier

**Velden:**
- Voornaam leerling (verplicht)
- Naam leerling (verplicht)
- Geboortedatum
- Geboorteplaats
- Nationaliteit (dropdown)
- Rijksregisternummer
- Geslacht (radio buttons)

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

---

## Frontend Componenten

### CSS Framework

**Locatie:** `src/main/resources/static/css/snh-style.css`

**Features:**
- ✅ Custom branding (Belfius Red: #c92617)
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
Stap 4: Student Info (✅ GEÏMPLEMENTEERD)
    ├─ Voornaam, naam, geboortedatum
    ├─ Nationaliteit, rijksregisternummer
    └─ Navigatie: Vorige (→ start) | Volgende

Stap 5-10: (🚧 TODO)
    └─ Zie TODO.md voor details
```

---

## Validatie Systeem

### Client-Side Validatie

**Locatie:** `src/main/resources/static/js/form-validation.js`

**Features:**
- Automatische activatie op forms met `data-validate="true"`
- Real-time validatie bij blur event
- Visuele states:
  - **Focus:** Blauwe border (blijft onderaan andere states)
  - **Valid:** Groene border met ✓ icon
  - **Invalid:** Rode border met foutmelding

**Implementation:**
```javascript
document.addEventListener('DOMContentLoaded', function() {
    const forms = document.querySelectorAll('form[data-validate="true"]');
    
    forms.forEach(form => {
        const inputs = form.querySelectorAll('input, select, textarea');
        
        inputs.forEach(input => {
            // Focus state
            input.addEventListener('focus', () => {
                input.classList.add('is-focused');
            });
            
            // Validation on blur
            input.addEventListener('blur', () => {
                input.classList.remove('is-focused');
                if (input.checkValidity()) {
                    input.classList.remove('is-invalid');
                    input.classList.add('is-valid');
                } else {
                    input.classList.remove('is-valid');
                    input.classList.add('is-invalid');
                }
            });
        });
    });
});
```

**CSS States:**
```css
/* Focus state - blue border */
.form-control:focus {
    border-color: #007bff;
    box-shadow: 0 0 0 0.2rem rgba(0, 123, 255, 0.25);
}

/* Valid state - green border */
.form-control.is-valid {
    border-color: #28a745;
}

/* Invalid state - red border */
.form-control.is-invalid {
    border-color: #dc3545;
}
```

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
| `http://localhost:8080/inschrijving/student-info?id={id}` | Student info formulier |
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
**Last Updated:** 25 december 2025

---

## Status Summary

### ✅ Completed
- Email verificatie systeem
- Multi-registratie ondersteuning
- Database model (Registration entity)
- Services (Registration, EmailVerification, Wizard)
- Repositories (Spring Data JPA)
- Student Info formulier (wizard stap 4)
- Wizard structuur en navigatie
- Alert systeem (8 types)
- Form validatie (client-side)
- Navigatie fragment (herbruikbaar)
- Button system (primary, secondary, outline)
- Thymeleaf fragments (layout, wizard, navigation)
- CSS framework (custom branding)
- Demo pagina's (alerts, buttons, validation)

### 🚧 In Progress
- N/A (Fase 1 voltooid)

### 📋 To Do
- Zie `TODO.md` voor volledige lijst
- Email integratie (echte verzending)
- Wizard stappen 5-10
- Server-side validatie
- Error handling
- Testing (unit, integration)
- Production database (PostgreSQL/MySQL)
- Deployment configuratie

---

**End of Document**
