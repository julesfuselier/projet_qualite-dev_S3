package fr.amu.iut.model.exceptions;

/**
 * Exception levée lorsqu'un personnage tente de se placer dans un emplacement non autorisé.
 */
public class UnauthorizedPlacementException extends Exception {

    /**
     * Constructeur de l'exception avec un message personnalisé.
     * @param characterName Le nom du personnage.
     * @param locationName Le nom de l'emplacement.
     */
    public UnauthorizedPlacementException(String characterName, String locationName) {
        super("Placement non autorisé : " + characterName + " ne peut pas entrer dans " + locationName);
    }
}
