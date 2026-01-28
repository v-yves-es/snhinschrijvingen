package be.achieveit.snhinschrijvingen.repository;

import be.achieveit.snhinschrijvingen.model.EmailStatus;
import be.achieveit.snhinschrijvingen.model.Registration;
import be.achieveit.snhinschrijvingen.model.RegistrationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, UUID> {

    List<Registration> findByEmailOrderByCreatedAtDesc(String email);

    List<Registration> findByEmailAndStatusOrderByCreatedAtDesc(String email, RegistrationStatus status);

    Optional<Registration> findByIdAndEmailHash(UUID id, String emailHash);

    Optional<Registration> findFirstByEmailAndStatusOrderByCreatedAtDesc(String email, RegistrationStatus status);

    boolean existsByEmailHash(String emailHash);
    
    Optional<Registration> findFirstByEmailHashAndEmailStatusOrderByCreatedAtDesc(String emailHash, EmailStatus emailStatus);
    
    List<Registration> findByEmail(String email);
}
