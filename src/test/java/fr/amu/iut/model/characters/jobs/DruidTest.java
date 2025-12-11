package fr.amu.iut.model.characters.jobs;

import fr.amu.iut.model.characters.Faction;
import fr.amu.iut.model.exceptions.InsufficientIngredientsException;
import fr.amu.iut.model.items.foods.Food;
import fr.amu.iut.model.items.foods.FoodType;
import fr.amu.iut.model.items.foods.FreshnessStatus;
import fr.amu.iut.model.items.potion.MagicPotion;
import fr.amu.iut.model.items.potion.PotionType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DruidTest {

    @Test
    void testCraftPotionSuccess() throws InsufficientIngredientsException {
        Druid panoramix = new Druid("Panoramix", 'M', 170, 80, 10, 10, Faction.GALISH);

        panoramix.getInventory().addItem(new Food("Gui", 0, true, FreshnessStatus.FRESH, FoodType.MISTLETOE));
        panoramix.getInventory().addItem(new Food("Carotte", 0, true, FreshnessStatus.FRESH, FoodType.CARROT));
        panoramix.getInventory().addItem(new Food("Sel", 0, false, FreshnessStatus.FRESH, FoodType.SALT));
        panoramix.getInventory().addItem(new Food("Trèfle", 0, true, FreshnessStatus.FRESH, FoodType.CLOVER));
        panoramix.getInventory().addItem(new Food("Poisson", 0, true, FreshnessStatus.FRESH, FoodType.FISH));
        panoramix.getInventory().addItem(new Food("Miel", 0, true, FreshnessStatus.FRESH, FoodType.HONEY));
        panoramix.getInventory().addItem(new Food("Hydromel", 0, false, FreshnessStatus.FRESH, FoodType.MEAD));
        panoramix.getInventory().addItem(new Food("Ingrédient Secret", 0, true, FreshnessStatus.FRESH, FoodType.SECRET_INGREDIENT));
        panoramix.getInventory().addItem(new Food("Huile", 0, false, FreshnessStatus.FRESH, FoodType.ROCK_OIL));

        panoramix.craftMagicPotion(PotionType.BASIC);

        boolean hasPotion = panoramix.getInventory().getItems().stream()
                .anyMatch(item -> item instanceof MagicPotion);

        assertTrue(hasPotion, "Le druide aurait dû réussir à créer la potion avec tous les ingrédients.");

        boolean hasMistletoe = panoramix.getInventory().getItems().stream()
                .anyMatch(item -> item.getName().equals("Gui"));
        assertFalse(hasMistletoe, "Les ingrédients utilisés (Gui) auraient dû être retirés de l'inventaire.");
    }

    @Test
    void testCraftPotionFailure_MissingIngredient() throws InsufficientIngredientsException {
        Druid panoramix = new Druid("Panoramix", 'M', 170, 80, 10, 10, Faction.GALISH);

        panoramix.getInventory().addItem(new Food("Gui", 0, true, FreshnessStatus.FRESH, FoodType.MISTLETOE));
        panoramix.craftMagicPotion(PotionType.BASIC);

        boolean hasPotion = panoramix.getInventory().getItems().stream()
                .anyMatch(item -> item instanceof MagicPotion);

        assertFalse(hasPotion, "Le druide ne devrait pas créer de potion s'il manque des ingrédients.");
    }

    @Test
    void testDruidCanFight() {
        Druid druid = new Druid("Panoramix", 'M', 170, 80, 20, 10, Faction.GALISH);
        Legionary romain = new Legionary("Minus", 'M', 170, 30, 10, 5, Faction.ROMAN);

        int initialHealth = romain.getHealth().get();
        druid.fight(romain);

        assertTrue(romain.getHealth().get() < initialHealth,
                "Le romain aurait dû perdre des PV.");

        // Calcul dynamique des dégâts attendus
        int expectedDamage = Math.max(1, druid.getStrength() - romain.getEndurance());
        assertEquals(initialHealth - expectedDamage, romain.getHealth().get(),
                "Le calcul des dégâts est incorrect.");
    }
}