package be.achieveit.snhinschrijvingen.repository;

import be.achieveit.snhinschrijvingen.model.StudyDomain;
import be.achieveit.snhinschrijvingen.model.StudyOrientation;
import be.achieveit.snhinschrijvingen.model.StudyProgram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudyProgramRepository extends JpaRepository<StudyProgram, Long> {
    
    Optional<StudyProgram> findByCode(String code);
    
    List<StudyProgram> findByYearAndIsActiveTrueOrderByDisplayOrder(Integer year);
    
    List<StudyProgram> findByYearAndDomainAndIsActiveTrueOrderByDisplayOrder(Integer year, StudyDomain domain);
    
    List<StudyProgram> findByYearAndDomainAndOrientationAndIsActiveTrueOrderByDisplayOrder(
        Integer year, StudyDomain domain, StudyOrientation orientation);
    
    @Query("SELECT DISTINCT sp.year FROM StudyProgram sp WHERE sp.isActive = true ORDER BY sp.year")
    List<Integer> findDistinctYears();
}
