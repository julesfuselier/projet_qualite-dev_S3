package fr.amu.iut.model;

import fr.amu.iut.model.characters.ClanLeader;
import fr.amu.iut.model.characters.Character;
import fr.amu.iut.model.characters.jobs.Druid;
import fr.amu.iut.model.exceptions.InsufficientIngredientsException;
import fr.amu.iut.model.items.foods.Food;
import fr.amu.iut.model.items.foods.FoodFactory;
import fr.amu.iut.model.items.foods.FoodType;
import fr.amu.iut.model.items.foods.FreshnessStatus;
import fr.amu.iut.model.items.potion.PotionType;
import fr.amu.iut.model.spaces.Space;
import fr.amu.iut.model.characters.Fighter;
import fr.amu.iut.model.characters.Faction;
import fr.amu.iut.model.spaces.Battlefield;
import fr.amu.iut.model.spaces.GallicVillage;
import fr.amu.iut.model.spaces.RomanFortifiedCamp;
import fr.amu.iut.GameConfig;
import fr.amu.iut.util.CharacterSorter;
import fr.amu.iut.util.GameEvents;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * La classe InvasionTheatre représente un théâtre d'invasion où se déroulent des batailles
 * entre différentes factions. Elle gère les emplacements, les personnages, les combats,
 * la nourriture et les chefs de clan.
 */
public class InvasionTheatre {

    private String name;
    private int maxLocations;
    private List<Space> existingLocations;
    private List<ClanLeader> clanChiefs;
    private Random random = new Random();


    /**
     * Constructeur de la classe InvasionTheatre.
     * @param name Le nom du théâtre d'invasion.
     * @param maxLocations Le nombre maximum d'emplacements dans le théâtre.
     */
    public InvasionTheatre(String name, int maxLocations) {
        this.name = name;
        this.maxLocations = maxLocations;
        this.existingLocations = new ArrayList<>();
        this.clanChiefs = new ArrayList<>();
    }

    /**
     * Affiche les emplacements existants dans le théâtre d'invasion.
     */
    public void showLocations() {
        if (existingLocations != null) {
            GameEvents.log("Emplacements du théâtre d'invasion " + name);
            for (Space location : existingLocations) {
                System.out.println(location);
            }
        }
    }

    /**
     * Affiche le nombre total de personnages dans tous les emplacements.
     */
    public void showTotalCharacterCount() {
        int total = 0;
        if (existingLocations != null) {
            for (Space loc : existingLocations) {
                total += loc.getCharacters().size();
            }
        }
        GameEvents.log("Nombre total de personnages en jeu : " + total);
    }

    /**
     * Affiche tous les personnages présents dans chaque emplacement, triés par nom.
     */
    public void showAllCharacters() {
        if (existingLocations != null) {
            for (Space loc : existingLocations) {
                GameEvents.log("Lieu : " + loc.getName());
                List<Character> sortedChars = new ArrayList<>(loc.getCharacters());

                // Utilisation du QuickSort ( cf CharacterSorter )
                CharacterSorter.quickSortByName(sortedChars);

                for (Character c : sortedChars) {
                    GameEvents.log(" - " + c.toString());
                }
            }
        }
    }

    /**
     * Gère les batailles dans tous les emplacements.
     */
    public void handleBattles() {
        if (existingLocations == null) return;

        for (Space loc : existingLocations) {
            if (loc.isBattlefield()) {
                loc.resolveCombat();
            }
        }
    }

    /**
     * Met à jour les états aléatoires des personnages, tels que la faim et les potions magiques.
     */
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

