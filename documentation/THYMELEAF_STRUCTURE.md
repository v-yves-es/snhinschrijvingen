# SNH Inschrijvingen - Thymeleaf Structure

## Project Structure

```
src/main/
├── java/be/achieveit/snhinschrijvingen/
│   ├── controller/
│   │   ├── EmailVerificationController.java    # Email verification step
│   │   └── StudentInfoController.java          # Student info step (wizard step 4)
│   ├── dto/
│   │   ├── EmailForm.java                      # Form for email verification
│   │   └── StudentForm.java                    # Form for student information
│   ├── model/
│   │   ├── WizardStep.java                     # Wizard step model with state
│   │   └── Nationaliteit.java                  # Nationality option model
│   └── service/
│       └── WizardService.java                  # Service managing wizard steps
│
└── resources/
    ├── static/
    │   └── css/
    │       └── snh-style.css                   # Main stylesheet
    └── templates/
        ├── fragments/
        │   ├── layout.html                     # Base layout with header
        │   ├── header.html                     # Header fragment
        │   └── wizard.html                     # Reusable wizard navigation
        ├── email-verification.html             # Email verification page (no wizard)
        └── student-info.html                   # Student info page (wizard step 4)
```

## Key Features

### 1. **Thymeleaf Fragments for Reusability**

#### Layout Fragment (`fragments/layout.html`)
- Base layout accepting `title` and `content` parameters
- Includes header and CSS references
- Eliminates code duplication across pages

#### Wizard Fragment (`fragments/wizard.html`)
- Reusable wizard navigation component
- **State managed in Java** for better maintainability
- CSS classes determined server-side
- Automatically styles active/completed/inactive steps

#### Header Fragment (`fragments/header.html`)
- Logo and title displayed on all pages
- Consistent branding across application

### 2. **Two-Screen Flow**

#### Screen 1: Email Verification (`/inschrijving/start`)
- **NO wizard navigation** (as requested)
- Simple email input form
- Controller: `EmailVerificationController`
- Redirects to student info after submission

#### Screen 2: Student Info (`/inschrijving/student-info`)
- **WITH wizard navigation** (step 4 of 10)
- Shows steps 1-3 as completed
- Comprehensive student information form
- Controller: `StudentInfoController`

### 3. **Wizard Service**

The `WizardService` centralizes wizard configuration with **state management**:
- Defines all 10 wizard steps
- `getWizardSteps(currentStep, completedSteps)` prepares steps with correct state
- Each WizardStep has `isActive()`, `isCompleted()`, and `getCssClass()` methods
- Logic in Java instead of complex Thymeleaf expressions

### 4. **Controllers Follow Spring Boot Best Practices**

- Constructor injection for dependencies
- `private final` fields
- Stateless services
- Clear separation of concerns
- State prepared in controller, not in template

## Running the Application

```bash
./mvnw spring-boot:run
```

Then navigate to:
- Email verification: http://localhost:8080/inschrijving/start
- Student info: http://localhost:8080/inschrijving/student-info

## Usage Example: Adding a New Wizard Step

1. **Update `WizardService` constructor:**
```java
wizardSteps.add(new WizardStep(11, "New Step", "/inschrijving/new-step"));
```

2. **Create controller:**
```java
@Controller
@RequestMapping("/inschrijving")
public class NewStepController {
    private final WizardService wizardService;
    
    public NewStepController(final WizardService wizardService) {
        this.wizardService = wizardService;
    }
    
    @GetMapping("/new-step")
    public String showNewStep(Model model) {
        model.addAttribute("wizardSteps", 
            wizardService.getWizardSteps(11, List.of(1, 2, 3, 4, ...)));
        // Add form and other attributes
        return "new-step";
    }
}
```

3. **Create template using layout:**
```html
<!DOCTYPE html>
<html lang="nl" 
      xmlns:th="http://www.thymeleaf.org"
      th:replace="~{fragments/layout :: layout(~{::title}, ~{::section})}">
<head>
    <title>New Step - Inschrijvingen SNH</title>
</head>
<body>
    <section>
        <div th:replace="~{fragments/wizard :: wizard}"></div>
        <div class="container">
            <!-- Your form content here -->
        </div>
    </section>
</body>
</html>
```

