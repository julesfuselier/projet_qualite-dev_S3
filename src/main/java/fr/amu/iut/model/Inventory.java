package fr.amu.iut.model;

import fr.amu.iut.model.items.foods.Food;
import fr.amu.iut.model.items.foods.FoodType;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

public class Inventory {
    // une liste pour stocker les objets ramassés
    private List<Food> items;

    public Inventory() {
        this.items = new ArrayList<>();
    }

    /**
     * Ajoute un objet à l'inventaire.
     */
    public void add(Food item) {
        items.add(item);
        System.out.println("Objet ajouté à l'inventaire : " + item.getName());
    }

    /**
     * Vérifie si l'inventaire contient un certain type de nourriture/ingrédient.
     */
    public boolean contains(FoodType type) {
        for (Food item : items) {
            if (item.getType() == type) {
                return true;
            }
        }
        return false;
    }

    /**
     * Récupère et retire un objet d'un type spécifique (pour le manger ou le cuisiner).
     */
    public Food consume(FoodType type) {
        Iterator<Food> iterator = items.iterator();
        while (iterator.hasNext()) {
            Food item = iterator.next();
            if (item.getType() == type) {
                iterator.remove(); // On retire l'objet de l'inventaire
                return item;
            }
        }
        return null; // L'objet n'est pas dans l'inventaire
    }

    // compter les objets
    public int count(FoodType type) {
        int count = 0;
        for (Food item : items) {
            if (item.getType() == type) count++;
        }
        return count;
    }

    @Override
    public String toString() {
        return "Inventaire : " + items.size() + " objets.";
    }

    public Food getFood(FoodType foodType) {
        for (Food item : items) {
            if (item.getType() == foodType) {
                return item;
            }
        }
        return null;
    }
}