package fr.amu.iut.model.characters.jobs;

import fr.amu.iut.model.characters.Character;
import fr.amu.iut.model.characters.Faction;
import fr.amu.iut.model.characters.Worker;
import fr.amu.iut.util.GameEvents;

/**
 * Classe représentant un forgeron dans le jeu.
 * Le forgeron est un personnage qui peut travailler en forgeant des objets.
 */
public class Blacksmith extends Craftsman {

    /**
     * Constructeur de la classe Blacksmith.
     *
     * @param name      Le nom du forgeron.
     * @param sex       Le sexe du forgeron.
     * @param size      La taille du forgeron.
     * @param age       L'âge du forgeron.
     * @param strength  La force du forgeron.
     * @param endurance L'endurance du forgeron.
     * @param faction   La faction à laquelle appartient le forgeron.
     */
    public Blacksmith(String name, char sex, int size, int age, int strength, int endurance, Faction faction) {
        super(name, sex, size, age, strength, endurance, faction);
    }

    /**
     * Méthode représentant le travail du forgeron.
     * Affiche un message indiquant que le forgeron travaille à son enclume.
     */
    @Override
    public void work() {
        GameEvents.log(this.getName() + " tape sur son enclume ...");
    }

    /**
     * Permet au forgeron de combattre un adversaire.
     *
     * @param opponent L'adversaire à combattre.
     */
    @Override
    public void fight(Character opponent) {
        System.out.println(getName() + " combat " + opponent.getName());
        // Implement combat logic here
    }
}
