package fr.amu.iut.model.characters.jobs;

import fr.amu.iut.model.characters.*;
import fr.amu.iut.model.characters.Character;
import fr.amu.iut.model.exceptions.InsufficientIngredientsException;
import fr.amu.iut.model.items.Item;
import fr.amu.iut.model.items.foods.Food;
import fr.amu.iut.model.items.foods.FoodType;
import fr.amu.iut.model.items.foods.FreshnessStatus;
import fr.amu.iut.model.items.potion.MagicPotion;
import fr.amu.iut.model.items.potion.PotionType;

import java.util.List;

/**
 * Classe représentant un druide dans le jeu.
 * Le druide est un personnage qui peut mener, travailler dans la nature et combattre.
 */
public class Druid extends Warrior implements Leader, Worker, Fighter {

    /**
     * Constructeur de la classe Druid.
     * @param name Le nom du druide
     * @param sex Le sexe du druide
     * @param size La taille du druide
     * @param age L'âge du druide
     * @param strength La force du druide
     * @param endurance L'endurance du druide
     * @param faction La faction à laquelle appartient le druide
     */
    public Druid(String name, char sex, int size, int age, int strength, int endurance, Faction faction) {
        super(name, sex, size, age, strength, endurance, faction );
    }

    /**
     * Méthode représentant le fait de mener un autre personnage.
     * Affiche un message indiquant que le druide mène le personnage spécifié.
     * @param character Le personnage à mener
     */
    @Override
    public void lead(Character character) {
        System.out.println(this.getName() + " is leading " + character.getName());
    }

    /**
     * Méthode représentant le travail du druide.
     * Affiche un message indiquant que le druide travaille dans la nature.
     */
    @Override
    public void work() {
        System.out.println(this.getName() + " est en train de travailler dans la nature...");
    }

    /**
     * Méthode utilitaire pour trouver un ingrédient spécifique dans une liste d'items.
     * @param items La liste des items à rechercher
     * @param type Le type de nourriture recherché
     * @param requiredStatus Le statut de fraîcheur requis (peut être null)
     * @return L'item trouvé ou null s'il n'existe pas
     */
    private Item findIngredient(List<Item> items, FoodType type, FreshnessStatus requiredStatus) {
        for (Item item : items) {
            if (item instanceof Food food) {
                if (food.getType() == type) {
                    if (requiredStatus == null || food.getStatus() == requiredStatus) {
                        return item;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Méthode pour concocter une potion magique.
     * Vérifie la présence des ingrédients nécessaires dans l'inventaire du druide.
     * Si tous les ingrédients sont présents, ils sont retirés de l'inventaire et une nouvelle potion est ajoutée.
     * @param desiredType Le type de potion magique à concocter
     * @throws InsufficientIngredientsException Si les ingrédients requis ne sont pas disponibles
     */
    public void craftMagicPotion(PotionType desiredType) throws InsufficientIngredientsException {
        List<Item> inventoryItems = this.getInventory().getItems();

        Item mistletoe = findIngredient(inventoryItems, FoodType.MISTLETOE, null);
        Item carrot = findIngredient(inventoryItems, FoodType.CARROT, null);
        Item salt = findIngredient(inventoryItems, FoodType.SALT, null);
        Item honey = findIngredient(inventoryItems, FoodType.HONEY, null);
        Item mead = findIngredient(inventoryItems, FoodType.MEAD, null);
        Item secretIngredient = findIngredient(inventoryItems, FoodType.SECRET_INGREDIENT, null);

        Item freshClover = findIngredient(inventoryItems, FoodType.CLOVER, FreshnessStatus.FRESH);
        Item freshFish = findIngredient(inventoryItems, FoodType.FISH, FreshnessStatus.FRESH);

        Item oilOrBeet = findIngredient(inventoryItems, FoodType.ROCK_OIL, null);
        if (oilOrBeet == null) {
            oilOrBeet = findIngredient(inventoryItems, FoodType.BEET_JUICE, null);
        }

        Item specialIngredient = null;
        if (desiredType == PotionType.SPLITTING) {
            specialIngredient = findIngredient(inventoryItems, FoodType.TWO_HEADED_UNICORNN_MILK, null);
        } else if (desiredType == PotionType.METAMORPHOSIS) {
            specialIngredient = findIngredient(inventoryItems, FoodType.IDEFIX_HAIR, null);
        }

        boolean hasBaseIngredients = mistletoe != null && carrot != null && salt != null &&
                freshClover != null && freshFish != null && honey != null &&
                mead != null && secretIngredient != null && oilOrBeet != null;

        boolean hasSpecialIngredient = true;
        if (desiredType == PotionType.SPLITTING || desiredType == PotionType.METAMORPHOSIS) {
            hasSpecialIngredient = (specialIngredient != null);
        }

        if (!hasBaseIngredients) {
            throw new InsufficientIngredientsException(
                    "Échec : " + this.getName() + " n'a pas les ingrédients de base requis (Gui, Poisson frais, etc.) pour une potion " + desiredType + "."
            );
        }

        if (!hasSpecialIngredient) {
            throw new InsufficientIngredientsException(
                    "Échec : " + this.getName() + " n'a pas l'ingrédient spécial requis pour la potion " + desiredType + "."
            );
        }

        this.getInventory().removeItem(mistletoe);
        this.getInventory().removeItem(carrot);
        this.getInventory().removeItem(salt);
        this.getInventory().removeItem(freshClover);
        this.getInventory().removeItem(freshFish);
        this.getInventory().removeItem(honey);
        this.getInventory().removeItem(mead);
        this.getInventory().removeItem(secretIngredient);
        this.getInventory().removeItem(oilOrBeet);

        if (specialIngredient != null) {
            this.getInventory().removeItem(specialIngredient);
        }

        MagicPotion newPotion = new MagicPotion(desiredType);
        this.getInventory().addItem(newPotion);

        System.out.println(this.getName() + " a réussi à concocter une " + newPotion.getName() + " !");
    }
}