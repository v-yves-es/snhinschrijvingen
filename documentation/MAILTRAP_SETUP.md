# Mailtrap Email Testing Setup

**Laatst bijgewerkt:** 28 januari 2026  
**Status:** ✅ Geïmplementeerd en klaar voor gebruik  
**Purpose:** Email testing voor development en demo zonder echte emails te versturen

---

## 📋 Wat is Mailtrap?

Mailtrap is een **Email Sandbox Service** die:
- ✅ **Vangt alle emails** die je applicatie probeert te versturen
- ✅ **Verstuurt GEEN echte emails** naar ontvangers
- ✅ **Test inbox** toont alle emails met volledige HTML preview
- ✅ **SMTP/API credentials** voor makkelijke integratie
- ✅ **Gratis account** (500 emails/maand, 1 inbox)
- ✅ **Perfect voor demo's** - toon emails zonder ze echt te versturen

---

## 🚀 Setup Guide (5 minuten)

### Stap 1: Mailtrap Account Aanmaken

1. **Ga naar:** https://mailtrap.io
2. **Klik op:** "Sign Up" (rechtsboven)
3. **Registreer met:**
   - Email adres
   - Password
   - Of via Google/GitHub login
4. **Bevestig** je email adres (check inbox)

### Stap 2: Inbox Aanmaken

1. **Na inloggen:** Je ziet het Mailtrap dashboard
2. **Standaard inbox:** "My Inbox" is al aangemaakt
3. **Of maak nieuwe inbox:**
   - Klik "+ Add Inbox"
   - Naam: "SNH Inschrijvingen"
   - Klik "Create"

### Stap 3: SMTP Credentials Ophalen

1. **Klik op je inbox** (bijv. "My Inbox")
2. **Klik op tab:** "SMTP Settings"
3. **Selecteer integration:** "Spring Boot" (in dropdown)
4. **Zie credentials:**
   ```
   Host: sandbox.smtp.mailtrap.io
   Port: 2525
   Username: [jouw username - bijv. a1b2c3d4e5f6g7]
   Password: [jouw password - bijv. h8i9j0k1l2m3n4]
   ```

### Stap 4: Configureer Application

**Open:** `src/main/resources/application.properties`

**Vervang de placeholder waarden:**

```properties
# VOOR (placeholders):
spring.mail.username=YOUR_MAILTRAP_USERNAME
spring.mail.password=YOUR_MAILTRAP_PASSWORD

# NA (echte credentials van stap 3):
spring.mail.username=a1b2c3d4e5f6g7
spring.mail.password=h8i9j0k1l2m3n4
```

**⚠️ BELANGRIJK:** 
- Host en Port blijven `sandbox.smtp.mailtrap.io` en `2525`
- Alleen username en password zijn uniek voor jouw account

### Stap 5: Test de Setup

**1. Start de applicatie:**
```bash
./mvnw spring-boot:run
```

**2. Open browser:** http://localhost:8080/inschrijving/start

**3. Vul email in:** bijv. `test@example.com` (mag elk adres zijn!)

**4. Klik "Doorgaan"**

**5. Check Mailtrap inbox:**
- Open https://mailtrap.io
- Ga naar je inbox
- Zie de verificatie email verschijnen! 🎉

---

## 📧 Email Types in Applicatie

### 1. Email Verificatie Email

**Wanneer:** Bij start nieuwe inschrijving  
**Naar:** Opgegeven email adres  
**Bevat:**
- Welkomstbericht
- Verificatie link/button
- Link geldig 24 uur
- SPES Nostra Heule branding

**Template:** `EmailService.buildVerificationEmailHtml()`

**Voorbeeld email:**
```
Van: SPES Nostra Heule <noreply@snh-kortrijk.be>
Aan: ouder@example.com
Onderwerp: Bevestig uw email adres - SPES Nostra Heule

[HTML email met blauwe header, witte content box, groene button]
Klik hier om uw email te bevestigen: [Grote groene knop]
Link: http://localhost:8080/inschrijving/verify?token=abc123...
```

### 2. Inschrijving Bevestiging Email

