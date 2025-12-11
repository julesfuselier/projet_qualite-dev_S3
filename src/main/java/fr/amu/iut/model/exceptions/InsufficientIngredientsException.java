package fr.amu.iut.model.exceptions;

/**
 * Exception levée lorsqu'il n'y a pas suffisamment d'ingrédients pour une action donnée.
 */
public class InsufficientIngredientsException extends Exception {

    /**
     * Constructeur de l'exception avec un message personnalisé.
     * @param message Le message décrivant l'erreur.
     */
    public InsufficientIngredientsException(String message) {
        super(message);
    }
}
