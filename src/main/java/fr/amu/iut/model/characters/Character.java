package fr.amu.iut.model.characters;

import fr.amu.iut.model.Statistics;

public abstract class Character {

    private String name;
    private char sex;
    private int size;
    private int age;
    private int strength;
    private int endurance;

    // Indicators
    private Statistics health = new Statistics(100, 0, 100);
    private Statistics hunger = new Statistics(100, 0, 100);
    private Statistics belligerence = new Statistics(100, 0, 100);
    private Statistics magicPotion = new Statistics(0, 0, 100);

    public Character(String name, char sex, int size, int age, int strength, int endurance) {
        this.name = name;
        this.sex = sex;
        this.size = size;
        this.age = age;
        this.strength = strength;
        this.endurance = endurance;
    }

    /**
     * Permet de soigner le personnage pour augmenter l'indicateur de santé.
     * @param healAmount La quantité de soins à ajouter.
     */
    public void beHealed(int healAmount) {
        this.health.add(healAmount);
    }

    // TODO  : ADD FOOD HERE
    /**
     * Permet de nourrir le personnage pour augmenter l'indicateur de faim.
     * @param hungerAmount La quantité de nourriture à ajouter.
     */
    public void eat(int hungerAmount) {
        this.hunger.add(hungerAmount);
    }

    /**
     * Permet de diminuer l'indicateur de faim du personnage.
     * @param hungerAmount La quantité de faim à diminuer.
     */
    public void getHungry(int hungerAmount) {
        this.hunger.add(-hungerAmount);
    }

    /**
     * Permet de boire une potion magique pour augmenter l'indicateur de potion magique.
     * @param potionAmount La quantité de potion magique à ajouter.
     */
    public void drinkMagicPotion(int potionAmount) {
        this.magicPotion.add(potionAmount);
    }

    /**
     * Vérifie si le personnage est mort (santé très critique).
     * @return true si le personnage est considéré comme mort, false sinon.
     */
    public boolean isDead() {
        return this.health.get() <= 0; // TODO : très critique = 0 ?
    }

    // Getters & Setters for Attributes
    public String getName() {return name;}
    public void setName(String name) {
        this.name = name;
    }
    public char getSex() {return sex;}
    public void setSex(char sex) {
        this.sex = sex;
    }
    public int getSize() {return size;}
    public void setSize(int size) {
        this.size = size;
    }
    public int getAge() {return age;}
    public void setAge(int age) {
        this.age = age;
    }
    public int getStrength() {return strength;}
    public void setStrength(int strength) {
        this.strength = strength;
    }
    public int getEndurance() {return endurance;}
    public void setEndurance(int endurance) {
        this.endurance = endurance;
    }

    // Indicators Getters & Setters
    public Statistics getHealth() { return health; }
    public void setHealth(Statistics health) {this.health = health;}
    public Statistics getHunger() { return hunger; }
    public void setHunger(Statistics hunger) {this.hunger = hunger;}
    public Statistics getBelligerence() { return belligerence; }
    public void setBelligerence(Statistics belligerence) {this.belligerence = belligerence;}
    public Statistics getMagicPotion() { return magicPotion;}
    public void setMagicPotion(Statistics magicPotion) {this.magicPotion = magicPotion;}

}