**Wanneer:** Na voltooien inschrijving (stap 10)  
**Naar:** Geverifieerd email adres  
**Bevat:**
- Bevestiging ontvangst
- Leerling naam
- Inschrijvingsnummer
- Volgende stappen
- Contactgegevens school

**Template:** `EmailService.buildConfirmationEmailHtml()`

**Voorbeeld email:**
```
Van: SPES Nostra Heule <noreply@snh-kortrijk.be>
Aan: ouder@example.com
Onderwerp: Inschrijving ontvangen - SPES Nostra Heule

[HTML email met groene header "✓ Inschrijving Ontvangen"]
Uw inschrijving voor Jan Janssens is succesvol ontvangen.
Inschrijvingsnummer: SNH-2026-001234

Volgende stappen:
- Onze administratie verwerkt uw aanvraag
- U ontvangt verdere instructies per email
```

---

## 🎯 Voor Demo's

### Scenario 1: Nieuwe Inschrijving Starten

**Demo flow:**
1. **Open:** http://localhost:8080/inschrijving/start
2. **Vertel:** "Laten we starten met een nieuwe inschrijving"
3. **Type:** `demo@example.com`
4. **Klik:** "Doorgaan"
5. **Toon:** Mailtrap inbox in nieuw browser tab
6. **Zie:** Email verschijnt instant! (refresh indien nodig)
7. **Open:** Email in Mailtrap (klik erop)
8. **Toon:** HTML preview - professioneel design
9. **Klik:** Op link in email
10. **Toon:** Verification success → redirect naar inschrijving

**Talking points:**
- "Emails worden niet echt verstuurd"
- "Perfect voor testing en development"
- "Zie de volledige HTML email met design"
- "Link werkt en brengt gebruiker naar de applicatie"

### Scenario 2: Inschrijving Voltooien

**Demo flow:**
1. **Doorloop:** Alle 10 wizard stappen (gebruik dummy data)
2. **Laatste stap:** Submission page
3. **Klik:** "Inschrijving voltooien"
4. **Toon:** Success confirmation page
5. **Switch:** Naar Mailtrap tab
6. **Refresh:** Inbox
7. **Zie:** Nieuwe bevestiging email
8. **Open:** Email
9. **Toon:** 
   - Groen vinkje "✓ Inschrijving Ontvangen"
   - Leerling naam
   - Inschrijvingsnummer
   - Volgende stappen
   - Contact info

**Talking points:**
- "Ouders ontvangen automatisch bevestiging"
- "Professionele communicatie"
- "Duidelijk inschrijvingsnummer voor follow-up"

---

## 🔧 Mailtrap Features voor Demo

### 1. HTML & Text Preview

**Toon beiden:**
- HTML tab: Volledige design met kleuren, buttons
- Text tab: Plain text versie (fallback)

**Demo tip:** "Emails werken op alle devices en email clients"

### 2. Raw Email Source

**Klik op:** "Show Original" (in email view)  
**Toon:** Volledige email headers, MIME structure  
**Demo tip:** "Technisch correct geïmplementeerd met Spring Mail"

### 3. Spam Analysis

**Klik op:** "Spam Analysis" tab  
**Toon:** Spam score (moet groen zijn, <5)  
**Demo tip:** "Emails zijn geoptimaliseerd om niet als spam gemarkeerd te worden"

### 4. HTML Check

**Klik op:** "HTML Check" tab  
**Toon:** HTML validatie resultaten  
**Demo tip:** "Valid HTML5 email templates"

### 5. Multiple Recipients

**Test:** Verstuur naar meerdere emails  
**Toon:** Alle emails in Mailtrap inbox  
**Demo tip:** "Kan bulk emails sturen (bijv. naar alle ouders)"

---

## 📱 Responsive Email Design

Beide email templates zijn **responsive**:
- ✅ Desktop: Full width (max 600px)
- ✅ Tablet: Scaled appropriately
- ✅ Mobile: Stack vertically, large buttons

**Demo tip:** In Mailtrap, klik op de "mobile" icon rechtsboven om mobile preview te zien.

---

## ⚙️ Technische Details

