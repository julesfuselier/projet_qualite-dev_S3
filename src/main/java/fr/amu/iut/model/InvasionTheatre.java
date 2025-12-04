package fr.amu.iut.model;

import fr.amu.iut.model.characters.ClanLeader;
import fr.amu.iut.model.characters.Character;
import fr.amu.iut.model.items.foods.Food;
import fr.amu.iut.model.items.foods.FoodType;
import fr.amu.iut.model.items.foods.FreshnessStatus;
import fr.amu.iut.model.spaces.Space;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class InvasionTheatre {

    private String name;
    private int maxLocations;
    private List<Space> existingLocations;
    private List<ClanLeader> clanChiefs;
    private Random random = new Random();

    // Constructor
    public InvasionTheatre(String name, int maxLocations) {
        this.name = name;
        this.maxLocations = maxLocations;
        this.existingLocations = new ArrayList<>();
        this.clanChiefs = new ArrayList<>();
    }

    // Displays the locations in the theatre
    public void showLocations() {
        if (existingLocations != null) {
            System.out.println("Theatre venues " + name);
            for (Space location : existingLocations) {
                System.out.println(location);
            }
        }
    }

    // Displays the total number of characters present
    public void showTotalCharacterCount() {
        int total = 0;
        if (existingLocations != null) {
            for (Space loc : existingLocations) {
                total += loc.getCharacters().size();
            }
        }
        System.out.println("Total number of characters in play : " + total);
    }

    // Displays characters from all locations
    public void showAllCharacters() {
        if (existingLocations != null) {
            for (Space loc : existingLocations) {
                System.out.println("In the place: " + loc.getName());
                for (Character c : loc.getCharacters()) {
                    System.out.println(" - " + c.toString());
                }
            }
        }
    }

    // Make the belligerents fight and send back the survivors
    public void handleBattles() {
        if (existingLocations == null) return;

        for (Space loc : existingLocations) {
            if (loc.isBattlefield()) {
                loc.resolveCombat();
            }
        }
    }

    // Randomly alter the status of certain characters (hunger, magic potion, etc.)
    public void updateRandomCharacterStates() {
        if (existingLocations == null) return;

        final int MAX_HUNGER_INCREASE = 5;
        final int MAX_POTION_DECREASE = 2;

        for (Space loc : existingLocations) {
            for (Character c : loc.getCharacters()) {

                // Gestion de la Faim
                if (random.nextInt(100) < 10) {
                    int randomIncrease = random.nextInt(MAX_HUNGER_INCREASE) + 1;
                    c.getHunger().add(-randomIncrease);
                }

                // Gestion de l'effet de Potion
                if (c.getMagicPotion().get() > 0) {
                    int randomDecrease = random.nextInt(MAX_POTION_DECREASE) + 1;
                    c.getMagicPotion().add(-randomDecrease);
                }
            }
        }
    }

    // Bringing food out of the battlefield
    public void spawnFood() {
        if (existingLocations == null) return;

        for (Space loc : existingLocations) {
            // Food does not appear on battlefields.
            if (!loc.isBattlefield()) {
                // 20% chance of a wild boar or fruit appearing
                if (random.nextInt(100) < 20) {
                    loc.addFood(new Food("Test food", 10, true, FreshnessStatus.FRESH, FoodType.FISH));
                    loc.addFood(loc.getFoods().get(random.nextInt(loc.getFoods().size())));
                    System.out.println("Food appeared at : " + loc.getName());
                }
            }
        }
    }

    // Changing fresh food into non-fresh food
    public void updateFoodFreshness() {
        if (existingLocations == null) return;

        for (Space loc : existingLocations) {
            for (Food food : loc.getFoods()) {
                if (food.isFresh()) {
                    food.setFreshnessStatus(FreshnessStatus.valueOf("NOT_FRESH"));
                }
            }
        }
    }

    // Hand over to a clan chief
    private void handleClanChiefTurn(ClanLeader chief) {
        System.out.println("It's the chef's turn : " + chief.getName());
        // TODO : Ajouter une fonction TakeTurn pour réellement donner la main au chef de clan
    }

    public void addLocation(Space location) {
        this.existingLocations.add(location);
    }
}