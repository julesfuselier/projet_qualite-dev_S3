package fr.amu.iut.model.characters;

import fr.amu.iut.model.characters.jobs.Druid;
import fr.amu.iut.model.exceptions.InsufficientIngredientsException;
import fr.amu.iut.model.items.foods.Food;
import fr.amu.iut.model.items.potion.MagicPotion;
import fr.amu.iut.model.items.potion.PotionType;
import fr.amu.iut.model.spaces.Space;
import fr.amu.iut.util.GameEvents;

/**
 * Représente un chef de clan dans le jeu.
 * Un chef de clan peut diriger des personnages, créer de nouveaux personnages,
 * soigner et nourrir les personnages dans son village, demander des potions
 * magiques
 * au druide, et donner des potions magiques aux personnages.
 */
public class ClanLeader extends Character implements Leader {

    private Space managedLocation;
    protected CharacterFactory characterFactory = new CharacterFactory();
    private MagicPotion magicPotion;

    /**
     * Permet de recevoir une potion magique.
     * 
     * @param potion La potion magique reçue.
     */
    public void receiveMagicPotion(MagicPotion potion) {
        this.magicPotion = potion;
    }

    /**
     * Constructeur de la classe ClanLeader.
     *
     * @param name     Le nom du chef de clan
     * @param sex      Le sexe du chef de clan
     * @param age      L'âge du chef de clan
     * @param location L'emplacement géré par le chef de clan
     */
    public ClanLeader(String name, char sex, int age, Space location) {
        super(name, sex, age);
        this.managedLocation = location;
    }

    /**
     * Dirige un personnage.
     * 
     * @param character Le personnage à diriger.
     */
    @Override
    public void lead(Character character) {
        GameEvents.log(getName() + " dirige " + character.getName());
    }

    /**
     * Examine les caractéristiques de l'emplacement géré.
     */
    public void examineLocation() {
        managedLocation.showCharacteristics();
    }

    /**
     * Crée un nouveau personnage dans le village.
     * 
     * @param faction La faction du nouveau personnage.
     * @param role    Le rôle du nouveau personnage.
     * @param name    Le nom du nouveau personnage.
     */
    public void createNewCharacterInVillage(Faction faction, JobType role, String name) {
        Character newCharacter = characterFactory.createCharacter(faction, role, name);
        if (newCharacter != null) {
            try {
                managedLocation.addCharacter(newCharacter);
                GameEvents.log(getName() + " a créé un nouveau personnage : " + newCharacter.getName());
            } catch (Exception e) {
                GameEvents.log("Erreur création : " + e.getMessage());
            }
        }
    }

    /**
     * Soigne un personnage dans le village.
     * 
     * @param character        Le personnage à soigner.
     * @param healAmountToHeal La quantité de soins à appliquer.
     */
    public void healCharacterInVillage(Character character, int healAmountToHeal) {
        if (managedLocation.getCharacters().contains(character)) {
            character.beHealed(healAmountToHeal);
            GameEvents.log(getName() + " soigne " + character.getName() + ". Sa vie est maintenant de "
                    + character.getHealth().get() + ".");
        } else {
            GameEvents.log(character.getName() + " n'est pas dans le village de " + getName() + ".");
        }
    }

    /**
     * Nourrit un personnage dans le village.
     * 
     * @param character Le personnage à nourrir.
     * @param food      La nourriture à donner.
     */
    public void feedCharacterInVillage(Character character, Food food) {
        if (managedLocation.getCharacters().contains(character) && managedLocation.getFoods().contains(food)) {
            character.eat(food);
            managedLocation.removeFood(food);
            GameEvents.log(getName() + " nourrit " + character.getName() + " avec " + food.getName() + ".");
        } else if (!managedLocation.getCharacters().contains(character)) {
            GameEvents.log(character.getName() + " n'est pas dans le village de " + getName() + ".");
        } else {
            GameEvents.log(food.getName() + " n'est pas disponible dans le village de " + getName() + ".");
        }
    }

    /**
     * Demande au druide de fabriquer une potion magique.
     * 
     * @param druid      Le druide à qui demander la potion.
     * @param potionType Le type de potion magique à fabriquer.
     * @throws InsufficientIngredientsException Si le druide n'a pas assez
     *                                          d'ingrédients.
     */
    public void askDruidForMagicPotion(Druid druid, PotionType potionType) throws InsufficientIngredientsException {
        if (managedLocation.getCharacters().contains(druid)) {
            druid.craftMagicPotion(potionType);
            GameEvents.log(
                    getName() + " a demandé à " + druid.getName() + " de faire une potion de type " + potionType + ".");
        } else {
            GameEvents.log("Le druide " + druid.getName() + " n'est pas dans le village.");
        }
    }

    /**
     * Donne une potion magique à un personnage dans le village.
     * 
     * @param character Le personnage à qui donner la potion.
     * @param amount    La quantité de potion à donner (non utilisée ici).
     */
    public void giveMagicPotionToCharacterInVillage(Character character, int amount) {
        if (managedLocation.getCharacters().contains(character)) {
            if (magicPotion != null) {
                character.drinkMagicPotion(magicPotion, false);
                GameEvents.log(getName() + " donne une potion magique à " + character.getName() + ".");
                magicPotion = null; // La potion a été utilisée
            } else {
                GameEvents.log(getName() + " n'a pas de potion magique à donner.");
            }
        } else {
            GameEvents.log(character.getName() + " n'est pas disponible dans le village de " + getName() + ".");
        }
    }

    /**
     * Transfère un personnage vers un autre emplacement.
     * 
     * @param character   Le personnage à transférer.
     * @param destination L'emplacement de destination.
     */
    public void transferCharacter(Character character, Space destination) {
        if (managedLocation.getCharacters().contains(character)) {
            try {
                destination.addCharacter(character);
                managedLocation.removeCharacter(character);
                GameEvents.log(getName() + " a transféré " + character.getName() + " vers " + destination.getName());
            } catch (Exception e) {
                GameEvents.log("Transfert impossible : " + e.getMessage());
            }
        } else {
            GameEvents.log(character.getName() + " n'est pas dans le lieu géré par " + getName());
        }
    }

    /**
     * Définit l'emplacement géré par le chef de clan.
     * 
     * @param location Le nouvel emplacement géré.
     */
    public void setLocation(Space location) {
        this.managedLocation = location;
    }
}