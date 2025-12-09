package fr.amu.iut.model.characters.jobs;

import fr.amu.iut.model.characters.Character;
import fr.amu.iut.model.characters.Fighter;
import fr.amu.iut.model.lycanthropes.Pack;

public class Lycanthrope extends Character implements Fighter {

    private String ageGroup;
    private String rank;
    private int dominationFactor;
    private double level;
    private double impulsiveness;
    private Pack pack;
    private boolean lone;

    private static final int DOMINATION_THRESHOLD = -5;

    // Constructeur
    public Lycanthrope(String name, char sex, int size, int age, int strength, int endurance, String ageGroup,
            String rank, int dominationFactor, double impulsiveness, Pack pack, boolean lone) {
        super(name, sex, size, age, strength, endurance, null);
        this.ageGroup = ageGroup;
        this.rank = rank;
        this.dominationFactor = dominationFactor;
        this.impulsiveness = impulsiveness;
        this.pack = pack;
        this.lone = lone;
        this.level = computeLevel();
    }

    @Override
    public void fight(Character opponent) {
        int damage = (this.getStrength() * 2) - opponent.getEndurance();
        if (damage > 0) {
            opponent.getHealth().add(-damage);
            System.out.println(getName() + " attaque sauvagement " + opponent.getName() + " (-" + damage + " HP)");
        } else {
            System.out.println(getName() + " tries to attack " + opponent.getName() + " but fails.");
        }
    }

    /**
     * Tente de dominer un autre Lycanthrope, ce qui peut entraîner un changement de rang.
     * Le succès de la domination dépend du niveau et du rang des deux lycanthropes.
     * En cas de succès, l'agresseur et la cible échangent leurs rangs, et leurs facteurs de domination sont ajustés.
     * En cas d'échec, la cible devient agressive.
     *
     * @param target Le Lycanthrope à dominer.
     */
    public void dominate(Lycanthrope target) {
        if (this == target) {
            System.out.println(getName() + " ne peut pas se dominer lui-même.");
            return;
        }

        // Vérification si la domination est possible
        if (target.getPack() != null && target.getPack().getAlphaFemale() == target) {
            System.out.println(getName() + " ne peut pas dominer la femelle Alpha !");
            return;
        }

        double perceivedSelfStrength = this.getStrength() * this.impulsiveness;
        if (perceivedSelfStrength < target.getStrength()) {
            System.out.println(getName() + " ne se sent pas assez fort pour dominer " + target.getName());
            return;
        }

        System.out.println(getName() + " tente de dominer " + target.getName() + " !");

        // Tentative de domination
        // Seulement si l’agresseur a un niveau supérieur à sa cible ou si
        // la cible est un lycanthrope ω
        boolean success = (this.level > target.getLevel()) || "ω".equals(target.getRank());

        if (success) {
            System.out.println("Domination réussie !");
            // Augmentation du facteur de domination
            this.dominationFactor++;

            // Échange du rang
            String tempRank = this.rank;
            this.rank = target.rank;
            target.rank = tempRank;

            // Diminution du facteur de domination
            target.dominationFactor--;

            // Recalcul des niveaux
            this.level = computeLevel();
            target.level = target.computeLevel();

            // Vérifier si la cible était un mâle Alpha
            Pack pack = target.getPack();
            if (pack != null && target == pack.getAlphaMale()) {
                System.out.println(target.getName() + " a été détrôné de sa place de Mâle Alpha !");
                Lycanthrope oldAlphaFemale = pack.getAlphaFemale();

                // Mettre à jour le couple Alpha dans la meute
                pack.updateAlphasAfterDomination(this);

                // L'ancienne femelle Alpha prend le rang de son ancien conjoint déchu
                if (oldAlphaFemale != null && oldAlphaFemale != pack.getAlphaFemale()) {
                    System.out.println(oldAlphaFemale.getName() + " a perdu sa place de Femelle Alpha.");
                    oldAlphaFemale.setRank(target.getRank()); // target est l'ancien mâle alpha avec son nouveau rang
                }
            }

        } else {
            System.out.println("Domination échouée !");
            // La cible se montre agressive
            target.becomeAggressive(this);

            // Un lycanthrope agressé baisse son facteur de domination
            target.dominationFactor--;
            target.level = target.computeLevel();
        }
    }

    // Un lycanthrope devient agressif
    public void becomeAggressive(Lycanthrope aggressor) {
        System.out.println(getName() + " devient agressif envers " + aggressor.getName() + " !");
        this.howl("AGRESSIVITÉ");
        this.fight(aggressor);
    }

