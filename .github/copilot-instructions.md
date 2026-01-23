---
description: 'Guidelines for building Spring Boot base applications'
applyTo: '**/*.java, **/*.kt'
---

# Spring Boot Development

## General Instructions

- Make only high confidence suggestions when reviewing code changes.
- Write code with good maintainability practices, including comments on why certain design decisions were made.
- Handle edge cases and write clear exception handling.
- For libraries or external dependencies, mention their usage and purpose in comments.
- **ALWAYS start by reviewing `/documentation/APPLICATION_STATUS.md` and `/documentation/TODO.md`** to understand the current project state and priorities before making changes.
- **ALWAYS consult the latest Spring Boot documentation via Context7 MCP server** when working on Spring Boot related tasks to ensure best practices and up-to-date patterns are followed.
- When in doubt about Spring Boot features, use Context7 to query the official Spring Boot documentation library.

## Spring Boot Instructions

### Dependency Injection

- Use constructor injection for all required dependencies.
- Declare dependency fields as `private final`.

### Configuration

- Use property files (`application.properties`) for externalized configuration.
- Environment Profiles: Use Spring profiles for different environments (dev, test, prod)
- Configuration Properties: Use @ConfigurationProperties for type-safe configuration binding
- Secrets Management: Externalize secrets using environment variables or secret management systems

### Code Organization

- Package Structure: Organize by layer
- Separation of Concerns: Keep controllers thin, services focused, and repositories simple
- Utility Classes: Make utility classes final with private constructors

### Service Layer

- Place business logic in `@Service`-annotated classes.
- Services should be stateless and testable.
- Inject repositories via the constructor.
- Service method signatures should use domain IDs or DTOs, not expose repository entities directly.

### Logging

- Use SLF4J for all logging (`private static final Logger logger = LoggerFactory.getLogger(MyClass.class);`).
- Do not use concrete implementations (Logback, Log4j2) or `System.out.println()` directly.
- Use parameterized logging: `logger.info("User {} logged in", userId);`.

### Security & Input Handling

- Use parameterized queries | Always use Spring Data JPA or `NamedParameterJdbcTemplate` to prevent SQL injection.
- Validate request bodies and parameters using JSR-380 (`@NotNull`, `@Size`, etc.) annotations and `BindingResult`

### Exception Handling

- Use `@ControllerAdvice` for centralized exception handling across all controllers.
- Implement RFC 7807 Problem Details for HTTP APIs by setting `spring.mvc.problemdetails.enabled=true`.
- Create custom exception classes that extend appropriate base exceptions.
- Return consistent error response structures with proper HTTP status codes.

### Testing

- Write unit tests for services using mocks (Mockito).
- Use `@WebMvcTest` for isolated controller layer tests.
- Use `@DataJpaTest` for repository layer tests with in-memory database.
- Write integration tests with `@SpringBootTest` for end-to-end scenarios.
- Aim for high test coverage of business logic.

### API Design

- Follow REST principles consistently (proper HTTP methods: GET, POST, PUT, DELETE, PATCH).
- Use appropriate HTTP status codes (200, 201, 204, 400, 404, 500, etc.).
- Use DTOs for request/response objects, never expose entities directly.
- Document APIs using SpringDoc OpenAPI (formerly Swagger).

### Transaction Management

- Apply `@Transactional` at service level, not repository level.
- Specify `readOnly=true` for read-only transactions to optimize performance.
- Keep transactions short and avoid long-running operations within them.
- Be mindful of transaction boundaries and propagation settings.

### Performance & Monitoring

- Enable Spring Boot Actuator for production monitoring (`spring-boot-starter-actuator`).
- Expose relevant endpoints: `/actuator/health`, `/actuator/metrics`, `/actuator/info`.
- Use HikariCP connection pooling (default in Spring Boot) and tune pool settings.
- Implement caching strategically using `@Cacheable`, `@CachePut`, `@CacheEvict`.
- Monitor application metrics and set up alerts for critical thresholds.

## Build and Verification

- After adding or modifying code, verify the project continues to build successfully.
- If the project uses Maven, run `mvn clean package`.
- Ensure all tests pass as part of the build.

