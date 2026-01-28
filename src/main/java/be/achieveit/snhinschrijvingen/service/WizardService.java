package be.achieveit.snhinschrijvingen.service;

import be.achieveit.snhinschrijvingen.model.WizardStep;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WizardService {
    
    private final List<WizardStep> wizardSteps;

    public WizardService() {
        this.wizardSteps = new ArrayList<>();
        wizardSteps.add(new WizardStep(1, "Algemeen", "/inschrijving/leerling-info/{id}"));
        wizardSteps.add(new WizardStep(2, "Richting", "/inschrijving/studierichting/{id}"));
        wizardSteps.add(new WizardStep(3, "Vorige school", "/inschrijving/vorige-school/{id}"));
        wizardSteps.add(new WizardStep(4, "Relaties", "/inschrijving/relaties/{id}"));
        wizardSteps.add(new WizardStep(5, "Huisarts", "/inschrijving/huisarts/{id}"));
        wizardSteps.add(new WizardStep(6, "Zorgvraag", "/inschrijving/zorgvraag/{id}"));
        wizardSteps.add(new WizardStep(7, "Privacy", "/inschrijving/privacy/{id}"));
        wizardSteps.add(new WizardStep(8, "Laptop", "/inschrijving/laptop/{id}"));
        wizardSteps.add(new WizardStep(9, "Schoolrekening", "/inschrijving/schoolrekening/{id}"));
        wizardSteps.add(new WizardStep(10, "Verzenden", "/inschrijving/verzenden/{id}"));
    }

    public List<WizardStep> getWizardSteps(int currentStep, List<Integer> completedSteps, String registrationId) {
        List<WizardStep> steps = new ArrayList<>();
        for (WizardStep step : wizardSteps) {
            String url = step.getUrl().replace("{id}", registrationId != null ? registrationId : "");
            WizardStep stepCopy = new WizardStep(step.getNumber(), step.getLabel(), url);
            stepCopy.setActive(step.getNumber() == currentStep);
            stepCopy.setCompleted(completedSteps != null && completedSteps.contains(step.getNumber()));
            steps.add(stepCopy);
        }
        return steps;
    }
    
    public List<WizardStep> getWizardSteps(int currentStep) {
        // Automatically mark all previous steps as completed
        List<Integer> completedSteps = new ArrayList<>();
        for (int i = 1; i < currentStep; i++) {
            completedSteps.add(i);
        }
        return getWizardSteps(currentStep, completedSteps, null);
    }
}
