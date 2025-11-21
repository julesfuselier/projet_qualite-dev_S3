package fr.amu.iut.model.characters;

/**
 * La classe Legionnaire représente un personnage de type légionnaire dans le jeu.
 * Un légionnaire est un combattant spécialisé avec des attributs spécifiques.
 */
public abstract class Legionnaire extends Warrior {

    /**
     * Constructeur de la classe Legionnaire.
     * @param name Le nom du légionnaire.
     * @param sex Le sexe du légionnaire.
     * @param size La taille du légionnaire.
     * @param age L'âge du légionnaire.
     * @param strength La force du légionnaire.
     * @param endurance L'endurance du légionnaire.
     */
    public Legionnaire(String name, char sex, int size, int age, int strength, int endurance) {
        super(name, sex, size, age, strength, endurance);
    }
}