## Documentation References

### Context7 Spring Boot Documentation
- **ALWAYS** check Context7 for latest Spring Boot documentation before implementing features
- Use Context7 MCP server to query: `/spring-projects/spring-boot` for latest practices
- Verify dependency versions and best practices against official Spring documentation
- Check for security advisories and deprecation notices
- For Thymeleaf questions, query `/thymeleaf/thymeleaf` via Context7
- For Spring Data JPA, query `/spring-projects/spring-data-jpa` via Context7
- For Spring Security features, query `/spring-projects/spring-security` via Context7

### Project Documentation
Before starting work, **ALWAYS** review in this order:
1. `@documentation/TODO.md` - Current tasks and priorities
2. `@documentation/APPLICATION_STATUS.md` - System architecture and implemented features
3. Then relevant specific docs:
   - `@documentation/REGISTRATION_FLOW.md` - Wizard flow details
   - `@documentation/FORM_VALIDATION.md` - Validation rules and patterns
   - `@documentation/THYMELEAF_STRUCTURE.md` - Template patterns and fragments

## Useful Commands

| Gradle Command            | Maven Command                     | Description                                   |
|:--------------------------|:----------------------------------|:----------------------------------------------|
| `./gradlew bootRun`       |`./mvnw spring-boot:run`           | Run the application.                          |
| `./gradlew build`         |`./mvnw package`                   | Build the application.                        |
| `./gradlew test`          |`./mvnw test`                      | Run tests.                                    |
| `./gradlew bootJar`       |`./mvnw spring-boot:repackage`     | Package the application as a JAR.             |
| `./gradlew bootBuildImage`|`./mvnw spring-boot:build-image`   | Package the application as a container image. |

## Spring Boot Best Practices

### REST Controllers
- Use `@RestController` for API endpoints, `@Controller` for Thymeleaf views
- Follow RESTful URL patterns: `/resource/{id}` not `/resource?id={id}`
- Use proper HTTP methods: GET (read), POST (create/update), DELETE (delete)
- Return appropriate HTTP status codes

### Exception Handling
- Use `@ControllerAdvice` for global exception handling
- Create custom exceptions that extend `RuntimeException`
- Log exceptions with appropriate levels (ERROR for unexpected, WARN for business logic)
- Return user-friendly error messages in Dutch

### Data Access
- Always use `@Transactional` on service methods that modify data
- Use `Optional<T>` for methods that might not find entities
- Prefer Spring Data JPA derived query methods over custom queries when possible
- Use `@Query` for complex queries that can't be derived

### Performance
- Use lazy loading for relationships (default for `@OneToMany`, `@ManyToMany`)
- Add database indexes on frequently queried fields
- Use pagination for large result sets
- Cache frequently accessed, rarely changing data with `@Cacheable`

### Testing
- Write unit tests for services (mock repositories)
- Write integration tests for controllers (use `@SpringBootTest` and `MockMvc`)
- Use `@DataJpaTest` for repository tests
- Aim for 80%+ test coverage on business logic

### Thymeleaf Best Practices
- **URL Expressions:** Always use `__${variable}__` syntax for variables in `@{...}` URL expressions
  - Example: `th:action="@{/inschrijving/leerling-info/__${registrationId}__}"`
  - Never use literal `${...}` without double underscores (results in URL encoding issues)
- **Model Attributes:** Use consistent naming between controller and template
  - Controller: `model.addAttribute("registrationId", id)`
  - Template: `${registrationId}` (same name!)
- **Entity Field Names:** Use exact field names from Java entities in templates
  - Example: Entity has `studentFirstname` → use `${registration.studentFirstname}` not `${registration.voornaam}`
- **Null Safety:** Always use null-safe expressions with ternary or elvis operator
  - Example: `${registration.studentFirstname ?: '-'}`
  - Example: `${registration.studentGeboortedatum != null ? #temporals.format(registration.studentGeboortedatum, 'dd/MM/yyyy') : '-'}`
- **Fragment Calls:** Keep URL parameter syntax consistent in fragment calls
  - Example: `th:replace="~{fragments/navigation :: navigation(@{/vorige/__${registrationId}__}, ...)}"`