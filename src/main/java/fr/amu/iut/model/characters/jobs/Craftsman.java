package fr.amu.iut.model.characters.jobs;

import fr.amu.iut.model.characters.Character;
import fr.amu.iut.model.characters.Faction;
import fr.amu.iut.model.characters.Worker;

/**
 * Classe abstraite intermédiaire pour regrouper tous les métiers d'artisanat.
 */
public abstract class Craftsman extends Character implements Worker {

    public Craftsman(String name, char sex, int size, int age, int strength, int endurance, Faction faction) {
        super(name, sex, size, age, strength, endurance, faction);
    }
}