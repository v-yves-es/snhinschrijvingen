package be.achieveit.snhinschrijvingen.repository;

import be.achieveit.snhinschrijvingen.model.StudyOrientation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudyOrientationRepository extends JpaRepository<StudyOrientation, Long> {
    
    Optional<StudyOrientation> findByCode(String code);
    
    List<StudyOrientation> findAllByOrderByDisplayOrder();
}
