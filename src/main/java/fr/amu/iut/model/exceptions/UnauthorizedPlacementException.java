package fr.amu.iut.model.exceptions;

public class UnauthorizedPlacementException extends Exception {
    public UnauthorizedPlacementException(String characterName, String locationName) {
        super("Placement non autorisé : " + characterName + " ne peut pas entrer dans " + locationName);
    }
}
