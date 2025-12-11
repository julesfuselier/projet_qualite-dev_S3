package fr.amu.iut.model.spaces;

import fr.amu.iut.model.characters.Character;
import fr.amu.iut.model.characters.Faction;
import fr.amu.iut.model.characters.Fighter;
import fr.amu.iut.model.items.foods.Food;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static java.util.Collections.shuffle;
/**
 * Classe abstraite représentant un espace dans le jeu.
 * Les espaces peuvent être des champs de bataille, des enclos, des villages gaulois,
 * des villages gallo-romains, des villes romaines ou des camps fortifiés romains.
 * Chaque espace a un nom, une surface, un leader (optionnel), une liste de personnages présents
 * et une liste de nourritures disponibles.
 * Les espaces peuvent autoriser ou non l'entrée de certains personnages en fonction de leur faction.
 * Ils peuvent également gérer les combats entre personnages présents dans un champ de bataille.
 */
public abstract sealed class Space permits Battlefield, Enclosure, GallicVillage, GalloRomanVillage, RomanCity, RomanFortifiedCamp {
    private final String name;
    private double surface;
    private Character leader;

    private final Set<Character> characters;

    private final List<Food> foods;
    private Random random;

    /**
     * Constructeur de la classe Space.
     *
     * @param name    Le nom de l'espace.
     * @param surface La surface de l'espace.
     * @param leader  Le personnage leader de l'espace (optionnel).
     */
    public Space(String name, double surface, Character leader) {
        this.name = name;
        this.surface = surface;
        this.leader = leader;
        this.characters = new HashSet<>();
        this.foods = new ArrayList<>();
    }

    /**
     * Constructeur de la classe Space sans leader.
     *
     * @param name    Le nom de l'espace.
     * @param surface La surface de l'espace.
     */
    public Space(String name, double surface) {
        this.name = name;
        this.surface = surface;
        this.characters = new HashSet<>();
        this.foods = new ArrayList<>();
    }

    /**
     * Constructeur de la classe Space avec seulement le nom.
     *
     * @param name Le nom de l'espace.
     */
    public Space(String name) {
        this.name = name;
        this.characters = new HashSet<>();
        this.foods = new ArrayList<>();
    }

    /**
     * Méthode abstraite pour vérifier si un personnage est autorisé à entrer dans l'espace.
     *
     * @param c Le personnage à vérifier.
     * @return Vrai si le personnage est autorisé, sinon faux.
     */
    public abstract boolean authorized(Character c);

    /**
     * Getter pour le nom de l'espace.
     * @return Le nom de l'espace.
     */
    public String getName() {
        return name;
    }

    /**
     * Getter pour la surface de l'espace.
     * @return La surface de l'espace.
     */
    public double getSurface() {
        return surface;
    }

    /**
     * Setter pour la surface de l'espace.
     * @param surface La nouvelle surface de l'espace.
     */
    public void setSurface(double surface) {
        this.surface = surface;
    }

    /**
     * Getter pour le leader de l'espace.
     * @return Le leader de l'espace.
     */
    public Character getLeader() {
        return leader;
    }
    /**
     * Setter pour le leader de l'espace.
     * @param leader Le nouveau leader de l'espace.
     */
    public void setLeader(Character leader) {
        this.leader = leader;
    }

    /**
     * Getter pour les personnages présents dans l'espace.
     * @return L'ensemble des personnages présents dans l'espace.
     */
    public Set<Character> getCharacters() {
        return characters;
    }

    /**
     * Getter pour les nourritures disponibles dans l'espace.
     * @return La liste des nourritures disponibles dans l'espace.
     */
    public List<Food> getFoods() {
        return foods;
    }

    /**
     * Ajoute une nourriture à l'espace.
     * @param food La nourriture à ajouter.
     */
    public void addFood(Food food) {
        this.foods.add(food);
    }

    /**
     * Retire une nourriture de l'espace.
     * @param food La nourriture à retirer.
     */
    public void removeFood(Food food) {
        this.foods.remove(food);
    }

