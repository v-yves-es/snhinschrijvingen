package be.achieveit.snhinschrijvingen.repository;

import be.achieveit.snhinschrijvingen.model.StudyDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudyDomainRepository extends JpaRepository<StudyDomain, Long> {
    
    Optional<StudyDomain> findByCode(String code);
    
    List<StudyDomain> findByApplicableFromYearLessThanEqualOrderByDisplayOrder(Integer year);
}
