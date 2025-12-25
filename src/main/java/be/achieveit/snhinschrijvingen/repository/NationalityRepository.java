package be.achieveit.snhinschrijvingen.repository;

import be.achieveit.snhinschrijvingen.model.Nationality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NationalityRepository extends JpaRepository<Nationality, Long> {
    
    List<Nationality> findAllByOrderByDisplayOrderAscNameNlAsc();
    
    Optional<Nationality> findByCode(String code);
}
