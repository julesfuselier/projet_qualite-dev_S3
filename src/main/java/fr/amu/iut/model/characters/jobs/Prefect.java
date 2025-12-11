package fr.amu.iut.model.characters.jobs;

import fr.amu.iut.model.characters.Character;
import fr.amu.iut.model.characters.Faction;
import fr.amu.iut.model.characters.Leader;
import fr.amu.iut.util.GameEvents;

/**
 * Classe représentant un préfet dans le jeu.
 * Le préfet est un personnage qui peut mener d'autres personnages.
 */
public class Prefect extends fr.amu.iut.model.characters.Character implements Leader {

    /**
     * Constructeur de la classe Prefect.
     *
     * @param name      Le nom du préfet.
     * @param sex       Le sexe du préfet.
     * @param size      La taille du préfet.
     * @param age       L'âge du préfet.
     * @param strength  La force du préfet.
     * @param endurance L'endurance du préfet.
     * @param faction   La faction à laquelle appartient le préfet.
     */
    public Prefect(String name, char sex, int size, int age, int strength, int endurance, Faction faction) {
        super(name, sex, size, age, strength, endurance, faction);
    }

    /**
     * Méthode représentant le fait de mener un autre personnage.
     * Affiche un message indiquant que le préfet mène le personnage spécifié.
     *
     * @param character Le personnage à mener.
     */
    @Override
    public void lead(Character character) {
        GameEvents.log(this.getName() + " dirige " + character.getName());
    }
}
