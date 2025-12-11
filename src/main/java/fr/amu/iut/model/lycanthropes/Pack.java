package fr.amu.iut.model.lycanthropes;

import fr.amu.iut.model.characters.jobs.Lycanthrope;
import fr.amu.iut.util.GameEvents;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Représente une meute de lycanthropes.
 * Une meute contient plusieurs membres, un couple Alpha, et une hiérarchie interne
 * allant de l'Alpha à l'Omega. Elle gère les ajouts/suppressions de membres,
 * la création de portées et l'évolution de la hiérarchie.
 */
public class Pack {

    /** Nom de la meute. */
    private String name;

    /** Liste des membres de la meute. */
    private List<Lycanthrope> members = new ArrayList<>();

    /** Couple Alpha de la meute (mâle et femelle dominants). */
    private AlphaCouple alphaCouple;

    /** Indique si la meute est en période de reproduction. */
    private boolean loveseasons = false;

    /**
     * Constructeur vide de la meute.
     */
    public Pack() {
    }

    /**
     * Affiche les caractéristiques de la meute.
     */
    public void showCharacteristics(){
        GameEvents.log("Nom: " + name);
        for (Lycanthrope l : members){
            GameEvents.log("Nom membre " + l.getName());
        }
        GameEvents.log("Nom du male alpha " + alphaCouple.getMale().getName());
        GameEvents.log("Nom de female alpha " + alphaCouple.getFemale().getName());
    }

    /**
     * Affiche les caractéristiques détaillées de chaque membre de la meute.
     */
    public void showMembersCharacteristics(){
        for (Lycanthrope l : members){
            GameEvents.log("Nom membre " + l.getName());
            GameEvents.log("age du membre " + l.getAgeGroup());
            GameEvents.log("rang du membre " + l.getRank());
            GameEvents.log("facteur de domination du membre " + l.getDominationFactor());
            GameEvents.log("niveau du membre " + l.getLevel());

        }
    }

    /**
     * Ajoute un membre à la meute et met à jour sa référence à la meute.
     * La hiérarchie interne est recréée après l'ajout.
     *
     * @param l le lycanthrope à ajouter
     */
    public void addMember(Lycanthrope l) {
        members.add(l);
        l.setPack(this);
        l.setLone(false);

        createHierarchy();
    }

    /**
     * Supprime un membre de la meute et met à jour sa référence à la meute.
     * La hiérarchie est recréée si le membre était un Alpha ou un Omega.
     *
     * @param l le lycanthrope à supprimer
     */
    public void removeMember(Lycanthrope l) {
        members.remove(l);
        l.setPack(null);
        l.setLone(true);

        if (l.getRank() == Rank.ALPHA || l.getRank() == Rank.OMEGA) {
            createHierarchy();
        }
    }

    /**
     * Crée une meute à partir d'une liste de lycanthropes solitaires.
     * Un couple Alpha sera créé si au moins un mâle et une femelle adultes sont présents.
     *
     * @param l liste de lycanthropes solitaires
     * @return la nouvelle meute si un couple Alpha peut être formé, sinon {@code null}
     */
    public static Pack createPackWithSolitary(List<Lycanthrope> l) {
        Pack pack = new Pack();
        for (Lycanthrope ls : l) {
            if ("M".equals(String.valueOf(ls.getSex())) || "F".equals(String.valueOf(ls.getSex()))) {
                pack.addMember(ls);
            }
        }
        if (pack.getMembers().stream().anyMatch(m -> "M".equals(String.valueOf(m.getSex()))) &&
                pack.getMembers().stream().anyMatch(f -> "F".equals(String.valueOf(f.getSex())))) {
            pack.createHierarchy();
            GameEvents.log("crétion d'une nouvelle hiérarchie à partir de lycanthropes solitaires");
            return pack;
        }
        return null;
    }

    /**
     * Retourne le nom de la meute.
     *
     * @return nom de la meute
     */
    public String getName() {
        return name;
    }

    /**
     * Retourne la liste des membres de la meute.
     *
     * @return liste des lycanthropes membres
     */
    public List<Lycanthrope> getMembers() {
        return members;
    }

    /**
     * Retourne le mâle Alpha de la meute.
     *
     * @return le lycanthrope mâle Alpha, ou {@code null} si aucun
     */
    public Lycanthrope getAlphaMale() {
        return alphaCouple != null ? alphaCouple.getMale() : null;
    }

