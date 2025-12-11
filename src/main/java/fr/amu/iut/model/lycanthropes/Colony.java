package fr.amu.iut.model.lycanthropes;

import fr.amu.iut.GameConfig;
import fr.amu.iut.model.characters.Character;
import fr.amu.iut.model.characters.jobs.Lycanthrope;
import fr.amu.iut.model.spaces.Space;
import fr.amu.iut.util.GameEvents;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Représente une colonie de lycanthropes, constituée de toutes les meutes sur un même lieu.
 */
public class Colony {
    private Space location;
    private List<Pack> packs;
    private final Random random;
    private final GameConfig gameConfig = GameConfig.getInstance();

    public Colony(Space location) {
        this.location = location;
        this.packs = new ArrayList<>();
        this.random = new Random();
    }

    /**
     * Point d'entrée pour la gestion temporelle de la colonie (appelé à chaque tour).
     */
    public void manageTime() {
        GameEvents.log("\n--- Gestion de la Colonie à " + location.getName() + " ---");

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
        GameEvents.log("Colonie du lieu : " + location.getName());
        for (Pack pack : packs) {
            GameEvents.log(" > Meute : " + pack.getName());
            pack.showMembersCharacteristics();
        }
    }

    /**
     * Crée de nouvelles meutes si des lycanthropes solitaires sont présents.
     */
    private void createNewPacksIfNeeded() {
        List<Lycanthrope> solitaries = location.getCharacters().stream()
                .filter(c -> c instanceof Lycanthrope)
                .map(c -> (Lycanthrope) c)
                .filter(Lycanthrope::isLone)
                .toList();

        if (!solitaries.isEmpty()) {
            Pack newPack = Pack.createPackWithSolitary(solitaries);
            if (newPack != null) {
                this.packs.add(newPack);
                GameEvents.log("Une nouvelle meute a été formée !");
            }
        }
    }

    /**
     * Gère la reproduction des meutes en fonction d'une probabilité définie.
     */
    private void handleReproduction() {
        if (random.nextInt(gameConfig.getProbabilityLycanReproduction()) == 0) {
            GameEvents.log("C'est la saison des amours !");
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

    /**
     * Fait évoluer la hiérarchie des meutes en diminuant le facteur de domination des membres.
     */
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

    /**
     * Vieillit tous les lycanthropes de la colonie et met à jour leur catégorie d'âge.
     */
    private void ageLycanthropes() {
        for (Character c : location.getCharacters()) {
            if (c instanceof Lycanthrope l) {
                l.setAge(l.getAge() + 1);
                updateAgeCategory(l);
            }
        }
    }


    /**
     * Met à jour la catégorie d'âge d'un lycanthrope et journalise le changement si nécessaire.
     * @param l Le lycanthrope dont la catégorie d'âge doit être mise à jour.
     */
    private void updateAgeCategory(Lycanthrope l) {
        String oldGroup = l.getAgeGroup();
        if (l.getAge() < 15) l.setAgeGroup("jeune");
        else if (l.getAge() < 50) l.setAgeGroup("adulte");
        else l.setAgeGroup("vieux");

        if (!l.getAgeGroup().equals(oldGroup)) {
            GameEvents.log(l.getName() + " est devenu " + l.getAgeGroup());
        }
    }

    /**
     * Gère les transformations aléatoires des lycanthropes.
     */
    private void handleTransformations() {
        List<Character> charactersSnapshot = new ArrayList<>(location.getCharacters());

        for (Character c : charactersSnapshot) {
            if (c instanceof Lycanthrope l) {
                if (random.nextInt(gameConfig.getProbabilityLycanTransformation()) == 0) {
                    l.transformToHuman();
                }
            }
        }
    }

    /**
     * Diffuse un hurlement à tous les lycanthropes de la colonie.
     * @param howl Le hurlement à diffuser.
     */
    public void broadcastHowl(Howl howl) {
        GameEvents.log("\n[HURLEMENT] " + howl.getEmitter().getName() + " pousse un hurlement puissant !");
        howl.showCharacteristics();

        for (Character c : location.getCharacters()) {
            if (c instanceof Lycanthrope l) {
                if (l != howl.getEmitter()) {
                    l.hearHowl(howl);
                }
            }
        }
    }

    /**
     * Génère des hurlements aléatoires de la part des lycanthropes de la colonie.
     */
    private void generateRandomHowls() {
        // Parcours explicite des meutes
        Iterator<Pack> packIt = packs.iterator();
        while (packIt.hasNext()) {
            Pack pack = packIt.next();

            // Parcours explicite des membres
            Iterator<Lycanthrope> memberIt = pack.getMembers().iterator();
            while (memberIt.hasNext()) {
                Lycanthrope l = memberIt.next();
                if (random.nextInt(gameConfig.getProbabilityHowl()) == 0) {
                    l.howl(HowlType.BELONGING, true);
                }
            }
        }
    }

    /**
     * Ajoute une meute à la colonie.
     * @param pack La meute à ajouter.
     */
    public void addPack(Pack pack) {
        this.packs.add(pack);
    }

    /**
     * Retourne la liste des meutes de la colonie.
     * @return La liste des meutes.
     */
    public List<Pack> getPacks() {
        return packs;
    }
}