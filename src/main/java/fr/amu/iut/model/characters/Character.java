package fr.amu.iut.model.characters;

import fr.amu.iut.model.Inventory;
import fr.amu.iut.model.Statistics;
import fr.amu.iut.model.characters.jobs.Lycanthrope;
import fr.amu.iut.model.items.Item;
import fr.amu.iut.model.items.foods.Food;
import fr.amu.iut.model.items.foods.FoodType;
import fr.amu.iut.model.items.foods.FreshnessStatus;
import fr.amu.iut.model.items.potion.MagicPotion;
import fr.amu.iut.model.fight.CombatStrategy;
import fr.amu.iut.model.fight.StandardCombatStrategy;

public abstract class Character implements Cloneable {

    private String name;
    private char sex;
    private int size;
    private int age;
    private int strength;
    private int endurance;
    private Faction faction;
    private FoodType lastEatenFoodType = null;

    // Indicateurs
    private Statistics health = new Statistics(100, 0, 100);
    private Statistics hunger = new Statistics(100, 0, 100);
    private Statistics belligerence = new Statistics(100, 0, 100);
    private Statistics magicPotion = new Statistics(0, 0, 100);

    private CombatStrategy combatStrategy = new StandardCombatStrategy();

    protected Inventory<Item> inventory;
    
    private boolean isStatue = false; // Indique si le personnage est transformé en statue
    private boolean permanentPotion = false; // Indique si le personnage a un effet permanent de potion magique
    private int potDrunkCount = 0; // Compteur du nombre de potions magiques consommées

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

    // Deuxième constructeur de personnage adapté à ClanLeader
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

    /**
     * Méthode principale pour manger.
     * Gère la faim, les restrictions de faction, et les pénalités de santé.
     */
    public void eat(Food food) {
        if (food == null) {
            System.out.println("Il n'y a rien à manger ici.");
            return;
        }

        if (!inventory.getItems().contains(food)) {
            System.out.println(getName() + " ne possède pas cet aliment (" + food.getName() + ").");
            return;
        }

        if (!canEat(food)) {
            System.out.println(getName() + " (" + getFaction() + ") refuse de manger : " + food.getName() + " !");
            return;
        }

        inventory.removeItem(food);
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
            return type == FoodType.FISH;
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

    /**
     * Fait boire de la potion au personnage.
     * @param potion L'objet potion (la marmite).
     * @param drinkAll Si true, boit toute la marmite d'un coup. Sinon, une seule dose.
     * @return Un nouvel objet si transformation (Lycanthrope), sinon this ou null si mort/statue.
     */
    public Object drinkMagicPotion(MagicPotion potion, boolean drinkAll) {
        if (isStatue || isDead()) {
            System.out.println(getName() + " ne peut pas boire.");
            return this;
        }

        if (drinkAll) {
            System.out.println(getName() + " boit toute la marmite d'un trait !");
            potDrunkCount++;

            if (potDrunkCount >= 2) {
                becomeGraniteStatue();
                this.inventory.removeItem(potion);
                return this;
            }

            permanentPotion = true;
            this.magicPotion.setMax(100);
            this.magicPotion.add(100);

            while(potion.takeDose());
            inventory.removeItem(potion);

        } else {
            if (potion.takeDose()) {
                System.out.println(getName() + " boit une gorgée de potion magique.");
                this.magicPotion.add(20);

                if (potion.getDoses() == 0) {
                    System.out.println("La marmite est vide !");
                    inventory.removeItem(potion);
                }
            } else {
                System.out.println("La marmite est déjà vide.");
            }
        }

        if (isActivePotion()) {
            switch (potion.getType()) {
                case METAMORPHOSIS:
                    System.out.println("Des poils commencent à pousser sur " + getName() + "...");
                    return transformToLycanthrope();
                case SPLITTING:
                    Character clone = this.clone();
                    clone.setName(this.getName() + " (Copie)");
                    clone.inventory = new Inventory();
                    return clone;

            }
        }

        return this;
    }

    public boolean isActivePotion() {
        return !isStatue && (permanentPotion || magicPotion.get() > 0);
    }

    private void becomeGraniteStatue() {
        isStatue = true;
        this.name += " (Statue de Granit)";
        System.out.println(getName() + " s'est transformé en statue de granit pour l'éternité !");
    }

    public int getStrength() {
        if (isActivePotion()) {
            return strength + 1000; // Force surhumaine
        }
        return strength;
    }

    public void updatePotionDuration() {
        if (permanentPotion || isStatue) return;

        if (magicPotion.get() > 0) {
            magicPotion.add(-1);
            if (magicPotion.get() == 0) {
                System.out.println("Les effets de la potion magique se dissipent pour " + getName() + ".");
            }
        }
    }

    public void getHungry(int hungerAmount) {
        this.hunger.add(-hungerAmount);
    }

    public boolean isDead() {
        return this.health.get() <= 0;
    }

    public Lycanthrope transformToLycanthrope() {
        Lycanthrope wolf = new Lycanthrope(
                this.name + " (Loup-Garou)",
                this.sex,
                this.size,
                this.age,
                this.strength + 50,
                this.endurance + 50,
                this.faction
        );

        wolf.inventory = this.inventory;

        return wolf;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    // Getters et Setters pour les attributs
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

    // Getters & Setters des indicateurs
    public Statistics getHealth() { return health; }
    public void setHealth(Statistics health) {this.health = health;}
    public Statistics getHunger() { return hunger; }
    public void setHunger(Statistics hunger) {this.hunger = hunger;}
    public Statistics getBelligerence() { return belligerence; }
    public void setBelligerence(Statistics belligerence) {this.belligerence = belligerence;}
    public Statistics getMagicPotion() { return magicPotion;}
    public void setMagicPotion(Statistics magicPotion) {this.magicPotion = magicPotion;}

    @Override
    public Character clone() {
        try {
            return (Character) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    @Override
    public String toString() {
        return String.format("%-15s [%-12s] | PV: %-3d/%-3d | Force: %-3d | Faim: %d",
                getName(),
                this.getClass().getSimpleName(),
                health.get(),
                health.getMax(),
                getStrength(),
                hunger.get()
        );
    }

    public void setCombatStrategy(CombatStrategy combatStrategy) {
        this.combatStrategy = combatStrategy;
    }

    public void performAttack(Character opponent) {
        if (this.isDead() || opponent.isDead()) return;
        this.combatStrategy.executeAttack(this, opponent);
    }
}