## Thymeleaf Techniques Used

1. **Layout Inheritance**: Base layout with parameterized fragments
2. **Fragment Expressions**: `~{::title}` and `~{::section}` syntax
3. **Simple Fragment Inclusion**: Wizard uses `th:replace` without parameters
4. **Server-Side State Management**: CSS classes determined in Java
5. **Form Binding**: `th:object` and `th:field` for form handling
6. **Iteration**: `th:each` for rendering wizard steps and options
7. **URL Generation**: `th:href="@{/path}"` for context-aware URLs

## ⚠️ Critical: Thymeleaf URL Expression Syntax

### Variabelen in URL Expressies

**Probleem dat we hadden:**
URLs werden gerenderd als `/inschrijving/leerling-info/$%7BregistrationId%7D` ipv `/inschrijving/leerling-info/123`.

**Oorzaak:**
Thymeleaf variabelen in `@{...}` URL expressies moeten **dubbele underscores** gebruiken om correct te worden geïnterpreteerd als expressies.

### ✅ CORRECTE Syntax

**Gebruik altijd `__${variable}__` binnen URL expressies:**

```html
<!-- ✅ CORRECT: Form actions -->
<form th:action="@{/inschrijving/leerling-info/__${registrationId}__}" method="post">

<!-- ✅ CORRECT: Href links -->
<a th:href="@{/inschrijving/studierichting/__${registrationId}__}">Volgende</a>

<!-- ✅ CORRECT: Fragment parameters -->
<div th:replace="~{fragments/navigation :: navigation(
    @{/inschrijving/vorige-school/__${registrationId}__}, 
    'Vorige', 
    'Volgende', 
    true
)}"></div>
```

### ❌ INCORRECTE Syntax (VERMIJDEN!)

```html
<!-- ❌ FOUT: Literal ${...} zonder dubbele underscores -->
<form th:action="@{/inschrijving/leerling-info/${registrationId}}" method="post">

<!-- ❌ FOUT: Wordt gerenderd als URL-encoded string -->
<!-- Resultaat: /inschrijving/leerling-info/$%7BregistrationId%7D -->
```

### Waarom Dubbele Underscores?

In Thymeleaf hebben underscores een speciale betekenis binnen URL expressies:
- `__${variable}__` = **Preprocessing expressie** - wordt eerst geëvalueerd
- `${variable}` = **Gewone expressie** - wordt behandeld als literal string in URL context

### Best Practices

1. **Altijd `__${variable}__`** gebruiken in `@{...}` URL expressies
2. **Test URLs** na implementatie om encoding issues te vermijden
3. **Consistent** gebruik door hele applicatie
4. **Code reviews** checken op correcte syntax

### Voorbeelden uit Applicatie

**Student Info Form:**
```html
<form th:action="@{/inschrijving/leerling-info/__${registrationId}__}" 
      method="post" 
      th:object="${studentForm}">
```

**Study Program Form:**
```html
<form th:action="@{/inschrijving/studierichting/__${registrationId}__}" 
      method="post">
```

**Navigation Fragment:**
```html
<div th:replace="~{fragments/navigation :: navigation(
    @{/inschrijving/huisarts/__${registrationId}__},
    'Vorige',
    'Volgende',
    true
)}"></div>
```

### Debugging Tips

Als URLs niet correct worden gerenderd:
1. ✅ Check of `__${variable}__` syntax wordt gebruikt
2. ✅ Verifieer dat variabele naam correct is in model
3. ✅ Controleer browser developer tools Network tab voor daadwerkelijke URL
4. ✅ Test met verschillende browsers

## CSS Naming Conventions

### BEM Methodologie

We gebruiken **BEM (Block Element Modifier)** voor CSS class namen:

```
block__element--modifier
```

**Voorbeelden:**
- `info-box` (block)
- `info-box__title` (element)
- `info-box__text` (element)
- `info-box--error` (modifier)
- `info-box--warning` (modifier)

