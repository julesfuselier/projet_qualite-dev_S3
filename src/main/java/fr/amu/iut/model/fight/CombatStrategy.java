package fr.amu.iut.model.fight;

import fr.amu.iut.model.characters.Character;

/**
 * Interface représentant une stratégie de combat entre deux personnages.
 */
public interface CombatStrategy {

    /**
     * Exécute une attaque entre un attaquant et un défenseur.
     *
     * @param attacker Le personnage attaquant.
     * @param defender Le personnage défenseur.
     */
    void executeAttack(Character attacker, Character defender);
}