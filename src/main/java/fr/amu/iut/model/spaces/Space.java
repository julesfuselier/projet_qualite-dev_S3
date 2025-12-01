package fr.amu.iut.model.spaces;

import fr.amu.iut.model.items.foods.Food;
import fr.amu.iut.model.characters.Character;


import java.util.ArrayList;
import java.util.List;

public abstract class Space {
    private final String name;
    private double surface;
    private Character leader;
    private final List<Character> characters;
    private final List<Food> foods;

    public Space(String name, double surface, Character leader) {
        this.name = name;
        this.surface = surface;
        this.leader = leader;
        characters = new ArrayList<>();
        foods = new ArrayList<>();
    }

    public Space(String name, double surface) {
        this.name = name;
        this.surface = surface;
        characters = new ArrayList<>();
        foods = new ArrayList<>();
    }

    public Space(String name) {
        this.name = name;
        this.characters = new ArrayList<>();
        this.foods = new ArrayList<>();
    }

    public abstract boolean authorized(Character c);

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

    // Add new food
    public void addFood(Food food) {
        this.foods.add(food);
    }

    // Remove food
    public void removeFood(Food food) {
        this.foods.remove(food);
    }

    // True if location istanceof battlefield
    public boolean isBattlefield() {
        if (!(this instanceof Battlefield)) {
            return false;
        } else {
            return true;
        }
    }

    // Add character to the space
    public boolean addCharacter(Character c) {
        if (!authorized(c)){
            return false;
        }
        characters.add(c);
        return true;
    }

    // Remove character from the space
    public void removeCharacter(Character c) {
        characters.remove(c);
    }

    // Show characteristics of the space
    public void showCharacteristics(){
        System.out.println("Name: " + name);
        System.out.println("Surface : " + surface);
        if(!(this instanceof Battlefield)){
            System.out.println("Character leader: " + leader);
        }
        System.out.println("Characters: " + characters.size());
        for(Character c : characters){
            System.out.println("Character: " + c.getName());
        }

        for(Food f : foods){
            System.out.println("Foods available : " + f.getName());
        }
    }

    // Heal character
    public void healCharacter(Character c, int amount){
        if(!(characters.contains(c))){
            return;
        }
        c.beHealed(amount);
    }

    // Faire manger un personnage
    public void eatFood(Character c, Food f){
        if(!(foods.contains(f))){
            return;
        } else if (!(characters.contains(c))) {
            return;
        }
        c.eat(f);
        foods.remove(f);
    }
}
