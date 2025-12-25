package be.achieveit.snhinschrijvingen.model;

public class Nationaliteit {
    private final String value;
    private final String label;

    public Nationaliteit(String value, String label) {
        this.value = value;
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }
}
