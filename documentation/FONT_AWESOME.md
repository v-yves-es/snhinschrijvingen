# Font Awesome - Usage Guide

**Versie:** 6.5.1  
**CDN:** Cloudflare  
**Documentatie:** https://fontawesome.com/icons  
**Geïntegreerd sinds:** 28 januari 2026

---

## 📦 Installatie

Font Awesome is geïntegreerd via CDN in `fragments/layout.html`:

```html
<!-- Font Awesome -->
<link rel="stylesheet" 
      href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css" 
      integrity="sha512-DTOQO9RWCH3ppGqcWaEA1BIZOC6xxalwEsw9c2QQeAIftl+Vegovlnee1c9QX4TctnWMn13TZye+giMm8e2LwA==" 
      crossorigin="anonymous" 
      referrerpolicy="no-referrer" />
```

**✅ Beschikbaar in:** Alle Thymeleaf templates die `layout.html` gebruiken

---

## 🎨 Gebruik

### Basis Syntax

```html
<i class="fa-solid fa-{icon-name}"></i>
```

### Icon Styles

Font Awesome heeft 3 icon styles:

```html
<!-- Solid (filled) - GRATIS -->
<i class="fa-solid fa-user"></i>

<!-- Regular (outlined) - GRATIS -->
<i class="fa-regular fa-circle-check"></i>

<!-- Brands (logo's) - GRATIS -->
<i class="fa-brands fa-facebook"></i>
```

---

## 📋 Veelgebruikte Icons

### Document & Files
```html
<i class="fa-solid fa-file-pdf"></i>         <!-- PDF document -->
<i class="fa-solid fa-file"></i>             <!-- Generic file -->
<i class="fa-solid fa-download"></i>         <!-- Download -->
<i class="fa-solid fa-upload"></i>           <!-- Upload -->
```

### Actions
```html
<i class="fa-solid fa-trash"></i>            <!-- Delete -->
<i class="fa-solid fa-pen"></i>              <!-- Edit -->
<i class="fa-solid fa-check"></i>            <!-- Check/Confirm -->
<i class="fa-solid fa-xmark"></i>            <!-- Close/Cancel -->
<i class="fa-solid fa-plus"></i>             <!-- Add -->
<i class="fa-solid fa-arrow-right"></i>      <!-- Next -->
<i class="fa-solid fa-arrow-left"></i>       <!-- Previous -->
```

### User & Contact
```html
<i class="fa-solid fa-user"></i>             <!-- User -->
<i class="fa-solid fa-users"></i>            <!-- Multiple users -->
<i class="fa-solid fa-envelope"></i>         <!-- Email -->
<i class="fa-solid fa-phone"></i>            <!-- Phone -->
<i class="fa-solid fa-location-dot"></i>     <!-- Location -->
```

### Status & Feedback
```html
<i class="fa-solid fa-circle-check"></i>     <!-- Success -->
<i class="fa-solid fa-circle-xmark"></i>     <!-- Error -->
<i class="fa-solid fa-circle-info"></i>      <!-- Info -->
<i class="fa-solid fa-triangle-exclamation"></i> <!-- Warning -->
<i class="fa-solid fa-spinner fa-spin"></i>  <!-- Loading -->
```

### Education & School
```html
<i class="fa-solid fa-graduation-cap"></i>   <!-- Education -->
<i class="fa-solid fa-book"></i>             <!-- Book -->
<i class="fa-solid fa-school"></i>           <!-- School building -->
<i class="fa-solid fa-chalkboard"></i>       <!-- Chalkboard -->
```

---

## 🎨 Styling Icons

### Size

```html
<!-- Relative sizing -->
<i class="fa-solid fa-user fa-xs"></i>       <!-- Extra small -->
<i class="fa-solid fa-user fa-sm"></i>       <!-- Small -->
<i class="fa-solid fa-user fa-lg"></i>       <!-- Large -->
<i class="fa-solid fa-user fa-xl"></i>       <!-- Extra large -->
<i class="fa-solid fa-user fa-2xl"></i>      <!-- 2x Extra large -->

<!-- Fixed sizing -->
<i class="fa-solid fa-user fa-1x"></i>
<i class="fa-solid fa-user fa-2x"></i>
<i class="fa-solid fa-user fa-3x"></i>
<i class="fa-solid fa-user fa-5x"></i>
<i class="fa-solid fa-user fa-10x"></i>

<!-- Custom via CSS -->
<i class="fa-solid fa-user" style="font-size: 1.5rem;"></i>
```

### Color

```html
<!-- Via inline style -->
<i class="fa-solid fa-file-pdf" style="color: #dc3545;"></i>

<!-- Via CSS class -->
<i class="fa-solid fa-check text-success"></i>
```

