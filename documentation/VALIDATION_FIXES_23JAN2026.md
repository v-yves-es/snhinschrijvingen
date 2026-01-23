# Relations Validation Fixes - 23 Januari 2026

## Problemen Geïdentificeerd

### 1. Box (Bus) Veld Moet Niet Verplicht Zijn
**Probleem:** Bij relatie 2 werd het busnummer als verplicht gemarkeerd, terwijl dit optioneel moet zijn.

**Oorzaak:** De `address-fields.html` fragment gebruikte de `isRequired` parameter voor ALLE velden inclusief het bus veld.

**Oplossing:** Het bus veld krijgt NOOIT een `required` attribuut, ongeacht de `isRequired` parameter.

**Bestanden aangepast:**
- `/src/main/resources/templates/fragments/address-fields.html` - regel 36-46: Bus veld nooit required

### 2. Validatie van Verborgen Relatie 2 Velden
**Probleem:** Wanneer relatie 2 niet zichtbaar is (niet geklikt op "Tweede relatie toevoegen"), werden de velden toch gevalideerd en blokkeerden ze het doorgaan.

**Oorzaak:** Velden hadden `required` attribuut ondanks dat de parent container `display: none` had.

**Oplossing:** 
1. Bij initialisatie: Als relatie 2 verborgen is, verwijder `required` van alle velden en clear validation states
2. Bij toevoegen relatie 2: Voeg `required` toe aan alle velden (behalve bus)
3. Bij verwijderen relatie 2: Verwijder `required` en clear alle velden en validation states

**Bestanden aangepast:**
- `/src/main/resources/templates/relations.html`:
  - regel 306-325: Initialisatie - clear velden en required states voor verborgen relatie 2
  - regel 334-338: Bij toevoegen - set required op alle velden behalve `.box`
  - regel 371: Bij verwijderen - verwijder required

**Hoe het werkt:**
- De bestaande `form-validation.js` skipt al automatisch velden in verborgen containers (regels 158-172)
- Door `required` te verwijderen van verborgen velden, voorkomt men ook HTML5 native validatie problemen
- Door validation states te clearen (`.is-valid`, `.is-invalid` classes) start men met een schone lei

### 3. Copy Domicile Address - Validation State Update
**Probleem:** Na klikken op "Kopieer domicilieadres" bleven velden rood (invalid) ondanks dat ze gevuld waren.

**Oorzaak:** De kopieer functie vulde alleen de waarden in maar triggerde geen re-validatie van de velden.

**Oplossing:** Na het kopiëren:
1. Mark veld als `touched`
2. Verwijder `is-invalid` class
3. Voeg `is-valid` class toe voor gevulde velden
4. Verberg error messages
5. Trigger `input` event voor re-validatie

**Bestanden aangepast:**
- `/src/main/resources/templates/relations.html` - regel 256-289: Verbeterde `copyDomicileAddress` functie

## Testing Checklist

### Scenario 1: Alleen Relatie 1
- [ ] Vul alle relatie 1 velden in (inclusief adres)
- [ ] Bus veld NIET verplicht - laat leeg
- [ ] Klik "Volgende"
- [ ] ✅ Moet doorgaan zonder validatie errors

### Scenario 2: Relatie 1 met Copy Domicile Address
- [ ] Vul relatie 1 basis velden in
- [ ] Klik "Kopieer domicilieadres"
- [ ] ✅ Adresvelden worden gevuld
- [ ] ✅ Rode borders worden direct groen
- [ ] ✅ Geen error messages meer zichtbaar
- [ ] Klik "Volgende" → Moet werken

### Scenario 3: Toevoegen en Verwijderen Relatie 2
- [ ] Vul relatie 1 volledig in
- [ ] Klik "Tweede relatie toevoegen"
- [ ] Relatie 2 sectie wordt zichtbaar
- [ ] Velden zijn leeg en zonder validation state
- [ ] Klik "Verwijderen"
- [ ] ✅ Relatie 2 sectie verdwijnt
- [ ] ✅ Velden worden gecleared
- [ ] Klik "Volgende" → Moet werken (geen validatie op verborgen velden)

### Scenario 4: Twee Relaties Met Volledige Data
- [ ] Vul relatie 1 volledig in
- [ ] Bus veld relatie 1 leeg laten
- [ ] Klik "Tweede relatie toevoegen"
- [ ] Vul relatie 2 volledig in
- [ ] Bus veld relatie 2 leeg laten
- [ ] Klik "Volgende"
- [ ] ✅ Moet doorgaan zonder errors

### Scenario 5: Relatie 2 Onvolledig
- [ ] Vul relatie 1 volledig in
- [ ] Klik "Tweede relatie toevoegen"
- [ ] Vul relatie 2 GEDEELTELIJK in (bijv. alleen naam)
- [ ] Klik "Volgende"
- [ ] ✅ Validatie errors op lege verplichte velden relatie 2
- [ ] ❌ Mag NIET doorgaan

### Scenario 6: Copy Domicile Address Meerdere Keren
- [ ] Vul relatie 1 basis velden in
- [ ] Klik "Kopieer domicilieadres" bij relatie 1
- [ ] ✅ Velden worden gevuld en groen
- [ ] Wijzig een adresveld (bijv. straat)
- [ ] Klik nogmaals "Kopieer domicilieadres"
- [ ] ✅ Oorspronkelijk domicilieadres wordt terug ingevuld
- [ ] ✅ Velden blijven groen

## Technische Details

### Form Validation Logic Flow

```
User submits form
    ↓
For each field in form:
    ↓
    Is field or parent hidden? (getComputedStyle check)
    ↓ YES: Skip validation
    ↓ NO: Continue
    ↓
    Does field have 'required' attribute?
    ↓ YES: Validate
    ↓ NO: Skip
    ↓
    Is field valid?
    ↓ YES: Add 'is-valid' class
    ↓ NO: Add 'is-invalid' class, show error message
    ↓
All fields validated
    ↓
Any invalid fields?
    ↓ YES: Prevent submit, scroll to first error
    ↓ NO: Allow submit
```

### Key JavaScript Functions

**`copyDomicileAddress(relationPrefix)`**
- Kopieert domicilieadres naar relatie adresvelden
- Update validation states direct
- Triggert re-validatie via `input` event

**`initRelationsToggle()`**
- Setup add/remove buttons voor relatie 2
- Manage `required` attributes
- Clear validation states bij show/hide
- Initialize/destroy Select2

## Code Standards Toegepast

1. ✅ **Explicit Validation State Management**: Clear/set validation states expliciet
2. ✅ **Conditional Required Attributes**: Required alleen op zichtbare velden
3. ✅ **Event-Driven Validation**: Gebruik native events (`input`, `blur`) voor validatie triggers
4. ✅ **Defensive Programming**: Check element existence voor DOM manipulaties
5. ✅ **Console Logging**: Duidelijke debug logs voor troubleshooting

## Gerelateerde Documentatie

- `/documentation/FORM_VALIDATION.md` - Algemene form validatie documentatie
- `/documentation/VALIDATION_COMPLETE.md` - Client-side validatie implementatie details
- `/src/main/resources/static/js/form-validation.js` - Core validatie logic

## Volgende Stappen

- [ ] Test alle scenarios handmatig
- [ ] Voeg unit tests toe voor validation logic (toekomstig)
- [ ] Overweeg server-side validatie voor dubbele check
- [ ] Documenteer validation patterns voor toekomstige formulieren

---

**Status:** ✅ Fixes geïmplementeerd - Ready for testing  
**Datum:** 23 januari 2026  
**Auteur:** GitHub Copilot CLI
