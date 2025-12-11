package fr.amu.iut.model.fight;

import fr.amu.iut.model.characters.Character;
import fr.amu.iut.util.GameEvents;

/**
 * Stratégie de combat spécifique aux lycanthropes.
 * Les lycanthropes infligent des dégâts doublés par rapport à la stratégie standard,
 * avec un minimum de 5 points de dégâts garantis.
 */
public class LycanthropeCombatStrategy implements CombatStrategy {

    /**
     * Exécute une attaque entre deux personnages en utilisant la stratégie lycanthrope.
     *
     * @param attacker Le personnage attaquant
     * @param defender Le personnage défenseur
     */
    @Override
    public void executeAttack(Character attacker, Character defender) {
        int damage = (attacker.getStrength() * 2) - defender.getEndurance();
        int realDamage = Math.max(5, damage);

        defender.getHealth().add(-realDamage);

        GameEvents.log(" [LOUP-GAROU] " + attacker.getName() + " lacère violemment " + defender.getName() +
                " (-" + realDamage + " PV)");
    }
}