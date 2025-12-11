package fr.amu.iut.model.lycanthropes;

import fr.amu.iut.model.characters.jobs.Lycanthrope;
import java.util.Random;
import fr.amu.iut.model.lycanthropes.Rank;

/**
 * Représente un couple Alpha composé d'un mâle et d'une femelle lycanthrope.
 * Le couple Alpha est responsable de diriger la meute et peut produire une portée.
 */
public class AlphaCouple {

    private Lycanthrope male;
    private Lycanthrope female;
    private Pack pack;

    /**
     * Construit un couple Alpha.
     *
     * @param male   le lycanthrope mâle (doit avoir le sexe 'M')
     * @param female le lycanthrope femelle (doit avoir le sexe 'F')
     * @param pack   la meute à laquelle appartient le couple
     * @throws IllegalArgumentException si les sexes ne correspondent pas
     */
    public AlphaCouple(Lycanthrope male, Lycanthrope female, Pack pack) {
        if (male.getSex() != 'M' || female.getSex() != 'F') {
            throw new IllegalArgumentException("Le couple Alpha doit être composé d'un mâle et d'une femelle.");
        }
        this.male = male;
        this.female = female;
        this.pack = pack;
    }

    /**
     * Affiche les caractéristiques détaillées des deux lycanthropes
     * composant le couple Alpha.
     */
    public void displayCharacteristics() {
        System.out.println("Couple Alpha : ");
        System.out.println("\nMâle :");
        male.printCharacteristics();
        System.out.println("\nFemelle :");
        female.printCharacteristics();
    }

    /**
     * Génère une nouvelle portée de lycanthropes.
     * Le nombre de jeunes varie entre 1 et 7.
     * Le rang attribué aux jeunes dépend de la présence d'un membre BETA dans la meute :
     * - S'il n'y a aucun BETA, la portée est classée BETA.
     * - Sinon, elle est classée GAMMA.
     *
     * Les jeunes créés sont automatiquement ajoutés à la meute.
     */
    public void reproduce() {
        int numberOfYoung = new Random().nextInt(7) + 1;

        // Déterminer le rang de la nouvelle portée
        Rank youngRank = Rank.GAMMA;
        boolean betaExists = pack.getMembers().stream().anyMatch(member -> member.getRank() == Rank.BETA);
        if (!betaExists) {
            youngRank = Rank.BETA;
        }

        for (int i = 0; i < numberOfYoung; i++) {
            char sex = new Random().nextBoolean() ? 'M' : 'F';
            String name = "Jeune " + (i + 1);

            // Statistiques minimales par défaut pour un jeune lycanthrope
            int size = 150;
            int age = 0;
            int strength = 5;
            int endurance = 5;
            int dominationFactor = 0;
            double impulsiveness = 0.5;

            Lycanthrope young = new Lycanthrope(
                    name,
                    sex,
                    size,
                    age,
                    strength,
                    endurance,
                    "jeune",
                    youngRank.getDisplay(),
                    dominationFactor,
                    impulsiveness,
                    pack,
                    false
            );

            pack.addMember(young);
        }

        System.out.println(numberOfYoung + " nouveau(x) lycanthrope(s) de rang " +
                youngRank.getDisplay() + " sont nés du couple alpha.");
    }

    /**
     * Retourne le mâle du couple Alpha.
     *
     * @return le lycanthrope mâle
     */
    public Lycanthrope getMale() {
        return male;
    }

    /**
     * Retourne la femelle du couple Alpha.
     *
     * @return le lycanthrope femelle
     */
    public Lycanthrope getFemale() {
        return female;
    }
}
