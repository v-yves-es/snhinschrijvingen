# Study Program Data Model

## Overview
The study program management system stores all available study programs (richtingen) for students enrolling in the school. The system supports years 1-6 with varying complexity.

## Database Structure

### Tables

#### `study_domains`
Represents the main study areas (applicable from year 3 onwards):
- **EO**: Economie en organisatie
- **MW**: Maatschappij en welzijn
- **STEM**: STEM
- **TC**: Taal en cultuur

Fields:
- `id` (BIGINT, PK, auto-generated)
- `code` (VARCHAR(10), unique, not null)
- `name` (VARCHAR(100), not null)
- `display_order` (INTEGER, not null)
- `applicable_from_year` (INTEGER, not null) - The year from which this domain applies

#### `study_orientations`
Represents the study orientation types:
- **D**: Doorstroom
- **DA**: Doorstroom/arbeidsmarkt
- **A**: Arbeidsmarkt

Fields:
- `id` (BIGINT, PK, auto-generated)
- `code` (VARCHAR(20), unique, not null)
- `name` (VARCHAR(100), not null)
- `display_order` (INTEGER, not null)

#### `study_programs`
Contains all study programs per year.

Fields:
- `id` (BIGINT, PK, auto-generated)
- `code` (VARCHAR(20), unique, not null) - e.g., "1A-LAT", "3-BW-CLIL"
- `name` (VARCHAR(200), not null) - Full display name
- `study_year` (INTEGER, not null) - Year 1-6
- `domain_id` (BIGINT, FK to study_domains) - NULL for years 1-2
- `orientation_id` (BIGINT, FK to study_orientations) - NULL for years 1-2
- `display_order` (INTEGER, not null)
- `is_active` (BOOLEAN, not null, default true)

## Program Structure by Year

### Year 1 & 2
- Simple list of programs without domain/orientation classification
- Programs stored directly with NULL domain and orientation

### Years 3-6
- Programs organized by:
  1. **Domain** (Economie, Maatschappij, STEM, Taal)
  2. **Orientation** (Doorstroom, Doorstroom/arbeidsmarkt, Arbeidsmarkt)
  3. **Specific program**

## Java Entities

### `StudyDomain.java`
- Entity representing study domains
- Fields: id, code, name, displayOrder, applicableFromYear

### `StudyOrientation.java`
- Entity representing study orientations
- Fields: id, code, name, displayOrder

### `StudyProgram.java`
- Entity representing individual study programs
- Fields: id, code, name, year, domain, orientation, displayOrder, isActive
- Relationships: ManyToOne to StudyDomain and StudyOrientation

## Repositories

### `StudyDomainRepository`
- `findByCode(String code)` - Find domain by code
- `findByApplicableFromYearLessThanEqualOrderByDisplayOrder(Integer year)` - Get domains for a specific year

### `StudyOrientationRepository`
- `findByCode(String code)` - Find orientation by code
- `findAllByOrderByDisplayOrder()` - Get all orientations ordered

### `StudyProgramRepository`
- `findByCode(String code)` - Find program by code
- `findByYearAndIsActiveTrueOrderByDisplayOrder(Integer year)` - All programs for a year
- `findByYearAndDomainAndIsActiveTrueOrderByDisplayOrder(Integer year, StudyDomain domain)` - Programs by year and domain
- `findByYearAndDomainAndOrientationAndIsActiveTrueOrderByDisplayOrder(...)` - Programs by year, domain, and orientation

## Service Layer

### `StudyProgramService`
Provides business logic for retrieving study programs:

- `getStudyProgramsByYear(Integer year)` - All programs for a year
- `getStudyProgramsByYearAndDomain(Integer year, String domainCode)` - Filter by domain
- `getStudyProgramsByYearDomainAndOrientation(...)` - Filter by domain and orientation
- `getDomainsForYear(Integer year)` - Get applicable domains
- `getAllOrientations()` - Get all orientations
- `getStudyProgramByCode(String code)` - Find specific program

## Data Initialization

All data is initialized via `data.sql`:
- 4 study domains
- 3 study orientations
- ~200 study programs across all years (1-6)

## Usage Notes

1. Years 1-2 have simple program lists (no domain/orientation)
2. Years 3-6 use the hierarchical structure (domain → orientation → program)
3. Some programs appear in multiple domains (e.g., "Economie-Wiskunde" in both EO and STEM for year 5)
4. CLIL variants are separate programs with distinct codes
5. Programs can be deactivated using the `is_active` flag without deletion
