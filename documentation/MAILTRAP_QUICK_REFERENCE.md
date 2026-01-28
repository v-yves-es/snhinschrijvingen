# 📧 Mailtrap Quick Reference - Demo Dag

**Datum:** 29 januari 2026  
**Voor:** Demo SNH Inschrijvingen

---

## ⚡ Quick Start (3 stappen)

### 1️⃣ Configureer Credentials (1 minuut)

**Open:** `src/main/resources/application.properties`

**Vul in:**
```properties
spring.mail.username=JOUW_MAILTRAP_USERNAME
spring.mail.password=JOUW_MAILTRAP_PASSWORD
```

**Waar vind ik deze?**
- Login op https://mailtrap.io
- Klik op je inbox
- Tab "SMTP Settings"
- Selecteer "Spring Boot"

---

### 2️⃣ Start Applicatie

```bash
./mvnw spring-boot:run
```

**Wacht tot je ziet:**
```
Started SnhinschrijvingenApplication in X.XXX seconds
```

---

### 3️⃣ Open Browser Tabs

**Tab 1:** http://localhost:8080/inschrijving/start  
**Tab 2:** https://mailtrap.io (login + open inbox)

✅ **KLAAR VOOR DEMO!**

---

## 📋 Demo Checklist

**Voor demo:**
- [ ] Mailtrap inbox leeg gemaakt
- [ ] Applicatie gestart
- [ ] Beide browser tabs open
- [ ] Dummy email adres klaar (bijv. `demo@example.com`)

**Tijdens demo:**
1. Vul email in op Tab 1
2. Klik "Doorgaan"
3. Switch naar Tab 2 (Mailtrap)
4. Refresh inbox (F5)
5. Open email
6. Klik op verificatie link
7. Doorloop wizard
8. Voltooi inschrijving
9. Check bevestiging email in Mailtrap

---

## 🎯 Email Types

| Email | Wanneer | Bevat |
|-------|---------|-------|
| **Verificatie** | Bij start inschrijving | Link om email te bevestigen |
| **Bevestiging** | Na voltooien inschrijving | Inschrijvingsnummer + volgende stappen |

---

## 🐛 Troubleshooting

### Email komt niet aan?

**Fix 1:** Refresh Mailtrap inbox (F5)  
**Fix 2:** Check console logs voor errors  
**Fix 3:** Herstart applicatie  
**Fix 4:** Verify credentials in application.properties  

### Applicatie start niet?

**Fix 1:** Check of port 8080 vrij is  
**Fix 2:** Run `./mvnw clean install` eerst  
**Fix 3:** Check Java versie: `java -version` (moet 25 zijn)

---

## 💡 Demo Tips

✅ **DO:**
- Toon Mailtrap inbox TIJDENS demo (live!)
- Open email en toon HTML design
- Klik op link om te tonen dat die werkt
- Vertel: "Emails worden niet echt verstuurd" (Mailtrap vangt ze)

❌ **DON'T:**
- Niet wachten tot email aankomt (komt instant!)
- Niet vergeten inbox te refreshen
- Niet oude test emails laten zien

---

## 📱 Mailtrap Dashboard Quick Nav

**Email openen:**
- Inbox → Click op email → HTML/Text tabs

**Mobile preview:**
- Open email → Klik 📱 icon rechtsboven

**Spam check:**
- Open email → "Spam Analysis" tab

**Raw source:**
- Open email → "Show Original"

---

## 🚀 One-Liner Demo Script

> "Ik vul hier een email in, verstuur... [switch tab] ...en zie, de email komt instant aan in onze test inbox. [open email] Professioneel design, school branding, werkende link. [click link] Perfect!"

**Duur:** 30 seconden  
**Impact:** Maximum

---

## 🔗 Emergency Links

- **Mailtrap Login:** https://mailtrap.io/signin
- **Applicatie:** http://localhost:8080/inschrijving/start
- **H2 Console:** http://localhost:8080/h2-console (als je database wil checken)

---

## 📝 Notes

**Belangrijke punten om te vermelden:**
1. Mailtrap = veilig (emails gaan niet naar echte ontvangers)
2. Perfect voor testing en development
3. Eenvoudig om te switchen naar productie (1 config wijziging)
4. Professionele email templates (HTML + text versie)

---

**Good luck met de demo! 🎉**

_Last check: Credentials ingevuld? Applicatie draait? Tabs open? → GO!_
