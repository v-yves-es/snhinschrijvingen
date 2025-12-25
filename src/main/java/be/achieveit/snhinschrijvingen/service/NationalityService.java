package be.achieveit.snhinschrijvingen.service;

import be.achieveit.snhinschrijvingen.model.Nationality;
import be.achieveit.snhinschrijvingen.repository.NationalityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NationalityService {

    private static final Logger logger = LoggerFactory.getLogger(NationalityService.class);

    private final NationalityRepository nationalityRepository;

    public NationalityService(NationalityRepository nationalityRepository) {
        this.nationalityRepository = nationalityRepository;
    }

    public List<Nationality> getAllNationalities() {
        return nationalityRepository.findAllByOrderByDisplayOrderAscNameNlAsc();
    }

    public Optional<Nationality> findByCode(String code) {
        return nationalityRepository.findByCode(code);
    }

    public Nationality save(Nationality nationality) {
        return nationalityRepository.save(nationality);
    }
}
