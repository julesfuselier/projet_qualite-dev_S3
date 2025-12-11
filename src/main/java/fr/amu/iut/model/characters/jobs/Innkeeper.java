package fr.amu.iut.model.characters.jobs;

import fr.amu.iut.model.characters.Character;
import fr.amu.iut.model.characters.Faction;
import fr.amu.iut.model.characters.Worker;
import fr.amu.iut.util.GameEvents;

/**
 * Classe représentant un aubergiste dans le jeu.
 * L'aubergiste est un personnage qui peut travailler en servant des boissons aux clients de l'auberge.
 */
public class Innkeeper extends Character implements Worker {

    /**
     * Constructeur de la classe Innkeeper.
     *
     * @param name      Le nom de l'aubergiste.
     * @param sex       Le sexe de l'aubergiste.
     * @param size      La taille de l'aubergiste.
     * @param age       L'âge de l'aubergiste.
     * @param strength  La force de l'aubergiste.
     * @param endurance L'endurance de l'aubergiste.
     * @param faction   La faction à laquelle appartient l'aubergiste.
     */
    public Innkeeper(String name, char sex, int size, int age, int strength, int endurance, Faction faction) {
        super(name, sex, size, age, strength, endurance, faction);
    }

    /**
     * Méthode représentant le travail de l'aubergiste.
     * Affiche un message indiquant que l'aubergiste sert des boissons aux clients de l'auberge.
     */
    @Override
    public void work() {
        GameEvents.log(this.getName() + " sert des boissons aux clients de l'auberge.");
    }
}
