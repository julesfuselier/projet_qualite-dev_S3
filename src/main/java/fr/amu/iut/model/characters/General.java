package fr.amu.iut.model.characters;

public class General extends Warrior implements Leader {

    /**
     * Constructeur de la classe General.
     *
     * @param name      Le nom du guerrier.
     * @param sex       Le sexe du guerrier.
     * @param size      La taille du guerrier.
     * @param age       L'âge du guerrier.
     * @param strength  La force du guerrier.
     * @param endurance L'endurance du guerrier.
     */
    public General(String name, char sex, int size, int age, int strength, int endurance, Faction faction) {
        super(name, sex, size, age, strength, endurance, faction);
    }

    @Override
    public void lead(Character character) {
        System.out.println(this.getName() + " is leading to " + character.getName());
    }
}
