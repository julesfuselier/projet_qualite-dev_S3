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

/**
 * Classe abstraite représentant un personnage dans le jeu.
 * Un personnage possède des attributs tels que le nom, le sexe, la taille, l'âge,
 * la force, l'endurance, la faction, ainsi que des indicateurs de santé, faim,
 * belligerence et potion magique.
 * Il peut manger des aliments, boire des potions magiques, se faire soigner,
 * et se transformer en loup-garou ou en statue de granit.
 */
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
    
    private boolean isStatue = false; // indicateur statue de granit
    private boolean permanentPotion = false; // Indicateur potion permanente
    private int potDrunkCount = 0; // indicateur nombre de marmites bus

    /**
     * Constructeur complet de la classe Character.
     * @param name Le nom du personnage
     * @param sex Le sexe du personnage
     * @param size La taille du personnage
     * @param age L'âge du personnage
     * @param strength La force du personnage
     * @param endurance L'endurance du personnage
     * @param faction La faction du personnage
     */
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
     * Constructeur simplifié de la classe Character.
     * Initialise la taille, la force et l'endurance à des valeurs par défaut.
     * @param name Le nom du personnage.
     * @param sex Le sexe du personnage.
     * @param age L'âge du personnage.
     */
    public Character(String name, char sex, int age) {
        this.name = name;
        this.sex = sex;
        this.age = age;
    }

    /**
     * Permet au personnage de se faire soigner.
     * @param healAmount Le montant de soins à recevoir.
     */
    public void beHealed(int healAmount) {
        this.health.add(healAmount);
    }

    /**
     * Permet au personnage de manger un aliment.
     * Gère les effets de l'aliment sur la faim et la santé du personnage.
     * @param food L'aliment à manger.
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
     * @param food L'aliment à vérifier.
     * @return true si le personnage peut manger l'aliment, false sinon.
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

    /** Vérifie si le type d'aliment est un légume.
     * @param type Le type d'aliment à vérifier.
     * @return true si c'est un légume, false sinon.
     * */
    private boolean isVegetable(FoodType type) {
        if (type == null) return false;

        return type == FoodType.CARROT
                || type == FoodType.CLOVER
                || type == FoodType.BEET_JUICE
                || type == FoodType.MISTLETOE
                || type == FoodType.STRAWBERRY;
    }

    /**
     * Permet au personnage de ramasser un objet et de l'ajouter à son inventaire.
     * @param item L'objet à ramasser.
     */
    public void pickUpItem(Item item) {
        this.inventory.addItem(item);
    }

    /**
     * Permet de boire une potion magique.
     * Gère les effets de la potion selon qu'on boive une gorgée ou toute la marmite.
     * @param potion La potion magique à boire.
     * @param drinkAll Indique si le personnage boit toute la marmite.
     * @return Le personnage après avoir bu la potion (peut être transformé).
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

    /**
     * Vérifie si la potion magique est active.
     * @return true si la potion est active, false sinon.
     */
    public boolean isActivePotion() {
        return !isStatue && (permanentPotion || magicPotion.get() > 0);
    }

    /**
     * Transforme le personnage en statue de granit.
     */
    private void becomeGraniteStatue() {
        isStatue = true;
        this.name += " (Statue de Granit)";
        System.out.println(getName() + " s'est transformé en statue de granit pour l'éternité !");
    }

    /**
     * Obtient la force du personnage, en tenant compte des effets de la potion magique.
     * @return La force effective du personnage.
     */
    public int getStrength() {
        if (isActivePotion()) {
            return strength + 1000; // Force surhumaine
        }
        return strength;
    }

    /**
     * Met à jour la durée de la potion magique.
     * Diminue le compteur de la potion si elle n'est pas permanente ou si le personnage n'est pas une statue.
     */
    public void updatePotionDuration() {
        if (permanentPotion || isStatue) return;

        if (magicPotion.get() > 0) {
            magicPotion.add(-1);
            if (magicPotion.get() == 0) {
                System.out.println("Les effets de la potion magique se dissipent pour " + getName() + ".");
            }
        }
    }

    /**
     * Augmente la faim du personnage.
     * @param hungerAmount Le montant de faim à ajouter.
     */
    public void getHungry(int hungerAmount) {
        this.hunger.add(-hungerAmount);
    }

    /**
     * Vérifie si le personnage est mort (points de vie <= 0).
     * @return true si le personnage est mort, false sinon.
     */
    public boolean isDead() {
        return this.health.get() <= 0;
    }

    /**
     * Transforme le personnage en loup-garou.
     * @return Le nouveau personnage loup-garou.
     */
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

    /** Clone method */
    @Override
    public Character clone() {
        try {
            return (Character) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    /** Affichage personnalisé */
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

    /** Gestion du combat */
    public void setCombatStrategy(CombatStrategy combatStrategy) {
        this.combatStrategy = combatStrategy;
    }

    /**
     * Effectue une attaque contre un adversaire en utilisant la stratégie de combat définie.
     * @param opponent L'adversaire à attaquer.
     */
    public void performAttack(Character opponent) {
        if (this.isDead() || opponent.isDead()) return;
        this.combatStrategy.executeAttack(this, opponent);
    }

    /* Setters et Getters de l'inventaire */
    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }
    public Inventory getInventory() {return inventory;}


    // Getters et Setters pour les attributs
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
}