package be.achieveit.snhinschrijvingen.config;

import be.achieveit.snhinschrijvingen.model.Nationality;
import be.achieveit.snhinschrijvingen.model.SchoolYear;
import be.achieveit.snhinschrijvingen.repository.NationalityRepository;
import be.achieveit.snhinschrijvingen.repository.SchoolYearRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    private final SchoolYearRepository schoolYearRepository;
    private final NationalityRepository nationalityRepository;

    public DataInitializer(
            final SchoolYearRepository schoolYearRepository,
            final NationalityRepository nationalityRepository) {
        this.schoolYearRepository = schoolYearRepository;
        this.nationalityRepository = nationalityRepository;
    }

    @Override
    public void run(String... args) {
        initializeSchoolYears();
        initializeNationalities();
    }

    private void initializeSchoolYears() {
        if (schoolYearRepository.count() == 0) {
            logger.info("Initializing school years...");

            schoolYearRepository.save(createSchoolYear("2024-2025", "Schooljaar 2024-2025", 2024));
            schoolYearRepository.save(createSchoolYear("2025-2026", "Schooljaar 2025-2026", 2025));
            schoolYearRepository.save(createSchoolYear("2026-2027", "Schooljaar 2026-2027", 2026));

            logger.info("School years initialized");
        }
    }

    private SchoolYear createSchoolYear(String id, String description, int year) {
        SchoolYear schoolYear = new SchoolYear();
        schoolYear.setId(id);
        schoolYear.setDescription(description);
        schoolYear.setStartDate(LocalDate.of(year, 9, 1));
        schoolYear.setEndDate(LocalDate.of(year + 1, 8, 31));
        return schoolYear;
    }

    private void initializeNationalities() {
        if (nationalityRepository.count() == 0) {
            logger.info("Initializing nationalities...");

            nationalityRepository.save(createNationality("0", "Anders", "Autre"));
            nationalityRepository.save(createNationality("1", "Belg", "Belge"));
            nationalityRepository.save(createNationality("2", "Bulgaars", "Bulgare"));
            nationalityRepository.save(createNationality("3", "Chinees", "Chinois"));
            nationalityRepository.save(createNationality("4", "Congolees", "Congolais"));
            nationalityRepository.save(createNationality("5", "Duits", "Allemand"));
            nationalityRepository.save(createNationality("6", "Engels", "Anglais"));
            nationalityRepository.save(createNationality("7", "Frans", "Français"));
            nationalityRepository.save(createNationality("8", "Nederlands", "Néerlandais"));
            nationalityRepository.save(createNationality("9", "Grieks", "Grec"));
            nationalityRepository.save(createNationality("10", "Indiaas", "Indien"));
            nationalityRepository.save(createNationality("11", "Italiaans", "Italien"));
            nationalityRepository.save(createNationality("12", "Japans", "Japonais"));
            nationalityRepository.save(createNationality("13", "Spaans", "Espagnol"));
            nationalityRepository.save(createNationality("14", "Pools", "Polonais"));
            nationalityRepository.save(createNationality("15", "Portugees", "Portugais"));
            nationalityRepository.save(createNationality("16", "Marokkaans", "Marocain"));
            nationalityRepository.save(createNationality("17", "Roemeens", "Roumain"));
            nationalityRepository.save(createNationality("18", "Russisch", "Russe"));
            nationalityRepository.save(createNationality("19", "Slowaaks", "Slovaque"));
            nationalityRepository.save(createNationality("20", "Tsjechisch", "Tchèque"));
            nationalityRepository.save(createNationality("21", "Turks", "Turc"));
            nationalityRepository.save(createNationality("22", "Oekraïens", "Ukrainien"));

            logger.info("Nationalities initialized");
        }
    }

    private Nationality createNationality(String code, String nameNl, String nameFr) {
        Nationality nationality = new Nationality();
        nationality.setCode(code);
        nationality.setNameNl(nameNl);
        nationality.setNameFr(nameFr);
        return nationality;
    }
}
