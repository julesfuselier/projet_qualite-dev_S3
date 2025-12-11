package fr.amu.iut.view; // Ou fr.amu.iut si tu ne veux pas créer de sous-dossier

import fr.amu.iut.model.characters.Character;
import fr.amu.iut.model.spaces.Space;

import java.util.List;
import java.util.Scanner;

/**
 * Classe responsable de l'affichage des informations dans la console
 * et de la gestion des interactions utilisateur.
 */
public class ConsoleView {

    private final Scanner scanner;

    // Codes couleurs
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String BLUE = "\u001B[34m";

    /**
     * Constructeur de la classe ConsoleView.
     * Initialise le scanner pour la lecture des entrées utilisateur.
     */
    public ConsoleView() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Affiche un message simple dans la console.
     *
     * @param message Le message à afficher.
     */
    public void displayMessage(String message) {
        System.out.println(message);
    }

    /**
     * Affiche un message d'erreur en rouge.
     *
     * @param error Le message d'erreur à afficher.
     */
    public void displayError(String error) {
        System.out.println(RED + error + RESET);
    }

    /**
     * Affiche un message de succès en vert.
     *
     * @param success Le message de succès à afficher.
     */
    public void displaySuccess(String success) {
        System.out.println(GREEN + success + RESET);
    }

    /**
     * Affiche un titre encadré.
     *
     * @param title Le titre à afficher.
     */
    public void displayTitle(String title) {
        System.out.println(BLUE + "\n=== " + title + " ===" + RESET);
    }

    /**
     * Affiche le menu principal.
     *
     * @param turnCount Le numéro du tour actuel.
     */
    public void showMainMenu(int turnCount) {
        System.out.println("\n" + BLUE + "╔════════ MENU PRINCIPAL (Tour " + turnCount + ") ════════╗" + RESET);
        System.out.println("1. Lancer la simulation temporelle (Combats, Faim...)");
        System.out.println("2. Actions de Chef de Clan");
        System.out.println("3. Afficher l'état du monde");
        System.out.println("4. MODE AUTO (Thread)");
        System.out.println("5. Quitter");
        System.out.print("Votre choix : ");
    }

    /**
     * Affiche le menu des actions du chef de clan.
     *
     * @param leaderName Le nom du chef de clan.
     */
    public void showClanLeaderMenu(String leaderName) {
        System.out.println(GREEN + "\n--- ACTION : " + leaderName + " ---" + RESET);
        System.out.println("1. Examiner le lieu");
        System.out.println("2. Soigner un personnage");
        System.out.println("3. Nourrir un personnage");
        System.out.println("4. Distribuer une potion magique");
        System.out.println("5. Retour");
    }

    /**
     * Lit une entrée entière de l'utilisateur dans une plage spécifiée.
     *
     * @param min La valeur minimale acceptable.
     * @param max La valeur maximale acceptable.
     * @return L'entier saisi par l'utilisateur.
     */
    public int getIntInput(int min, int max) {
        while (true) {
            try {
                String input = scanner.nextLine();
                int value = Integer.parseInt(input);
                if (value >= min && value <= max)
                    return value;
                System.out.print(RED + "Invalide (" + min + "-" + max + ") : " + RESET);
            } catch (NumberFormatException e) {
                System.out.print(RED + "Entrez un nombre : " + RESET);
            }
        }
    }

    /**
     * Attend que l'utilisateur appuie sur Entrée pour continuer.
     *
     * @param message Le message à afficher avant d'attendre.
     */
    public void waitForEnter(String message) {
        System.out.println(RED + message + RESET);
        scanner.nextLine();
    }

    /**
     * Permet à l'utilisateur de sélectionner un élément dans une liste.
     *
     * @param list   La liste des éléments à choisir.
     * @param prompt Le message d'invite à afficher.
     * @param <T>    Le type des éléments dans la liste.
     * @return L'élément sélectionné par l'utilisateur.
     */
    public <T> T selectFromList(List<T> list, String prompt) {
        if (list.isEmpty()) {
            displayError("La liste est vide.");
            return null;
        }

        System.out.println(prompt);
        for (int i = 0; i < list.size(); i++) {
            String display = list.get(i).toString();
            if (list.get(i) instanceof Character) {
                display = ((Character) list.get(i)).getName();
            } else if (list.get(i) instanceof Space) {
                display = ((Space) list.get(i)).getName();
            }

            System.out.println((i + 1) + ". " + display);
        }

        System.out.print("Votre choix : ");
        int choice = getIntInput(1, list.size());
        return list.get(choice - 1);
    }
}