package fr.amu.iut.model.items.foods;

public class FoodFactory {

    public Food createFood(FoodType type) {
        switch (type) {
            case FISH:
                return new Food("Fish", 50, true, FreshnessStatus.FRESH);
            case CLOVER:
        }
    }
}
