package fr.amu.iut.model.characters.jobs;

import fr.amu.iut.model.characters.*;
import fr.amu.iut.model.characters.Character;
import fr.amu.iut.model.items.Item;
import fr.amu.iut.model.items.foods.Food;
import fr.amu.iut.model.items.foods.FoodType;
import fr.amu.iut.model.items.foods.FreshnessStatus;
import fr.amu.iut.model.items.potion.MagicPotion;
import fr.amu.iut.model.items.potion.PotionType;

import java.util.List;

public class Druid extends Warrior implements Leader, Worker, Fighter {

    public Druid(String name, char sex, int size, int age, int strength, int endurance, Faction faction) {
        super(name, sex, size, age, strength, endurance, faction );
    }

    @Override
    public void lead(Character character) {
        System.out.println(this.getName() + " is leading " + character.getName());
    }

    @Override
    public void work() {

    }

    /**
     * Méthode utilitaire pour trouver un ingrédient spécifique dans l'inventaire.
     * @param items La liste des objets de l'inventaire.
     * @param type Le type de nourriture recherché.
     * @param requiredStatus Le statut requis (ex: FRESH). Si null, on ignore la fraîcheur.
     * @return L'objet trouvé, ou null s'il n'existe pas.
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

    public void craftMagicPotion(PotionType desiredType) {
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

        if (hasBaseIngredients && hasSpecialIngredient) {
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
        } else {
            System.out.println("Échec : " + this.getName() + " n'a pas les ingrédients requis pour une potion de type " + desiredType + ".");
            if (!hasBaseIngredients) System.out.println("(Ingrédients de base manquants)");
            if (!hasSpecialIngredient) System.out.println("(Ingrédient spécial manquant)");
        }
    }
}