    /**
     * Fait apparaître de la nourriture dans les emplacements non-bataille selon une probabilité définie.
     */
    public void spawnFood() {
        if (existingLocations == null) return;
        for (Space loc : existingLocations) {
            if (!loc.isBattlefield()) {
                if (random.nextInt(100) < GameConfig.PROBABILITY_FOOD_SPAWN) {
                    loc.addFood(new Food("Poisson", 10, true, FreshnessStatus.FRESH, FoodType.FISH));
                    loc.addFood(loc.getFoods().get(random.nextInt(loc.getFoods().size())));
                    GameEvents.log("De la nourriture est apparu à : " + loc.getName());
                }
            }
        }
    }

    /**
     * Met à jour la fraîcheur de la nourriture dans tous les emplacements.
     * La nourriture fraîche devient non fraîche.
     */
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
        GameEvents.log("C'est le tour du chef : " + chief.getName());
        // TODO : Ajouter une fonction TakeTurn pour réellement donner la main au chef de clan
    }

    /**
     * Ajoute un emplacement au théâtre d'invasion.
     * @param space L'emplacement à ajouter.
     */
    public void addLocation(Space space) {
        if (existingLocations == null) existingLocations = new ArrayList<>();
        this.existingLocations.add(space);
    }

    /**
     * Getter pour les emplacements existants dans le théâtre d'invasion.
     * @return La liste des emplacements existants.
     */
    public List<Space> getExistingLocations() {
        return this.existingLocations;
    }

    /**
     * Gère les mouvements autonomes des personnages en fonction de leur état et de leur faction.
     * Les personnages blessés ou affamés cherchent un lieu sûr, tandis que les combattants en bonne santé cherchent des champs de bataille.
     */
    public void handleAutonomousMovements() {
        if (existingLocations == null) return;

        GameEvents.log("Mouvements autonomes des troupes...");

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

    /**
     * Trouve un lieu sûr pour une faction donnée.
     * @param faction La faction du personnage cherchant un lieu sûr.
     * @return L'emplacement sûr correspondant à la faction, ou null s'il n'en existe pas.
     */
    private Space findSafeHaven(Faction faction) {
        for (Space s : existingLocations) {
            if (faction == Faction.GAULOIS && s instanceof GallicVillage) return s;
            if (faction == Faction.ROMAIN && s instanceof RomanFortifiedCamp) return s;
        }
        return null;
    }

    /**
     * Trouve un champ de bataille dans les emplacements existants.
     * @return Le champ de bataille trouvé, ou null s'il n'en existe pas.
     */
    private Space findBattlefield() {
        for (Space s : existingLocations) {
            if (s instanceof Battlefield) return s;
        }
        return null;
    }

    /**
     * Déplace un personnage d'un emplacement à un autre s'il est autorisé à le faire.
     * @param c Le personnage à déplacer.
     * @param from L'emplacement de départ.
     * @param to L'emplacement de destination.
     */
    private void moveCharacter(Character c, Space from, Space to) {
        if (to.authorized(c)) {
            try {
                to.addCharacter(c);
                from.removeCharacter(c);
                GameEvents.log("   -> " + c.getName() + " quitte " + from.getName() + " pour " + to.getName());
            } catch (Exception e) {
                GameEvents.log("Erreur de mouvement : " + e.getMessage());
            }
        }
    }

    /**
     * Gère l'activité des druides dans les emplacements non-bataille.
     * Les druides ont une chance de fabriquer une potion magique à chaque appel.
     */
    public void handleDruidActivity() {
        if (existingLocations == null) return;

        for ( Space loc : existingLocations) {
            if(!loc.isBattlefield()) {
                for (Character c : loc.getCharacters()) {
                    if (c instanceof Druid druid) {
                        if(random.nextInt(3) == 0) {
                            try {
                                druid.craftMagicPotion(PotionType.BASIC);
                                GameEvents.log("[POTION] Le druide " + c.getName() + " a fabriqué une potion !");
                            } catch (InsufficientIngredientsException e) {
                                GameEvents.log("[POTION] Le druide " + c.getName() + " n'a pas pu fabriquer de potion : " + e.getMessage());
                            }
                        }
                    }
                }
            }
        }
    }
}