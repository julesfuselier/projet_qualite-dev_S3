package fr.amu.iut.model.lycanthropes;

import java.util.Arrays;

public enum Rank {
    ALPHA("α", 0),
    BETA("β", 1),
    GAMMA("γ", 2),
    DELTA("δ", 3),
    EPSILON("ε", 4),
    ZETA("𝞯", 5),
    ETA("𝞰", 6),
    THETA("𝞱", 7),
    IOTA("𝞲", 8),
    KAPPA("𝞳", 9),
    LAMBDA("𝞴", 10),
    MU("𝞵", 11),
    NU("𝞶", 12),
    XI("𝞷", 13),
    OMICRON("𝞸", 14),
    PI("𝞹", 15),
    RHO("𝞺", 16),
    SIGMA("𝞻", 17),
    TAU("𝞼", 18),
    UPSILON("𝞽", 19),
    PHI("𝞾", 20),
    CHI("𝞿", 21),
    PSI("𝟀", 22),
    OMEGA_ALT("𝟁", 23),
    OMEGA("ω", 24);

    private final String display;
    private final int value;

    Rank(String display, int value) {
        this.display = display;
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public String getDisplay() {
        return display;
    }

    public static Rank fromString(String display) {
        // Vérifiez également les versions en gras.
        return Arrays.stream(Rank.values())
                .filter(r -> r.display.equals(display) || r.toBoldString().equals(display))
                .findFirst()
                .orElse(null);
    }

    public static Rank fromValue(int value) {
        return Arrays.stream(Rank.values())
                .filter(r -> r.value == value)
                .findFirst()
                .orElse(null);
    }

    @Override
    public String toString() {
        return this.display;
    }

    // Aide pour gérer les caractères gras incohérents utilisés dans le code
    private String toBoldString() {
        return switch (this) {
            case ALPHA -> "𝞪";
            case BETA -> "𝞫";
            case GAMMA -> "𝞬";
            case DELTA -> "𝞭";
            case EPSILON -> "𝞮";
            case OMEGA -> "𝟂";
            default -> this.display;
        };
    }
}