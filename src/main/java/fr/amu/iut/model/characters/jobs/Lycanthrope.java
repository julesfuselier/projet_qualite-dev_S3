package fr.amu.iut.model.characters.jobs;

import fr.amu.iut.model.characters.Character;
import fr.amu.iut.model.characters.Faction;
import fr.amu.iut.model.characters.Fighter;

public class Lycanthrope extends Character implements Fighter {

    public Lycanthrope(String name, char sex, int size, int age, int strength, int endurance, Faction faction) {
        super(name, sex, size, age, strength, endurance, faction);
    }

    @Override
    public void fight(Character opponent) {
        // Combat féroce
        int damage = (this.getStrength() * 2) - opponent.getEndurance();
        if (damage > 0) {
            opponent.getHealth().add(-damage);
            System.out.println("GRAOU ! " + getName() + " lacère " + opponent.getName() + " (-" + damage + " PV)");
        }
    }

    public int getStrength() {
        return super.getStrength();
    }
}