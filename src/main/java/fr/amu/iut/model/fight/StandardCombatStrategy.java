package fr.amu.iut.model.fight;

import fr.amu.iut.model.characters.Character;

/**
 * Stratégie de combat standard où les dégâts sont calculés en fonction de la force de l'attaquant
 * et de l'endurance du défenseur.
 */
public class StandardCombatStrategy implements CombatStrategy {

    /**
     * Exécute une attaque standard entre un attaquant et un défenseur.
     *
     * @param attacker Le personnage attaquant.
     * @param defender Le personnage défenseur.
     */
    @Override
    public void executeAttack(Character attacker, Character defender) {
        if (defender.isActivePotion()) {
            System.out.println(defender.getName() + " est invincible grâce à la potion !");
            return;
        }

        int rawDamage = attacker.getStrength() - defender.getEndurance();
        int realDamage = Math.max(1, rawDamage);

        defender.getHealth().add(-realDamage);

        System.out.println(" [COMBAT] " + attacker.getName() + " frappe " + defender.getName() +
                " (-" + realDamage + " PV)");
    }
}