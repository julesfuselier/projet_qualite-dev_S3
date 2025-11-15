package fr.amu.iut.model.characters;



public abstract class Warrior extends Character implements Fighter {

    /**
     * Constructeur de la classe Warrior.
     * @param name Le nom du guerrier.
     * @param sex Le sexe du guerrier.
     * @param size La taille du guerrier.
     * @param age L'âge du guerrier.
     * @param strength La force du guerrier.
     * @param endurance L'endurance du guerrier.
     */
    public Warrior(String name, char sex, int size, int age, int strength, int endurance) {
        super(name, sex, size, age, strength, endurance);
    }

    /**
     * Permet à un légionnaire de combattre un autre personnage.
     * Le combat diminue la santé des deux personnages en fonction de leur force et endurance.
     * @param opponent Le personnage adversaire.
     */
    @Override
    public void fight(Character opponent) {
        int damageToOpponent = this.getStrength() - opponent.getEndurance();
        if (damageToOpponent > 0) {
            opponent.getHealth().add(-damageToOpponent);
        }

        int damageToSelf = opponent.getStrength() - this.getEndurance();
        if (damageToSelf > 0) {
            this.getHealth().add(-damageToSelf);
        }
    }


}
