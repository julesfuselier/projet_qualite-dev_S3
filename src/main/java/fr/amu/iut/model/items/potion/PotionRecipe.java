package fr.amu.iut.model.items.potion;

import fr.amu.iut.model.items.foods.FoodType;
import java.util.List;

/**
 * Enumération représentant les recettes de potions magiques.
 */
public enum PotionRecipe {
    BASIC(PotionType.BASIC, List.of(
            FoodType.MISTLETOE, FoodType.CARROT, FoodType.SALT,
            FoodType.CLOVER, FoodType.FISH, FoodType.HONEY,
            FoodType.MEAD, FoodType.SECRET_INGREDIENT, FoodType.ROCK_OIL
    )),

    // Recettes avancée : Base + Ingrédient spécial
    SPLITTING(PotionType.SPLITTING, List.of(
            FoodType.TWO_HEADED_UNICORNN_MILK
    )),

    // Recettes avancée : Base + Ingrédient spécial
    METAMORPHOSIS(PotionType.METAMORPHOSIS, List.of(
            FoodType.IDEFIX_HAIR
    ));

    private final PotionType potionType;
    private final List<FoodType> requiredIngredients;

    /** Constructeur de l'énumération PotionRecipe.
     *
     * @param potionType          Le type de potion associé à la recette.
     * @param requiredIngredients La liste des ingrédients nécessaires pour la recette.
     */
    PotionRecipe(PotionType potionType, List<FoodType> requiredIngredients) {
        this.potionType = potionType;
        this.requiredIngredients = requiredIngredients;
    }

    /**
     * Obtient la recette de potion correspondant au type de potion spécifié.
     *
     * @param type Le type de potion.
     * @return La recette de potion correspondante, ou la recette BASIC par défaut si le type n'est pas trouvé.
     */
    public static PotionRecipe fromType(PotionType type) {
        for (PotionRecipe recipe : values()) {
            if (recipe.potionType == type) return recipe;
        }
        return BASIC; // défaut
    }

    /**
     * Obtient la liste des ingrédients nécessaires pour la recette de potion.
     *
     * @return La liste des ingrédients requis.
     */
    public List<FoodType> getIngredients() {
        return requiredIngredients;
    }
}