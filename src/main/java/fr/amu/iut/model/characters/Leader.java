package fr.amu.iut.model.characters;

import fr.amu.iut.util.GameEvents;

/**
 * Interface représentant une capacité de leadership pour un personnage.
 */
public interface Leader {

    /**
     * Permet à un personnage de mener d'autres personnages.
     *
     * @param character Le personnage à mener.
     */
    void lead(Character character);

    /**
     * Permet à un personnage de gouverner.
     * Affiche un message satyrique sur la gouvernance.
     */
    default void govern() {
        GameEvents.log("Augmente les impôts pour financer des statues de soi-même en or massif");
    }
}
