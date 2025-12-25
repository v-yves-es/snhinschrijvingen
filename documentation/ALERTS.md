# Alert Componenten - Documentatie

## Overzicht

Het alert systeem biedt visuele feedback berichten voor gebruikers, gebaseerd op Bootstrap alert componenten zoals te zien op https://demo.dashboardpack.com/architectui-html-pro/components-notifications.html

## Alert Types

### Beschikbare Kleuren

| Type | Class | Gebruik | Voorbeeld |
|------|-------|---------|-----------|
| **Primary** | `alert-primary` | Algemene informatie | Standaard mededelingen |
| **Success** | `alert-success` | Succesvolle acties | ✅ Email verzonden |
| **Danger** | `alert-danger` | Fouten en errors | ❌ Actie mislukt |
| **Warning** | `alert-warning` | Waarschuwingen | ⚠️ Let op! |
| **Info** | `alert-info` | Informatieve berichten | ℹ️ Belangrijk te weten |
| **Secondary** | `alert-secondary` | Minder belangrijke info | Algemeen |
| **Light** | `alert-light` | Subtiele berichten | Lichte achtergrond |
| **Dark** | `alert-dark` | Donkere alerts | Contrast |

## Basis Gebruik

### Eenvoudige Alert

```html
<div class="alert alert-info fade show" role="alert">
    Dit is een informatief bericht.
</div>
```

### Alert met Sterke Tekst

```html
<div class="alert alert-success fade show" role="alert">
    <strong>✅ Gelukt!</strong> Uw actie is succesvol uitgevoerd.
</div>
```

### Alert met Link

```html
<div class="alert alert-info fade show" role="alert">
    Meer informatie? Bekijk onze <a href="#" class="alert-link">FAQ pagina</a>.
</div>
```

## Geavanceerd Gebruik

### Sluitbare Alert

```html
<div class="alert alert-warning alert-dismissible fade show" role="alert">
    <strong>⚠️ Let op!</strong> Dit is een waarschuwing.
    <button type="button" class="close" aria-label="Close">
        <span aria-hidden="true">&times;</span>
    </button>
</div>
```

### Alert met Icoon

```html
<div class="alert alert-info fade show" role="alert">
    <div class="alert-icon">
        <svg width="24" height="24" viewBox="0 0 24 24" fill="none">
            <circle cx="12" cy="12" r="10" stroke="currentColor" stroke-width="2"/>
            <path d="M12 8v4M12 16h.01" stroke="currentColor" stroke-width="2"/>
        </svg>
        <div class="alert-icon-content">
            <strong>Belangrijk bericht</strong><br>
            Dit is een alert met een icoon aan de linkerkant.
        </div>
    </div>
</div>
```

### Alert met Lijst

```html
<div class="alert alert-warning fade show" role="alert">
    <strong>⚠️ Let op:</strong>
    <ul style="margin: 0.5rem 0 0 0; padding-left: 1.5rem;">
        <li>Eerste punt</li>
        <li>Tweede punt</li>
        <li>Derde punt</li>
    </ul>
</div>
```

## Gebruik in Thymeleaf

### Statische Alert

```html
<div class="alert alert-info fade show" role="alert">
    <strong>ℹ️ Belangrijk:</strong><br>
    Geef het <strong>emailadres van de ouder of voogd</strong> op.
</div>
```

### Dynamische Alert met Data

```html
<div class="alert alert-success fade show" role="alert" th:if="${email}">
    <strong>✅ Email verzonden!</strong><br>
    We hebben een email gestuurd naar: <strong th:text="${email}">email@voorbeeld.be</strong>
</div>
```

### Conditionele Alert

```html
<!-- Toon alleen als er een fout is -->
<div class="alert alert-danger fade show" role="alert" th:if="${error}">
    <strong>❌ Fout:</strong> <span th:text="${error}">Er is iets misgegaan</span>
</div>

<!-- Toon alleen als actie succesvol was -->
<div class="alert alert-success fade show" role="alert" th:if="${success}">
    <strong>✅ Gelukt!</strong> <span th:text="${success}">Actie uitgevoerd</span>
</div>
```

## Email Verificatie Flow

### Stap 1: Email Input Pagina (`/inschrijving/start`)

```html
<!-- Info alert -->
<div class="alert alert-info fade show" role="alert">
    <strong>ℹ️ Belangrijk:</strong><br>
    Geef het <strong>emailadres van de ouder of voogd</strong> op, 
    niet dit van de leerling die u wenst in te schrijven.<br><br>
    Na het opgeven van het emailadres zal een <strong>verificatie-email</strong> 
    verstuurd worden. <strong>Controleer uw mailbox</strong> 
    (inclusief spam/ongewenste e-mail map).
</div>

<form>
    <!-- form fields -->
    <button type="submit">Verifiëren</button>
</form>
```

