package fr.amu.iut.model.lycanthropes;

import java.util.Arrays;

/**
 * Représente les différents rangs hiérarchiques d'une meute de lycanthropes.
 * Chaque rang possède :
 * <ul>
 *     <li>un symbole grec (attribut {@code display}) pour l'affichage,</li>
 *     <li>une valeur numérique (attribut {@code value}) permettant de comparer les positions hiérarchiques.</li>
 * </ul>
 * L’indice hiérarchique va de 0 (rang le plus élevé : ALPHA)
 * jusqu'à 24 (rang le plus faible : OMEGA).
 */
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

    /** Symbole grec utilisé pour représenter le rang. */
    private final String display;

    /** Valeur numérique permettant de classer hiérarchiquement les rangs. */
    private final int value;

    /**
     * Construit un rang avec son symbole et sa valeur hiérarchique.
     *
     * @param display symbole grec affiché pour représenter le rang
     * @param value   valeur numérique indiquant la position hiérarchique
     */
    Rank(String display, int value) {
        this.display = display;
        this.value = value;
    }

    /**
     * Retourne la valeur hiérarchique associée au rang.
     *
     * @return la valeur du rang
     */
    public int getValue() {
        return value;
    }

    /**
     * Retourne le symbole grec représentant le rang.
     *
     * @return le symbole d'affichage du rang
     */
    public String getDisplay() {
        return display;
    }

    /**
     * Retourne le rang correspondant à un symbole donné.
     * Cette méthode reconnaît également certains symboles en versions "gras"
     * afin de gérer des incohérences d'encodage.
     *
     * @param display symbole du rang recherché
     * @return le rang correspondant, ou {@code null} si aucun ne correspond
     */
    public static Rank fromString(String display) {
        return Arrays.stream(Rank.values())
                .filter(r -> r.display.equals(display) || r.toBoldString().equals(display))
                .findFirst()
                .orElse(null);
    }

    /**
     * Retourne le rang correspondant à une valeur hiérarchique donnée.
     *
     * @param value valeur hiérarchique recherchée
     * @return le rang correspondant, ou {@code null} si aucun ne correspond
     */
    public static Rank fromValue(int value) {
        return Arrays.stream(Rank.values())
                .filter(r -> r.value == value)
                .findFirst()
                .orElse(null);
    }

    /**
     * Retourne le symbole grec associé au rang.
     * Identique à {@link #getDisplay()}.
     *
     * @return symbole du rang
     */
    @Override
    public String toString() {
        return this.display;
    }

    /**
     * Retourne une version alternative du symbole grec en gras.
     * Principalement utilisée pour corriger des variations d'encodage
     * lors de la comparaison dans {@link #fromString(String)}.
     *
     * @return une version potentiellement en gras du symbole du rang
     */
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
