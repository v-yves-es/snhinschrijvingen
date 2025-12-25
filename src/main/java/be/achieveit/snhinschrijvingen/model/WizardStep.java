package be.achieveit.snhinschrijvingen.model;

public class WizardStep {
    private final int number;
    private final String label;
    private final String url;
    private boolean active;
    private boolean completed;

    public WizardStep(int number, String label, String url) {
        this.number = number;
        this.label = label;
        this.url = url;
        this.active = false;
        this.completed = false;
    }

    public int getNumber() {
        return number;
    }

    public String getLabel() {
        return label;
    }

    public String getUrl() {
        return url;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
    
    public String getCssClass() {
        String baseClass = "wizard__step";
        if (active) {
            baseClass += " wizard__step--active";
        }
        if (completed) {
            baseClass += " wizard__step--completed";
        }
        return baseClass;
    }
}
