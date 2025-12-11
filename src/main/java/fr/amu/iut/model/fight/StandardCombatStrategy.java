package fr.amu.iut.model.fight;

import fr.amu.iut.model.characters.Character;
import fr.amu.iut.util.GameEvents; // Import de notre bus d'événements

/**
 * Stratégie de combat standard où les dégâts sont calculés
 * en soustrayant l'endurance du défenseur de la force de l'attaquant.
 * Les dégâts minimums sont de 1 point.
 */
public class StandardCombatStrategy implements CombatStrategy {

    /**
     * Exécute une attaque entre deux personnages en utilisant la stratégie standard.
     *
     * @param attacker Le personnage attaquant
     * @param defender Le personnage défenseur
     */
    @Override
    public void executeAttack(Character attacker, Character defender) {
        if (defender.isActivePotion()) {
            GameEvents.log(defender.getName() + " est invincible grâce à la potion !");
            return;
        }

        int rawDamage = attacker.getStrength() - defender.getEndurance();
        int realDamage = Math.max(1, rawDamage);

        defender.getHealth().add(-realDamage);

        GameEvents.log(" [COMBAT] " + attacker.getName() + " frappe " + defender.getName() +
                " (-" + realDamage + " PV)");
    }
}