    /**
     * Retourne la femelle Alpha de la meute.
     *
     * @return le lycanthrope femelle Alpha, ou {@code null} si aucun
     */
    public Lycanthrope getAlphaFemale() {
        return alphaCouple != null ? alphaCouple.getFemale() : null;
    }

    /**
     * Affiche tous les membres et leurs caractéristiques détaillées.
     */
    public void displayPack() {
        GameEvents.log("Membres de la meute :");
        for (Lycanthrope l : members) {
            l.printCharacteristics();
        }
    }

    /**
     * Crée la hiérarchie de la meute, en s'assurant qu'il y a au moins
     * un Omega et un couple Alpha.
     */
    public void createHierarchy() {
        members.sort(Comparator.comparingInt(l -> (l.getRank() != null) ? l.getRank().getValue() : 25));

        boolean hasOmega = members.stream().anyMatch(l -> l.getRank() == Rank.OMEGA);

        if (!hasOmega) {
            Lycanthrope weakest = members.stream().min(Comparator.comparingInt(Lycanthrope::getStrength)).orElse(null);
            if (weakest != null) {
                weakest.setRank(Rank.OMEGA);
                GameEvents.log(weakest.getName() + " devient le souffre douleur de la meute");
            }
        }

        setAlphaCouple();
    }

    /**
     * Détermine le couple Alpha (mâle et femelle adultes les plus forts)
     * et l'assigne à {@link #alphaCouple}.
     */
    public void setAlphaCouple() {
        Lycanthrope bestMale = null;
        Lycanthrope bestFemale = null;

        for (Lycanthrope l : members) {
            if ("adulte".equals(l.getAgeGroup())) {
                if (l.getSex() == 'M' && (bestMale == null || l.getStrength() > bestMale.getStrength())) {
                    bestMale = l;
                }
                if (l.getSex() == 'F' && (bestFemale == null || l.getStrength() > bestFemale.getStrength())) {
                    bestFemale = l;
                }
            }
        }
        if (bestMale != null && bestFemale != null) {
            this.alphaCouple = new AlphaCouple(bestMale, bestFemale, this);
        } else {
            this.alphaCouple = null;
        }
    }

    /**
     * Met à jour le couple Alpha après une domination.
     * Le nouveau mâle Alpha est le vainqueur du combat,
     * et la femelle Alpha est la femelle adulte ayant le plus haut niveau.
     *
     * @param newAlphaMale le nouveau mâle Alpha
     */
    public void updateAlphasAfterDomination(Lycanthrope newAlphaMale) {
        GameEvents.log("Le couple Alpha de la meute est en train de changer !");

        Lycanthrope newAlphaFemale = null;
        for (Lycanthrope l : members) {
            if (l.getSex() == 'F' && "adulte".equals(l.getAgeGroup())) {
                if (newAlphaFemale == null || l.getLevel() > newAlphaFemale.getLevel()) {
                    newAlphaFemale = l;
                }
            }
        }

        if (newAlphaMale != null && newAlphaFemale != null) {
            this.alphaCouple = new AlphaCouple(newAlphaMale, newAlphaFemale, this);
            GameEvents.log("Le nouveau couple Alpha est : " + this.alphaCouple.getMale().getName() + " et " + this.alphaCouple.getFemale().getName());
        } else {
            this.alphaCouple = null;
            GameEvents.log("Impossible de former un nouveau couple Alpha.");
        }
    }

    /**
     * Vérifie si un lycanthrope est le dernier de son rang et de son sexe dans la meute.
     *
     * @param rank le rang à vérifier
     * @param sex le sexe à vérifier ('M' ou 'F')
     * @return {@code true} si c'est le dernier du rang et du sexe, sinon {@code false}
     */
    public boolean isLastOfRank(Rank rank, char sex) {
        int count = 0;
        for (Lycanthrope member : members) {
            if (member.getSex() == sex && rank == member.getRank()) {
                count++;
            }
        }
        return count <= 1;
    }

    /**
     * Crée une nouvelle portée à partir du couple Alpha.
     * Si aucun couple Alpha n'est présent, la portée ne peut pas être créée.
     */
    public void createLitter() {
        if (alphaCouple != null) {
            alphaCouple.reproduce();
        } else {
            GameEvents.log("Aucun couple Alpha dans la meute pour se reproduire.");
        }
    }

    /**
     * Diminue le facteur de domination de chaque membre de la meute.
     * (Méthode actuellement vide, à implémenter selon la logique désirée)
     */
    public void decreaseDominationOfMembers() {

    }
}
