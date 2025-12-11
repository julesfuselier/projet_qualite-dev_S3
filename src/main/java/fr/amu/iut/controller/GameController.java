package fr.amu.iut.controller;

import fr.amu.iut.model.InvasionTheatre;
import fr.amu.iut.model.characters.Character;
import fr.amu.iut.model.characters.CharacterFactory;
import fr.amu.iut.model.characters.ClanLeader;
import fr.amu.iut.model.characters.Faction;
import fr.amu.iut.model.characters.jobs.Druid;
import fr.amu.iut.model.items.potion.MagicPotion;
import fr.amu.iut.model.spaces.*;
import fr.amu.iut.view.ConsoleView;

import java.util.ArrayList;
import java.util.List;

/**
 * Contrôleur principal de la simulation de jeu.
 * Gère la boucle de jeu, les interactions utilisateur et la logique principale.
 */
public class GameController {

    private InvasionTheatre theatre;
    private final CharacterFactory characterFactory;
    private final ConsoleView view;

    private int turnCount;
    private volatile boolean autoModeRunning;

    public GameController() {
        this.characterFactory = new CharacterFactory();
        this.view = new ConsoleView();
        this.turnCount = 0;
        this.autoModeRunning = false;
    }

    /**
     * Démarre la simulation de jeu.
     */
    public void start() {
        initSimulation();
        runGameLoop();
    }

    /**
     * Boucle principale du jeu, gérant les tours et les interactions utilisateur.
     */
    private void runGameLoop() {
        boolean running = true;
        while (running) {
            running = handleMainMenuChoice();
        }
        view.displayMessage("Fin de la simulation. Au revoir !");
    }

    /**
     * Gère le menu principal et les choix de l'utilisateur.
     * @return true si la simulation doit continuer, false pour quitter.
     */
    private boolean handleMainMenuChoice() {
        view.showMainMenu(turnCount);
        int choice = view.getIntInput(1, 5);

        return switch (choice) {
            case 1 -> {
                runNextTurn();
                yield true;
            }
            case 2 -> {
                handleClanLeaderActions();
                yield true;
            }
            case 3 -> {
                view.displayTitle("ÉTAT DU MONDE");
                theatre.showAllCharacters();
                yield true;
            }
            case 4 -> {
                startAutoSimulation();
                yield true;
            }
            case 5 -> false;
            default -> {
                view.displayError("Choix invalide.");
                yield true;
            }
        };
    }

