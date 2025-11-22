package fr.amu.iut.model;

import fr.amu.iut.model.characters.Character;
import fr.amu.iut.model.items.foods.Food;

import java.util.ArrayList;
import java.util.List;

public class Location {
    private String name;
    private List<Character> characters;
    private List<Food> foods;

    // Village Constructor
    public Location(String name) {
        this.name = name;
        this.characters = new ArrayList<>();
        this.foods = new ArrayList<>();
    }

    // Get village name
    public String getName() {
        return name;
    }

    // Get characters name
    public List<Character> getCharacters() {
        return characters;
    }

    // Get foods
    public List<Food> getFoods() {
        return foods;
    }

    // Add new character
    public void addCharacter(Character character) {
        this.characters.add(character);
    }

    // Remove character
    public void removeCharacter(Character character) {
        this.characters.remove(character);
    }

    // Add new food
    public void addFood(Food food) {
        this.foods.add(food);
    }

    // Remove food
    public void removeFood(Food food) {
        this.foods.remove(food);
    }

    // Displays the characteristics of the village
    public void displayCharacteristics() {
        System.out.println("Location information : " + name);
        System.out.println("Characters presents :");
        if (characters.isEmpty()) {
            System.out.println("No characters present.");
        } else {
            for (Character character : characters) {
                System.out.println("  - " + character.getName() + " (" + character.getClass().getSimpleName() + ")");
            }
        }
        System.out.println("Available foods :");
        if (foods.isEmpty()) {
            System.out.println("No foods available.");
        } else {
            for (Food food : foods) {
                System.out.println("  - " + food.getName() + " (Valeur de nutrition : " + food.getNutritionValue() + ")");
            }
        }
    }
}