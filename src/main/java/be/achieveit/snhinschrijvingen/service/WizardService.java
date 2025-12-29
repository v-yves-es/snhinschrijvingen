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
        wizardSteps.add(new WizardStep(1, "Algemeen", "/inschrijving/student-info"));
        wizardSteps.add(new WizardStep(2, "Richting", "/inschrijving/study-program"));
        wizardSteps.add(new WizardStep(3, "Vorige school", "/inschrijving/previous-school"));
        wizardSteps.add(new WizardStep(4, "Relaties", "/inschrijving/relations"));
        wizardSteps.add(new WizardStep(5, "Huisarts", "/inschrijving/huisarts"));
        wizardSteps.add(new WizardStep(6, "Zorgvraag", "/inschrijving/zorgvraag"));
        wizardSteps.add(new WizardStep(7, "Privacy", "/inschrijving/privacy"));
        wizardSteps.add(new WizardStep(8, "Laptop", "/inschrijving/laptop"));
        wizardSteps.add(new WizardStep(9, "Schoolrekening", "/inschrijving/schoolrekening"));
        wizardSteps.add(new WizardStep(10, "Verzenden", "/inschrijving/verzenden"));
    }

    public List<WizardStep> getWizardSteps(int currentStep, List<Integer> completedSteps) {
        List<WizardStep> steps = new ArrayList<>();
        for (WizardStep step : wizardSteps) {
            WizardStep stepCopy = new WizardStep(step.getNumber(), step.getLabel(), step.getUrl());
            stepCopy.setActive(step.getNumber() == currentStep);
            stepCopy.setCompleted(completedSteps != null && completedSteps.contains(step.getNumber()));
            steps.add(stepCopy);
        }
        return steps;
    }
    
    public List<WizardStep> getWizardSteps(int currentStep) {
        return getWizardSteps(currentStep, null);
    }
}
