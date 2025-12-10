package fr.amu.iut.model;

import fr.amu.iut.model.characters.ClanLeader;
import fr.amu.iut.model.characters.Character;
import fr.amu.iut.model.characters.jobs.Druid;
import fr.amu.iut.model.items.foods.Food;
import fr.amu.iut.model.items.foods.FoodFactory;
import fr.amu.iut.model.items.foods.FoodType;
import fr.amu.iut.model.items.foods.FreshnessStatus;
import fr.amu.iut.model.items.potion.MagicPotion;
import fr.amu.iut.model.items.potion.PotionType;
import fr.amu.iut.model.spaces.Space;
import fr.amu.iut.model.characters.Fighter;
import fr.amu.iut.model.characters.Faction;
import fr.amu.iut.model.spaces.Battlefield;
import fr.amu.iut.model.spaces.GallicVillage;
import fr.amu.iut.model.spaces.RomanFortifiedCamp;
import fr.amu.iut.GameConfig;
import fr.amu.iut.util.CharacterSorter;

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
                System.out.println("Lieu : " + loc.getName());
                List<Character> sortedChars = new ArrayList<>(loc.getCharacters());

                // Utilisation du QuickSort ( cf CharacterSorter )
                CharacterSorter.quickSortByName(sortedChars);

                for (Character c : sortedChars) {
                    System.out.println(" - " + c.toString());
                }
            }
        }
    }

    // Tri des personnages par nom (ordre alphabétique) - méthode d'insertion
    private void sortCharactersByName(List<Character> characters) {
        for (int i = 1; i < characters.size(); i++) {
            Character keyChar = characters.get(i);
            String keyName = keyChar.getName();
            int j = i - 1;

            // Déplace les éléments plus grands que la clé vers la droite
            while (j >= 0 && characters.get(j).getName().compareToIgnoreCase(keyName) > 0) {
                characters.set(j + 1, characters.get(j));
                j = j - 1;
            }
            characters.set(j + 1, keyChar);
        }
    }

    // Faites combattre les belligérants et renvoyez les survivants
    public void handleBattles() {
        if (existingLocations == null) return;

        for (Space loc : existingLocations) {
            if (loc.isBattlefield()) {
                loc.resolveCombat();
            }
        }
    }

    // Modifier aléatoirement l'état de certains personnages (faim, potion magique, etc.)
    public void updateRandomCharacterStates() {
        if (existingLocations == null) return;

        for (Space loc : existingLocations) {
            for (Character c : loc.getCharacters()) {

                // Utilisation de GameConfig
                if (random.nextInt(100) < GameConfig.PROBABILITY_HUNGER_EVENT) {
                    int randomIncrease = random.nextInt(GameConfig.MAX_HUNGER_INCREASE) + 1;
                    c.getHunger().add(-randomIncrease);
                }

                if (c.getMagicPotion().get() > 0) {
                    int randomDecrease = random.nextInt(GameConfig.MAX_POTION_DECREASE) + 1;
                    c.getMagicPotion().add(-randomDecrease);
                }
            }
        }
    }

    // Sortir la nourriture du champ de bataille
    public void spawnFood() {
        if (existingLocations == null) return;

        FoodFactory factory = new FoodFactory();
        FoodType[] types = FoodType.values();

        for (Space loc : existingLocations) {
            if (!loc.isBattlefield()) {
                if (random.nextInt(100) < GameConfig.PROBABILITY_FOOD_SPAWN) {
                    loc.addFood(new Food("Test food", 10, true, FreshnessStatus.FRESH, FoodType.FISH));
                    loc.addFood(loc.getFoods().get(random.nextInt(loc.getFoods().size())));
                    System.out.println("Food appeared at : " + loc.getName());
                }
            }
        }
    }

    // Transformer des aliments frais en aliments non frais
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

    // Donner la main au chef de clan
    public void handleClanChiefTurn(ClanLeader chief) {
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

    public void handleAutonomousMovements() {
        if (existingLocations == null) return;

        System.out.println("Mouvements autonomes des troupes...");

        for (Space currentSpace : existingLocations) {
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

    public void handleDruidActivity() {
        if (existingLocations == null) return;

        for ( Space loc : existingLocations) {
            if(!loc.isBattlefield()) {
                for (Character c : loc.getCharacters()) {
                    if (c instanceof Druid) {
                        if(random.nextInt(3) == 0) { // 1 chance sur 3 de fabriquer une potion
                            MagicPotion potion = new MagicPotion(PotionType.BASIC);
                            c.getInventory().addItem(potion);
                            System.out.println("[POTION] Le druide " + c.getName() + " a fabriqué une potion magique de type " + potion.getType());
                        }
                    }
                }
            }
        }
    }
}