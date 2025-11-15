package fr.amu.iut.model.characters;

public class Prefect extends Character implements Leader {

    public Prefect(String name, char sex, int size, int age, int strength, int endurance) {
        super(name, sex, size, age, strength, endurance);
    }

    @Override
    public void lead(Character character) {

    }
}
