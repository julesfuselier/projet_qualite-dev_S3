package fr.amu.iut.model.characters.jobs;

import fr.amu.iut.model.characters.Character;
import fr.amu.iut.model.characters.Faction;
import fr.amu.iut.model.characters.Fighter;
import fr.amu.iut.model.characters.Leader;
import fr.amu.iut.model.characters.Worker;
import fr.amu.iut.model.items.Item;
import fr.amu.iut.model.items.foods.Food;
import fr.amu.iut.model.items.foods.FoodType;
import fr.amu.iut.model.items.foods.FreshnessStatus;
import fr.amu.iut.model.items.potion.MagicPotion;
import fr.amu.iut.model.items.potion.PotionType;

import java.util.List;

public class Druid extends fr.amu.iut.model.characters.Character implements Leader, Worker, Fighter {

    public Druid(String name, char sex, int size, int age, int strength, int endurance, Faction faction) {
        super(name, sex, size, age, strength, endurance, faction );
    }

    @Override
    public void lead(fr.amu.iut.model.characters.Character character) {
        System.out.println(this.getName() + " is leading " + character.getName());
    }

    @Override
    public void work() {

    }

    @Override
    public void fight(Character character) {
        System.out.println(this.getName() + " is fighting " + character.getName());
    }

    /**
     * Tente de créer une potion du type spécifié.
     * @param desiredType Le type de potion que le druide veut créer.
     */
    public void craftMagicPotion(PotionType desiredType) {
        List<Item> inventoryItems = this.getInventory().getItems();

        // 1. Identification des ingrédients dans l'inventaire
        Item mistletoe = null;
        Item carrot = null;
        Item salt = null;
        Item freshClover = null;
        Item freshFish = null;
        Item honey = null;
        Item mead = null;
        Item secretIngredient = null;
        Item oilOrBeet = null; // Huile de roche OU Jus de betterave

        Item specialIngredient = null; // Lait de licorne OU Poils d'Idéfix selon le besoin

        for (Item item : inventoryItems) {
            if (item instanceof Food) {
                Food food = (Food) item;
                FoodType fType = food.getType();
                FreshnessStatus status = food.getStatus();

                if (fType == FoodType.MISTLETOE && mistletoe == null) mistletoe = item;
                else if (fType == FoodType.CARROT && carrot == null) carrot = item;
                else if (fType == FoodType.SALT && salt == null) salt = item;
                else if (fType == FoodType.CLOVER && status == FreshnessStatus.FRESH && freshClover == null) freshClover = item;
                else if (fType == FoodType.FISH && status == FreshnessStatus.FRESH && freshFish == null) freshFish = item;
                else if (fType == FoodType.HONEY && honey == null) honey = item;
                else if (fType == FoodType.MEAD && mead == null) mead = item;
                else if (fType == FoodType.SECRET_INGREDIENT && secretIngredient == null) secretIngredient = item;

                else if ((fType == FoodType.ROCK_OIL || fType == FoodType.BEET_JUICE) && oilOrBeet == null) {
                    oilOrBeet = item;
                }

                // Recherche de l'ingrédient spécial SEULEMENT si c'est celui requis par le type demandé
                else if (desiredType == PotionType.SPLITTING && fType == FoodType.TWO_HEADED_UNICORNN_MILK && specialIngredient == null) {
                    specialIngredient = item;
                }
                else if (desiredType == PotionType.METAMORPHOSIS && fType == FoodType.IDEFIX_HAIR && specialIngredient == null) {
                    specialIngredient = item;
                }
            }
        }

        // 2. Vérification de la disponibilité des ingrédients
        boolean hasBaseIngredients = mistletoe != null && carrot != null && salt != null &&
                freshClover != null && freshFish != null && honey != null &&
                mead != null && secretIngredient != null && oilOrBeet != null;

        boolean hasSpecialIngredient = true; // Par défaut vrai pour BASIC
        if (desiredType == PotionType.SPLITTING || desiredType == PotionType.METAMORPHOSIS) {
            hasSpecialIngredient = (specialIngredient != null);
        }

        // 3. Consommation et Création
        if (hasBaseIngredients && hasSpecialIngredient) {
            // Retrait des ingrédients de base
            this.getInventory().removeItem(mistletoe);
            this.getInventory().removeItem(carrot);
            this.getInventory().removeItem(salt);
            this.getInventory().removeItem(freshClover);
            this.getInventory().removeItem(freshFish);
            this.getInventory().removeItem(honey);
            this.getInventory().removeItem(mead);
            this.getInventory().removeItem(secretIngredient);
            this.getInventory().removeItem(oilOrBeet);

            // Retrait de l'ingrédient spécial si nécessaire
            if (specialIngredient != null) {
                this.getInventory().removeItem(specialIngredient);
            }

            // Création de la potion demandée
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