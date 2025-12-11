package fr.amu.iut.model.lycanthropes;

import fr.amu.iut.model.characters.jobs.Lycanthrope;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Pack {
    private String name;
    private List<Lycanthrope> members = new ArrayList<>();
    private AlphaCouple alphaCouple;
    private boolean loveseasons = false;

    // Constructeur
    public Pack() {
    }

    // Affiche les caractéristiques de la meute
    public void showCharacteristics(){
        System.out.println("Nom: " + name);
        for (Lycanthrope l : members){
            System.out.println("Nom membre " + l.getName());
        }
        System.out.println("Nom du male alpha " + alphaCouple.getMale().getName());
        System.out.println("Nom de female alpha " + alphaCouple.getFemale().getName());
    }

    // Affiche les caractéristiques des membres de la meute
    public void showMembersCharacteristics(){
        for (Lycanthrope l : members){
            System.out.println("Nom membre " + l.getName());
            System.out.println("age du membre " + l.getAgeGroup());
            System.out.println("rang du membre " + l.getRank());
            System.out.println("facteur de domination du membre " + l.getDominationFactor());
            System.out.println("niveau du membre " + l.getLevel());

        }
    }

    // Ajouter un membre à la meute
    public void addMember(Lycanthrope l) {
        members.add(l);
        l.setPack(this);
        l.setLone(false);

        createHierarchy();
    }

    // Supprimer un membre de la meute
    public void removeMember(Lycanthrope l) {
        members.remove(l);
        l.setPack(null);
        l.setLone(true);

        //recrée la hiérarchie si un Alpha ou Omega part
        if (l.getRank() == Rank.ALPHA || l.getRank() == Rank.OMEGA){
            createHierarchy();
        }
    }

    // Créer une meute avec plusieurs lycanthropes solitaires
    public static Pack createPackWithSolitary(List<Lycanthrope> l) {
        Pack pack = new Pack();
        for (Lycanthrope ls : l) {
            if ("M".equals(String.valueOf(ls.getSex())) || "F".equals(String.valueOf(ls.getSex()))) {
                pack.addMember(ls);
            }
        }
        if (pack.getMembers().stream().anyMatch(m -> "M".equals(String.valueOf(m.getSex()))) && pack.getMembers().stream().anyMatch(f -> "F".equals(String.valueOf(f.getSex())))) {
            pack.createHierarchy();
            System.out.println("crétion d'une nouvelle hiérarchie à partir de lycanthropes solitaires");
            return pack;
        }
        return null;
    }

    //Récupérer le nom de la meute
    public String getName() {
        return name;
    }

    // Afficher la liste des membres de la meute
    public List<Lycanthrope> getMembers() {
        return members;
    }

    // Récupérer le male le plus fort
    public Lycanthrope getAlphaMale() {
        return alphaCouple != null ? alphaCouple.getMale() : null;
    }

    // Récupérer la femelle la plus forte
    public Lycanthrope getAlphaFemale() {
        return alphaCouple != null ? alphaCouple.getFemale() : null;
    }

    // Affiche les membres et les caractéristiques de la meute
    public void displayPack() {
        System.out.println("Membres de la meute :");
        for (Lycanthrope l : members) {
            l.printCharacteristics();
        }
    }

    // Création d'une hiérarchie avec au moins un Omega et un couple Alpha
    public void createHierarchy() {
        members.sort(Comparator.comparingInt(l -> (l.getRank() != null) ? l.getRank().getValue() : 25));

        boolean hasOmega = members.stream().anyMatch(l -> l.getRank() == Rank.OMEGA);

        if (!hasOmega) {
            Lycanthrope weakest = members.stream().min(Comparator.comparingInt(Lycanthrope::getStrength)).orElse(null);
            if (weakest != null) {
                weakest.setRank(Rank.OMEGA);
                System.out.println(weakest.getName() + " devient le souffre douleur de la meute");
            }
        }

        setAlphaCouple();
    }

    // Créer le couple alpha (mâle et femelle adultes les plus forts)
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
     * Le nouveau mâle Alpha est le vainqueur du combat.
     * La nouvelle femelle Alpha est la femelle adulte avec le plus haut niveau.
     * @param newAlphaMale Le lycanthrope qui est devenu le nouveau mâle Alpha.
     */
    public void updateAlphasAfterDomination(Lycanthrope newAlphaMale) {
        System.out.println("Le couple Alpha de la meute est en train de changer !");

        Lycanthrope newAlphaFemale = null;
        for (Lycanthrope l : members) {
            // On ne cherche que parmi les femelles adultes
            if (l.getSex() == 'F' && "adulte".equals(l.getAgeGroup())) {
                if (newAlphaFemale == null || l.getLevel() > newAlphaFemale.getLevel()) {
                    newAlphaFemale = l;
                }
            }
        }
        
        if (newAlphaMale != null && newAlphaFemale != null) {
            this.alphaCouple = new AlphaCouple(newAlphaMale, newAlphaFemale, this);
            System.out.println("Le nouveau couple Alpha est : " + this.alphaCouple.getMale().getName() + " et " + this.alphaCouple.getFemale().getName());
        } else {
            this.alphaCouple = null;
            System.out.println("Impossible de former un nouveau couple Alpha.");
        }
    }

    /**
     * Vérifie si un lycanthrope est le dernier de son rang et de son sexe dans la meute.
     * @param rank Le rang à vérifier.
     * @param sex Le sexe à vérifier.
     * @return true si c'est le dernier, false sinon.
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

    // Créer une portée
    public void createLitter() {
        if (alphaCouple != null) {
            alphaCouple.reproduce();
        } else {
            System.out.println("Aucun couple Alpha dans la meute pour se reproduire.");
        }
    }

    public void decreaseDominationOfMembers() {
    }
}