package fr.amu.iut.model;
import fr.amu.iut.model.items.Item;
import java.util.List;

public class Inventory {

    private List<Item> items;
    private final int MAX_CAPACITY = 36;
    private final int capacity = 64;

    public Inventory() {
        this.items = new java.util.ArrayList<>();
    }

    public void addItem(Item item) {
        if (items.size() < MAX_CAPACITY) {
            items.add(item);
        }
    }

    public boolean removeItem(Item item) {
        return items.remove(item);
    }

    public List<Item> getItems() {
        return items;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getCurrentSize() {
        return items.size();
    }
}