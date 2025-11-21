package fr.amu.iut.model.characters;

public class Merchant extends Character implements Worker {


    public Merchant(String name, char sex, int size, int age, int strength, int endurance, Faction faction) {
        super(name, sex, size, age, strength, endurance, faction);
    }

    @Override
    public void work() {

    }
}
