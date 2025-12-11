package fr.amu.iut.model.lycanthropes;

import fr.amu.iut.model.characters.Character;
import fr.amu.iut.model.characters.jobs.Lycanthrope;
import fr.amu.iut.model.spaces.Space;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Représente une colonie de lycanthropes, constituée de toutes les meutes sur un même lieu.
 */
public class Colony {
    private Space location;
    private List<Pack> packs;
    private Random random;

    public Colony(Space location) {
        this.location = location;
        this.packs = new ArrayList<>();
        this.random = new Random();
    }

    /**
     * Point d'entrée pour la gestion temporelle de la colonie (appelé à chaque tour).
     */
    public void manageTime() {
        System.out.println("\n--- Gestion de la Colonie à " + location.getName() + " ---");

        createNewPacksIfNeeded();
        handleReproduction();
        evolveHierarchy();
        ageLycanthropes();
        generateRandomHowls();
        handleTransformations();
    }

    /**
     * Affiche tous les lycanthropes de toutes les meutes.
     */
    public void showAllLycanthropes() {
        System.out.println("Colonie du lieu : " + location.getName());
        for (Pack pack : packs) {
            System.out.println(" > Meute : " + pack.getName());
            pack.showMembersCharacteristics();
        }
    }

    private void createNewPacksIfNeeded() {
        List<Lycanthrope> solitaries = new ArrayList<>();
        for (Character c : location.getCharacters()) {
            if (c instanceof Lycanthrope l && l.isLone()) {
                solitaries.add(l);
            }
        }

        if (!solitaries.isEmpty()) {
            Pack newPack = Pack.createPackWithSolitary(solitaries);
            if (newPack != null) {
                this.packs.add(newPack);
                System.out.println("Une nouvelle meute a été formée !");
            }
        }
    }

    private void handleReproduction() {
        if (random.nextInt(10) == 0) { // 10% de chance par tour
            System.out.println("C'est la saison des amours !");
            for (Pack pack : packs) {
                pack.createLitter();
                for(Lycanthrope baby : pack.getMembers()) {
                    if (!location.getCharacters().contains(baby)) {
                        location.addCharacter(baby);
                    }
                }
            }
        }
    }

    private void evolveHierarchy() {
        for (Pack pack : packs) {
            pack.decreaseDominationOfMembers();
            for (Lycanthrope l : pack.getMembers()) {
                if (l.getDominationFactor() > 0) {
                    l.setDominationFactor(l.getDominationFactor() - 1);
                }
                l.updateRankFromDominationFactor(); // Méthode existante
            }
        }
    }

    private void ageLycanthropes() {
        for (Character c : location.getCharacters()) {
            if (c instanceof Lycanthrope l) {
                l.setAge(l.getAge() + 1);
                updateAgeCategory(l);
            }
        }
    }

    private void updateAgeCategory(Lycanthrope l) {
        String oldGroup = l.getAgeGroup();
        if (l.getAge() < 15) l.setAgeGroup("jeune");
        else if (l.getAge() < 50) l.setAgeGroup("adulte");
        else l.setAgeGroup("vieux");

        if (!l.getAgeGroup().equals(oldGroup)) {
            System.out.println(l.getName() + " est devenu " + l.getAgeGroup());
        }
    }

    private void generateRandomHowls() {
        for (Pack pack : packs) {
            for (Lycanthrope l : pack.getMembers()) {
                if (random.nextInt(20) == 0) { // 5% de chance
                    l.howl("Appartenance");
                }
            }
        }
    }

    private void handleTransformations() {
        List<Character> charactersSnapshot = new ArrayList<>(location.getCharacters());

        for (Character c : charactersSnapshot) {
            if (c instanceof Lycanthrope l) {
                if (random.nextInt(50) == 0) { // 2% de chance de transformation spontanée
                    l.transformToHuman();
                }
            }
        }
    }

    public void addPack(Pack pack) {
        this.packs.add(pack);
    }

    public List<Pack> getPacks() {
        return packs;
    }
}