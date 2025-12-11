package fr.amu.iut.model.characters.jobs;

import fr.amu.iut.model.characters.Character;
import fr.amu.iut.model.characters.Fighter;
import fr.amu.iut.model.lycanthropes.Howl;
import fr.amu.iut.model.lycanthropes.HowlType;
import fr.amu.iut.model.lycanthropes.Pack;
import fr.amu.iut.model.lycanthropes.Rank;
import fr.amu.iut.util.GameEvents;

import java.util.Random;

/**
 * Classe représentant un lycanthrope dans le jeu.
 * Le lycanthrope est un personnage de type combattant avec des capacités spéciales liées à sa nature de loup-garou.
 */
public class Lycanthrope extends Character implements Fighter {

    private String ageGroup;
    private Rank rank;
    private int dominationFactor;
    private double level;
    private double impulsiveness;
    private Pack pack;
    private boolean lone;

    private static final int DOMINATION_THRESHOLD = -5;

    /**
     * Constructeur de la classe Lycanthrope.
     *
     * @param name             Le nom du lycanthrope.
     * @param sex              Le sexe du lycanthrope.
     * @param size             La taille du lycanthrope.
     * @param age              L'âge du lycanthrope.
     * @param strength         La force du lycanthrope.
     * @param endurance        L'endurance du lycanthrope.
     * @param ageGroup         Le groupe d'âge du lycanthrope (jeune, adulte, vieux).
     * @param rank             Le rang du lycanthrope dans la meute.
     * @param dominationFactor Le facteur de domination du lycanthrope.
     * @param impulsiveness    L'impulsivité du lycanthrope.
     * @param pack             La meute à laquelle appartient le lycanthrope.
     * @param lone             Indique si le lycanthrope est solitaire.
     */
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

    /**
     * Méthode représentant le combat entre le lycanthrope et un adversaire.
     * Le lycanthrope inflige des dégâts basés sur sa force et l'endurance de l'adversaire.
     *
     * @param opponent L'adversaire à combattre.
     */
    @Override
    public void fight(Character opponent) {
        int damage = (this.getStrength() * 2) - opponent.getEndurance();
        if (damage > 0) {
            opponent.getHealth().add(-damage);
            if (opponent instanceof Lycanthrope && opponent.getHealth().get() < 1) {
                opponent.getHealth().set(1);
            }
            GameEvents.log(getName() + " attaque sauvagement " + opponent.getName() + " (-" + damage + " HP)");
        } else {
            GameEvents.log(getName() + " essaie d'attaquer " + opponent.getName() + " mais échoue.");
        }
    }


    /**
     * Tente de prendre la place d'un autre Lycanthrope dans la hiérarchie par la force.
     * @param target Le Lycanthrope à dominer.
     */
    public void dominate(Lycanthrope target) {
        if (this == target) {
            GameEvents.log(getName() + " ne peut pas se dominer lui-même.");
            return;
        }
        if (this.rank == Rank.OMEGA) {
            GameEvents.log(getName() + " est un " + Rank.OMEGA.getDisplay() + ", il ne peut donc dominer personne");
            return;
        }

        if (target.getPack() != null && target.getPack().getAlphaFemale() == target) {
            GameEvents.log(getName() + " ne peut pas dominer la femelle Alpha !");
            return;
        }

        double perceivedSelfStrength = this.getStrength() * this.impulsiveness;
        if (perceivedSelfStrength < target.getStrength()) {
            GameEvents.log(getName() + " ne se sent pas assez fort pour dominer " + target.getName());
            return;
        }

        GameEvents.log(getName() + " tente de dominer " + target.getName() + " !");

        boolean success = (this.level > target.getLevel()) || target.getRank() == Rank.OMEGA;

        if (success) {
            GameEvents.log("Domination réussie !");
            this.dominationFactor++;

            Rank tempRank = this.rank;
            this.setRank(target.getRank());
            target.setRank(tempRank);

            target.dominationFactor--;

            this.level = computeLevel();
            target.level = target.computeLevel();

            Pack pack = target.getPack();
            if (pack != null && target == pack.getAlphaMale()) {
                GameEvents.log(target.getName() + " a été détrôné de sa place de Mâle Alpha !");
                Lycanthrope oldAlphaFemale = pack.getAlphaFemale();

                pack.updateAlphasAfterDomination(this);

                if (oldAlphaFemale != null && oldAlphaFemale != pack.getAlphaFemale()) {
                    GameEvents.log(oldAlphaFemale.getName() + " a perdu sa place de Femelle Alpha.");
                    oldAlphaFemale.setRank(target.getRank());
                }
            }

        } else {
            GameEvents.log("Domination échouée !");
            target.becomeAggressive(this);

            target.dominationFactor--;
            target.level = target.computeLevel();

            if (target.getRank() == Rank.ALPHA && target.getSex() == 'M') {
                this.becomeSolitary();
            }
        }
    }

    /**
     * Rend le lycanthrope agressif envers un autre lycanthrope.
     * @param aggressor Le lycanthrope envers lequel devenir agressif.
     */
    public void becomeAggressive(Lycanthrope aggressor) {
        GameEvents.log(getName() + " devient agressif envers " + aggressor.getName() + " !");
        this.howl(HowlType.AGGRESSION, true);
        this.fight(aggressor);
    }

    /**
     * Calcule le niveau du lycanthrope en fonction de ses caractéristiques.
     * @return Le niveau calculé.
     */
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

