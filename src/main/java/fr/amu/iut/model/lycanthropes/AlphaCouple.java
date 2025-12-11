package fr.amu.iut.model.lycanthropes;

import fr.amu.iut.model.characters.jobs.Lycanthrope;
import java.util.Random;
import fr.amu.iut.model.lycanthropes.Rank;
import fr.amu.iut.util.GameEvents;

public class AlphaCouple {

    private Lycanthrope male;
    private Lycanthrope female;
    private Pack pack;

    // Constructeur
    public AlphaCouple(Lycanthrope male, Lycanthrope female, Pack pack) {
        if (male.getSex() != 'M' || female.getSex() != 'F') {
            throw new IllegalArgumentException("Le couple Alpha doit être composé d'un mâle et d'une femelle.");
        }
        this.male = male;
        this.female = female;
        this.pack = pack;
    }

    // Affiche les charactéristiques du couple
    public void displayCharacteristics() {
        GameEvents.log("Couple Alpha : ");
        GameEvents.log("\nMâle :");
        male.printCharacteristics();
        GameEvents.log("\nFemelle :");
        female.printCharacteristics();
    }

    // Permet la reproduction
    public void reproduce() {
        int numberOfYoung = new Random().nextInt(7) + 1;

        // Détermine le rang de la nouvelle portée
        Rank youngRank = Rank.GAMMA;
        boolean betaExists = pack.getMembers().stream().anyMatch(member -> member.getRank() == Rank.BETA);
        if (!betaExists) {
            youngRank = Rank.BETA;
        }

        for (int i = 0; i < numberOfYoung; i++) {
            char sex = new Random().nextBoolean() ? 'M' : 'F';
            String name = "Jeune " + (i + 1);
            // Statistiques par défaut pour un jeune lycanthrope
            int size = 150;
            int age = 0;
            int strength = 5;
            int endurance = 5;
            int dominationFactor = 0;
            double impulsiveness = 0.5;

            Lycanthrope young = new Lycanthrope(name, sex, size, age, strength, endurance, "jeune", youngRank.getDisplay(), dominationFactor, impulsiveness, pack, false);
            pack.addMember(young);
        }
        GameEvents.log(numberOfYoung + " nouveau(x) lycanthrope(s) de rang " + youngRank.getDisplay() + " sont nés du couple alpha.");
    }

    // Getters & Setters
    public Lycanthrope getMale() {
        return male;
    }
    public Lycanthrope getFemale() {
        return female;
    }
}
