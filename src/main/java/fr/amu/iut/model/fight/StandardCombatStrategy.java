package fr.amu.iut.model.fight;

import fr.amu.iut.model.characters.Character;

public class StandardCombatStrategy implements CombatStrategy {
    @Override
    public static void executeAttack(Character attacker, Character defender) {
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