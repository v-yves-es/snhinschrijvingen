# SNH Inschrijvingen - Documentatie

**Laatst bijgewerkt:** 28 januari 2026

---

## 📚 Documentatie Index

Deze folder bevat alle technische documentatie voor het SNH Inschrijvingen project.

---

## 🎯 Start Hier

### Nieuwe Ontwikkelaars
1. **[APPLICATION_STATUS.md](APPLICATION_STATUS.md)** - Complete project status, architectuur en features
2. **[TODO.md](TODO.md)** - Huidige taken en toekomstige planning
3. **[REGISTRATION_FLOW.md](REGISTRATION_FLOW.md)** - Wizard flow en user journey

### Specifieke Onderdelen
- **[THYMELEAF_STRUCTURE.md](THYMELEAF_STRUCTURE.md)** - Template structuur en patterns
- **[VALIDATION.md](VALIDATION.md)** - Complete validatie systeem documentatie
- **[FONT_AWESOME.md](FONT_AWESOME.md)** - Icon library usage guide
- **[MAILTRAP_SETUP.md](MAILTRAP_SETUP.md)** - Email testing setup voor demo's

---

## 📖 Documentatie Overzicht

### 🏗️ Architectuur & Status

#### [APPLICATION_STATUS.md](APPLICATION_STATUS.md)
**Belangrijkste document!** Bevat:
- ✅ Project overzicht en huidige status
- 🏗️ Complete technologie stack
- 📦 Database model en relaties
- 🎨 Geïmplementeerde features (alle 10 wizard stappen)
- 🔒 Security updates (28 jan 2026)
- 🐛 Recente bugfixes
- 📊 Data centralisatie implementatie

**Laatst bijgewerkt:** 28 januari 2026 19:24

---

### 📋 Planning & Tasks

#### [TODO.md](TODO.md)
Huidige TODO lijst met:
- 🔴 Hoge prioriteit taken
- 🟡 Gemiddelde prioriteit
- 🟢 Lage prioriteit (nice to have)
- ✅ Afgeronde taken (Fase 1 & 2)
- 🚧 In progress items
- 📅 Sprint planning

**Fase 2 Status:** ✅ VOLTOOID (inclusief security updates)

---

### 🧭 User Flow & Wizard

#### [REGISTRATION_FLOW.md](REGISTRATION_FLOW.md)
Complete beschrijving van de inschrijvingsflow:
- Email verificatie systeem
- 10 wizard stappen (gedetailleerd)
- Navigatie tussen stappen
- Data opslag per stap
- Confirmation page

**Stappen:**
1. Email Verificatie
2. Leerling Info
3. Vorige School
4. Studierichting Keuze
5. Relaties (Ouders/Voogd)
6. Huisarts
7. Zorgvragen
8. Privacy
9. Laptop
10. Schoolrekening
11. Overzicht & Bevestiging

---

### 🎨 Frontend Documentatie

#### [THYMELEAF_STRUCTURE.md](THYMELEAF_STRUCTURE.md)
Thymeleaf templates en structuur:
- Fragment systeem (layout, header, navigation)
- Wizard component patterns
- Info-box systeem
- Form patterns
- CSS classes en styling
- JavaScript integraties

**Key Concepts:**
- Layout inheritance
- Reusable fragments
- URL parameter syntax: `__${variable}__`
- Data binding patterns

#### [FONT_AWESOME.md](FONT_AWESOME.md) ⭐ NIEUW
Font Awesome 6.5.1 usage guide:
- Installatie via CDN
- Icon styles (Solid, Regular, Brands)
- Veelgebruikte icons
- Styling (size, color, animation)
- Praktijkvoorbeelden
- Accessibility best practices
- Currently used icons

**Geïntegreerd sinds:** 28 januari 2026

---

### ✅ Validatie

#### [VALIDATION.md](VALIDATION.md) ⭐ GECONSOLIDEERD
**Complete validatie systeem documentatie:**
- Client-side validatie (JavaScript + CSS)
- 7 Validators met volledige details:
  - Required, Email, Phone, Postal Code, Rijksregisternummer, Min/Max Length
- Regex patterns en breakdowns
- Geldige vs ongeldige waarden (voorbeelden)
- Visuele feedback systeem (kleuren, icons)
- Special cases (verborgen velden, copy actions, focus states)
- Testing scenarios per validator
- Troubleshooting guide
- Browser compatibiliteit
- Toegankelijkheid (A11y)
- Best practices

**Geconsolideerd uit:**
- ~~FORM_VALIDATION.md~~
- ~~VALIDATION_COMPLETE.md~~
- ~~VALIDATION_FIXES_23JAN2026.md~~

**Laatst bijgewerkt:** 28 januari 2026

---

### 🎯 Features

#### [STUDY_PROGRAM_SELECTION.md](STUDY_PROGRAM_SELECTION.md)
Studierichting selectie implementatie:
- Select2 dropdown integratie
- Study programs per jaar
- Orientatie filtering
- Database structuur
- Custom styling

