# Registration Flow - Email Verification System

## Overview

Complete implementation of email verification and registration management system for SNH school registrations.

## Database Schema

### Registration Entity

```java
@Entity
@Table(name = "registrations")
public class Registration {
    @Id UUID id;                              // Unique registration ID
    String email;                              // Parent/guardian email
    String emailHash;                          // SHA-256 hash of email
    String schoolYear;                         // Format: "2025-2026"
    RegistrationStatus status;                 // PENDING | COMPLETED
    String currentStep;                        // Current wizard step
    EmailStatus emailStatus;                   // UNVERIFIED | VERIFIED | ERROR
    LocalDateTime createdAt;                   // Registration timestamp
    LocalDateTime updatedAt;                   // Last update timestamp
    String studentFirstname;                   // Student first name
    String studentLastname;                    // Student last name
}
```

### Enums

- **RegistrationStatus**: `PENDING`, `COMPLETED`
- **EmailStatus**: `UNVERIFIED`, `VERIFIED`, `ERROR`

## Services

### RegistrationService

**Key Methods:**
- `createRegistration(email)` - Create new registration with PENDING status
- `verifyEmailAndGetRegistration(id, hash)` - Verify email and update status
- `findRegistrationsByEmail(email)` - Get all registrations for email
- `findPendingRegistrationByEmail(email)` - Find active pending registration
- `updateStudentInfo(id, firstname, lastname)` - Update student details
- `hashEmail(email)` - SHA-256 hash for verification links

### EmailVerificationService

**Key Methods:**
- `sendVerificationEmail(registration)` - Send verification email (console output)
- `buildVerificationLink(registration)` - Create verification URL

**Verification Link Format:**
```
http://localhost:8080/inschrijving/verify/{registrationId}/{emailHash}
```

## Flow Diagrams

### 1. Initial Registration Flow

```
┌─────────────────────────────────────────────────────────────┐
│ Step 1: Email Input (/inschrijving/start)                  │
├─────────────────────────────────────────────────────────────┤
│ • User enters email address                                 │
│ • POST creates Registration (PENDING, UNVERIFIED)          │
│ • Email hash generated (SHA-256)                            │
│ • School year auto-calculated                               │
│ • Verification email "sent" (console log)                   │
└─────────────────────────────────────────────────────────────┘
                            ↓
┌─────────────────────────────────────────────────────────────┐
│ Step 2: Email Sent Confirmation                            │
├─────────────────────────────────────────────────────────────┤
│ • Success message displayed                                 │
│ • Instructions shown                                        │
│ • Troubleshooting tips                                      │
└─────────────────────────────────────────────────────────────┘
                            ↓
┌─────────────────────────────────────────────────────────────┐
│ Step 3: Email Verification Link (from console)             │
├─────────────────────────────────────────────────────────────┤
│ Format: /verify/{id}/{hash}                                 │
│ • Validates ID + hash combination                           │
│ • Updates emailStatus: UNVERIFIED → VERIFIED                │
│ • Updates currentStep: EMAIL_VERIFICATION → STUDENT_INFO    │
└─────────────────────────────────────────────────────────────┘
                            ↓
┌─────────────────────────────────────────────────────────────┐
│ Step 4: Routing Decision                                   │
├─────────────────────────────────────────────────────────────┤
│ IF: First registration (no completed ones)                  │
│   → Redirect to /student-info?id={registrationId}           │
│                                                             │
│ ELSE: Has existing registrations                            │
│   → Show registrations overview                             │
└─────────────────────────────────────────────────────────────┘
```

### 2. Registrations Overview (Multiple Registrations)

```
┌─────────────────────────────────────────────────────────────┐
│ /verify/{id}/{hash} → registrations-overview.html          │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│ ✉️ Email: parent@example.com                                │
│                                                             │
│ ┌───────────────────────────────────────────────────────┐   │
│ │ Existing Registrations Table                          │   │
│ ├─────────────┬──────────┬──────────┬──────────┬────────┤   │
│ │ Student     │ Year     │ Date     │ Status   │ Action │   │
│ ├─────────────┼──────────┼──────────┼──────────┼────────┤   │
│ │ Jan Jansen  │ 2024-25  │ 10/01/25 │ ✅ Done  │   -    │   │
│ │ Piet Pieters│ 2025-26  │ 25/12/25 │ ⏳ Pending│ [Finish]│   │
│ │ (Not filled)│ 2025-26  │ 25/12/25 │ ⏳ Pending│ [Finish]│   │
│ └─────────────┴──────────┴──────────┴──────────┴────────┘   │
│                                                             │
│ [➕ Nieuwe leerling inschrijven]                            │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

### 3. Student Info Flow

```
┌─────────────────────────────────────────────────────────────┐
│ /student-info?id={registrationId}                          │
├─────────────────────────────────────────────────────────────┤
│ • Wizard navigation (step 4/10)                             │
│ • Pre-fill form if data exists                              │
│ • Validate input (client-side)                              │
│ • POST saves firstname + lastname                           │
│ • Updates currentStep                                       │
│ • Redirects back to overview                                │
└─────────────────────────────────────────────────────────────┘
```

## Key Features

### 1. Email Hashing

```java
SHA-256(email.toLowerCase().trim())
→ 973dfe463ec85785f5f95af5ba3906eedb2d931c24e69824a89ea65dba4e813b
```

**Purpose:**
- Secure verification links
- Cannot derive email from hash
- Unique per email address

### 2. School Year Auto-Calculation

```java
currentMonth >= 9 
  ? currentYear + "-" + (currentYear + 1)
  : (currentYear - 1) + "-" + currentYear