### ⚠️ Probleem met Dubbele Underscores in CSS

**LET OP:** Vermijd verwarring tussen:
- **Thymeleaf preprocessing:** `__${variable}__` (dubbele underscores)
- **BEM CSS naming:** `block__element` (dubbele underscores)

**Dit zijn twee totaal verschillende contexten!**

### CSS Class Naming Regels

**✅ CORRECT:**
```html
<!-- CSS classes: gebruik BEM met dubbele underscores -->
<div class="info-box info-box--error">
    <h2 class="info-box__title">Foutcode: 404</h2>
    <p class="info-box__text">Pagina niet gevonden</p>
</div>

<!-- URL expressies: gebruik __${variable}__ -->
<form th:action="@{/inschrijving/leerling-info/__${registrationId}__}">
```

**❌ FOUT - Verwar ze niet:**
```html
<!-- ❌ CSS class met Thymeleaf syntax (FOUT!) -->
<div class="summary-item-value__${status}">

<!-- ❌ URL expressie met CSS syntax (FOUT!) -->
<form th:action="@{/inschrijving/leerling-info/${registrationId}}">
```

### Beste Praktijken

1. **CSS Classes:** Gebruik BEM (`block__element--modifier`)
2. **CSS variabelen:** Gebruik custom properties (`--primary-color`)
3. **Thymeleaf URLs:** Gebruik preprocessing (`__${var}__`)
4. **Avoid mixing:** Houd CSS en Thymeleaf syntax gescheiden

### CSS Class Voorbeelden

```css
/* Block */
.info-box { }

/* Elements (dubbele underscore) */
.info-box__title { }
.info-box__text { }
.info-box__list { }

/* Modifiers (dubbele streepje) */
.info-box--primary { }
.info-box--info { }
.info-box--success { }
.info-box--warning { }
.info-box--error { }

/* Combined */
.summary-item-value { }
.summary-item-value--empty { }
.summary-item-value--multiline { }
```

## Design Decision: State in Java vs. Thymeleaf

**Why we moved wizard state logic to Java:**

- ❌ **Complex Thymeleaf expressions** are hard to read and debug
- ❌ **Multi-line expressions** cause parsing issues
- ❌ **Nested ternary operators** are error-prone
- ✅ **Java is type-safe** and easier to test
- ✅ **Better IDE support** and refactoring
- ✅ **Cleaner templates** focus on presentation
- ✅ **Easier to maintain** and extend

## Benefits of This Structure

- ✅ **DRY Principle**: No code duplication with fragments
- ✅ **Maintainability**: Wizard logic in single reusable component
- ✅ **Flexibility**: Easy to add/modify wizard steps
- ✅ **Consistency**: Unified look and feel across pages
- ✅ **Type Safety**: Java models with proper DTOs
- ✅ **Best Practices**: Constructor injection, final fields, service layer
- ✅ **Clean Templates**: Logic in Java, presentation in HTML
- ✅ **Correct URL Generation**: Consistent use of `__${variable}__` syntax
- ✅ **BEM CSS**: Maintainable and scalable styling

## Common Pitfalls to Avoid

### 1. URL Encoding Issues
❌ `@{/path/${var}}`  
✅ `@{/path/__${var}__}`

### 2. Mixing CSS and Thymeleaf Syntax
❌ Class names with Thymeleaf variables  
✅ Separate concerns: static CSS classes, dynamic Thymeleaf logic

### 3. Complex Logic in Templates
❌ Multi-line Thymeleaf expressions  
✅ Prepare data in controller/service

### 4. Hardcoded Values
❌ Duplicate data mapping in templates  
✅ Service methods for data transformation

### 5. Inconsistent Naming
❌ Mixed CSS naming conventions  
✅ Consistent BEM methodology throughout

---

**End of Document**

**Samenvatting:**
- **Thymeleaf URLs:** Gebruik `__${variable}__` in `@{...}` expressies
- **CSS Classes:** Gebruik BEM met `block__element--modifier`
- **Scheiding:** Houd Thymeleaf syntax en CSS naming gescheiden
- **Consistency:** Test en review voor correcte implementatie

