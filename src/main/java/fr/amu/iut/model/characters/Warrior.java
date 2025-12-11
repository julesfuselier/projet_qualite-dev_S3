package fr.amu.iut.model.characters;

import fr.amu.iut.model.Statistics;

/**
 * Classe abstraite représentant un guerrier dans le jeu.
 * Un guerrier est un personnage capable de combattre.
 */
public abstract class Warrior extends Character implements Fighter {

    /**
     * Constructeur de la classe Warrior.
     *
     * @param name      Le nom du guerrier.
     * @param sex       Le sexe du guerrier.
     * @param size      La taille du guerrier.
     * @param age       L'âge du guerrier.
     * @param strength  La force du guerrier.
     * @param endurance L'endurance du guerrier.
     * @param faction   La faction du guerrier.
     */
    public Warrior(String name, char sex, int size, int age, int strength, int endurance, Faction faction) {
        super(name, sex, size, age, strength, endurance, faction);
    }

    /**
     * Méthode permettant à un guerrier de combattre un adversaire.
     * 
     * @param opponent L'adversaire à combattre.
     */
    @Override
    public void fight(Character opponent) {
        this.performAttack(opponent);
    }

    @Override
    public Statistics getHealth() {
        return super.getHealth();
    }

    @Override
    public Statistics getHunger() {
        return super.getHunger();
    }
}
