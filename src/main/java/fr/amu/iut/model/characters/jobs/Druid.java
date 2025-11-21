package fr.amu.iut.model.characters.jobs;

import fr.amu.iut.model.characters.Character;
import fr.amu.iut.model.characters.Faction;
import fr.amu.iut.model.characters.Fighter;
import fr.amu.iut.model.characters.Leader;
import fr.amu.iut.model.characters.Worker;

public class Druid extends fr.amu.iut.model.characters.Character implements Leader, Worker, Fighter {

    public Druid(String name, char sex, int size, int age, int strength, int endurance, Faction faction) {
        super(name, sex, size, age, strength, endurance, faction );
    }

    @Override
    public void lead(fr.amu.iut.model.characters.Character character) {
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