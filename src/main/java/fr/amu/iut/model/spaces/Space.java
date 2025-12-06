package fr.amu.iut.model.spaces;

import fr.amu.iut.model.characters.Faction;
import fr.amu.iut.model.items.foods.Food;
import fr.amu.iut.model.characters.Character;
import fr.amu.iut.model.characters.Fighter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import static java.util.Collections.shuffle;

public abstract class Space {
    private final String name;
    private double surface;
    private Character leader;
    private final List<Character> characters;
    private final List<Food> foods;
    private Random random;

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
        System.out.println("Nom: " + name);
        System.out.println("Surface : " + surface);
        if(!(this instanceof Battlefield)){
            System.out.println("Personnage principal: " + leader);
        }
        System.out.println("Personnages: " + characters.size());
        for(Character c : characters){
            System.out.println("Personnages: " + c.getName());
        }

        for(Food f : foods){
            System.out.println("Nourriture disponible : " + f.getName());
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

    /*
     * Résout les combats entre les personnages de factions opposées présents dans cet espace.
     * Les combats sont organisés en duels aléatoires entre membres des deux factions.
     * Les personnages morts sont retirés de l'espace après les combats.
     */
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

        // Duel aléatoire
        shuffle(teamGaulois);
        shuffle(teamRomain);

        // Former des duos et les faire se battre
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

    /**
     * Méthode helper pour retirer les personnages dont la santé est inférieure ou égale à 0.
     */
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