### Stap 2: Email Verzonden Pagina (`/inschrijving/email-sent`)

```html
<!-- Success alert -->
<div class="alert alert-success fade show" role="alert">
    <strong>✅ Email succesvol verzonden!</strong><br>
    We hebben een verificatie-email gestuurd naar: 
    <strong th:text="${email}">email@voorbeeld.be</strong>
</div>

<!-- Info alert met checklist -->
<div class="alert alert-info fade show" role="alert">
    <div class="alert-icon">
        <svg>...</svg>
        <div class="alert-icon-content">
            <strong>Volgende stappen:</strong>
            <ol>
                <li>Open uw mailbox</li>
                <li>Zoek naar email van SNH</li>
                <li>Controleer spam folder</li>
                <li>Klik op verificatielink</li>
                <li>Word doorgestuurd naar volgende stap</li>
            </ol>
        </div>
    </div>
</div>

<!-- Warning alert met troubleshooting -->
<div class="alert alert-warning fade show" role="alert">
    <strong>⚠️ Email niet ontvangen?</strong><br>
    <ul>
        <li>Wacht enkele minuten</li>
        <li>Controleer spam/junk folder</li>
        <li>Controleer emailadres</li>
        <li>Neem contact op met school</li>
    </ul>
</div>
```

## JavaScript Functionaliteit

Het `alerts.js` script zorgt voor het sluiten van dismissible alerts:

```javascript
// Automatisch geladen
// Alerts met class "alert-dismissible" kunnen gesloten worden
// Klik op de × button om de alert te sluiten
```

### Events

```javascript
// Alert wordt gesloten
document.addEventListener('DOMContentLoaded', function() {
    // Close buttons worden automatisch gedetecteerd
    // Fade out animatie wordt toegepast
    // Alert wordt na 150ms verwijderd uit DOM
});
```

## CSS Classes Overzicht

| Class | Doel |
|-------|------|
| `.alert` | Basis alert styling |
| `.alert-{type}` | Kleur variant (primary, success, etc.) |
| `.alert-dismissible` | Maakt alert sluitbaar |
| `.alert-link` | Styling voor links in alerts |
| `.alert-icon` | Container voor alert met icoon |
| `.alert-icon-content` | Content naast icoon |
| `.fade` | Fade animatie |
| `.show` | Toont de alert (opacity 1) |
| `.close` | Sluit button styling |

## Best Practices

### Do's ✅

1. **Gebruik emoji's** voor visuele impact (✅ ❌ ⚠️ ℹ️)
2. **Gebruik `<strong>`** voor belangrijke tekst
3. **Voeg altijd `role="alert"`** toe voor toegankelijkheid
4. **Gebruik `fade show`** voor smooth animaties
5. **Gebruik juiste kleuren** voor het type bericht
6. **Houd berichten kort** en to-the-point
7. **Gebruik lijsten** voor meerdere punten

### Don'ts ❌

1. Niet te veel alerts op één pagina
2. Geen extreem lange teksten in alerts
3. Geen kritieke informatie alleen in alerts
4. Niet te veel kleuren door elkaar gebruiken
5. Geen alerts voor normale content

## Voorbeelden uit Applicatie

### Email Verificatie Info

```html
<div class="alert alert-info fade show" role="alert">
    <strong>ℹ️ Belangrijk:</strong><br>
    Geef het <strong>emailadres van de ouder of voogd</strong> op.
</div>
```

### Success Melding

```html
<div class="alert alert-success fade show" role="alert">
    <strong>✅ Email succesvol verzonden!</strong><br>
    We hebben een email gestuurd naar: <strong>user@email.be</strong>
</div>
```

### Waarschuwing met Tips

```html
<div class="alert alert-warning fade show" role="alert">
    <strong>⚠️ Email niet ontvangen?</strong><br>
    <ul style="margin: 0.5rem 0 0 0; padding-left: 1.5rem;">
        <li>Wacht enkele minuten</li>
        <li>Controleer spam folder</li>
    </ul>
</div>
```

## Demo Pagina

Bezoek de demo pagina voor alle alert types en voorbeelden:
```
http://localhost:8080/alerts-demo.html
```

## Browser Compatibiliteit

- ✅ Chrome/Edge (Chromium)
- ✅ Firefox
- ✅ Safari
- ✅ Mobile browsers

## Toegankelijkheid

- `role="alert"` voor screen readers
- `aria-label` op close buttons
- Keyboard navigatie support
- Kleurcontrast volgens WCAG richtlijnen
