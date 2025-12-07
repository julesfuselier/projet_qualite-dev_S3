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

    // Constructeur
    public InvasionTheatre(String name, int maxLocations) {
        this.name = name;
        this.maxLocations = maxLocations;
        this.existingLocations = new ArrayList<>();
        this.clanChiefs = new ArrayList<>();
    }

    // Affiche les emplacements dans le théâtre
    public void showLocations() {
        if (existingLocations != null) {
            System.out.println("Theatre venues " + name);
            for (Space location : existingLocations) {
                System.out.println(location);
            }
        }
    }

    // Affiche le nombre total de caractères présents
    public void showTotalCharacterCount() {
        int total = 0;
        if (existingLocations != null) {
            for (Space loc : existingLocations) {
                total += loc.getCharacters().size();
            }
        }
        System.out.println("Total number of characters in play : " + total);
    }

    // Affiche les caractères de tous les emplacements
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

    // Faites combattre les belligérants et renvoyez les survivants
    private void handleBattles() {
        if (existingLocations == null) return;

        for (Space loc : existingLocations) {
            if (loc.isBattlefield()) {
                loc.resolveCombat();
            }
        }
    }

    // Modifier aléatoirement l'état de certains personnages (faim, potion magique, etc.)
    private void updateRandomCharacterStates() {
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

    // Sortir la nourriture du champ de bataille
    private void spawnFood() {
        if (existingLocations == null) return;

        for (Space loc : existingLocations) {
            // La nourriture n'apparaît pas sur les champs de bataille.
            if (!loc.isBattlefield()) {
                // 20 % de chances qu'un sanglier ou un fruit apparaisse
                if (random.nextInt(100) < 20) {
                    loc.addFood(new Food("Test food", 10, true, FreshnessStatus.FRESH, FoodType.FISH));
                    loc.addFood(loc.getFoods().get(random.nextInt(loc.getFoods().size())));
                    System.out.println("Food appeared at : " + loc.getName());
                }
            }
        }
    }

    // Transformer des aliments frais en aliments non frais
    private void updateFoodFreshness() {
        if (existingLocations == null) return;

        for (Space loc : existingLocations) {
            for (Food food : loc.getFoods()) {
                if (food.isFresh()) {
                    food.setFreshnessStatus(FreshnessStatus.valueOf("NOT_FRESH"));
                }
            }
        }
    }

    // Donner la main au chef de clan
    private void handleClanChiefTurn(ClanLeader chief) {
        System.out.println("It's the chef's turn : " + chief.getName());
        // TODO : Ajouter une fonction TakeTurn pour réellement donner la main au chef de clan
    }
}