### Spring Mail Configuration

```properties
# SMTP Server
spring.mail.host=sandbox.smtp.mailtrap.io
spring.mail.port=2525

# Authentication
spring.mail.username=[JOUW_USERNAME]
spring.mail.password=[JOUW_PASSWORD]

# SMTP Properties
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
spring.mail.properties.mail.smtp.starttls.required=false

# Sender Info
app.email.from=noreply@snh-kortrijk.be
app.email.from-name=SPES Nostra Heule
```

### EmailService Methods

```java
// Verificatie email versturen
emailService.sendVerificationEmail(
    "ouder@example.com", 
    "verification-token-xyz", 
    "http://localhost:8080"
);

// Bevestiging email versturen
emailService.sendRegistrationConfirmationEmail(
    "ouder@example.com",
    "Jan Janssens",
    "SNH-2026-001234"
);
```

### Email Templates

**Locatie:** Inline in `EmailService.java`

**HTML Features:**
- Inline CSS (best practice voor emails)
- Table-based layout (compatibility)
- Web fonts fallback
- Proper encoding (UTF-8)
- Responsive meta viewport

---

## 🚀 Van Development naar Productie

### Development (NU - Mailtrap)

```properties
spring.mail.host=sandbox.smtp.mailtrap.io
spring.mail.port=2525
spring.mail.username=[mailtrap_username]
spring.mail.password=[mailtrap_password]
```

**Effect:** Emails worden NIET echt verstuurd, gevangen in Mailtrap

### Productie (TOEKOMST - Echte SMTP)

```properties
# Optie 1: Gmail SMTP
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=school@snh-kortrijk.be
spring.mail.password=[app_specific_password]

# Optie 2: SendGrid (professioneel)
spring.mail.host=smtp.sendgrid.net
spring.mail.port=587
spring.mail.username=apikey
spring.mail.password=[sendgrid_api_key]

# Optie 3: School SMTP server
spring.mail.host=mail.snh-kortrijk.be
spring.mail.port=587
spring.mail.username=noreply@snh-kortrijk.be
spring.mail.password=[smtp_password]
```

**Effect:** Emails worden ECHT verstuurd naar ontvangers

**⚠️ Belangrijk:** Verander ALLEEN de properties, code blijft hetzelfde!

---

## 📊 Mailtrap Limits (Gratis Account)

| Feature | Limit | Voldoende voor Demo? |
|---------|-------|---------------------|
| **Emails per maand** | 500 | ✅ Ruim voldoende |
| **Inboxes** | 1 | ✅ Eén is genoeg |
| **Email retention** | 1 maand | ✅ Prima |
| **Concurrent emails** | 50/min | ✅ Meer dan genoeg |
| **Attachment size** | 2MB | ✅ Geen attachments nu |
| **API access** | Nee | ✅ Gebruiken SMTP |

**Voor demo:** Je kunt makkelijk 50+ demo flows doen zonder problemen.

---

## 🐛 Troubleshooting

### Email komt niet aan in Mailtrap

**Check 1: Credentials correct?**
```bash
# In application.properties
spring.mail.username=a1b2c3d4e5f6g7  # Moet jouw username zijn
spring.mail.password=h8i9j0k1l2m3n4  # Moet jouw password zijn
```

**Check 2: Applicatie herstart?**
- Na wijzigen application.properties → herstart applicatie
- `./mvnw spring-boot:run`

**Check 3: Console logs?**
```bash
# Zoek naar:
INFO ... EmailService : Verificatie email verstuurd naar: test@example.com

# Of errors:
ERROR ... : Could not connect to SMTP host
```

**Check 4: Mailtrap inbox geselecteerd?**
- Zorg dat je de juiste inbox bekijkt in Mailtrap dashboard

### Connection refused error

**Oorzaak:** Firewall blokkeert port 2525

**Oplossing:** Gebruik alternatieve port
```properties
spring.mail.port=25    # Of
spring.mail.port=465   # Of
spring.mail.port=587   # Probeer deze
```

### Authentication failed

**Oorzaak:** Verkeerde username/password

