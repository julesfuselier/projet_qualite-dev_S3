package fr.amu.iut.model.fight;

import fr.amu.iut.model.characters.Character;

public class LycanthropeCombatStrategy implements CombatStrategy {
    @Override
    public void executeAttack(Character attacker, Character defender) {
        int damage = (attacker.getStrength() * 2) - defender.getEndurance();
        int realDamage = Math.max(5, damage);

        defender.getHealth().add(-realDamage);
        System.out.println(" [GRAOU] " + attacker.getName() + " lacère violemment " + defender.getName() +
                " (-" + realDamage + " PV)");
    }
}