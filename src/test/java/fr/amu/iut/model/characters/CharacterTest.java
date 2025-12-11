package fr.amu.iut.model.characters;

import fr.amu.iut.GameConfig;
import fr.amu.iut.model.Inventory;
import fr.amu.iut.model.characters.jobs.Lycanthrope;
import fr.amu.iut.model.fight.CombatStrategy;
import fr.amu.iut.model.items.Item;
import fr.amu.iut.model.items.foods.Food;
import fr.amu.iut.model.items.foods.FoodType;
import fr.amu.iut.model.items.foods.FreshnessStatus;
import fr.amu.iut.model.items.potion.MagicPotion;
import fr.amu.iut.model.items.potion.PotionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class CharacterTest {

    private class ConcreteCharacter extends Character {
        public ConcreteCharacter(String name, char sex, int age, Faction faction) {
            super(name, sex, 1, age, 1, 1, faction);
        }

        public void fight(Character opponent) {
            System.out.println(getName() + " fights " + opponent.getName());
        }

        // Pas besoin de redéfinir canEat ici, on utilise celle de Character.
        // Mais Character.canEat est privée... sauf si CharacterTest est dans le même package (ce qui est le cas).
        // Cependant, le code de Character fourni a une méthode privée canEat.
        // Le test testEatAllowedFood dépend de la logique interne.
    }

    private ConcreteCharacter gaulois;
    private ConcreteCharacter romain;
    private Food sanglier;
    private Food poissonFrais;
    private Food poissonPasFrais;
    private Food vin;

    @BeforeEach
    public void setUp() {
        gaulois = new ConcreteCharacter("TestGaulois", 'M', 30, Faction.GALISH);
        romain = new ConcreteCharacter("TestRomain", 'F', 25, Faction.ROMAN);
        sanglier = new Food("Sanglier", 50, true, FreshnessStatus.FRESH, FoodType.WILD_BOAR);
        poissonFrais = new Food("Poisson", 20, true, FreshnessStatus.FRESH, FoodType.FISH);
        poissonPasFrais = new Food("Poisson", 20, true, FreshnessStatus.STALE, FoodType.FISH);
        vin = new Food("Vin", 10, false, FreshnessStatus.FRESH, FoodType.WINE);
    }

    @Test
    public void testEatForbiddenFood() {
        Food miel = new Food("Miel", 10, true, FreshnessStatus.FRESH, FoodType.HONEY);
        gaulois.pickUpItem(miel);
        int initialHunger = gaulois.getHunger().get();
        gaulois.eat(miel);
        assertEquals(initialHunger, gaulois.getHunger().get());
        assertTrue(gaulois.getInventory().getItems().contains(miel));
    }

    @Test
    public void testEatStaleFish() {
        // Le poisson n'est accepté que par les Gaulois
        gaulois.pickUpItem(poissonPasFrais);
        int initialHealth = gaulois.getHealth().get();
        gaulois.eat(poissonPasFrais);
        // Mange mais perd des PV
        assertTrue(gaulois.getHealth().get() < initialHealth);
    }

    @Test
    public void testPickUpItem() {
        gaulois.pickUpItem(sanglier);
        assertTrue(gaulois.getInventory().getItems().contains(sanglier));
    }

    @Test
    public void testIsDead() {
        assertFalse(gaulois.isDead());
        gaulois.getHealth().set(0);
        assertTrue(gaulois.isDead());
    }

    @Test
    public void testBeHealed() {
        gaulois.getHealth().set(50);
        gaulois.beHealed(20);
        assertEquals(70, gaulois.getHealth().get());
        gaulois.beHealed(100);
        assertEquals(100, gaulois.getHealth().getMax());
    }

    @Test
    public void testGetHungry() {
        int initialHunger = gaulois.getHunger().get();
        gaulois.getHungry(20);
        assertEquals(initialHunger - 20, gaulois.getHunger().get());
    }

    @Test
    public void testClone() {
        ConcreteCharacter original = new ConcreteCharacter("Original", 'M', 40, Faction.GALISH);
        original.pickUpItem(sanglier);
        Character clone = original.clone();
        assertEquals(original.getName(), clone.getName());
        assertEquals(original.getAge(), clone.getAge());
        assertEquals(original.getFaction(), clone.getFaction());
        assertEquals(original.getInventory().getItems(), clone.getInventory().getItems());
    }

    @Test
    public void toStringTest() {
        String expected = String.format("%-15s [%-12s] | PV: %-3d/%-3d | Force: %-3d | Faim: %d",
                gaulois.getName(),
                "ConcreteCharacter",
                gaulois.getHealth().get(),
                gaulois.getHealth().getMax(),
                gaulois.getStrength(),
                gaulois.getHunger().get());
        assertEquals(expected, gaulois.toString());
    }

    @Test
    public void testDrinkMagicPotionSingleDose() {
        MagicPotion potion = new MagicPotion(PotionType.METAMORPHOSIS, 5);
        gaulois.pickUpItem(potion);
        int initialPotionStat = gaulois.getMagicPotion().get();
        gaulois.drinkMagicPotion(potion, false);
        assertTrue(gaulois.getMagicPotion().get() > initialPotionStat);
        assertEquals(4, potion.getDoses());
    }

    @Test
    public void testDrinkMagicPotionAllDosesAndBecomeStatue() {
        MagicPotion potion1 = new MagicPotion(PotionType.METAMORPHOSIS, 5);
        MagicPotion potion2 = new MagicPotion(PotionType.METAMORPHOSIS, 5);
        gaulois.pickUpItem(potion1);
        gaulois.pickUpItem(potion2);
        assertFalse(gaulois.getName().contains("Statue"));
        gaulois.drinkMagicPotion(potion1, true);
        gaulois.drinkMagicPotion(potion2, true);
        assertTrue(gaulois.getName().contains("Statue"));
    }

    @Test
    public void testIsActivePotion() {
        assertFalse(gaulois.isActivePotion());
        gaulois.getMagicPotion().set(10);
        assertTrue(gaulois.isActivePotion());
    }

    @Test
    public void testGetStrengthWithPotion() {
        int baseStrength = gaulois.getStrength();
        gaulois.getMagicPotion().set(10);
        assertEquals(baseStrength + GameConfig.BONUS_STRENGTH_POTION, gaulois.getStrength());
    }

    @Test
    public void testUpdatePotionDuration() {
        gaulois.getMagicPotion().set(10);
        gaulois.updatePotionDuration();
        assertEquals(9, gaulois.getMagicPotion().get());
    }

    @Test
    public void testTransformToLycanthrope() {
        Character lycanthrope = gaulois.transformToLycanthrope();
        assertTrue(lycanthrope instanceof Lycanthrope);
        assertTrue(lycanthrope.getName().contains("Loup-Garou"));
    }

    @Test
    public void testPerformAttack() {
        CombatStrategy mockStrategy = mock(CombatStrategy.class);
        gaulois.setCombatStrategy(mockStrategy);
        romain.getHealth().set(100);
        gaulois.performAttack(romain);
        verify(mockStrategy).executeAttack(gaulois, romain);
    }
}