    // Calcul du niveau du lycanthrope
    private double computeLevel() {
        int ageScore = switch (ageGroup != null ? ageGroup.toLowerCase() : "adulte") {
            case "jeune" -> 1;
            case "adulte" -> 3;
            case "vieux" -> 2;
            default -> 1;
        };

        int rankScore = getRankValue(rank);

        return (ageScore * 2) + (getStrength() * 0.5) + (dominationFactor * 1.5) + (20 - rankScore);
    }

    // Récupérer la valeur du rang
    private int getRankValue(String rank) {
        if (rank == null)
            return 10;
        return switch (rank) {
            case "α" -> 0;
            case "β" -> 1;
            case "γ" -> 2;
            case "δ" -> 3;
            case "ε" -> 4;
            case "ω" -> 20;
            default -> 10;
        };
    }

    // Affiche les charactéristiques dy lycanthrope
    public void printCharacteristics() {
        System.out.println("Name: " + getName());
        System.out.println("Sex: " + getSex());
        System.out.println("Age group: " + ageGroup);
        System.out.println("Strength: " + getStrength());
        System.out.println("Domination factor: " + dominationFactor);
        System.out.println("Rank: " + rank);
        System.out.println("Level: " + level);
        System.out.println("Impulsiveness: " + impulsiveness);
        System.out.println("Pack: " + (lone ? "Lone" : (pack != null ? "Member of a pack" : "None")));
    }

    // Faire crier un lycanthrope
    public void howl(String type) {
        System.out.println(getName() + " hurle (" + type + ") !");
    }

    // Un lycanthrope entend un hurlement
    public void hearHowl(String type) {
        if (getHealth().get() > 20) {
            System.out.println(getName() + " entend un hurlement : " + type);
            if ("DOMINATION".equals(type)) {
                howl("SOUMISSION");
            }
        } else {
            System.out.println(getName() + " est trop malade pour entendre.");
        }
    }

    // Quitter la meute
    public void leavePack() {
        if (pack != null) {
            pack.removeMember(this);
            this.pack = null;
            this.lone = true;
            System.out.println(getName() + " quitte la meute et devient solitaire.");
        }
    }

    // Transformer le lycanthrope en humain
    public void transformToHuman() {
        System.out.println(getName() + " se transforme en humain !");
        leavePack();
    }

    /**
     * Vérifie si le facteur de domination est tombé en dessous d'un seuil
     * et dégrade le rang du lycanthrope si nécessaire.
     */
    public void updateRankFromDominationFactor() {
        if (this.dominationFactor < DOMINATION_THRESHOLD) {
            demoteRank();
        }
    }

    // Dégrade le rang du lycanthrope s'il n'est pas le dernier de son sexe à avoir ce rang.
    private void demoteRank() {
        // On ne peut pas dégrader les alphas ou les omegas de cette manière
        if ("α".equals(this.rank) || "ω".equals(this.rank)) {
            return;
        }

        // Vérifier si le lycanthrope est le dernier de son rang pour son sexe
        if (this.pack != null && !this.pack.isLastOfRank(this.rank, this.getSex())) {
            String nextRank = getNextRank(this.rank);
            if (nextRank != null) {
                System.out.println(getName() + " a un facteur de domination trop bas et est dégradé au rang " + nextRank);
                this.setRank(nextRank);
                this.setDominationFactor(0);
            }
        }
    }

    //Renvoie le rang immédiatement inférieur.
    private String getNextRank(String currentRank) {
        return switch (currentRank) {
            case "β" -> "γ";
            case "γ" -> "δ";
            case "δ" -> "ε";
            default -> null; // Pas de dégradation depuis ε ou autre
        };
    }

    // Getters & Setters
    public void setAgeGroup(String ageGroup) {
        this.ageGroup = ageGroup;
        this.level = computeLevel();
    }
    public void setRank(String rank) {
        this.rank = rank;
        this.level = computeLevel();
    }
    public String getAgeGroup() {
        return ageGroup;
    }
    public String getRank() {
        return rank;
    }
    public int getDominationFactor() {
        return dominationFactor;
    }
    public void setDominationFactor(int dominationFactor) {
        this.dominationFactor = dominationFactor;
        this.level = computeLevel();
    }
    public double getLevel() {
        return level;
    }
    public double getImpulsiveness() {
        return impulsiveness;
    }
    public void setImpulsiveness(double impulsiveness) {
        this.impulsiveness = impulsiveness;
    }
    public Pack getPack() {
        return pack;
    }
    public void setPack(Pack pack) {
        this.pack = pack;
    }
    public boolean isLone() {
        return lone;
    }
    public void setLone(boolean lone) {
        this.lone = lone;
    }
}
