# Alert & Info Box Componenten - Documentatie

## Overzicht

Het systeem biedt twee manieren om visuele feedback berichten aan gebruikers te tonen:
1. **Info Boxes** - Moderne, op maat gemaakte componenten (primair gebruikt)
2. **Bootstrap Alerts** - Legacy componenten (worden uitgefaseerd)

**⚠️ Belangrijk:** Gebruik bij voorkeur **Info Boxes** voor nieuwe implementaties. Bootstrap alerts worden momenteel uitgefaseerd.

## Info Box Componenten (Nieuw & Aanbevolen)

### Beschikbare Types

| Type | Class | Gebruik | Kleur |
|------|-------|---------|-------|
| **Primary** | `info-box--primary` | Algemene belangrijke informatie | Blauw |
| **Info** | `info-box--info` | Informatieve berichten | Lichtblauw |
| **Success** | `info-box--success` | Succesvolle acties | Groen |
| **Warning** | `info-box--warning` | Waarschuwingen | Oranje/Geel |
| **Error** | `info-box--error` | Fouten en errors | Rood |

### Basis Gebruik

#### Eenvoudige Info Box

```html
<div class="info-box info-box--info">
    <h2 class="info-box__title">Belangrijk</h2>
    <p class="info-box__text">
        Dit is een informatief bericht.
    </p>
</div>
```

#### Info Box met Lijst

```html
<div class="info-box info-box--warning">
    <h2 class="info-box__title">Let op</h2>
    <p class="info-box__text">
        Controleer de volgende punten:
    </p>
    <ul class="info-box__list">
        <li>Eerste punt</li>
        <li>Tweede punt</li>
        <li>Derde punt</li>
    </ul>
</div>
```

#### Info Box met Meerdere Paragrafen

```html
<div class="info-box info-box--primary">
    <h2 class="info-box__title">Belangrijk voor het 2de jaar</h2>
    <p class="info-box__text">
        Ook in het 2de jaar maken we de opsplitsing in plus- en accentklassen.
    </p>
    <p class="info-box__text">
        Mocht u het niet eens zijn met dat advies, neem dan gerust contact op 
        met de school: <a href="mailto:school@example.com">school@example.com</a>.
    </p>
</div>
```

### Info Box in Error Pagina's

#### 404 Fout Pagina

```html
<div class="info-box info-box--warning mb-4">
    <h2 class="info-box__title">Foutcode: 404</h2>
    <p class="info-box__text">
        De pagina die u zoekt bestaat niet of is verplaatst.
    </p>
</div>

<div class="info-box info-box--info">
    <h2 class="info-box__title">Wat kunt u doen?</h2>
    <ul class="info-box__list">
        <li>Controleer of de URL correct is</li>
        <li>Ga terug naar de startpagina</li>
        <li>Contacteer de schooladministratie indien nodig</li>
    </ul>
</div>
```

#### 500 Server Fout Pagina

```html
<div class="info-box info-box--error mb-4">
    <h2 class="info-box__title">Foutcode: 500</h2>
    <p class="info-box__text">
        Er is een interne fout opgetreden op de server. 
        Onze excuses voor het ongemak.
    </p>
</div>

<div class="info-box info-box--info">
    <h2 class="info-box__title">Wat kunt u doen?</h2>
    <ul class="info-box__list">
        <li>Probeer het over enkele ogenblikken opnieuw</li>
        <li>Contacteer de schooladministratie indien probleem aanhoudt</li>
        <li>Vermeld datum en tijdstip van deze fout</li>
    </ul>
</div>
```

### Gebruik in Thymeleaf

#### Conditionele Info Box

```html
<div class="info-box info-box--success" th:if="${success}">
    <h2 class="info-box__title">Gelukt!</h2>
    <p class="info-box__text" th:text="${success}">
        Actie succesvol uitgevoerd.
    </p>
</div>
```

#### Met Dynamische Data

```html
<div class="info-box info-box--info">
    <h2 class="info-box__title">Controleer uw gegevens</h2>
    <p class="info-box__text">
        Gelieve alle onderstaande gegevens zorgvuldig na te kijken voordat u de inschrijving definitief maakt.
        Na bevestiging zal dit overzicht u bezorgd worden op het e-mailadres: 
        <strong th:text="${registration.email}"></strong>
    </p>
</div>
```

### CSS Classes Overzicht

| Class | Doel |
|-------|------|
| `.info-box` | Basis info box styling |
| `.info-box--{type}` | Kleur variant (primary, info, success, warning, error) |
| `.info-box__title` | Titel van de info box |
| `.info-box__text` | Tekst paragraaf in de info box |
| `.info-box__list` | Lijst binnen de info box |
| `.mb-4` | Margin bottom spacing |

## Bootstrap Alerts (Legacy - Wordt Uitgefaseerd)

