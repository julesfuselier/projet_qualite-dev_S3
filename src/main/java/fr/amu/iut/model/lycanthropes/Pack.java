package fr.amu.iut.model.lycanthropes;

import fr.amu.iut.model.characters.jobs.Lycanthrope;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static fr.amu.iut.model.characters.jobs.Lycanthrope.getRankFromValue;

public class Pack {
    private String name;
    private List<Lycanthrope> members = new ArrayList<>();
    private Lycanthrope alphaMale;
    private Lycanthrope alphaFemale;
    private boolean loveseasons = false;

    // Constructeur
    public Pack() {
    }

    //affiche les caractéristiques de la meute
    public void showCharacteristics(){
        System.out.println("Nom: " + name);
        for (Lycanthrope l : members){
            System.out.println("Nom membre " + l.getName());
        }
        System.out.println("Nom du male alpha " + alphaMale.getName());
        System.out.println("Nom de female alpha " + alphaFemale.getName());
    }

    //affiche les caractéristiques des membres de la meute
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

        //recrée la hiérarchie si un 𝞪 ou 𝟂 part
        if (l.getRank().equals("𝞪") || l.getRank().equals("𝟂")){
            createHierarchy();
        }
    }

    //créer une meute avec plusieurs lycanthropes solitaires
    public static Pack createPackWithSolitary(List<Lycanthrope> l) {
        Pack pack = new Pack();
        for (Lycanthrope ls : l) {
            if ("M".equals(String.valueOf(ls.getSex())) || "F".equals(String.valueOf(ls.getSex()))) {
                pack.addMember(ls);
            }
        }
        if (pack.getMembers().stream().anyMatch(m -> "M".equals(m.getSex())) && pack.getMembers().stream().anyMatch(f -> "F".equals(f.getSex()))) {
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
        return alphaMale;
    }

    // Récupérer la femelle la plus forte
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

    // Creation d'une hiérarchie avec au moins un 𝟂 et un couple 𝞪
    public void createHierarchy() {
        members.sort(Comparator.comparingInt(l -> Lycanthrope.getRankValue(l.getRank())));

        boolean Omega = members.stream().anyMatch(l ->"𝟂".equals(l.getRank()));

        if (!Omega) {
            Lycanthrope weakest = members.stream().min(Comparator.comparingInt(Lycanthrope::getStrength)).orElse(null);
            if (weakest != null) {
                weakest.setRank("𝟂");
                System.out.println(weakest.getName() + "devient le souffre douleur de la meute");
            }
        }

        setAlphaCouple();
    }

    // Créer le couple alpha (male et femelle adultes les plus forts)
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
        this.alphaMale = bestMale;
        this.alphaFemale = bestFemale;
    }

    /**
     * Met à jour le couple Alpha après une domination.
     * Le nouveau mâle Alpha est le vainqueur du combat.
     * La nouvelle femelle Alpha est la femelle adulte avec le plus haut niveau.
     * @param newAlphaMale Le lycanthrope qui est devenu le nouveau mâle Alpha.
     */
    public void updateAlphasAfterDomination(Lycanthrope newAlphaMale) {
        System.out.println("Le couple Alpha de la meute est en train de changer !");
        this.alphaMale = newAlphaMale;

        Lycanthrope newAlphaFemale = null;
        for (Lycanthrope l : members) {
            // On ne cherche que parmi les femelles adultes
            if (l.getSex() == 'F' && "adulte".equals(l.getAgeGroup())) {
                if (newAlphaFemale == null || l.getLevel() > newAlphaFemale.getLevel()) {
                    newAlphaFemale = l;
                }
            }
        }
        this.alphaFemale = newAlphaFemale;
        System.out.println("Le nouveau couple Alpha est : " + this.alphaMale.getName() + " et " + (this.alphaFemale != null ? this.alphaFemale.getName() : "personne"));
    }

    /**
     * Vérifie si un lycanthrope est le dernier de son rang et de son sexe dans la meute.
     * @param rank Le rang à vérifier.
     * @param sex Le sexe à vérifier.
     * @return true si c'est le dernier, false sinon.
     */
    public boolean isLastOfRank(String rank, char sex) {
        int count = 0;
        for (Lycanthrope member : members) {
            if (member.getSex() == sex && rank.equals(member.getRank())) {
                count++;
            }
        }
        return count <= 1;
    }

    // Créer une portée
    public void createLitter() {
        if (alphaMale != null && alphaFemale != null) {
            int numberOfYoung = new java.util.Random().nextInt(7) + 1;

            // Déterminer le rang de la nouvelle portée
            String youngRank = "γ";
            boolean betaExists = members.stream().anyMatch(member -> "β".equals(member.getRank()));
            if (!betaExists) {
                youngRank = "β";
            }

            for (int i = 0; i < numberOfYoung; i++) {
                char sex = new java.util.Random().nextBoolean() ? 'M' : 'F';
                String name = "Young " + (i + 1);
                // Statistiques par défaut pour un jeune lycanthrope
                int size = 150;
                int age = 0;
                int strength = 5;
                int endurance = 5;
                int dominationFactor = 0;
                double impulsiveness = 0.5;

                Lycanthrope young = new Lycanthrope(name, sex, size, age, strength, endurance, "jeune", youngRank, dominationFactor, impulsiveness, this, false);
                addMember(young);
            }
            System.out.println(numberOfYoung + " nouveau(x) lycanthrope(s) de rang " + youngRank + " sont nés.");
        }
    }
}
