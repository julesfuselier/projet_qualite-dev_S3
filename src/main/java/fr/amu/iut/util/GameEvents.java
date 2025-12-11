package fr.amu.iut.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe centrale pour diffuser les messages du jeu.
 * Le Modèle publie ici, et le Contrôleur s'abonne pour afficher.
 */
public class GameEvents {
    // Liste des abonnés au logger
    private static final List<GameLogger> listeners = new ArrayList<>();

    /**
     * Méthode pour s'abonner aux événements (utilisée par le Contrôleur)
     * @param logger L'instance de GameLogger à enregistrer
     */
    public static void register(GameLogger logger) {
        listeners.add(logger);
    }

    /**
     * Méthode pour publier un message (utilisée par le Modèle)
     * @param message Le message à diffuser
     */
    public static void log(String message) {
        for (GameLogger logger : listeners) {
            logger.log(message);
        }
    }

    /**
     * Méthode pour effacer tous les abonnés (utile pour les tests)
     */
    public static void clear() {
        listeners.clear();
    }
}