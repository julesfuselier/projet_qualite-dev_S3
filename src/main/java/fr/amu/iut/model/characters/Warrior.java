package fr.amu.iut.model.characters;



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
     * @param faction  La faction du guerrier.
     */
    public Warrior(String name, char sex, int size, int age, int strength, int endurance, Faction faction) {
        super(name, sex, size, age, strength, endurance, faction);
    }

    /**
     * Méthode pour combattre un adversaire.
     *
     * @param opponent L'adversaire à combattre.
     */
    @Override
    public void fight(Character opponent) {
        if (opponent.isActivePotion()) {
            System.out.println(opponent.getName() + " est invincible grâce à la potion !");
        } else {
            int damageToOpponent = this.getStrength() - opponent.getEndurance();
            if (damageToOpponent > 0) {
                opponent.getHealth().add(-damageToOpponent);
            }
        }
    }


}
