package fr.amu.iut.model.characters.jobs;

import fr.amu.iut.model.characters.Character;
import fr.amu.iut.model.characters.Faction;
import fr.amu.iut.model.characters.Leader;

public class Prefect extends fr.amu.iut.model.characters.Character implements Leader {

    public Prefect(String name, char sex, int size, int age, int strength, int endurance, Faction faction) {
        super(name, sex, size, age, strength, endurance, faction);
    }

    @Override
    public void lead(Character character) {

    }
}
