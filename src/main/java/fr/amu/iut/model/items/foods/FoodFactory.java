package fr.amu.iut.model.items.foods;

public class FoodFactory {

    public Food createFood(FoodType type) {
        switch (type) {
            case FISH:
                return new Food("Fish", 50, true, FreshnessStatus.FRESH, type);
            case CLOVER:
                return new Food("Clover", 20, true, FreshnessStatus.FRESH, type);
            case WILD_BOAR:
                return new Food("Wild Boar", 80, true, FreshnessStatus.FRESH, type);
            case SALT:
                return new Food("Salt", 5, false, FreshnessStatus.FRESH, type);
            case CARROT:
                return new Food("Carrot", 30, true, FreshnessStatus.FRESH, type);
            case MISTLETOE:
                return new Food("Mistletoe", 15, true, FreshnessStatus.FRESH, type);
            case LOBSTER:
                return new Food("Lobster", 70, true, FreshnessStatus.FRESH, type);
            case STRAWBERRY:
                return new Food("Strawberry", 25, true, FreshnessStatus.FRESH, type);
            case ROCK_OIL:
                return new Food("Rock Oil", 10, false, FreshnessStatus.FRESH, type);
            case BEET_JUICE:
                return new Food("Beet Juice", 35, true, FreshnessStatus.FRESH, type);
            case HONEY:
                return new Food("Honey", 40, true, FreshnessStatus.FRESH, type);
            case WINE:
                return new Food("Wine", 60, false, FreshnessStatus.FRESH, type);
            case MEAD:
                return new Food("Mead", 55, false, FreshnessStatus.FRESH, type);
            case TWO_HEADED_UNICORNN_MILK:
                return new Food("Two-Headed Unicorn Milk", 100, true, FreshnessStatus.FRESH, type);
            case IDEFIX_HAIR:
                return new Food("Idefix Hair", 5, false, FreshnessStatus.FRESH, type);
            case SECRET_INGREDIENT:
                return new Food("Secret Ingredient", 150, true, FreshnessStatus.FRESH, type);
            default:
                throw new IllegalArgumentException("Unknown food type: " + type);
        }
    }
}