package fr.amu.iut.model.characters;

public abstract class Druid extends Character implements Leader, Worker {

    public Druid(String name, char sex, int size, int age, int strength, int endurance) {
        super(name, sex, size, age, strength, endurance);
    }

    @Override
    public void lead(Character character) {
        System.out.println(this.getName() + " is leading " + character.getName());
    }

    @Override
    public void work() {

    }
}
