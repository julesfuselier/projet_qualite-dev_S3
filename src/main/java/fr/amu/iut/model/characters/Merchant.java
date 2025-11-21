package fr.amu.iut.model.characters;

public abstract class Merchant extends Character implements Worker {


    public Merchant(String name, char sex, int size, int age, int strength, int endurance) {
        super(name, sex, size, age, strength, endurance);
    }

    @Override
    public void work() {

    }
}
