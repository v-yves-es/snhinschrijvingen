package be.achieveit.snhinschrijvingen.service;

import be.achieveit.snhinschrijvingen.model.StudyDomain;
import be.achieveit.snhinschrijvingen.model.StudyOrientation;
import be.achieveit.snhinschrijvingen.model.StudyProgram;
import be.achieveit.snhinschrijvingen.repository.StudyDomainRepository;
import be.achieveit.snhinschrijvingen.repository.StudyOrientationRepository;
import be.achieveit.snhinschrijvingen.repository.StudyProgramRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudyProgramService {

    private final StudyProgramRepository studyProgramRepository;
    private final StudyDomainRepository studyDomainRepository;
    private final StudyOrientationRepository studyOrientationRepository;

    public StudyProgramService(StudyProgramRepository studyProgramRepository,
                              StudyDomainRepository studyDomainRepository,
                              StudyOrientationRepository studyOrientationRepository) {
        this.studyProgramRepository = studyProgramRepository;
        this.studyDomainRepository = studyDomainRepository;
        this.studyOrientationRepository = studyOrientationRepository;
    }

    public List<StudyProgram> getStudyProgramsByYear(Integer year) {
        return studyProgramRepository.findByYearAndIsActiveTrueOrderByDisplayOrder(year);
    }

    public List<StudyProgram> getStudyProgramsByYearAndDomain(Integer year, String domainCode) {
        Optional<StudyDomain> domain = studyDomainRepository.findByCode(domainCode);
        return domain.map(d -> studyProgramRepository.findByYearAndDomainAndIsActiveTrueOrderByDisplayOrder(year, d))
                    .orElse(List.of());
    }

    public List<StudyProgram> getStudyProgramsByYearDomainAndOrientation(Integer year, String domainCode, String orientationCode) {
        Optional<StudyDomain> domain = studyDomainRepository.findByCode(domainCode);
        Optional<StudyOrientation> orientation = studyOrientationRepository.findByCode(orientationCode);
        
        if (domain.isPresent() && orientation.isPresent()) {
            return studyProgramRepository.findByYearAndDomainAndOrientationAndIsActiveTrueOrderByDisplayOrder(
                year, domain.get(), orientation.get());
        }
        return List.of();
    }

    public List<StudyDomain> getDomainsForYear(Integer year) {
        return studyDomainRepository.findByApplicableFromYearLessThanEqualOrderByDisplayOrder(year);
    }

    public List<StudyOrientation> getAllOrientations() {
        return studyOrientationRepository.findAllByOrderByDisplayOrder();
    }

    public Optional<StudyProgram> getStudyProgramByCode(String code) {
        return studyProgramRepository.findByCode(code);
    }
    
    public List<Integer> getAvailableYears() {
        return studyProgramRepository.findDistinctYears();
    }
    
    public String getDomainColor(String domainCode) {
        return switch (domainCode) {
            case "EO" -> "#00a3e0";
            case "MW" -> "#e6007e";
            case "STEM" -> "#00a651";
            case "TC" -> "#ff6600";
            default -> "#333333";
        };
    }
}
