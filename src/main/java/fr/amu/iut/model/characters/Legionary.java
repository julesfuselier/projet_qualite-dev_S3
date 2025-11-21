package fr.amu.iut.model.characters;

/**
 * La classe Legionary représente un personnage de type légionnaire dans le jeu.
 * Un légionnaire est un combattant spécialisé avec des attributs spécifiques.
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
     */
    public Legionary(String name, char sex, int size, int age, int strength, int endurance, Faction faction) {
        super(name, sex, size, age, strength, endurance, faction);
    }
}
