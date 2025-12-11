package fr.amu.iut.model.characters.jobs;

import fr.amu.iut.model.characters.Faction;
import fr.amu.iut.model.characters.Warrior;

/**
 * Classe représentant un légionnaire dans le jeu.
 * Le légionnaire est un personnage de type guerrier.
 */
public class Legionary extends Warrior {

    /**
     * Constructeur de la classe Legionary.
     * @param name Le nom du légionnaire.
     * @param sex Le sexe du légionnaire.
     * @param size La taille du légionnaire.
     * @param age L'âge du légionnaire.
     * @param strength La force du légionnaire.
     * @param endurance L'endurance du légionnaire.
     * @param faction La faction du légionnaire.
     */
    public Legionary(String name, char sex, int size, int age, int strength, int endurance, Faction faction) {
        super(name, sex, size, age, strength, endurance, faction);
    }
}
