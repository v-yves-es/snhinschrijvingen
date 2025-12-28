# SNH Inschrijvingen - TODO List

**Laatst bijgewerkt:** 26 december 2025  
**Status:** Fase 2 gedeeltelijk voltooid, voorbereiding Fase 3

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
- [ ] Wizard stappen 3-8 implementeren
- [ ] Study program selectie persisteren in database
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

### ✅ Stap 1: Algemeen (Student Info) - VOLTOOID

**Status:** ✅ Completed

**Geïmplementeerd:**
- [x] StudentForm DTO aangemaakt
- [x] Controller endpoint: `/inschrijving/student-info`
- [x] Template: `student-info.html`
- [x] Form sectie: Leerling (voornaam, naam, rijksregisternummer, geboortedatum, geboorteplaats, geslacht, nationaliteit)
- [x] Form sectie: Domicilieadres (straat, nummer, postcode, gemeente, GSM)
- [x] Form sectie: Vorige school (naam/adres, jaar, richting, toestemming gegevensuitwisseling)
- [x] Registration entity uitgebreid met alle velden
- [x] Service methods: `updateStudentInfo()`
- [x] Client-side validatie
- [x] Flatpickr datepicker integratie (Nederlands)
- [x] Wizard update: stap 1 actief
- [x] Pre-fill bij herladen
- [x] Responsive layout (Bootstrap grid)

---

### ✅ Stap 2: Vorige School (Previous School) - VOLTOOID

**Status:** ✅ Completed

**Geïmplementeerd:**
- [x] PreviousSchoolForm DTO aangemaakt
- [x] Controller endpoint: `/inschrijving/previous-school`
- [x] Template: `previous-school.html`
- [x] Select2 dropdown met zoekfunctie
- [x] Gegroepeerde schoollijst:
  - Basisonderwijs (17 scholen)
  - Secundair onderwijs (5 scholen)
  - Andere (custom input)
- [x] "Anders" functionaliteit met dynamisch tekstveld
- [x] Jaar vorige school (radio buttons: 3de/4de jaar + anders)
- [x] Gevolgde richting (text input)
- [x] Toestemming gegevensuitwisseling (ja/nee)
- [x] Wizard navigatie (vorige/volgende)
- [x] Client-side validatie
- [x] Pre-fill bij herladen

**Select2 Integratie:**
- [x] Select2 bibliotheek geïntegreerd (4.1.0-rc.0)
- [x] Custom styling met SNH branding (#c92617)
- [x] Optgroup styling voor categorieën
- [x] Nederlandse vertalingen
- [x] Zoekfunctionaliteit
- [x] data-no-scroll attribuut voor kleine lijsten
- [x] Arrow styling gefixed (geen box meer)
- [x] Responsive design

---

### ✅ Stap 3: Studierichting (Study Program Selection) - VOLTOOID

**Status:** ✅ Completed

**Geïmplementeerd:**
- [x] StudyProgramForm DTO aangemaakt
- [x] Controller endpoint: `/inschrijving/study-program`
- [x] Template: `study-program.html`
- [x] Database entities: StudyDomain, StudyOrientation, StudyProgram
- [x] Services: StudyProgramService, StudyDomainService, StudyOrientationService
- [x] Select2 jaar dropdown (1-6) met data-no-scroll
- [x] Dynamische richtingen weergave per jaar (AJAX)
- [x] Jaar 1-2: Platte lijst met radiobuttons
- [x] Jaar 3-6: Gegroepeerd per domein/oriëntatie met kleurcodering
- [x] Data initialisatie (100+ studierichtingen)
- [x] Info box voor 2de jaar (plus/accent klassen)
- [x] Extra info textarea bij twijfel (optioneel)
- [x] Wizard navigatie (vorige/volgende)
- [x] Client-side validatie
- [x] Pre-fill bij herladen

**Database vulling:**
- [x] Study domains met kleuren (Economie, Maatschappij, STEM, Taal)
- [x] Study orientations (Doorstroom, Doorstroom/arbeidsmarkt, Arbeidsmarkt)
- [x] Study programs voor jaar 1-6 (volledige lijst)

---

### Stap 4: Nog te bepalen

**Prioriteit:** 🔴 Hoog  
**Status:** ❌ Not Started

**Volgende stappen in planning**

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
- [ ] Parent entities en relaties implementeren

### Sprint 2 (Week 3-4): Wizard Voltooiing Deel 1
- [ ] Stap 4: Te bepalen
- [ ] Stap 5: Contactgegevens Ouders

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
