package fr.amu.iut.model;

import fr.amu.iut.model.items.Item;
import java.util.ArrayList;
import java.util.List;

public class Inventory<T extends Item> {

    private List<T> items;
    private static final int MAX_CAPACITY = 64;

    public Inventory() {
        this.items = new ArrayList<>();
    }

    public void addItem(T item) {
        if (items.size() < MAX_CAPACITY) {
            items.add(item);
        } else {
            System.out.println("Inventaire plein ! Impossible d'ajouter " + item.getName());
        }
    }

    public boolean removeItem(T item) {
        return items.remove(item);
    }

    public List<T> getItems() {
        return items;
    }

    public int getCapacity() {
        return MAX_CAPACITY;
    }

    public int getCurrentSize() {
        return items.size();
    }
}