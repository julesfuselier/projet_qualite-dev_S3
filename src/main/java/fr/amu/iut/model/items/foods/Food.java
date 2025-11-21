package fr.amu.iut.model.items.foods;

public class Food {
    private String name;
    private int nutritionValue;
    private boolean isPerishable;
    private FreshnessStatus status;
    private FoodType type; // Important pour les règles

    public Food(String name, int nutritionValue, boolean isPerishable, FreshnessStatus status, FoodType type) {
        this.name = name;
        this.nutritionValue = nutritionValue;
        this.isPerishable = isPerishable;
        this.status = status;
        this.type = type;
    }

    public String getName() { return name; }
    public int getNutritionValue() { return nutritionValue; }
    public FreshnessStatus getStatus() { return status; }
    public FoodType getType() { return type; }
}