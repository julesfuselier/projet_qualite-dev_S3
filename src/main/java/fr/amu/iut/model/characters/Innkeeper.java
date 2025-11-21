package fr.amu.iut.model.characters;

public abstract class Innkeeper extends Character implements Worker{

    public Innkeeper(String name, char sex, int size, int age, int strength, int endurance) {
        super(name, sex, size, age, strength, endurance);
    }

    @Override
    public void work() {

    }
}
