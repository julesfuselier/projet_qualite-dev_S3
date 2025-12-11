package fr.amu.iut.model.lycanthropes;

/**
 * Représente les différents types de hurlements émis par les lycanthropes.
 * Chaque type de hurlement traduit une intention ou une émotion spécifique
 * permettant de communiquer au sein de la meute.
 */
public enum HowlType {

    /**
     * Hurlement exprimant l'appartenance à la meute,
     * souvent utilisé pour signaler sa présence ou renforcer la cohésion.
     */
    BELONGING("Appartenance"),

    /**
     * Hurlement signalant la domination,
     * généralement utilisé par les membres de haut rang.
     */
    DOMINATION("Domination"),

    /**
     * Hurlement exprimant la soumission,
     * utilisé par les lycanthropes de rang inférieur pour reconnaître une autorité.
     */
    SUBMISSION("Soumission"),

    /**
     * Hurlement traduisant l'agressivité,
     * souvent émis lors d’une confrontation ou pour intimider.
     */
    AGGRESSION("Agressivité");

    /** Libellé lisible de ce type de hurlement. */
    private final String label;

    /**
     * Construit un type de hurlement avec son libellé associé.
     *
     * @param label le libellé humainement lisible du type de hurlement
     */
    HowlType(String label) {
        this.label = label;
    }

    /**
     * Retourne le libellé associé au type de hurlement.
     *
     * @return le libellé du hurlement
     */
    public String getLabel() {
        return label;
    }
}
