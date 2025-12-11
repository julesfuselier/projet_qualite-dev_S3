package fr.amu.iut.model.items.foods;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FoodFactoryTest {

    private FoodFactory foodFactory;

    @BeforeEach
    public void setUp() {
        foodFactory = new FoodFactory();
    }

    @Test
    public void testCreateFish() {
        Food food = foodFactory.createFood(FoodType.FISH);
        assertEquals("Fish", food.getName());
        assertEquals(50, food.getNutritionValue());
        assertEquals(FoodType.FISH, food.getType());
    }

    @Test
    public void testCreateWildBoar() {
        Food food = foodFactory.createFood(FoodType.WILD_BOAR);
        assertEquals("Wild Boar", food.getName());
        assertEquals(80, food.getNutritionValue());
        assertEquals(FoodType.WILD_BOAR, food.getType());
    }

    @Test
    public void testCreateAllFoodTypes() {
        for (FoodType type : FoodType.values()) {
            Food food = foodFactory.createFood(type);
            assertNotNull(food);
            assertEquals(type, food.getType());
        }
    }
}
