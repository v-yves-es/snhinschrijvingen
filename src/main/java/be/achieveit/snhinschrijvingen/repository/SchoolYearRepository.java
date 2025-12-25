package be.achieveit.snhinschrijvingen.repository;

import be.achieveit.snhinschrijvingen.model.SchoolYear;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface SchoolYearRepository extends JpaRepository<SchoolYear, String> {
    
    Optional<SchoolYear> findByActiveTrue();
    
    Optional<SchoolYear> findByStartDateLessThanEqualAndEndDateGreaterThanEqual(LocalDate date1, LocalDate date2);
}
