# Form Validation Systeem

## Overzicht

Het form validation systeem biedt client-side validatie met visuele feedback voor alle formulieren in de applicatie.

## Visuele Feedback

### Kleurcodering

1. **🔵 Blauw (Focus)**: Actief veld krijgt een blauwe rand
2. **🟢 Groen (Valid)**: Geldig veld toont een groen vinkje
3. **🔴 Rood (Invalid)**: Ongeldig veld toont een rood kruis met foutmelding

### Gedrag

- **Bij focus**: Veld krijgt blauwe rand
- **Bij blur (verlaten)**: Validatie wordt uitgevoerd
- **Real-time**: Na eerste blur wordt bij elke wijziging opnieuw gevalideerd
- **Bij submit**: Alle velden worden gevalideerd, scroll naar eerste fout

## Implementatie

### 1. Activeer Validatie op Formulier

```html
<form data-validate="true" method="post" action="...">
    <!-- form fields -->
</form>
```

Of gebruik de class `needs-validation`:

```html
<form class="needs-validation" method="post" action="...">
    <!-- form fields -->
</form>
```

Formulieren met `required` velden worden automatisch gevalideerd.

### 2. Basis Validatie Attributen

#### Required Field
```html
<input type="text" 
       id="naam" 
       name="naam" 
       class="form-input" 
       required>
<div class="invalid-feedback"></div>
```

#### Email Validatie
```html
<input type="email" 
       id="email" 
       name="email" 
       class="form-input" 
       required
       data-validate="email">
<div class="invalid-feedback"></div>
```

#### Telefoon Validatie (Belgisch formaat)
```html
<input type="tel" 
       id="telefoon" 
       name="telefoon" 
       class="form-input" 
       required
       data-validate="phone"
       placeholder="+32476123456">
<div class="invalid-feedback"></div>
```

### 3. Geavanceerde Validatie

#### Minimum Lengte
```html
<input type="text" 
       id="voornaam" 
       name="voornaam" 
       class="form-input" 
       required
       data-min-length="2">
<div class="invalid-feedback"></div>
```

#### Maximum Lengte
```html
<input type="text" 
       id="omschrijving" 
       name="omschrijving" 
       class="form-input" 
       data-max-length="100">
<div class="invalid-feedback"></div>
```

#### Postcode (4 cijfers)
```html
<input type="text" 
       id="postcode" 
       name="postcode" 
       class="form-input" 
       required
       data-validate="postalCode"
       maxlength="4">
<div class="invalid-feedback"></div>
```

#### Rijksregisternummer (YYMMDD-XXX-XX)
```html
<input type="text" 
       id="rijksregister" 
       name="rijksregister" 
       class="form-input" 
       required
       data-validate="rijksregisternummer"
       placeholder="YYMMDD-XXX-XX"
       maxlength="15">
<div class="invalid-feedback"></div>
```

## Beschikbare Validators

| Validator | Data Attribuut | Beschrijving | Voorbeeld |
|-----------|----------------|--------------|-----------|
| Required | `required` | Veld moet ingevuld zijn | - |
| Email | `data-validate="email"` | Geldig email formaat | test@email.be |
| Phone | `data-validate="phone"` | Belgisch telefoon formaat | +32476123456 of 0476123456 |
| Postal Code | `data-validate="postalCode"` | 4 cijfers | 8500 |
| Rijksregisternummer | `data-validate="rijksregisternummer"` | YYMMDD-XXX-XX formaat | 000101-123-45 |
| Min Length | `data-min-length="X"` | Minimaal X karakters | - |
| Max Length | `data-max-length="X"` | Maximaal X karakters | - |

## Foutmeldingen

Alle foutmeldingen zijn in het Nederlands:

```javascript
const messages = {
    required: 'Dit veld is verplicht',
    email: 'Voer een geldig e-mailadres in',
    phone: 'Voer een geldig telefoonnummer in',
    rijksregisternummer: 'Voer een geldig rijksregisternummer in (YYMMDD-XXX-XX)',
    postalCode: 'Voer een geldige postcode in (4 cijfers)',
    minLength: 'Te kort, minimaal {length} karakters',
    maxLength: 'Te lang, maximaal {length} karakters'
};
```

## CSS Classes

Het validatie systeem gebruikt de volgende CSS classes:

- `.form-input:focus` - Blauw gemarkeerd actief veld
- `.form-input.is-valid` - Groen met vinkje icon
- `.form-input.is-invalid` - Rood met kruis icon
- `.invalid-feedback` - Foutmelding (rood)
- `.valid-feedback` - Success melding (groen)

## Voorbeelden

### Compleet Formulier Voorbeeld

