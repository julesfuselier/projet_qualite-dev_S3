package fr.amu.iut.model.characters;

public class Prefect extends Character implements Leader {

    public Prefect(String name, char sex, int size, int age, int strength, int endurance, Faction faction) {
        super(name, sex, size, age, strength, endurance, faction);
    }

    @Override
    public void lead(Character character) {

    }
}
