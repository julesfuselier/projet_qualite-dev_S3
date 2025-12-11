package fr.amu.iut.model.characters;

import fr.amu.iut.model.Statistics;

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

    /**
     * Obtient la santé du personnage.
     *
     * @return La santé du personnage sous forme de statistiques.
     */
    Statistics getHealth();

    /**
     * Obtient la faim du personnage.
     *
     * @return La faim du personnage sous forme de statistiques.
     */
    Statistics getHunger();

    /**
     * Obtient la faction du personnage.
     *
     * @return La faction du personnage.
     */
    Faction getFaction();
}
