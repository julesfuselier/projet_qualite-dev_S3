package fr.amu.iut.model.characters;

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
}
