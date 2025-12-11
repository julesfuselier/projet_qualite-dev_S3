package fr.amu.iut.model.items.potion;

import fr.amu.iut.model.items.foods.FoodType;
import java.util.List;

public enum PotionRecipe {
    // TODO : Rajouter les ingrédients
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

    PotionRecipe(PotionType potionType, List<FoodType> requiredIngredients) {
        this.potionType = potionType;
        this.requiredIngredients = requiredIngredients;
    }

    public static PotionRecipe fromType(PotionType type) {
        for (PotionRecipe recipe : values()) {
            if (recipe.potionType == type) return recipe;
        }
        return BASIC; // défaut
    }

    public List<FoodType> getIngredients() {
        return requiredIngredients;
    }
}