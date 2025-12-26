# Study Program Selection Module - Implementation Guide

## Overview
The Study Program Selection module is the second step in the registration wizard. It allows students to select their year (1-6) and then choose a study program (richting) appropriate for that year.

## Database Structure

### Tables Created
All tables are defined in `schema.sql` and populated in `data.sql`:

1. **study_domains** - Study domains (applicable from year 3+)
   - Economie en organisatie (EO) - Blue (#00a3e0)
   - Maatschappij en welzijn (MW) - Pink (#e6007e)
   - STEM - Green (#00a651)
   - Taal en cultuur (TC) - Orange (#ff6600)

2. **study_orientations** - Study orientations (applicable from year 3+)
   - Doorstroom (D)
   - Doorstroom/arbeidsmarkt (DA)
   - Arbeidsmarkt (A)

3. **study_programs** - Individual study programs (~200 total)
   - All years (1-6) with their respective programs
   - Years 1-2: Simple list (no domain/orientation)
   - Years 3-6: Grouped by domain and orientation

## Backend Implementation

### Models (in `model/` package)
- `StudyDomain.java` - Domain entity with color information
- `StudyOrientation.java` - Orientation entity  
- `StudyProgram.java` - Program entity with relationships

### Repositories (in `repository/` package)
Updated `StudyProgramRepository.java`:
```java
@Query("SELECT DISTINCT sp.year FROM StudyProgram sp WHERE sp.isActive = true ORDER BY sp.year")
List<Integer> findDistinctYears();
```

### Services (in `service/` package)
Updated `StudyProgramService.java` with new methods:
```java
public List<Integer> getAvailableYears()
public String getDomainColor(String domainCode)
```

Color mapping:
- EO → #00a3e0 (blue)
- MW → #e6007e (pink)
- STEM → #00a651 (green)
- TC → #ff6600 (orange)

### Controllers (in `controller/` package)
**New file**: `StudyProgramController.java`

Key endpoints:
1. `GET /inschrijving/study-program?id={uuid}`
   - Displays the study program selection page
   - Loads available years from database
   - Sets wizard step to 2

2. `GET /inschrijving/study-program/programs?year={year}`
   - AJAX endpoint returning JSON
   - Returns programs for selected year
   - For years 3-6: includes grouping by domain/orientation with colors
   - For years 1-2: returns flat list

3. `POST /inschrijving/study-program`
   - Saves selected study program
   - Redirects to next wizard step

## Frontend Implementation

### Template: `study-program.html`
Structure:
```html
<form>
  <!-- Year Selection -->
  <select id="studyYear">
    <option>1ste jaar</option>
    <option>2de jaar</option>
    ...
  </select>
  
  <!-- Programs Container (dynamically populated) -->
  <div id="studyProgramsContainer">
    <div id="programsList"></div>
  </div>
  
  <!-- Navigation Buttons -->
  <button type="button" class="btn btn--secondary">Vorige</button>
  <button type="submit" class="btn btn--primary">Volgende</button>
</form>
```

### JavaScript Functionality
Located inline in `study-program.html`:

1. **Year Selection Handler**
   - Listens to year dropdown change
   - Fetches programs via AJAX
   - Calls appropriate render function

2. **renderSimplePrograms(data)** - For years 1-2
   - Displays flat list of radio buttons
   - No grouping or colors

3. **renderGroupedPrograms(data)** - For years 3-6
   - Groups by domain (colored headers)
   - Sub-groups by orientation (bold)
   - Radio buttons for individual programs

4. **Form Validation**
   - Year must be selected
   - Program must be selected
   - Submit button disabled until both selected

### Styling: `snh-style.css`
Added new styles:

```css
.study-domain-group {
    margin-bottom: 2rem;
}

.study-domain-title {
    font-size: 1.25rem;
    font-weight: 700;
    margin-bottom: 1rem;
    padding-left: 1rem;
    /* Color applied dynamically via JavaScript */
    /* Border-left applied dynamically */
}

.study-orientation-group {
    margin-left: 1.5rem;
    margin-bottom: 1.5rem;
}

.study-orientation-title {
    font-weight: 600;
    font-size: 1rem;
    margin-bottom: 0.75rem;
    color: #333;
}

.study-programs {
    margin-left: 1rem;
}
```

## Wizard Integration

### Updated `WizardService.java`
```java
wizardSteps.add(new WizardStep(1, "Algemeen", "/inschrijving/student-info"));
wizardSteps.add(new WizardStep(2, "Richting", "/inschrijving/study-program"));
wizardSteps.add(new WizardStep(3, "Huisarts", "/inschrijving/huisarts"));
// ... etc
```

Changes:
- Removed separate "Jaar" step
- "Richting" is now step 2 (includes year selection)
- All subsequent steps renumbered

### Updated `StudentInfoController.java`
Changed redirect after form submission:
```java
return "redirect:/inschrijving/study-program?id=" + id;
```

## User Flow

1. **Student Info Page** (Step 1 - Algemeen)
   - Student fills in personal information
   - Clicks "Volgende"
   - Redirects to Study Program page

2. **Study Program Page** (Step 2 - Richting)
   - Student selects year from dropdown (1ste - 6de)
   - Programs load dynamically via AJAX
   - **For years 1-2**: Simple radio button list
   - **For years 3-6**: Hierarchical colored display
   - Student selects program
   - Clicks "Volgende"

3. **Rendering Logic**
   - Year 1-2: Flat list (5-7 options each)
   - Year 3-6: Grouped display (~20 options each)
     ```
     [Domain Name in color with left border]
       [Orientation Name in bold]
         ○ Program 1
         ○ Program 2
     ```

## Example Output (Year 3+)

```
🔵 Economie en organisatie (blue #00a3e0)
   Doorstroom
     ○ 3 Bedrijfswetenschappen (BW)
     ○ 3 Bedrijfswetenschappen (BW) met CLIL
     ○ 3 Economische Wetenschappen (E)
   Doorstroom/arbeidsmarkt
     ○ 3 Bedrijf en Organisatie (BO)
   Arbeidsmarkt
     ○ 3 Organisatie en Logistiek (OL)

🔴 Maatschappij en welzijn (pink #e6007e)
   Doorstroom
     ○ 3 Humane Wetenschappen (H)
     ○ 3 Maatschappij- en Welzijnswetenschappen (MW)
   ...
```

## Data Volume

- **Year 1**: 5 programs
- **Year 2**: 7 programs
- **Year 3**: ~20 programs (4 domains × 1-2 orientations × 1-5 programs)
- **Year 4**: ~20 programs
- **Year 5**: ~35 programs (more cross-domain options)
- **Year 6**: ~30 programs

## Future Enhancements

1. **Persistence**
   - Add `studyProgramId` field to `Registration` entity
   - Save selected program when form submits

2. **Validation**
   - Server-side validation of year/program combination
   - Check if program is still active

3. **UI Improvements**
   - Add search/filter for years 3-6 (many options)
   - Add program descriptions on hover/click
   - Add "Meer informatie" links to program details

4. **Accessibility**
   - Add ARIA labels to radio buttons
   - Keyboard navigation improvements
   - Screen reader announcements

## Testing

To test manually:
1. Start application: `./mvnw spring-boot:run`
2. Navigate to: `http://localhost:8080/inschrijving/start`
3. Enter email and verify
4. Fill in student info (step 1)
5. Click "Volgende" → should show study-program page
6. Select a year → programs should load
7. Select a program → "Volgende" button enables
8. Click "Volgende" → proceeds to next step

## Files Modified/Created

### Created:
- `src/main/java/be/achieveit/snhinschrijvingen/controller/StudyProgramController.java`
- `src/main/resources/templates/study-program.html`

### Modified:
- `src/main/java/be/achieveit/snhinschrijvingen/repository/StudyProgramRepository.java` (added `findDistinctYears()`)
- `src/main/java/be/achieveit/snhinschrijvingen/service/StudyProgramService.java` (added color mapping and years)
- `src/main/java/be/achieveit/snhinschrijvingen/service/WizardService.java` (updated wizard steps)
- `src/main/java/be/achieveit/snhinschrijvingen/controller/StudentInfoController.java` (updated redirect)
- `src/main/resources/static/css/snh-style.css` (added study program styles)

## Colors Reference
Based on school branding from official PDF (studieaanbod_2-3de_graad_webv2.pdf):
- **Primary Red** (#c92617) - snh red (header)
- **EO Blue** (#00a3e0) - Economie en organisatie
- **MW Pink** (#e6007e) - Maatschappij en welzijn
- **STEM Green** (#00a651) - STEM
- **TC Orange** (#ff6600) - Taal en cultuur