⚠️ **Let op:** Bootstrap alerts worden nog ondersteund maar zijn legacy. Gebruik bij voorkeur Info Boxes voor nieuwe implementaties.

### Beschikbare Kleuren (Legacy)

| Type | Class | Gebruik |
|------|-------|---------|
| **Primary** | `alert-primary` | Algemene informatie |
| **Success** | `alert-success` | Succesvolle acties |
| **Danger** | `alert-danger` | Fouten en errors |
| **Warning** | `alert-warning` | Waarschuwingen |
| **Info** | `alert-info` | Informatieve berichten |

### Basis Gebruik (Legacy)

```html
<div class="alert alert-info fade show" role="alert">
    Dit is een informatief bericht.
</div>
```

## Migratie van Alerts naar Info Boxes

### Voorbeelden

**Oud (Alert):**
```html
<div class="alert alert-warning fade show" role="alert">
    <strong>⚠️ Let op!</strong><br>
    Dit is een waarschuwing.
</div>
```

**Nieuw (Info Box):**
```html
<div class="info-box info-box--warning">
    <h2 class="info-box__title">Let op!</h2>
    <p class="info-box__text">
        Dit is een waarschuwing.
    </p>
</div>
```

## Best Practices

### Do's ✅

1. **Gebruik Info Boxes** voor alle nieuwe implementaties
2. **Gebruik duidelijke titels** met de `info-box__title` class
3. **Gebruik `info-box__text`** voor paragrafen
4. **Gebruik `info-box__list`** voor lijsten
5. **Gebruik juiste kleuren** voor het type bericht:
   - `--error` voor fouten (500 errors, validatie errors)
   - `--warning` voor waarschuwingen (404 errors, let op berichten)
   - `--info` voor informatieve berichten
   - `--success` voor succesberichten
   - `--primary` voor belangrijke informatie
6. **Houd berichten kort** en to-the-point
7. **Gebruik lijsten** voor meerdere punten
8. **Voeg links toe** waar relevant (email, telefoon, URLs)

### Don'ts ❌

1. Gebruik geen nieuwe Bootstrap alerts (migreer naar info boxes)
2. Niet te veel info boxes op één pagina
3. Geen extreem lange teksten in info boxes
4. Niet te veel kleuren door elkaar gebruiken
5. Geen info boxes voor normale content

## Voorbeelden uit Applicatie

### Email Verificatie

```html
<div class="info-box info-box--primary">
    <h2 class="info-box__title">Belangrijk</h2>
    <p class="info-box__text">
        Geef het <strong>emailadres van de ouder of voogd</strong> op, 
        niet dit van de leerling die u wenst in te schrijven.
    </p>
    <p class="info-box__text">
        Na het opgeven van het emailadres zal een 
        <strong>verificatie-email</strong> verstuurd worden met een 
        persoonlijke link, waarna het inschrijvingsproces kan aangevat worden.
    </p>
</div>
```

### Submission Page

```html
<div class="info-box info-box--info mb-4">
    <h2 class="info-box__title">Controleer uw gegevens</h2>
    <p class="info-box__text">
        Gelieve alle onderstaande gegevens zorgvuldig na te kijken 
        voordat u de inschrijving definitief maakt.
        Na bevestiging van de inschrijving zal dit overzicht u bezorgd 
        worden op het e-mailadres: 
        <strong th:text="${registration.email}"></strong>
    </p>
</div>
```

### Ondertekening Waarschuwing

```html
<div class="info-box info-box--warning mb-4">
    <p class="info-box__text">
        De inschrijving wordt definitief na schriftelijke akkoordverklaring 
        met het schoolreglement; het indienen van het attest van 
        basisonderwijs (niet in 1B) en het voldoen aan de 
        toelatingsvoorwaarden.
    </p>
    <p class="info-box__text">
        De inschrijvende ouder verklaart t.o.v. de school in toepassing 
        van de artikels 374 B.W. en 375 B.W. te handelen met instemming 
        van de andere ouder.
    </p>
</div>
```

### Error Pagina's

```html
<!-- 404 Error -->
<div class="info-box info-box--warning mb-4">
    <h2 class="info-box__title">Foutcode: 404</h2>
    <p class="info-box__text">
        De pagina die u zoekt bestaat niet of is verplaatst.
    </p>
</div>

<!-- 500 Error -->
<div class="info-box info-box--error mb-4">
    <h2 class="info-box__title">Foutcode: 500</h2>
    <p class="info-box__text">
        Er is een interne fout opgetreden op de server. 
        Onze excuses voor het ongemak.
    </p>
</div>
```

## Browser Compatibiliteit

- ✅ Chrome/Edge (Chromium)
- ✅ Firefox
- ✅ Safari
- ✅ Mobile browsers

## Toegankelijkheid

- Semantische HTML structuur
- Duidelijke heading hierarchy
- Kleurcontrast volgens WCAG richtlijnen
- Leesbare font sizes
- Goede spacing en witruimte