#### [STUDY_PROGRAMS.md](STUDY_PROGRAMS.md)
Beschikbare studierichtingen data

#### [ALERTS.md](ALERTS.md)
Alert systeem documentatie

#### [MAILTRAP_SETUP.md](MAILTRAP_SETUP.md) ⭐ NIEUW
**Email testing voor demo's en development:**
- Mailtrap account setup (5 minuten)
- SMTP configuratie met Spring Mail
- Email templates (HTML + responsive)
- Demo scenario's en scripts
- Troubleshooting guide
- Quick reference card voor demo dag
- Van development naar productie

**Geïmplementeerd:** 28 januari 2026  
**Ready for demo:** ✅ Ja!

**Email Types:**
1. Email verificatie (bij start inschrijving)
2. Inschrijving bevestiging (na voltooien)

---

## 🔍 Zoeken naar Specifieke Info

### Backend
- **Controllers:** `APPLICATION_STATUS.md` → API Endpoints sectie
- **Services:** `APPLICATION_STATUS.md` → Backend Services sectie
- **Database:** `APPLICATION_STATUS.md` → Database Model sectie
- **Security:** `APPLICATION_STATUS.md` → Security Updates sectie (28 jan)

### Frontend
- **Templates:** `THYMELEAF_STRUCTURE.md`
- **Icons:** `FONT_AWESOME.md`
- **Validation:** `VALIDATION.md`
- **Wizard Flow:** `REGISTRATION_FLOW.md`
- **Styling:** `THYMELEAF_STRUCTURE.md` → CSS Classes

### Features
- **Email Verificatie:** `REGISTRATION_FLOW.md` → Stap 1
- **Student Info:** `REGISTRATION_FLOW.md` → Stap 2
- **Study Program:** `STUDY_PROGRAM_SELECTION.md`
- **Registrations Management:** `APPLICATION_STATUS.md` → Security Updates

---

## 🆕 Recent Updates (28 januari 2026)

### Security & Registration Management
- ✅ Email niet meer in URL (session-based security)
- ✅ Registration delete functionality met confirmation
- ✅ Registrations overview table (professional design)
- ✅ Font Awesome 6.5.1 integratie
- ✅ PDF placeholder icon voor voltooide inschrijvingen
- ✅ Multi-layer security checks voor delete operations
- ✅ Thymeleaf security compliance (data attributes)

**Zie:** 
- `APPLICATION_STATUS.md` → Security Updates sectie
- `TODO.md` → Fase 2.5 sectie
- `FONT_AWESOME.md` → Complete icon guide

---

## 📝 Documentatie Conventions

### Bestanden Naamgeving
- UPPERCASE voor belangrijke docs (`APPLICATION_STATUS.md`, `TODO.md`)
- Descriptive namen (`FONT_AWESOME.md`, `REGISTRATION_FLOW.md`)
- Datum suffix voor fixes (`VALIDATION_FIXES_23JAN2026.md`)

### Markdown Formatting
- **Headers:** H1 voor titel, H2 voor sections, H3 voor subsections
- **Emoji's:** Voor visual clarity (✅ ❌ 🚧 🔴 🟡 🟢)
- **Code blocks:** Voor alle code voorbeelden
- **Tables:** Voor gestructureerde data
- **Links:** Relatieve links tussen docs

### Update Conventions
- Datum format: "DD maand YYYY"
- Status indicators: ✅ VOLTOOID, 🚧 In Progress, ❌ Todo
- Version notes aan top van document

---

## 🔗 Related Resources

### Project Root
- **README.md** (project root) - Project overview
- **pom.xml** - Maven dependencies
- **application.properties** - Configuration

### Source Code
- `src/main/java/be/achieveit/snhinschrijvingen/` - Backend code
- `src/main/resources/templates/` - Thymeleaf templates
- `src/main/resources/static/` - CSS, JS, images

### External
- **Spring Boot Docs:** https://docs.spring.io/spring-boot/
- **Thymeleaf Docs:** https://www.thymeleaf.org/documentation.html
- **Font Awesome:** https://fontawesome.com/icons
- **Select2:** https://select2.org/

---

## 💡 Tips voor Ontwikkelaars

### Voor Nieuwe Features
1. Check `TODO.md` voor planning
2. Check `APPLICATION_STATUS.md` voor architectuur
3. Check relevante feature docs voor patterns
4. Update docs na implementatie

### Voor Bugfixes
1. Check `APPLICATION_STATUS.md` → Recente Bugfixes
2. Documenteer fix in relevante doc
3. Update TODO.md als applicable

### Voor Code Review
1. Check `THYMELEAF_STRUCTURE.md` voor template patterns
2. Check `FORM_VALIDATION.md` voor validatie patterns
3. Verify coding standards zijn gevolgd

---

## 📧 Contact

Voor vragen over documentatie of project:
- **Project:** SNH Inschrijvingen
- **School:** SPES Nostra Heule
- **Developer:** AchieveIT

---

**Laatst bijgewerkt:** 28 januari 2026 20:30