**Oplossing:**
1. Login op Mailtrap.io
2. Ga naar inbox
3. Tab "SMTP Settings"
4. Kopieer EXACT de credentials (let op spaties!)

### Email HTML breekt

**Oorzaak:** Email client rendering issues

**Oplossing:**
- Test in Mailtrap (ondersteunt alle email clients)
- Gebruik inline CSS (al gedaan ✅)
- Test "HTML Check" tab in Mailtrap

---

## ✅ Pre-Demo Checklist

**24 uur voor demo:**

- [ ] **Mailtrap account actief?** Login en test
- [ ] **Credentials geconfigureerd?** Check application.properties
- [ ] **Applicatie werkt?** Start en test lokaal
- [ ] **Emails komen aan?** Verstuur test email
- [ ] **Browser tabs klaar?**
  - Tab 1: http://localhost:8080/inschrijving/start
  - Tab 2: https://mailtrap.io (ingelogd)
- [ ] **Demo data voorbereid?** Dummy gegevens klaar
- [ ] **Internet connectie?** Mailtrap vereist internet

**5 minuten voor demo:**

- [ ] **Applicatie gestart?** `./mvnw spring-boot:run`
- [ ] **Mailtrap inbox leeg?** Verwijder oude test emails
- [ ] **Browser tabs open?** Applicatie + Mailtrap
- [ ] **Demo flow doorlopen?** Eén keer snel testen

---

## 🎓 Demo Script

### Opening (30 seconden)

> "Laten we het inschrijvingsproces doorlopen. We beginnen met het opgeven van een email adres. Deze ontvangt een verificatie link."

### Email Verificatie (1 minuut)

> "Ik typ hier een email adres in... [typ demo@example.com]  
> En klik op Doorgaan... [klik]  
> Nu wordt er een email verstuurd... [switch naar Mailtrap tab]  
> En daar zie je hem al verschijnen! [refresh indien nodig]  
> Dit is de email die de ouder ontvangt... [open email]  
> Professioneel design met de school branding...  
> De ouder klikt op deze knop... [klik op link in email]  
> En wordt doorgestuurd naar het inschrijvingsformulier."

### Inschrijving Voltooien (30 seconden)

> "Na het invullen van alle gegevens... [snelle demo van stappen]  
> Klikt de ouder op 'Inschrijving voltooien'... [klik]  
> En ontvangt direct een bevestigingsmail... [switch naar Mailtrap]  
> Met alle details: naam leerling, inschrijvingsnummer, volgende stappen."

### Closing (15 seconden)

> "Dus het volledige email systeem werkt, klaar voor productie. We gebruiken nu Mailtrap voor testing, maar dit wordt makkelijk omgezet naar echte email in productie."

---

## 📚 Resources

- **Mailtrap Website:** https://mailtrap.io
- **Mailtrap Docs:** https://help.mailtrap.io/
- **Spring Mail Docs:** https://docs.spring.io/spring-framework/reference/integration/email.html
- **Email Design Guide:** https://www.campaignmonitor.com/css/

---

## 🔮 Toekomstige Uitbreidingen

### Email Templates

- [ ] Template engine (Thymeleaf) voor emails
- [ ] Separate .html bestanden ipv inline strings
- [ ] Variables voor dynamische content
- [ ] Partials/fragments voor reusable components

### Extra Emails

- [ ] **Password reset:** Voor admin users
- [ ] **Status updates:** "Uw aanvraag is goedgekeurd"
- [ ] **Reminders:** "Ontbrekende documenten"
- [ ] **Notifications:** Nieuwe berichten van school

### Email Tracking

- [ ] Open rate tracking
- [ ] Click tracking (links)
- [ ] Bounce handling
- [ ] Unsubscribe functionality

### Bulk Emails

- [ ] Email naar alle ouders (announcements)
- [ ] Per studierichting emails
- [ ] Per leerjaar emails
- [ ] Personalisatie per ontvanger

---

**Demo setup is compleet en ready to go!** 🚀📧

**Laatste check:** Vergeet niet je Mailtrap credentials in te vullen in `application.properties` voor de demo!