    /**
     * Démarre le mode de simulation automatique.
     */
    private void startAutoSimulation() {
        autoModeRunning = true;

        Thread gameThread = new Thread(() -> {
            view.displaySuccess(">>> Démarrage du Mode Auto (3s / tour) <<<");
            while (autoModeRunning) {
                try {
                    runNextTurn();
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            view.displaySuccess(">>> Arrêt du Mode Auto <<<");
        });

        gameThread.start();

        view.waitForEnter("Appuyez sur [ENTRÉE] pour revenir au menu...");

        autoModeRunning = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            view.displayError("Erreur lors de l'arrêt du thread.");
        }
    }

    /**
     * Exécute les actions d'un tour de jeu.
     */
    private void runNextTurn() {
        turnCount++;
        view.displayTitle("DÉROULEMENT DU TOUR " + turnCount);

        theatre.handleAutonomousMovements();

        view.displayMessage("⚔ Gestion des batailles...");
        theatre.handleBattles();

        view.displayMessage("Mise à jour des états (Faim, Potions)...");
        theatre.updateRandomCharacterStates();

        theatre.handleDruidActivity();

        view.displayMessage("Apparition de nourriture...");
        theatre.spawnFood();

        view.displayMessage("Vérification de la fraîcheur des aliments...");
        theatre.updateFoodFreshness();

        view.displaySuccess(">>> FIN DU TOUR <<<");
    }

    /**
     * Gère les actions spécifiques aux Chefs de Clan.
     */
    private void handleClanLeaderActions() {
        List<ClanLeader> leaders = new ArrayList<>();
        if (theatre.getExistingLocations() != null) {
            for (Space space : theatre.getExistingLocations()) {
                for (Character c : space.getCharacters()) {
                    if (c instanceof ClanLeader) {
                        leaders.add((ClanLeader) c);
                    }
                }
            }
        }

        if (leaders.isEmpty()) {
            view.displayError("Aucun Chef de Clan trouvé dans le monde !");
            return;
        }

        ClanLeader selectedLeader = view.selectFromList(leaders, "--- SÉLECTION DU CHEF ---");
        if (selectedLeader == null) return;

        boolean acting = true;
        while (acting) {
            view.showClanLeaderMenu(selectedLeader.getName());
            int action = view.getIntInput(1, 5);

            Space currentSpace = findSpaceOfCharacter(selectedLeader);
            if (currentSpace == null) {
                view.displayError("Le chef est perdu dans le vide intersidéral.");
                break;
            }

            switch (action) {
                case 1 -> selectedLeader.examineLocation();
                case 2 -> {
                    List<Character> targets = new ArrayList<>(currentSpace.getCharacters());
                    Character target = view.selectFromList(targets, "Qui soigner ?");
                    if (target != null) {
                        selectedLeader.healCharacterInVillage(target, 20);
                    }
                }
                case 3 -> {
                    List<Character> targets = new ArrayList<>(currentSpace.getCharacters());
                    Character target = view.selectFromList(targets, "Qui nourrir ?");
                    if (target != null) {
                        if (!currentSpace.getFoods().isEmpty()) {
                            selectedLeader.feedCharacterInVillage(target, currentSpace.getFoods().get(0));
                        } else {
                            view.displayError("Il n'y a plus de nourriture ici !");
                        }
                    }
                }
                case 4 -> handlePotionDistribution(currentSpace);
                case 5 -> acting = false;
            }
        }
    }

    /**
     * Gère la distribution de potions magiques par un Druide.
     * @param space L'espace où se trouve le Druide.
     */
    private void handlePotionDistribution(Space space) {
        Character druidWithPotion = null;
        MagicPotion potionFound = null;

        for (Character c : space.getCharacters()) {
            if (c instanceof Druid) {
                for (Object item : c.getInventory().getItems()) {
                    if (item instanceof MagicPotion) {
                        druidWithPotion = c;
                        potionFound = (MagicPotion) item;
                        break;
                    }
                }
            }
            if (druidWithPotion != null) break;
        }

        if (druidWithPotion != null && potionFound != null) {
            view.displayMessage("Le Druide " + druidWithPotion.getName() + " a une potion !");

            List<Character> targets = new ArrayList<>(space.getCharacters());
            Character target = view.selectFromList(targets, "À qui la donner ?");

            if (target != null) {
                druidWithPotion.getInventory().removeItem(potionFound);
                target.drinkMagicPotion(potionFound, false);
            }
        } else {
            view.displayError("Pas de Druide avec une potion disponible ici !");
        }
    }

    /**
     * Initialise la simulation en mode démo ou manuel.
     */
    private void initSimulation() {
        view.displayTitle("BIENVENUE DANS LA SIMULATION");
        theatre = new InvasionTheatre("Armorique", 20);

        view.displayMessage("Comment voulez-vous commencer ?");
        view.displayMessage("1. Mode DÉMO (Configuration automatique)");
        view.displayMessage("2. Mode MANUEL (Créer ses propres lieux et persos)");
        int choice = view.getIntInput(1, 2);

        if (choice == 1) {
            initDemo();
        } else {
            initPlaces();
            initCharacters();
        }
    }

    /**
     * Initialise une démo étendue avec des lieux et personnages prédéfinis.
     */
    private void initDemo() {
        view.displaySuccess("Chargement de la démo étendue...");

        ClanLeader abraracourcix = new ClanLeader("Abraracourcix", 'M', 50, null);
        GallicVillage village = new GallicVillage("Village des Irréductibles", 1000, abraracourcix);

        RomanFortifiedCamp camp = new RomanFortifiedCamp("Babaorum", 1000, null);
        Battlefield battlefield = new Battlefield("Plaine des baffes", 2000);
        RomanCity city = new RomanCity("Lutetia", 5000, null);
        GalloRomanVillage serum = new GalloRomanVillage("Sérum", 800, null);
        Enclosure enclosure = new Enclosure("Forêt des Carnutes", 2000);

        abraracourcix.setLocation(village);
        village.addCharacter(abraracourcix);

        theatre.addLocation(village);
        theatre.addLocation(camp);
        theatre.addLocation(battlefield);
        theatre.addLocation(city);
        theatre.addLocation(serum);
        theatre.addLocation(enclosure);

        try {
            village.addCharacter(characterFactory.createCharacter(Faction.GAULOIS, "druide", "Panoramix"));
            village.addCharacter(characterFactory.createCharacter(Faction.GAULOIS, "forgeron", "Cétautomatix"));
            village.addCharacter(characterFactory.createCharacter(Faction.GAULOIS, "marchand", "Ordralphabétix"));
            village.addCharacter(characterFactory.createCharacter(Faction.GAULOIS, "aubergiste", "Bonemine"));
            village.addCharacter(characterFactory.createCharacter(Faction.GAULOIS, "druide", "Panoramix Jr"));

            camp.addCharacter(characterFactory.createCharacter(Faction.ROMAIN, "général", "Caius Bonus"));
            camp.addCharacter(characterFactory.createCharacter(Faction.ROMAIN, "préfet", "Brutus"));
            camp.addCharacter(characterFactory.createCharacter(Faction.ROMAIN, "legionnaire", "Minus"));
            camp.addCharacter(characterFactory.createCharacter(Faction.ROMAIN, "legionnaire", "Chorus"));
            camp.addCharacter(characterFactory.createCharacter(Faction.ROMAIN, "legionnaire", "Motus"));

            city.addCharacter(characterFactory.createCharacter(Faction.ROMAIN, "marchand", "Technocratus"));
            city.addCharacter(characterFactory.createCharacter(Faction.ROMAIN, "aubergiste", "Tifus"));

            serum.addCharacter(characterFactory.createCharacter(Faction.GAULOIS, "marchand", "Pneumatix"));
            serum.addCharacter(characterFactory.createCharacter(Faction.ROMAIN, "legionnaire", "Tikedbus"));

            view.displaySuccess("Monde généré avec succès ! (6 Lieux créés)");
            theatre.showTotalCharacterCount();

        } catch (Exception e) {
            view.displayError("Erreur lors de la démo : " + e.getMessage());
        }
    }

    /**
     * Initialise les lieux en mode manuel.
     */
    private void initPlaces() {
        boolean running = true;
        while (running) {
            view.displayTitle("Création des Lieux");
            view.displayMessage("1. Ajouter un Champ de bataille");
            view.displayMessage("2. Ajouter un Village gaulois");
            view.displayMessage("3. Ajouter un Camp romain");
            view.displayMessage("4. Terminer");

            int choice = view.getIntInput(1, 4);

            if (choice == 4) {
                running = false;
                continue;
            }

            view.displayMessage("Nom du lieu :");
            String name = "Lieu " + (theatre.getExistingLocations() != null ? theatre.getExistingLocations().size() + 1 : 1);

            Space newSpace = null;
            switch (choice) {
                case 1 -> newSpace = new Battlefield(name, 1000);
                case 2 -> {
                    ClanLeader chef = new ClanLeader("Chef", 'M', 40, null);
                    newSpace = new GallicVillage(name, 1000, chef);
                    newSpace.addCharacter(chef);
                }
                case 3 -> newSpace = new RomanFortifiedCamp(name, 1000, null);
            }

            if (newSpace != null) {
                theatre.addLocation(newSpace);
                view.displaySuccess(newSpace.getName() + " ajouté !");
            }
        }
    }

    /**
     * Initialise les personnages en mode manuel.
     */
    private void initCharacters() {
        if (theatre.getExistingLocations() == null || theatre.getExistingLocations().isEmpty()) {
            view.displayError("Aucun lieu disponible. Impossible de créer des personnages.");
            return;
        }

        boolean running = true;
        while (running) {
            view.displayTitle("Création des Personnages");
            view.displayMessage("1. Créer un Gaulois");
            view.displayMessage("2. Créer un Romain");
            view.displayMessage("3. Terminer");

            int choice = view.getIntInput(1, 3);
            if (choice == 3) {
                running = false;
                continue;
            }

            Faction faction = (choice == 1) ? Faction.GAULOIS : Faction.ROMAIN;
            String role = (faction == Faction.GAULOIS) ? "druide" : "legionnaire"; // Simplifié pour l'exemple manuel

            try {
                Character newChar = characterFactory.createCharacter(faction, role, "Personnage " + System.currentTimeMillis() % 1000);

                Space destination = view.selectFromList(theatre.getExistingLocations(), "Où placer ce personnage ?");

                if (destination != null) {
                    if (destination.addCharacter(newChar)) {
                        view.displaySuccess(newChar.getName() + " a rejoint " + destination.getName());
                    } else {
                        view.displayError("Placement impossible ici (Règles d'autorisation).");
                    }
                }
            } catch (Exception e) {
                view.displayError("Erreur : " + e.getMessage());
            }
        }
    }

    /**
     * Trouve l'espace où se trouve un personnage donné.
     * @param c Le personnage recherché.
     * @return L'espace contenant le personnage, ou null s'il n'est pas trouvé.
     */
    private Space findSpaceOfCharacter(Character c) {
        if (theatre.getExistingLocations() == null) return null;
        for (Space s : theatre.getExistingLocations()) {
            if (s.getCharacters().contains(c)) return s;
        }
        return null;
    }
}