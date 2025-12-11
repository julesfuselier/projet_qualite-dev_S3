package fr.amu.iut.model.characters.jobs;

import fr.amu.iut.model.characters.*;
import fr.amu.iut.model.characters.Character;
import fr.amu.iut.model.exceptions.InsufficientIngredientsException;
import fr.amu.iut.model.items.Item;
import fr.amu.iut.model.items.foods.Food;
import fr.amu.iut.model.items.foods.FoodType;
import fr.amu.iut.model.items.potion.MagicPotion;
import fr.amu.iut.model.items.potion.PotionRecipe;
import fr.amu.iut.model.items.potion.PotionType;
import fr.amu.iut.util.GameEvents;

/**
 * Classe représentant un druide dans le jeu.
 * Le druide est un personnage qui peut mener, travailler dans la nature et
 * combattre.
 */
public class Druid extends Warrior implements Leader, Worker {

    /**
     * Constructeur de la classe Druid.
     * 
     * @param name      Le nom du druide
     * @param sex       Le sexe du druide
     * @param size      La taille du druide
     * @param age       L'âge du druide
     * @param strength  La force du druide
     * @param endurance L'endurance du druide
     * @param faction   La faction à laquelle appartient le druide
     */
    public Druid(String name, char sex, int size, int age, int strength, int endurance, Faction faction) {
        super(name, sex, size, age, strength, endurance, faction);
    }

    /**
     * Méthode représentant le fait de mener un autre personnage.
     * Affiche un message indiquant que le druide mène le personnage spécifié.
     * 
     * @param character Le personnage à mener
     */
    @Override
    public void lead(Character character) {
        GameEvents.log(this.getName() + " is leading " + character.getName());
    }

    /**
     * Méthode représentant le travail du druide dans la nature.
     * Affiche un message indiquant que le druide travaille dans la nature.
     */
    @Override
    public void work() {
        GameEvents.log(this.getName() + " est en train de travailler dans la nature...");
    }

    /**
     * Recherche un ingrédient spécifique dans l'inventaire du druide.
     * 
     * @param type Le type d'ingrédient à rechercher
     * @return L'ingrédient trouvé ou null s'il n'est pas présent
     */
    private Item findIngredientInInventory(FoodType type) {
        return this.getInventory().getItems().stream()
                .filter(item -> item instanceof Food)
                .map(item -> (Food) item)
                .filter(food -> food.getType() == type)
                .filter(food -> {
                    if (type == FoodType.FISH || type == FoodType.CLOVER) {
                        return food.isFresh();
                    }
                    return true;
                })
                .findFirst()
                .orElse(null);
    }

    /**
     * Méthode permettant au druide de concocter une potion magique.
     * Vérifie la présence des ingrédients nécessaires dans l'inventaire.
     * 
     * @param desiredType Le type de potion magique à concocter
     * @throws InsufficientIngredientsException Si les ingrédients nécessaires sont
     *                                          absents
     */
    public void craftMagicPotion(PotionType desiredType) throws InsufficientIngredientsException {
        PotionRecipe recipe = PotionRecipe.fromType(desiredType);

        PotionRecipe baseRecipe = PotionRecipe.BASIC;

        for (FoodType ingredientType : baseRecipe.getIngredients()) {
            if (findIngredientInInventory(ingredientType) == null) {
                throw new InsufficientIngredientsException("Manque ingrédient de base : " + ingredientType);
            }
        }

        if (desiredType != PotionType.BASIC) {
            for (FoodType ingredientType : recipe.getIngredients()) {
                if (findIngredientInInventory(ingredientType) == null) {
                    throw new InsufficientIngredientsException("Manque ingrédient spécial : " + ingredientType);
                }
            }
        }

        for (FoodType ingredientType : baseRecipe.getIngredients()) {
            this.getInventory().removeItem(findIngredientInInventory(ingredientType));
        }
        if (desiredType != PotionType.BASIC) {
            for (FoodType ingredientType : recipe.getIngredients()) {
                this.getInventory().removeItem(findIngredientInInventory(ingredientType));
            }
        }

        MagicPotion newPotion = new MagicPotion(desiredType);
        this.getInventory().addItem(newPotion);

        GameEvents.log(this.getName() + " a réussi à concocter une " + newPotion.getName() + " !");
    }
}