```html
<form class="form" method="post" action="/submit" data-validate="true">
    <!-- Naam velden -->
    <div class="form-row">
        <div class="form-group">
            <label for="voornaam" class="form-label">
                Voornaam 
                <span class="badge badge--required">VERPLICHT</span>
            </label>
            <input type="text" 
                   id="voornaam" 
                   name="voornaam" 
                   class="form-input" 
                   required
                   data-min-length="2">
            <div class="invalid-feedback"></div>
        </div>
        <div class="form-group">
            <label for="achternaam" class="form-label">
                Achternaam 
                <span class="badge badge--required">VERPLICHT</span>
            </label>
            <input type="text" 
                   id="achternaam" 
                   name="achternaam" 
                   class="form-input" 
                   required
                   data-min-length="2">
            <div class="invalid-feedback"></div>
        </div>
    </div>

    <!-- Contact velden -->
    <div class="form-row">
        <div class="form-group">
            <label for="email" class="form-label">
                Email 
                <span class="badge badge--required">VERPLICHT</span>
            </label>
            <input type="email" 
                   id="email" 
                   name="email" 
                   class="form-input" 
                   required
                   data-validate="email">
            <div class="invalid-feedback"></div>
        </div>
        <div class="form-group">
            <label for="telefoon" class="form-label">
                Telefoon 
                <span class="badge badge--required">VERPLICHT</span>
            </label>
            <input type="tel" 
                   id="telefoon" 
                   name="telefoon" 
                   class="form-input" 
                   required
                   data-validate="phone"
                   placeholder="+32476123456">
            <div class="invalid-feedback"></div>
        </div>
    </div>

    <!-- Submit button -->
    <div class="form-actions">
        <button type="submit" class="btn btn--primary">Verzenden</button>
    </div>
</form>
```

### Optionele Velden

Velden zonder `required` attribuut worden alleen gevalideerd als ze een waarde bevatten:

```html
<div class="form-group">
    <label for="opmerking" class="form-label">Opmerking (optioneel)</label>
    <input type="text" 
           id="opmerking" 
           name="opmerking" 
           class="form-input"
           data-max-length="200">
    <div class="invalid-feedback"></div>
</div>
```

## Testing

### Demo Pagina

Bezoek de demo pagina om de validatie te testen:
```
http://localhost:8080/validation-demo.html
```

### Test Scenario's

1. **Required validatie**: Laat een verplicht veld leeg en klik submit
2. **Email validatie**: Voer "test" in (invalid) vs "test@email.be" (valid)
3. **Telefoon validatie**: Voer "123" in (invalid) vs "+32476123456" (valid)
4. **Postcode validatie**: Voer "12" in (invalid) vs "8500" (valid)
5. **Minimum lengte**: Voer 1 karakter in een naam veld (invalid)
6. **Rijksregisternummer**: Gebruik formaat "YYMMDD-XXX-XX"

## JavaScript API

Het validatie script is standalone en werkt automatisch. Er is geen extra JavaScript configuratie nodig.

### Programmatisch Valideren

Als je handmatig een veld wilt valideren:

```javascript
// Wordt automatisch afgehandeld door het systeem
// Geen extra code nodig
```

### Event Listeners

Het systeem gebruikt de volgende events:
- `blur`: Valideer veld na verlaten
- `input`: Real-time validatie (alleen na eerste blur)
- `submit`: Valideer alle velden voor verzending
- `change`: Voor radio buttons en checkboxes

## Best Practices

1. **Altijd `<div class="invalid-feedback"></div>` toevoegen** na input velden
2. **Gebruik `data-validate="true"`** op het `<form>` element
3. **Combineer validators** voor betere validatie (bijv. `required` + `data-min-length`)
4. **Test alle velden** in verschillende browsers
5. **Gebruik placeholder text** om het verwachte formaat te tonen
6. **Voeg `maxlength` toe** bij strikte formaten (postcode, rijksregister)

## Browser Compatibiliteit

- ✅ Chrome/Edge (Chromium)
- ✅ Firefox
- ✅ Safari
- ✅ Mobile browsers (iOS Safari, Chrome Mobile)

## Toegankelijkheid

- Foutmeldingen zijn duidelijk leesbaar
- Kleurgebruik wordt aangevuld met icons
- Focus states zijn duidelijk zichtbaar
- Screen readers kunnen foutmeldingen lezen (ARIA attributen)

## Performance

- Minimale overhead (<8KB gecomprimeerd)
- Geen externe dependencies
- Efficiënte event handling
- Geen DOM manipulatie tijdens typing (alleen na blur)

## CSS Priority & Focus States

### Prioriteitsvolgorde

De CSS is zo geconfigureerd dat validation states **altijd voorrang** hebben op de default focus state:

1. **Default focus** (blauw) - alleen voor velden zonder validatie state
2. **Valid state** (groen) - overschrijft focus state met `!important`
3. **Invalid state** (rood) - overschrijft focus state met `!important`

### Focus Behavior per State

| State | Geen Focus | Met Focus |
|-------|-----------|-----------|
| **Normaal** | Geen border | 🔵 Blauw border + shadow |
| **Valid** | 🟢 Groen border | 🟢 Groen border + shadow |
| **Invalid** | 🔴 Rood border | 🔴 Rood border + shadow |

### Technische Implementatie

```css
/* Default focus (alleen voor niet-gevalideerde velden) */
.form-input:focus {
    border-color: #007bff;
    box-shadow: 0 0 0 0.2rem rgba(0, 123, 255, 0.25);
}

/* Valid state heeft voorrang */
.form-input.is-valid {
    border-color: #28a745 !important;
}

.form-input.is-valid:focus {
    border-color: #28a745 !important;
    box-shadow: 0 0 0 0.2rem rgba(40, 167, 69, 0.25) !important;
}

/* Invalid state heeft voorrang */
.form-input.is-invalid {
    border-color: #dc3545 !important;
}

.form-input.is-invalid:focus {
    border-color: #dc3545 !important;
    box-shadow: 0 0 0 0.2rem rgba(220, 53, 69, 0.25) !important;
}
```

Dit zorgt ervoor dat:
- ✅ Groene velden groen blijven, ook bij focus
- ✅ Rode velden rood blijven, ook bij focus
- ✅ Alleen niet-gevalideerde velden tonen blauw bij focus
