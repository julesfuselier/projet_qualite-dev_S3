package fr.amu.iut.model;

import fr.amu.iut.model.characters.ClanLeader;
import fr.amu.iut.model.characters.Character;
import fr.amu.iut.model.items.foods.Food;
import fr.amu.iut.model.items.foods.FoodType;
import fr.amu.iut.model.items.foods.FreshnessStatus;
import fr.amu.iut.model.spaces.Space;
import fr.amu.iut.model.characters.Fighter;
import fr.amu.iut.model.characters.Faction;
import fr.amu.iut.model.spaces.Battlefield;
import fr.amu.iut.model.spaces.GallicVillage;
import fr.amu.iut.model.spaces.RomanFortifiedCamp;

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

    public void addLocation(Space space) {
        if (existingLocations == null) existingLocations = new ArrayList<>();
        this.existingLocations.add(space);
    }

    public List<Space> getExistingLocations() {
        return this.existingLocations;
    }

    // --- INTELLIGENCE ARTIFICIELLE (Déplacements autonomes) ---

    public void handleAutonomousMovements() {
        if (existingLocations == null) return;

        System.out.println("Mouvements autonomes des troupes...");

        for (Space currentSpace : existingLocations) {
            // IMPORTANT : On fait une copie de la liste pour pouvoir modifier l'originale sans planter la boucle
            List<Character> charactersSnapshot = new ArrayList<>(currentSpace.getCharacters());

            for (Character c : charactersSnapshot) {
                Space destination = null;

                // Si blessé ou affamé ET sur un champ de bataille, chercher un lieu sûr
                boolean isInjured = c.getHealth().get() < 30;
                boolean isStarving = c.getHunger().get() < 20;

                if ((isInjured || isStarving) && currentSpace.isBattlefield()) {
                    destination = findSafeHaven(c.getFaction());
                }

                // Guerre si combattant (bonne santé) et pas sur un champ de bataille, chercher un champ de bataille
                else if (c instanceof Fighter && !currentSpace.isBattlefield()) {
                    if (!isInjured && !isStarving) {
                        destination = findBattlefield();
                    }
                }

                // Exécution du mouvement si une destination valide est trouvée
                if (destination != null && destination != currentSpace) {
                    moveCharacter(c, currentSpace, destination);
                }
            }
        }
    }

    // Trouve un lieu sûr (Village ou Camp) selon la faction
    private Space findSafeHaven(Faction faction) {
        for (Space s : existingLocations) {
            if (faction == Faction.GAULOIS && s instanceof GallicVillage) return s;
            if (faction == Faction.ROMAIN && s instanceof RomanFortifiedCamp) return s;
        }
        return null;
    }

    // Trouve le premier champ de bataille disponible
    private Space findBattlefield() {
        for (Space s : existingLocations) {
            if (s instanceof Battlefield) return s;
        }
        return null;
    }

    // Déplace physiquement le personnage
    private void moveCharacter(Character c, Space from, Space to) {
        if (to.authorized(c)) {
            from.removeCharacter(c);
            to.addCharacter(c);
            System.out.println("   -> " + c.getName() + " quitte " + from.getName() + " pour " + to.getName());
        }
    }
}