    /**
     * Vérifie si l'espace est un champ de bataille.
     * @return Vrai si l'espace est un champ de bataille, sinon faux.
     */
    public boolean isBattlefield() {
        return this instanceof Battlefield;
    }

    /**
     * Ajoute un personnage à l'espace s'il est autorisé.
     * @param c Le personnage à ajouter.
     * @return Vrai si le personnage a été ajouté, sinon faux.
     */
    public boolean addCharacter(Character c) {
        if (!authorized(c)){
            return false;
        }
        return characters.add(c);
    }

    /**
     * Retire un personnage de l'espace.
     * @param c Le personnage à retirer.
     */
    public void removeCharacter(Character c) {
        characters.remove(c);
    }

    /**
     * Affiche les caractéristiques de l'espace, y compris le nom, la surface,
     * le leader (si applicable), les personnages présents et les nourritures disponibles.
     */
    public void showCharacteristics(){
        System.out.println("Nom: " + name);
        System.out.println("Surface : " + surface);
        if(!(this instanceof Battlefield)){
            System.out.println("Personnage principal: " + leader);
        }
        System.out.println("Personnages: " + characters.size());
        for(Character c : characters){
            System.out.println(" - " + c.getName());
        }

        for(Food f : foods){
            System.out.println("Nourriture disponible : " + f.getName());
        }
    }

    /**
     * Soigne un personnage dans l'espace.
     * @param c Le personnage à soigner.
     * @param amount La quantité de soins à appliquer.
     */
    public void healCharacter(Character c, int amount){
        if(!(characters.contains(c))){
            return;
        }
        c.beHealed(amount);
    }

    /**
     * Permet à un personnage de manger une nourriture dans l'espace.
     * @param c Le personnage qui mange.
     * @param f La nourriture à manger.
     */
    public void eatFood(Character c, Food f){
        if(!(foods.contains(f))){
            return;
        } else if (!(characters.contains(c))) {
            return;
        }
        c.eat(f);
        foods.remove(f);
    }

    /**
     * Résout les combats entre personnages dans un champ de bataille.
     * Les personnages de la faction Gaulois combattent contre ceux de la faction Romain.
     * Les combats sont effectués par paires jusqu'à ce qu'il n'y ait plus de combattants disponibles.
     * Les personnages morts sont retirés du champ de bataille.
     */
    public void resolveCombat() {
        if (!isBattlefield() || getCharacters().size() < 2) {
            return;
        }

        List<Character> teamGaulois = new ArrayList<>();
        List<Character> teamRomain = new ArrayList<>();

        for (Character c : getCharacters()) {
            if (c instanceof Fighter) {
                if (c.getFaction() == Faction.GAULOIS) {
                    teamGaulois.add(c);
                } else if (c.getFaction() == Faction.ROMAIN) {
                    teamRomain.add(c);
                }
            }
        }

        if (teamGaulois.isEmpty() || teamRomain.isEmpty()) {
            return;
        }

        System.out.println( "--- BASTON GÉNÉRALE à " + getName() + " ---");

        shuffle(teamGaulois);
        shuffle(teamRomain);

        int fightsCount = Math.min(teamGaulois.size(), teamRomain.size());

        for (int i = 0; i < fightsCount; i++) {
            Character gaulois = teamGaulois.get(i);
            Character romain = teamRomain.get(i);

            if (!gaulois.isDead() && !romain.isDead()) {
                System.out.println("   Duel : " + gaulois.getName() + " VS " + romain.getName());

                ((Fighter) gaulois).fight(romain);

                if (!romain.isDead()) {
                    ((Fighter) romain).fight(gaulois);
                }
            }
        }
        removeDeadCharacters();
    }

    /**
     * Retire les personnages morts du champ de bataille et affiche un message pour chaque personnage tombé au combat.
     */
    private void removeDeadCharacters() {
        getCharacters().removeIf(c -> {
            if (c.isDead()) {
                System.out.println(c.getName() + " est tombé au combat à " + getName() + " !");
                return true;
            }
            return false;
        });
    }
}