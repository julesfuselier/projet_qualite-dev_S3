package fr.amu.iut.model.items.potion;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MagicPotionTest {

    @Test
    public void testMagicPotionCreation() {
        MagicPotion potion = new MagicPotion(PotionType.METAMORPHOSIS);
        assertEquals("Potion Magique (METAMORPHOSIS)", potion.getName());
        assertEquals(5, potion.getDoses());
        assertEquals(PotionType.METAMORPHOSIS, potion.getType());
    }

    @Test
    public void testTakeDose() {
        MagicPotion potion = new MagicPotion(PotionType.SPLITTING);
        assertTrue(potion.takeDose());
        assertEquals(4, potion.getDoses());
    }

    @Test
    public void testTakeDoseUntilEmpty() {
        MagicPotion potion = new MagicPotion(PotionType.SPLITTING);
        for (int i = 0; i < 5; i++) {
            assertTrue(potion.takeDose());
        }
        assertFalse(potion.takeDose());
        assertEquals(0, potion.getDoses());
    }

    @Test
    public void testGetType() {
        MagicPotion potion = new MagicPotion(PotionType.METAMORPHOSIS);
        assertEquals(PotionType.METAMORPHOSIS, potion.getType());
    }

    @Test
    public void testGetDoses() {
        MagicPotion potion = new MagicPotion(PotionType.SPLITTING);
        assertEquals(5, potion.getDoses());
        potion.takeDose();
        assertEquals(4, potion.getDoses());
    }
}