### Rotation & Animation

```html
<!-- Rotate -->
<i class="fa-solid fa-shield fa-rotate-90"></i>
<i class="fa-solid fa-shield fa-rotate-180"></i>
<i class="fa-solid fa-shield fa-rotate-270"></i>
<i class="fa-solid fa-shield fa-flip-horizontal"></i>
<i class="fa-solid fa-shield fa-flip-vertical"></i>

<!-- Animate -->
<i class="fa-solid fa-spinner fa-spin"></i>
<i class="fa-solid fa-heart fa-beat"></i>
<i class="fa-solid fa-triangle-exclamation fa-fade"></i>
```

---

## 💡 Praktijkvoorbeelden

### PDF Download Button (zoals in registrations-overview.html)

```html
<button type="button" 
        class="btn btn--icon"
        title="Download PDF"
        style="padding: 0.5rem 0.75rem; 
               background-color: transparent; 
               border: 1px solid #6c757d; 
               border-radius: 0.25rem; 
               cursor: pointer; 
               font-size: 1.25rem; 
               color: #dc3545;">
    <i class="fa-solid fa-file-pdf"></i>
</button>
```

### Delete Button

```html
<button type="button" 
        class="btn btn--danger">
    <i class="fa-solid fa-trash"></i>
    Verwijderen
</button>
```

### Success Message

```html
<div class="alert alert-success">
    <i class="fa-solid fa-circle-check"></i>
    Inschrijving succesvol voltooid!
</div>
```

### Loading Spinner

```html
<button type="submit" disabled>
    <i class="fa-solid fa-spinner fa-spin"></i>
    Bezig met opslaan...
</button>
```

### Info Box Icon

```html
<div class="info-box info-box--info">
    <h2 class="info-box__title">
        <i class="fa-solid fa-circle-info"></i>
        Belangrijk
    </h2>
    <p class="info-box__text">...</p>
</div>
```

---

## 🔍 Icon Zoeken

**Online search:** https://fontawesome.com/search

**Tips:**
- Zoek op Nederlands keyword (bijv. "trash" voor prullenbak)
- Filter op "Free" icons
- Check "Solid" style voor de meeste use cases
- Kopieer de class name (bijv. `fa-solid fa-trash`)

---

## 📱 Accessibility

### Best Practices

**Voor decoratieve icons (met tekst):**
```html
<button>
    <i class="fa-solid fa-download" aria-hidden="true"></i>
    Download
</button>
```

**Voor standalone icons (zonder tekst):**
```html
<button title="Verwijderen" aria-label="Verwijderen">
    <i class="fa-solid fa-trash"></i>
</button>
```

**Voor belangrijke informatie:**
```html
<i class="fa-solid fa-circle-check" role="img" aria-label="Succes"></i>
```

---

## 🎯 Currently Used Icons

### In Production

| Icon | Locatie | Gebruik |
|------|---------|---------|
| `fa-file-pdf` | registrations-overview.html | PDF download button voor voltooide inschrijvingen |

### Recommended for Future Use

| Icon | Suggested Use |
|------|---------------|
| `fa-envelope` | Email verification, contact info |
| `fa-user` | Student profile, user accounts |
| `fa-graduation-cap` | Study program selection |
| `fa-school` | Previous school selection |
| `fa-heart-pulse` | Care needs, medical info |
| `fa-users` | Relations/family info |
| `fa-laptop` | Laptop section |
| `fa-money-bill` | Financial/school account |
| `fa-shield-halved` | Privacy settings |
| `fa-check-circle` | Confirmation page |
| `fa-circle-info` | Info boxes |
| `fa-triangle-exclamation` | Warning boxes |
| `fa-circle-xmark` | Error messages |

---

## ⚠️ Important Notes

1. **Only use FREE icons** - Premium icons require license
2. **Always include fallback** - Text labels for accessibility
3. **Consistent sizing** - Use same size for similar actions
4. **Color meaning** - Follow conventions (red=delete, green=success)
5. **Loading states** - Use `fa-spin` for spinners
6. **Mobile friendly** - Ensure icons are large enough on mobile (min 44x44px touch target)

---

## 🔗 Resources

- **Official Documentation:** https://fontawesome.com/docs
- **Icon Search:** https://fontawesome.com/search
- **Version 6.5.1 Changes:** https://fontawesome.com/changelog/latest
- **Accessibility Guide:** https://fontawesome.com/docs/web/dig-deeper/accessibility
- **CDN Info:** https://cdnjs.com/libraries/font-awesome

---

**End of Font Awesome Guide**
