package fr.amu.iut.model.characters.jobs;

import fr.amu.iut.model.characters.Character;
import fr.amu.iut.model.characters.Faction;
import fr.amu.iut.model.characters.Worker;
import fr.amu.iut.util.GameEvents;

/**
 * Classe représentant un marchand dans le jeu.
 * Le marchand est un personnage qui peut travailler en vendant des marchandises
 * aux voyageurs.
 */
public class Merchant extends Craftsman {

    /**
     * Constructeur de la classe Merchant.
     *
     * @param name      Le nom du marchand.
     * @param sex       Le sexe du marchand.
     * @param size      La taille du marchand.
     * @param age       L'âge du marchand.
     * @param strength  La force du marchand.
     * @param endurance L'endurance du marchand.
     * @param faction   La faction à laquelle appartient le marchand.
     */
    public Merchant(String name, char sex, int size, int age, int strength, int endurance, Faction faction) {
        super(name, sex, size, age, strength, endurance, faction);
    }

    /**
     * Méthode représentant le travail du marchand.
     * Affiche un message indiquant que le marchand vend des marchandises aux
     * voyageurs.
     */
    @Override
    public void work() {
        GameEvents.log(this.getName() + " vend des marchandises aux voyageurs.");
    }

    /**
     * Permet au marchand de combattre un adversaire.
     *
     * @param opponent L'adversaire à combattre.
     */
    @Override
    public void fight(Character opponent) {
        System.out.println(getName() + " combat " + opponent.getName());
        // Implement combat logic here
    }
}
