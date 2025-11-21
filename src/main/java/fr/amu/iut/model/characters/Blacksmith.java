package fr.amu.iut.model.characters;

public abstract class Blacksmith extends Character implements Worker {

    public Blacksmith(String name, char sex, int size, int age, int strength, int endurance) {
        super(name, sex, size, age, strength, endurance);
    }

    @Override
    public void work() {

    }
}
