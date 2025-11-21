package fr.amu.iut.model.characters;

public class Innkeeper extends Character implements Worker{

    public Innkeeper(String name, char sex, int size, int age, int strength, int endurance, Faction faction) {
        super(name, sex, size, age, strength, endurance, faction);
    }

    @Override
    public void work() {

    }
}
