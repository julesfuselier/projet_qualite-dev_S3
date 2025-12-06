package fr.amu.iut;

import fr.amu.iut.model.InvasionTheatre;
import fr.amu.iut.model.characters.Character;
import fr.amu.iut.model.characters.CharacterFactory;
import fr.amu.iut.model.characters.ClanLeader;
import fr.amu.iut.model.characters.Faction;
import fr.amu.iut.model.items.foods.Food;
import fr.amu.iut.model.spaces.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainApp {

    // --- Couleurs pour le terminal ---
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String BLUE = "\u001B[34m";
    private static final String YELLOW = "\u001B[33m";

    private static InvasionTheatre theatre;
    private static final CharacterFactory characterFactory = new CharacterFactory();
    private static final Scanner scanner = new Scanner(System.in);
    private static int turnCount = 0;

    public static void main(String[] args) {
        initSimulation();

        boolean running = true;
        while (running) {
            printMenu();
            int choice = getIntInput(1, 4);
            switch (choice) {
                case 1:
                    runNextTurn(); // Lancement du tour temporel
                    break;
                case 2:
                    handleClanLeaderActions(); // Actions du joueur
                    break;
                case 3:
                    System.out.println(BLUE + "\n--- ÉTAT DU MONDE ---" + RESET);
                    theatre.showAllCharacters();
                    break;
                case 4:
                    running = false;
                    System.out.println("Fin de la simulation. Au revoir !");
                    break;
                default:
                    System.out.println(RED + "Choix invalide." + RESET);
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n" + BLUE + "╔════════ MENU PRINCIPAL (Tour " + turnCount + ") ════════╗" + RESET);
        System.out.println("1. ⏳ Lancer la simulation temporelle (Combats, Faim...)");
        System.out.println("2. 👑 Actions de Chef de Clan");
        System.out.println("3. 🌍 Afficher l'état du monde");
        System.out.println("4. ❌ Quitter");
        System.out.print("Votre choix : ");
    }

    /**
     * Exécute les événements automatiques du tour (Sujet PDF Section 7)
     */
    private static void runNextTurn() {
        turnCount++;
        System.out.println(YELLOW + "\n>>> DÉROULEMENT DU TOUR " + turnCount + " <<<" + RESET);

        System.out.println("⚔️  Gestion des batailles...");
        theatre.handleBattles(); //

        System.out.println("✨ Mise à jour des états (Faim, Potion)...");
        theatre.updateRandomCharacterStates(); //

        System.out.println("🍎 Apparition de nourriture...");
        theatre.spawnFood(); //

        System.out.println("🤢 Vérification de la fraîcheur des aliments...");
        theatre.updateFoodFreshness(); //

        System.out.println(YELLOW + ">>> FIN DES ÉVÉNEMENTS AUTOMATIQUES <<<" + RESET);
    }

    /**
     * Gère l'interaction avec les Chefs de Clan (Sujet PDF Section 6)
     */
    private static void handleClanLeaderActions() {
        // 1. Trouver les chefs disponibles
        List<ClanLeader> leaders = new ArrayList<>();
        for (Space space : theatre.getExistingLocations()) {
            for (Character c : space.getCharacters()) {
                if (c instanceof ClanLeader) {
                    leaders.add((ClanLeader) c);
                }
            }
        }

        if (leaders.isEmpty()) {
            System.out.println(RED + "Aucun Chef de Clan trouvé dans le monde !" + RESET);
            return;
        }

        // 2. Choisir un chef
        System.out.println("\n--- SÉLECTION DU CHEF ---");
        for (int i = 0; i < leaders.size(); i++) {
            System.out.println((i + 1) + ". " + leaders.get(i).getName());
        }
        System.out.println((leaders.size() + 1) + ". Retour");

        int choice = getIntInput(1, leaders.size() + 1);
        if (choice > leaders.size()) return;

        ClanLeader selectedLeader = leaders.get(choice - 1);
        boolean acting = true;

        // 3. Menu d'actions du chef
        while (acting) {
            System.out.println(GREEN + "\n--- ACTION : " + selectedLeader.getName() + " ---" + RESET);
            System.out.println("1. Examiner le lieu");
            System.out.println("2. Soigner un personnage");
            System.out.println("3. Nourrir un personnage");
            System.out.println("4. Retour au menu principal");

            int action = getIntInput(1, 4);
            Space currentSpace = findSpaceOfCharacter(selectedLeader); // Helper pour trouver où est le chef

            if (currentSpace == null) {
                System.out.println(RED + "Le chef est perdu dans le vide intersidéral." + RESET);
                break;
            }

            switch (action) {
                case 1:
                    selectedLeader.examineLocation(); //
                    break;
                case 2: // Soigner
                    Character targetToHeal = selectCharacterInSpace(currentSpace);
                    if (targetToHeal != null) {
                        selectedLeader.healCharacterInVillage(targetToHeal, 20);
                    }
                    break;
                case 3: // Nourrir
                    Character targetToFeed = selectCharacterInSpace(currentSpace);
                    if (targetToFeed != null && !currentSpace.getFoods().isEmpty()) {
                        Food food = currentSpace.getFoods().get(0); // Prend le premier aliment dispo
                        selectedLeader.feedCharacterInVillage(targetToFeed, food);
                    } else {
                        System.out.println("Pas de nourriture ou pas de personnage !");
                    }
                    break;
                case 4:
                    acting = false;
                    break;
            }
        }
    }


    private static Space findSpaceOfCharacter(Character c) {
        for (Space s : theatre.getExistingLocations()) {
            if (s.getCharacters().contains(c)) return s;
        }
        return null;
    }

    private static Character selectCharacterInSpace(Space space) {
        if (space.getCharacters().isEmpty()) {
            System.out.println("Personne ici !");
            return null;
        }
        System.out.println("Choisissez une cible :");
        for (int i = 0; i < space.getCharacters().size(); i++) {
            System.out.println((i + 1) + ". " + space.getCharacters().get(i).getName());
        }
        int idx = getIntInput(1, space.getCharacters().size());
        return space.getCharacters().get(idx - 1);
    }

    public static void initSimulation() {
        System.out.println(BLUE + "Initialisation de la simulation..." + RESET);
        theatre = new InvasionTheatre("Armorique", 20);
        initPlaces();
        initCharacters();
    }

    public static void initPlaces() {
        boolean running = true;
        String name;
        while (running) {
            System.out.println("\n========== Création des Lieux ==========");
            System.out.println("1. Ajouter un Champ de bataille");
            System.out.println("2. Ajouter un Enclos");
            System.out.println("3. Ajouter un Village gaulois");
            System.out.println("4. Ajouter un Village gallo-romain");
            System.out.println("5. Ajouter une Cité romaine");
            System.out.println("6. Ajouter un Camp fortifié romain");
            System.out.println("7. Terminer la création des lieux");
            System.out.print("Choix (1-7): ");

            int choice = getIntInput(1, 7);
            Space newSpace = null;

            switch (choice) {
                case 1:
                    name = getStringInput("Champ de bataille");
                    newSpace = new Battlefield(name, 1000);
                    break;
                case 2:
                    name = getStringInput("Enclos");
                    newSpace = new Enclosure(name, 500);
                    break;
                case 3:
                    name = getStringInput("Village gaulois");
                    ClanLeader chefG = new ClanLeader("Abraracourcix", 'M', 50, null);
                    newSpace = new GallicVillage(name, 1000, chefG);
                    newSpace.addCharacter(chefG);
                    break;
                case 4:
                    name = getStringInput("Village gallo-romain");
                    newSpace = new GalloRomanVillage(name, 1000, null);
                    break;
                case 5:
                    name = getStringInput("Cité romaine");
                    newSpace = new RomanCity(name, 1000, null);
                    break;
                case 6:
                    name = getStringInput("Camp fortifié romain");
                    newSpace = new RomanFortifiedCamp(name, 1000, null);
                    break;
                case 7:
                    running = false;
                    break;
            }

            if (newSpace != null) {
                theatre.addLocation(newSpace);
                System.out.println(GREEN + newSpace.getName() + " ajouté !" + RESET);
            }
        }
    }

    public static void initCharacters() {
        if (theatre == null || theatre.getExistingLocations().isEmpty()) {
            System.out.println(RED + "Erreur : Aucun lieu disponible. Créez d'abord des lieux." + RESET);
            return;
        }

        boolean running = true;
        while (running) {
            System.out.println("\n========== Création de Personnages ==========");
            System.out.println("1. Créer un Gaulois");
            System.out.println("2. Créer un Romain");
            System.out.println("3. Terminer la création");
            System.out.print("Choix : ");

            int choice = getIntInput(1, 3);
            if (choice == 3) {
                running = false;
                continue;
            }

            Faction faction = (choice == 1) ? Faction.GAULOIS : Faction.ROMAIN;
            String role = chooseRole(faction); // On choisit le rôle (Chef inclus)

            if (role != null) {
                String name = getStringInput("Nom du personnage");

                if (role.equals("chef_de_clan")) {
                    System.out.println("--- De quel lieu est-il le chef ? ---");
                    Space location = chooseLocation();

                    if (location != null) {
                        ClanLeader chef = new ClanLeader(name, 'M', 50, location);
                        location.addCharacter(chef);
                        System.out.println(GREEN + "Chef de clan " + name + " créé et assigné à " + location.getName() + " !" + RESET);
                    }
                }
                else {
                    try {
                        Character newChar = characterFactory.createCharacter(faction, role, name);
                        System.out.println("--- Où placer " + newChar.getName() + " ? ---");
                        Space destination = chooseLocation();

                        if (destination != null) {
                            if (destination.addCharacter(newChar)) {
                                System.out.println(GREEN + newChar.getName() + " a rejoint " + destination.getName() + RESET);
                            } else {
                                System.out.println(RED + "Placement impossible ici." + RESET);
                            }
                        }
                    } catch (Exception e) {
                        System.out.println(RED + "Erreur : " + e.getMessage() + RESET);
                    }
                }
            }
        }
    }

    private static String chooseRole(Faction faction) {
        System.out.println("--- Choisissez un métier (" + faction + ") ---");
        if (faction == Faction.GAULOIS) {
            System.out.println("1. Druide");
            System.out.println("2. Forgeron");
            System.out.println("3. Aubergiste");
            System.out.println("4. Marchand");
            System.out.println("5. CHEF DE CLAN (Abraracourcix)"); // Nouvelle option

            int c = getIntInput(1, 5);
            return switch (c) {
                case 1 -> "druide";
                case 2 -> "forgeron";
                case 3 -> "aubergiste";
                case 4 -> "marchand";
                case 5 -> "chef_de_clan"; // Identifiant spécial
                default -> null;
            };
        } else {
            System.out.println("1. Légionnaire");
            System.out.println("2. Général");
            System.out.println("3. Préfet");
            System.out.println("4. CHEF DE CLAN (Caius Bonus)"); // Nouvelle option

            int c = getIntInput(1, 4);
            return switch (c) {
                case 1 -> "legionnaire";
                case 2 -> "général";
                case 3 -> "préfet";
                case 4 -> "chef_de_clan"; // Identifiant spécial
                default -> null;
            };
        }
    }

    private static Space chooseLocation() {
        if (theatre.getExistingLocations().isEmpty()) return null;
        for (int i = 0; i < theatre.getExistingLocations().size(); i++) {
            System.out.println((i + 1) + ". " + theatre.getExistingLocations().get(i).getName());
        }
        return theatre.getExistingLocations().get(getIntInput(1, theatre.getExistingLocations().size()) - 1);
    }

    private static int getIntInput(int min, int max) {
        while (true) {
            try {
                String input = scanner.nextLine();
                int value = Integer.parseInt(input);
                if (value >= min && value <= max) return value;
                System.out.print(RED + "Invalide (" + min + "-" + max + ") : " + RESET);
            } catch (NumberFormatException e) {
                System.out.print(RED + "Entrez un nombre : " + RESET);
            }
        }
    }

    private static String getStringInput(String type) {
        System.out.print("Nom (" + type + ") : ");
        return scanner.nextLine();
    }
}