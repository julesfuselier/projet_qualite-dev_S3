package fr.amu.iut;

import fr.amu.iut.model.InvasionTheatre;
import fr.amu.iut.model.characters.Character;
import fr.amu.iut.model.characters.ClanLeader;
import fr.amu.iut.model.characters.Faction;
import fr.amu.iut.model.characters.jobs.*;
import fr.amu.iut.model.spaces.*;

import java.util.Scanner;

public class MainApp {

    private static InvasionTheatre theatre;
    private static Scanner scanner = new Scanner(System.in);
    private static int turnCount = 0;

    public static void main(String[] args) {
        // 1. Initialisation
        initSimulation();

        boolean running = true;
        while (running) {
            printMenu();
            int choice = getUserChoice();

            switch (choice) {
                case 1:
                    runNextTurn();
                    break;
                case 2:
                    handleClanLeaderActions();
                    break;
                case 3:
                    theatre.showAllCharacters(); // Affiche les détails [cite: 107]
                    break;
                case 4:
                    System.out.println("Fin de la simulation. Au revoir !");
                    running = false;
                    break;
                default:
                    System.out.println("Choix invalide.");
            }
        }
    }

    private static void initSimulation() {
        System.out.println("Initialisation du Théâtre d'Invasion...");
        theatre = new InvasionTheatre("Armorique", 10);

        // TODO: Créer ici vos lieux et personnages initiaux manuellement
        // car votre InvasionTheatre ne le fait pas encore automatiquement.
        // Exemple (si vous ajoutez une méthode addLocation dans InvasionTheatre) :

        GallicVillage village = new GallicVillage("Village des Irréductibles", 100, null);
        Druid panoramix = new Druid("Panoramix", 'M', 170, 90, 10, 50, Faction.GAULOIS);
        village.addCharacter(panoramix);
        Battlefield plaine = new Battlefield("Plaine de Babaorum", 500);

        theatre.addLocation(village); // Méthode à créer dans InvasionTheatre !
        theatre.addLocation(plaine);
    }

    private static void printMenu() {
        System.out.println("\n=================================");
        System.out.println("   SIMULATION GAULOIS - TOUR " + turnCount);
        System.out.println("=================================");
        System.out.println("1. Lancer la simulation temporelle (Combats, Faim...)");
        System.out.println("2. Actions de Chef de Clan (Utilisateur)");
        System.out.println("3. Afficher l'état du monde");
        System.out.println("4. Quitter");
        System.out.print("Votre choix : ");
    }

    private static int getUserChoice() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /**
     * Modélise l'aspect temporel [cite: 108]
     */
    private static void runNextTurn() {
        turnCount++;
        System.out.println("\n--- DÉROULEMENT DU TOUR " + turnCount + " ---");

        // 1. Les combats [cite: 110]
        System.out.println(">> Gestion des batailles...");
        theatre.handleBattles();

        // 2. États aléatoires (Faim, Potion) [cite: 111]
        System.out.println(">> Mise à jour des états (Faim, Potion)...");
        theatre.updateRandomCharacterStates();

        // 3. Apparition nourriture [cite: 112]
        System.out.println(">> Apparition de nourriture...");
        theatre.spawnFood();

        // 4. Pourrissement nourriture [cite: 113]
        System.out.println(">> Vérification de la fraîcheur des aliments...");
        theatre.updateFoodFreshness();

        System.out.println("--- FIN DES ÉVÉNEMENTS AUTOMATIQUES ---");
    }

    /**
     * Permet à l'utilisateur d'agir comme Chef de Clan [cite: 114]
     */
    private static void handleClanLeaderActions() {
        System.out.println("\n--- ACTION CHEF DE CLAN ---");
        // Ici, vous pouvez demander quel chef de clan l'utilisateur veut incarner
        // Puis proposer un sous-menu : 1. Soigner, 2. Nourrir, 3. Faire Potion...

        System.out.println("(Fonctionnalité à implémenter : lister les chefs, choisir une action)");
        // Exemple :
        // ClanLeader chef = theatre.getClanLeaders().get(0);
        // chef.examineLocation();
    }
}