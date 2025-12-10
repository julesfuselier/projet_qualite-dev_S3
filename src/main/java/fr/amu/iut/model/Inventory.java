package fr.amu.iut.model;

import fr.amu.iut.model.items.Item;
import java.util.ArrayList;
import java.util.List;

public class Inventory <T extends Item> {

    private List<T> items;
    private final int CAPACITY_OF_THE_INVENTORY = 36;
    private final int capacity = 64;

    public Inventory() {
        this.items = new ArrayList<>();
    }
    public void addItem(T item) {
        if (items.size() < CAPACITY_OF_THE_INVENTORY) {
            items.add(item);
        } else {
            System.out.println("Inventaire plein !");
        }
    }

    public boolean removeItem(T item) {
        return items.remove(item);
    }

    public List<T> getItems() {
        return items;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getCurrentSize() {
        return items.size();
    }
}