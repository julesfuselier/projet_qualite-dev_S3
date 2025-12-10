package fr.amu.iut.model.spaces;

import fr.amu.iut.model.characters.Character;
import fr.amu.iut.model.characters.Faction;
import fr.amu.iut.model.characters.Fighter;
import fr.amu.iut.model.items.foods.Food;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static java.util.Collections.shuffle;

public abstract sealed class Space permits Battlefield, Enclosure, GallicVillage, GalloRomanVillage, RomanCity, RomanFortifiedCamp {
    private final String name;
    private double surface;
    private Character leader;

    private final Set<Character> characters;

    private final List<Food> foods;
    private Random random;

    public Space(String name, double surface, Character leader) {
        this.name = name;
        this.surface = surface;
        this.leader = leader;
        this.characters = new HashSet<>();
        this.foods = new ArrayList<>();
    }

    public Space(String name, double surface) {
        this.name = name;
        this.surface = surface;
        this.characters = new HashSet<>();
        this.foods = new ArrayList<>();
    }

    public Space(String name) {
        this.name = name;
        this.characters = new HashSet<>();
        this.foods = new ArrayList<>();
    }

    public abstract boolean authorized(Character c);

    public String getName() {
        return name;
    }

    public Set<Character> getCharacters() {
        return characters;
    }

    public List<Food> getFoods() {
        return foods;
    }

    public void addFood(Food food) {
        this.foods.add(food);
    }

    public void removeFood(Food food) {
        this.foods.remove(food);
    }

    public boolean isBattlefield() {
        return this instanceof Battlefield;
    }

    public boolean addCharacter(Character c) {
        if (!authorized(c)){
            return false;
        }
        return characters.add(c);
    }

    public void removeCharacter(Character c) {
        characters.remove(c);
    }

    public void showCharacteristics(){
        System.out.println("Nom: " + name);
        System.out.println("Surface : " + surface);
        if(!(this instanceof Battlefield)){
            System.out.println("Personnage principal: " + leader);
        }
        System.out.println("Personnages: " + characters.size());
        for(Character c : characters){
            System.out.println(" - " + c.getName());
        }

        for(Food f : foods){
            System.out.println("Nourriture disponible : " + f.getName());
        }
    }

    public void healCharacter(Character c, int amount){
        if(!(characters.contains(c))){
            return;
        }
        c.beHealed(amount);
    }

    public void eatFood(Character c, Food f){
        if(!(foods.contains(f))){
            return;
        } else if (!(characters.contains(c))) {
            return;
        }
        c.eat(f);
        foods.remove(f);
    }

    public void resolveCombat() {
        if (!isBattlefield() || getCharacters().size() < 2) {
            return;
        }

        List<Character> teamGaulois = new ArrayList<>();
        List<Character> teamRomain = new ArrayList<>();

        for (Character c : getCharacters()) {
            if (c instanceof Fighter) {
                if (c.getFaction() == Faction.GAULOIS) {
                    teamGaulois.add(c);
                } else if (c.getFaction() == Faction.ROMAIN) {
                    teamRomain.add(c);
                }
            }
        }

        if (teamGaulois.isEmpty() || teamRomain.isEmpty()) {
            return;
        }

        System.out.println( "--- BASTON GÉNÉRALE à " + getName() + " ---");

        shuffle(teamGaulois);
        shuffle(teamRomain);

        int fightsCount = Math.min(teamGaulois.size(), teamRomain.size());

        for (int i = 0; i < fightsCount; i++) {
            Character gaulois = teamGaulois.get(i);
            Character romain = teamRomain.get(i);

            if (!gaulois.isDead() && !romain.isDead()) {
                System.out.println("   Duel : " + gaulois.getName() + " VS " + romain.getName());

                ((Fighter) gaulois).fight(romain);

                if (!romain.isDead()) {
                    ((Fighter) romain).fight(gaulois);
                }
            }
        }
        removeDeadCharacters();
    }

    private void removeDeadCharacters() {
        getCharacters().removeIf(c -> {
            if (c.isDead()) {
                System.out.println(c.getName() + " est tombé au combat à " + getName() + " !");
                return true;
            }
            return false;
        });
    }
}