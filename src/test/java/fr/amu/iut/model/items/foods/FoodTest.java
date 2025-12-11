package fr.amu.iut.model.items.foods;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FoodTest {

    @Test
    public void testFoodCreation() {
        Food food = new Food("Apple", 10, true, FreshnessStatus.FRESH, FoodType.FRUIT);
        assertEquals("Apple", food.getName());
        assertEquals(10, food.getNutritionValue());
        assertEquals(FreshnessStatus.FRESH, food.getStatus());
        assertEquals(FoodType.FRUIT, food.getType());
        assertTrue(food.isFresh());
    }

    @Test
    public void testIsFresh() {
        Food freshFood = new Food("Steak", 50, true, FreshnessStatus.FRESH, FoodType.MEAT);
        assertTrue(freshFood.isFresh());

        Food staleFood = new Food("Old Bread", 5, true, FreshnessStatus.STALE, FoodType.CEREAL);
        assertFalse(staleFood.isFresh());
    }

    @Test
    public void testSetFreshnessStatus() {
        Food food = new Food("Milk", 20, true, FreshnessStatus.FRESH, FoodType.DAIRY);
        assertTrue(food.isFresh());
        food.setFreshnessStatus(FreshnessStatus.STALE);
        assertFalse(food.isFresh());
        assertEquals(FreshnessStatus.STALE, food.getStatus());
    }
}
