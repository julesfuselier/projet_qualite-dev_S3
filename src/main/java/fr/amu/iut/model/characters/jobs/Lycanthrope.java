package fr.amu.iut.model.characters.jobs;

import fr.amu.iut.model.characters.Character;
import fr.amu.iut.model.characters.Faction;
import fr.amu.iut.model.characters.Fighter;
import fr.amu.iut.model.fight.LycanthropeCombatStrategy; // ← AJOUTER IMPORT

public class Lycanthrope extends Character implements Fighter {

    public Lycanthrope(String name, char sex, int size, int age, int strength, int endurance, Faction faction) {
        super(name, sex, size, age, strength, endurance, faction);
        this.setCombatStrategy(new LycanthropeCombatStrategy());
    }

    @Override
    public void fight(Character opponent) {
        this.performAttack(opponent);
    }

    @Override
    public int getStrength() {
        return super.getStrength();
    }
}