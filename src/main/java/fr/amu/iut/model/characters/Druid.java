package fr.amu.iut.model.characters;

public class Druid extends Character implements Leader, Worker, Fighter {

    public Druid(String name, char sex, int size, int age, int strength, int endurance, Faction faction) {
        super(name, sex, size, age, strength, endurance, faction );
    }

    @Override
    public void lead(Character character) {
        System.out.println(this.getName() + " is leading " + character.getName());
    }

    @Override
    public void work() {

    }

    @Override
    public void fight(Character character) {
        System.out.println(this.getName() + " is fighting " + character.getName());
    }
}
