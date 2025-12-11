package fr.amu.iut.model;

import fr.amu.iut.model.items.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class InventoryTest {

    private Inventory<Item> inventory;
    private Item item1;
    private Item item2;

    private static class TestItem extends Item {
        public TestItem(String name, String description) {
            super(name, description);
        }
    }

    @BeforeEach
    public void setUp() {
        inventory = new Inventory<>();
        item1 = new TestItem("Epee", "Une simple epee");
        item2 = new TestItem("Bouclier", "Un simple bouclier");
    }

    @Test
    public void testAddItem() {
        inventory.addItem(item1);
        assertEquals(1, inventory.getCurrentSize());
        assertTrue(inventory.getItems().contains(item1));
    }

    @Test
    public void testAddItemWhenFull() {
        for (int i = 0; i < 64; i++) {
            inventory.addItem(new TestItem("Item " + i, ""));
        }
        assertEquals(64, inventory.getCurrentSize());
        inventory.addItem(item1);
        assertEquals(64, inventory.getCurrentSize());
        assertFalse(inventory.getItems().contains(item1));
    }

    @Test
    public void testRemoveItem() {
        inventory.addItem(item1);
        inventory.addItem(item2);
        assertTrue(inventory.removeItem(item1));
        assertEquals(1, inventory.getCurrentSize());
        assertFalse(inventory.getItems().contains(item1));
        assertTrue(inventory.getItems().contains(item2));
    }

    @Test
    public void testRemoveItemNotInInventory() {
        inventory.addItem(item1);
        assertFalse(inventory.removeItem(item2));
        assertEquals(1, inventory.getCurrentSize());
    }

    @Test
    public void testGetItems() {
        inventory.addItem(item1);
        inventory.addItem(item2);
        assertEquals(2, inventory.getItems().size());
        assertTrue(inventory.getItems().contains(item1));
        assertTrue(inventory.getItems().contains(item2));
    }

    @Test
    public void testGetCapacity() {
        assertEquals(64, inventory.getCapacity());
    }

    @Test
    public void testGetCurrentSize() {
        assertEquals(0, inventory.getCurrentSize());
        inventory.addItem(item1);
        assertEquals(1, inventory.getCurrentSize());
    }
}
