package fr.amu.iut.model.lycanthropes;

public enum HowlType {
    BELONGING("Appartenance"),
    DOMINATION("Domination"),
    SUBMISSION("Soumission"),
    AGGRESSION("Agressivité");

    private final String label;

    HowlType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}