    /**
     * Affiche les caractéristiques du lycanthrope.
     */
    public void printCharacteristics() {
        GameEvents.log("Nom : " + getName());
        GameEvents.log("Sexe : " + getSex());
        GameEvents.log("Groupe d'âge : " + ageGroup);
        GameEvents.log("Force : " + getStrength());
        GameEvents.log("Facteur de domination : " + dominationFactor);
        GameEvents.log("Rang : " + rank);
        GameEvents.log("Niveau : " + level);
        GameEvents.log("Impulsivité : " + impulsiveness);
        GameEvents.log("Meute : " + (lone ? "Solitaire" : (pack != null ? "Membre d'une meute" : "Aucune")));
    }

    /**
     * Émet un hurlement.
     * @param type Le type de hurlement.
     * @param expectResponse Si true, le hurlement appelle une réponse (broadcast). Sinon, c'est juste une réponse locale.
     */
    public void howl(HowlType type, boolean expectResponse) {
        Howl howl = new Howl(this, type, "Aouuuuuuuh !");

        if (expectResponse && getCurrentSpace() != null && getCurrentSpace().getColony() != null) {
            getCurrentSpace().getColony().broadcastHowl(howl);
        } else {
            GameEvents.log(getName() + " répond par un hurlement (" + type.getLabel() + ").");
        }
    }

    /**
     * Reçoit et réagit à un hurlement (Implémentation TD4).
     * @param howl Le hurlement entendu.
     */
    public void hearHowl(Howl howl) {
        if (getHealth().get() <= 20) {
            GameEvents.log(getName() + " est trop faible pour réagir au hurlement.");
            return;
        }

        GameEvents.log(getName() + " entend le hurlement de " + howl.getEmitter().getName());

        switch (howl.getType()) {
            case BELONGING:
                handleBelongingHowl(howl);
                break;
            case DOMINATION:
                // Réponse : Soumission ou Agressivité
                if (shouldSubmit(howl.getEmitter())) {
                    GameEvents.log(getName() + " choisit de se soumettre.");
                    howl(HowlType.SUBMISSION, false);
                } else {
                    GameEvents.log(getName() + " choisit de répondre par l'agressivité !");
                    howl(HowlType.AGGRESSION, false);
                }
                break;
            default:
                break;
        }
    }

    /**
     * Gère la réaction à un hurlement d'appartenance.
     * Si même meute -> répond.
     * Si autre meute -> probabilité de répondre pour défier.
     */
    private void handleBelongingHowl(Howl howl) {
        Pack myPack = this.getPack();
        Pack emitterPack = howl.getEmitter().getPack();

        if (myPack != null && myPack == emitterPack) {
            howl(HowlType.BELONGING, false);
        }
        // Si je suis d'une autre meute
        else if (myPack != null && emitterPack != null) {
            if (new Random().nextBoolean()) {
                GameEvents.log(getName() + " répond pour affirmer son propre clan !");
                howl(HowlType.BELONGING, false);
            }
        }
    }

    /**
     * Détermine si le lycanthrope doit se soumettre face à un opposant.
     * @param opponent L'émetteur du hurlement de domination.
     * @return true si soumission, false sinon.
     */
    private boolean shouldSubmit(Lycanthrope opponent) {
        return opponent.getStrength() > this.getStrength() * 1.5;
    }

    // Quitter la meute
    public void leavePack() {
        if (pack != null) {
            pack.removeMember(this);
            this.pack = null;
            this.lone = true;
            GameEvents.log(getName() + " quitte la meute et devient solitaire.");
        }
    }

    /**
     * Transforme le lycanthrope en humain avec une probabilité basée sur son niveau.
     * Si la transformation réussit, le lycanthrope quitte la meute et l'enclos.
     */
    public void transformToHuman() {
        GameEvents.log(getName() + " se transforme en humain !");

        // La probabilité de partir est de niveau * 2%
        double chanceOfLeaving = getLevel() * 2;
        double roll = new Random().nextDouble() * 100;

        if (roll < chanceOfLeaving) {
            GameEvents.log(getName() + " profite de sa forme humaine pour quitter la meute et l'enclos !");

            leavePack();

            if (getCurrentSpace() != null) {
                getCurrentSpace().removeCharacter(this);
            }

        } else {
            GameEvents.log(getName() + " reste sous forme humaine, mais ne quitte pas la meute.");
        }
    }

    /**
     * Met à jour le rang du lycanthrope en fonction de son facteur de domination.
     * Si le facteur est inférieur au seuil, le lycanthrope est dégradé.
     */
    public void updateRankFromDominationFactor() {
        if (this.dominationFactor < DOMINATION_THRESHOLD) {
            demoteRank();
        }
    }

    /**
     * Dégrade le rang du lycanthrope s'il ne remplit pas les conditions.
     */
    private void demoteRank() {
        if (this.rank == Rank.ALPHA || this.rank == Rank.OMEGA) {
            return;
        }

        if (this.pack != null && !this.pack.isLastOfRank(this.rank, this.getSex())) {
            Rank nextRank = getNextRank(this.rank);
            if (nextRank != null) {
                GameEvents.log(getName() + " a un facteur de domination trop bas et est dégradé au rang " + nextRank);
                this.setRank(nextRank);
                this.setDominationFactor(0);
            }
        }
    }

    /**
     * Obtient le rang suivant dans la hiérarchie.
     * @param currentRank Le rang actuel.
     * @return Le rang suivant ou null si aucun.
     */
    private Rank getNextRank(Rank currentRank) {
        if (currentRank == null || currentRank == Rank.OMEGA) {
            return null;
        }
        int nextRankValue = currentRank.getValue() + 1;
        return Rank.fromValue(nextRankValue);
    }

    /**
     * Rend le lycanthrope solitaire en le retirant de sa meute.
     */
    public void becomeSolitary() {
        if (pack != null) {
            pack.removeMember(this);
            this.pack = null;
        }
        lone = true;
        rank = null;
        GameEvents.log(getName() + " est devenu solitaire !");
    }

    /** Getter et Setter **/
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