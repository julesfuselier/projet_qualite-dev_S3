package fr.amu.iut.model.characters;

import fr.amu.iut.model.Statistics;
import fr.amu.iut.model.items.foods.Food;
import fr.amu.iut.model.items.foods.FoodType;
import fr.amu.iut.model.items.foods.FreshnessStatus;

public abstract class Character {

    private String name;
    private char sex;
    private int size;
    private int age;
    private int strength;
    private int endurance;
    private Faction faction;
    private FoodType lastEatenFoodType = null;

    // Indicators
    private Statistics health = new Statistics(100, 0, 100);
    private Statistics hunger = new Statistics(100, 0, 100);
    private Statistics belligerence = new Statistics(100, 0, 100);
    private Statistics magicPotion = new Statistics(0, 0, 100);

    public Character(String name, char sex, int size, int age, int strength, int endurance, Faction faction) {
        this.name = name;
        this.sex = sex;
        this.size = size;
        this.age = age;
        this.strength = strength;
        this.endurance = endurance;
        this.faction = faction;
    }

    // Second Character constructor suitable for ClanLeader
    public Character(String name, char sex, int age) {
        this.name = name;
        this.sex = sex;
        this.age = age;
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
     * Méthode principale pour manger en respectant les règles du PDF.
     */
    public void eat(Food food) {
        if (!canEat(food)) {
            System.out.println(getName() + " (" + getFaction() + ") refuse de manger : " + food.getName());
            return;
        }
        this.hunger.add(food.getNutritionValue());
        System.out.println(getName() + " mange " + food.getName());

        if (food.getType() == FoodType.FISH && food.getStatus() != FreshnessStatus.FRESH) {
            this.health.add(-10);
            System.out.println("Beurk ! Le poisson n'était pas frais ! " + getName() + " perd de la vie.");
        }

        if (isVegetable(food.getType()) && isVegetable(lastEatenFoodType)) {
            this.health.add(-5);
            System.out.println("Encore des légumes ?! " + getName() + " ne se sent pas bien.");
        }

        this.lastEatenFoodType = food.getType();
    }

    /**
     * Définit le régime alimentaire selon la faction.
     */
    private boolean canEat(Food food) {
        FoodType type = food.getType();

        if (getFaction() == Faction.GAULOIS) {
            // Les Gaulois : Sanglier, Poisson, Vin
            return type == FoodType.WILD_BOAR || type == FoodType.FISH || type == FoodType.WINE;
        } else if (getFaction() == Faction.ROMAIN) {
            // Les Romains : Sanglier, Miel, Vin, Hydromel
            return type == FoodType.WILD_BOAR || type == FoodType.HONEY || type == FoodType.WINE || type == FoodType.MEAD;
        }
        return false;
    }

    /**
     * Helper pour identifier les végétaux (Carottes, Trèfles, etc.)
     */
    private boolean isVegetable(FoodType type) {
        if (type == null) return false;
        return type == FoodType.CARROT
                || type == FoodType.CLOVER
                || type == FoodType.BEET_JUICE
                || type == FoodType.MISTLETOE
                || type == FoodType.STRAWBERRY; // fruits = végétaux ?
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
    public Faction getFaction() {return faction;}
    public void setFaction(Faction faction) {
        this.faction = faction;
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
