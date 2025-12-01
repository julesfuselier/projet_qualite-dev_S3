package fr.amu.iut.model.spaces;

import fr.amu.iut.model.items.foods.Food;
import fr.amu.iut.model.characters.Character;


import java.util.ArrayList;
import java.util.List;

public abstract class Space {
    private String name;
    private double surface;
    private Character leader;
    private List<Character> characters;
    private List<Food> foods;

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

    public abstract boolean authorized(Character c);

    public boolean addCharacter(Character c) {
        if (!authorized(c)){
            return false;
        }
        characters.add(c);
        return true;
    }

    public void removeCharacter(Character c) {
        characters.remove(c);
    }

    public void showcharacteristics(){
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

    public void healCharacter(Character c, int amount){
        if(!(characters.contains(c))){
            return;
        }
        c.beHealed(amount);
    }

    public  void healFood(Character c, Food f, int amount){
        if(!(foods.contains(f))){
            return;
        } else if (!(characters.contains(c))) {
            return;
        }
        c.eat(amount);
        foods.remove(f);
    }


}
