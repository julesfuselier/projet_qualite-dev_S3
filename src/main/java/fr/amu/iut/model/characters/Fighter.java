package fr.amu.iut.model.characters;

/**
 * Interface représentant une capacité de combat pour un personnage.
 */
public interface Fighter {

    /**
     * Permet à un personnage de combattre un autre personnage.
     *
     * @param opponent Le personnage opposant.
     */
    void fight(Character opponent);
}
