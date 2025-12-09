package fr.amu.iut.model.lycanthropes;

import fr.amu.iut.model.characters.jobs.Lycanthrope;
import java.util.ArrayList;
import java.util.List;

public class Pack {
    private List<Lycanthrope> members = new ArrayList<>();
    private Lycanthrope alphaMale;
    private Lycanthrope alphaFemale;

    // Constructeur
    public Pack() {
    }

    // Ajouter un membre à la meute
    public void addMember(Lycanthrope l) {
        members.add(l);
        l.setPack(this);
        l.setLone(false);
    }

    // Supprimer un membre de la meute
    public void removeMember(Lycanthrope l) {
        members.remove(l);
        l.setPack(null);
        l.setLone(true);
    }

    // Afficher la liste des membres de la meute
    public List<Lycanthrope> getMembers() {
        return members;
    }

    // Récupérer le male le plus fort (basé sur la force, force max)
    public Lycanthrope getAlphaMale() {
        return alphaMale;
    }

    // Récupérer la femelle la plus forte (basé sur le niveau, niveau max)
    public Lycanthrope getAlphaFemale() {
        return alphaFemale;
    }

    // Affiche les membres et les charactéristiques de la meute
    public void displayPack() {
        System.out.println("Pack members:");
        for (Lycanthrope l : members) {
            l.printCharacteristics();
        }
    }

    public void createHierarchy() {
        // TODO : Tri par rang, force, etc. à implémenter
    }

    // Créer le couple alpha (male et femelle les plus forts)
    public void setAlphaCouple() {
        // Trouver le mâle adulte le plus fort et la femelle adulte au plus haut niveau
        Lycanthrope bestMale = null;
        Lycanthrope bestFemale = null;
        for (Lycanthrope l : members) {
            if (l.getSex() == 'M' && (bestMale == null || l.getStrength() > bestMale.getStrength())) {
                bestMale = l;
            }
            if (l.getSex() == 'F' && (bestFemale == null || l.getLevel() > bestFemale.getLevel())) {
                bestFemale = l;
            }
        }
        this.alphaMale = bestMale;
        this.alphaFemale = bestFemale;
    }
}
