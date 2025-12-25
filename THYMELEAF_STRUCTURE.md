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

