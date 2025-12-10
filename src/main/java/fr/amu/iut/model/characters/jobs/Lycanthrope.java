package fr.amu.iut.model.characters.jobs;

import fr.amu.iut.model.characters.Character;
import fr.amu.iut.model.characters.Fighter;
import fr.amu.iut.model.lycanthropes.Pack;
import fr.amu.iut.model.lycanthropes.Rank;

public class Lycanthrope extends Character implements Fighter {

    private String ageGroup;
    private Rank rank;
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
        this.rank = Rank.fromString(rank);
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
            // Empêche les lycanthropes de se tuer entre eux
            if (opponent instanceof Lycanthrope && opponent.getHealth().get() < 1) {
                opponent.getHealth().add(1);
            }
            System.out.println(getName() + " attaque sauvagement " + opponent.getName() + " (-" + damage + " HP)");
        } else {
            System.out.println(getName() + " essaie d'attaquer " + opponent.getName() + " mais échoue.");
        }
    }

    public void dominate(Lycanthrope target) {
        if (this == target) {
            System.out.println(getName() + " ne peut pas se dominer lui-même.");
            return;
        }
        if (this.rank == Rank.OMEGA) {
            System.out.println(getName() + " est un " + Rank.OMEGA.getDisplay() + ", il ne peut donc dominer personne");
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

        boolean success = (this.level > target.getLevel()) || target.getRank() == Rank.OMEGA;

        if (success) {
            System.out.println("Domination réussie !");
            this.dominationFactor++;

            Rank tempRank = this.rank;
            this.setRank(target.getRank());
            target.setRank(tempRank);

            target.dominationFactor--;

            this.level = computeLevel();
            target.level = target.computeLevel();

            Pack pack = target.getPack();
            if (pack != null && target == pack.getAlphaMale()) {
                System.out.println(target.getName() + " a été détrôné de sa place de Mâle Alpha !");
                Lycanthrope oldAlphaFemale = pack.getAlphaFemale();

                pack.updateAlphasAfterDomination(this);

                if (oldAlphaFemale != null && oldAlphaFemale != pack.getAlphaFemale()) {
                    System.out.println(oldAlphaFemale.getName() + " a perdu sa place de Femelle Alpha.");
                    oldAlphaFemale.setRank(target.getRank());
                }
            }

        } else {
            System.out.println("Domination échouée !");
            target.becomeAggressive(this);

            target.dominationFactor--;
            target.level = target.computeLevel();

            if (target.getRank() == Rank.ALPHA && target.getSex() == 'M') {
                this.becomeSolitary();
            }
        }
    }

    public void becomeAggressive(Lycanthrope aggressor) {
        System.out.println(getName() + " devient agressif envers " + aggressor.getName() + " !");
        this.howl("AGRESSIVITÉ");
        this.fight(aggressor);
    }

    private double computeLevel() {
        int ageScore = switch (ageGroup != null ? ageGroup.toLowerCase() : "adulte") {
            case "jeune" -> 1;
            case "adulte" -> 3;
            case "vieux" -> 2;
            default -> 1;
        };

        int rankScore = (rank != null) ? rank.getValue() : 25;

        return (ageScore * 2) + (getStrength() * 0.5) + (dominationFactor * 1.5) + (20 - rankScore);
    }

    public void printCharacteristics() {
        System.out.println("Nom : " + getName());
        System.out.println("Sexe : " + getSex());
        System.out.println("Groupe d'âge : " + ageGroup);
        System.out.println("Force : " + getStrength());
        System.out.println("Facteur de domination : " + dominationFactor);
        System.out.println("Rang : " + rank);
        System.out.println("Niveau : " + level);
        System.out.println("Impulsivité : " + impulsiveness);
        System.out.println("Meute : " + (lone ? "Solitaire" : (pack != null ? "Membre d'une meute" : "Aucune")));
    }

    public void howl(String type) {
        System.out.println(getName() + " hurle (" + type + ") !");
    }

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

    public void leavePack() {
        if (pack != null) {
            pack.removeMember(this);
            this.pack = null;
            this.lone = true;
            System.out.println(getName() + " quitte la meute et devient solitaire.");
        }
    }

    public void transformToHuman() {
        System.out.println(getName() + " se transforme en humain !");

        // La probabilité de partir est de niveau * 2%
        double chanceOfLeaving = getLevel() * 2;
        double roll = new java.util.Random().nextDouble() * 100;

        if (roll < chanceOfLeaving) {
            System.out.println(getName() + " profite de sa forme humaine pour quitter la meute et l'enclos !");

            // Quitter la meute
            leavePack();

            // Quitter l'enclos
            if (getCurrentSpace() != null) {
                getCurrentSpace().removeCharacter(this);
            }

        } else {
            System.out.println(getName() + " reste sous forme humaine, mais ne quitte pas la meute.");
        }
    }

    public void updateRankFromDominationFactor() {
        if (this.dominationFactor < DOMINATION_THRESHOLD) {
            demoteRank();
        }
    }

    private void demoteRank() {
        if (this.rank == Rank.ALPHA || this.rank == Rank.OMEGA) {
            return;
        }

        if (this.pack != null && !this.pack.isLastOfRank(this.rank, this.getSex())) {
            Rank nextRank = getNextRank(this.rank);
            if (nextRank != null) {
                System.out.println(getName() + " a un facteur de domination trop bas et est dégradé au rang " + nextRank);
                this.setRank(nextRank);
                this.setDominationFactor(0);
            }
        }
    }

    private Rank getNextRank(Rank currentRank) {
        if (currentRank == null || currentRank == Rank.OMEGA) {
            return null;
        }
        int nextRankValue = currentRank.getValue() + 1;
        return Rank.fromValue(nextRankValue);
    }

    public void becomeSolitary() {
        if (pack != null) {
            pack.removeMember(this);
            this.pack = null;
        }
        lone = true;
        rank = null;
        System.out.println(getName() + " est devenu solitaire !");
    }

    // Getters & Setters
    public void setAgeGroup(String ageGroup) {
        this.ageGroup = ageGroup;
        this.level = computeLevel();
    }

    public void setRank(Rank rank) {
        this.rank = rank;
        this.level = computeLevel();
    }

    public void setRank(String rank) {
        this.setRank(Rank.fromString(rank));
    }

    public String getAgeGroup() {
        return ageGroup;
    }

    public Rank getRank() {
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