package fr.amu.iut.model.fight;

import fr.amu.iut.model.characters.Character;

/**
 * Stratégie de combat spécifique aux lycanthropes.
 */
public class LycanthropeCombatStrategy implements CombatStrategy {

    /**
     * Exécute une attaque lycanthrope entre un attaquant et un défenseur.
     *
     * @param attacker Le personnage attaquant.
     * @param defender Le personnage défenseur.
     */
    @Override
    public void executeAttack(Character attacker, Character defender) {
        int damage = (attacker.getStrength() * 2) - defender.getEndurance();
        int realDamage = Math.max(5, damage);

        defender.getHealth().add(-realDamage);
        System.out.println(" [LOUP-GAROU] " + attacker.getName() + " lacère violemment " + defender.getName() +
                " (-" + realDamage + " PV)");
    }
}