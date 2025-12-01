package fr.amu.iut.model.characters;

import fr.amu.iut.model.Inventory;
import fr.amu.iut.model.Statistics;
import fr.amu.iut.model.characters.jobs.Lycanthrope;
import fr.amu.iut.model.items.Item;
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

    protected Inventory inventory;

    public Character(String name, char sex, int size, int age, int strength, int endurance, Faction faction) {
        this.name = name;
        this.sex = sex;
        this.size = size;
        this.age = age;
        this.strength = strength;
        this.endurance = endurance;
        this.faction = faction;
        this.inventory = new Inventory();
    }

    /**
     * Permet de soigner le personnage pour augmenter l'indicateur de santé.
     * @param healAmount La quantité de soins à ajouter.
     */
    public void beHealed(int healAmount) {
        this.health.add(healAmount);
    }

    /**
     * Méthode principale pour manger.
     * Gère la faim, les restrictions de faction, et les pénalités de santé.
     * @param food La nourriture à consommer.
     */
    public void eat(Food food) {
        if (food == null) {
            System.out.println("Il n'y a rien à manger ici.");
            return;
        }

        if (!inventory.removeItem(food)) {
            System.out.println(getName() + " ne possède pas cet aliment (" + food.getName() + ").");
            return;
        }

        if (!canEat(food)) {
            System.out.println(getName() + " (" + getFaction() + ") refuse de manger : " + food.getName() + " !");
            inventory.addItem(food);
            return;
        }

        this.hunger.add(food.getNutritionValue());
        System.out.println(getName() + " mange " + food.getName() + ". (Faim : " + hunger.get() + "/" + hunger.getMax() + ")");

        int healthDamage = 0;

        if (food.getType() == FoodType.FISH && food.getStatus() == FreshnessStatus.STALE) {
            System.out.println("Beurk ! Ce poisson n'est pas frais...");
            healthDamage += 20;
        }

        if (isVegetable(food.getType()) && isVegetable(this.lastEatenFoodType)) {
            System.out.println("Encore de la verdure ?! J'ai mal au ventre...");
            healthDamage += 15;
        }

        if (healthDamage > 0) {
            this.health.add(-healthDamage);
            System.out.println(getName() + " perd " + healthDamage + " points de vie à cause d'une mauvaise alimentation.");
        }

        this.lastEatenFoodType = food.getType();
    }

    /**
     * Vérifie si le personnage accepte de manger cet aliment selon sa faction.
     */
    private boolean canEat(Food food) {
        FoodType type = food.getType();

        if (getFaction() == Faction.GAULOIS) {
            if (type == FoodType.WILD_BOAR || type == FoodType.WINE) return true;
            if (type == FoodType.FISH) return true;
        }

        else if (getFaction() == Faction.ROMAIN) {
            return type == FoodType.WILD_BOAR ||
                    type == FoodType.HONEY ||
                    type == FoodType.WINE ||
                    type == FoodType.MEAD;
        }
        return false;
    }

    /**
     * Helper pour identifier les végétaux (Carottes, Trèfles, etc.)
     * Important pour la règle des "2 fois consécutivement".
     */
    private boolean isVegetable(FoodType type) {
        if (type == null) return false;

        return type == FoodType.CARROT
                || type == FoodType.CLOVER
                || type == FoodType.BEET_JUICE
                || type == FoodType.MISTLETOE
                || type == FoodType.STRAWBERRY;
    }

    public void pickUpItem(Item item) {
        this.inventory.addItem(item);
    }

    public void drinkMagicPotion() {
        // TODO : Effets de la potion magique
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
        return this.health.get() <= 0;
    }

    /**
     * Permet de dupliquer un personnage en utilisant le mécanisme de clonage.
     * @param character Le personnage à dupliquer.
     * @return Une nouvelle instance de Character identique à l'original.
     * @throws CloneNotSupportedException Si le clonage échoue.
     */
    public Character duplicateCharacter(Character character) throws CloneNotSupportedException {
        return (Character) character.clone();
    }

    public Lycanthrope transformToLycanthrope() {
        // TODO : Implémenter la transformation en Loup-Garou
        return null;
    }

    // Getters & Setters for Attributes
    public Inventory getInventory() {return inventory;}
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