Example: December 2025 → "2025-2026"
Example: August 2026 → "2025-2026"
Example: September 2026 → "2026-2027"
```

### 3. Resume Capability

Users can **always** resume their registration by clicking the verification link again:
- Link remains valid indefinitely
- Shows overview of all registrations
- Can continue pending registration
- Can start new registration

## API Endpoints

### Email Verification

| Endpoint | Method | Purpose |
|----------|--------|---------|
| `/inschrijving/start` | GET | Show email input form |
| `/inschrijving/start` | POST | Create registration + send email |
| `/inschrijving/email-sent` | GET | Show confirmation page |
| `/inschrijving/verify/{id}/{hash}` | GET | Verify email + route to appropriate page |

### Registration Management

| Endpoint | Method | Purpose |
|----------|--------|---------|
| `/inschrijving/student-info?id={id}` | GET | Show student info form |
| `/inschrijving/student-info` | POST | Save student info |
| `/inschrijving/continue/{id}` | GET | Resume registration at current step |

## Database Queries

```java
// Find all registrations for email
findByEmailOrderByCreatedAtDesc(email)

// Find pending registration
findFirstByEmailAndStatusOrderByCreatedAtDesc(email, PENDING)

// Verify by ID + hash
findByIdAndEmailHash(id, hash)

// Check if hash exists
existsByEmailHash(hash)
```

## Console Output Example

```
================================================================================
EMAIL VERIFICATION LINK FOR: test@example.com
Registration ID: 74b6ba25-8855-4055-a91d-f44c5a09299c
Link: http://localhost:8080/inschrijving/verify/74b6ba25-8855-4055-a91d-f44c5a09299c/973dfe463ec85785f5f95af5ba3906eedb2d931c24e69824a89ea65dba4e813b
================================================================================

╔════════════════════════════════════════════════════════════════╗
║                    EMAIL VERIFICATION                          ║
╚════════════════════════════════════════════════════════════════╝

To: test@example.com
Subject: Verifieer uw emailadres - SPES Nostra Heule

Beste ouder/voogd,

[Email content...]

http://localhost:8080/inschrijving/verify/...

════════════════════════════════════════════════════════════════
```

## Testing

### Manual Test Sequence

```bash
# 1. Start application
./mvnw spring-boot:run

# 2. Go to email input
http://localhost:8080/inschrijving/start

# 3. Submit email (e.g., test@example.com)
# 4. Check console for verification link
# 5. Copy link from logs
# 6. Open link in browser
# 7. Verify redirect to student-info (first time)
# 8. Fill student info and submit
# 9. See registrations overview
# 10. Click "Nieuwe leerling inschrijven"
# 11. Repeat flow
```

### Verification Link Format

```
Base URL: http://localhost:8080
Path: /inschrijving/verify/{UUID}/{SHA256-hash}

Example:
http://localhost:8080/inschrijving/verify/
74b6ba25-8855-4055-a91d-f44c5a09299c/
973dfe463ec85785f5f95af5ba3906eedb2d931c24e69824a89ea65dba4e813b
```

## Next Steps (TODO)

1. **Email Integration**
   - Replace console logging with actual email service
   - Use JavaMailSender or external email API
   - Add email templates (HTML)

2. **Additional Wizard Steps**
   - Implement remaining steps (5-10)
   - Add step routing in RegistrationController
   - Update currentStep on each submission

3. **Validation**
   - Server-side validation with @Valid
   - Email format validation
   - Duplicate email handling strategy

4. **Security**
   - Link expiration (optional)
   - Rate limiting on email sending
   - CSRF protection

5. **User Experience**
   - Email resend functionality
   - Edit existing registration
   - Delete pending registration

## Files Modified/Created

### New Entities
- `Registration.java`
- `RegistrationStatus.java`
- `EmailStatus.java`

### New Services
- `RegistrationService.java`
- `EmailVerificationService.java`

### New Repository
- `RegistrationRepository.java`

### Updated Controllers
- `EmailVerificationController.java`
- `StudentInfoController.java`

### New Controller
- `RegistrationController.java`

### New Templates
- `registrations-overview.html`

### Updated Templates
- `student-info.html` (added registration ID parameter)

## Database Schema (H2)

Table automatically created via JPA:

```sql
CREATE TABLE registrations (
    id UUID PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    email_hash VARCHAR(255) NOT NULL UNIQUE,
    school_year VARCHAR(255) NOT NULL,
    status VARCHAR(255) NOT NULL,
    current_step VARCHAR(255),
    email_status VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    student_firstname VARCHAR(255),
    student_lastname VARCHAR(255)
);

CREATE INDEX idx_email ON registrations(email);
CREATE INDEX idx_status ON registrations(status);
CREATE INDEX idx_email_status ON registrations(email_status);
```

## Status: ✅ COMPLETED

Email verification system is fully functional and ready for integration with actual email sending service.
