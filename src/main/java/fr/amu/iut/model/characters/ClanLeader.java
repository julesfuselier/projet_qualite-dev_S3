package fr.amu.iut.model.characters;

import fr.amu.iut.model.InvasionTheatre;
import fr.amu.iut.model.characters.jobs.Druid;
import fr.amu.iut.model.items.foods.Food;
import fr.amu.iut.model.items.potion.MagicPotion;
import fr.amu.iut.model.items.potion.PotionType;
import fr.amu.iut.model.spaces.Space;

public class ClanLeader extends Character implements Leader {

    private Space managedLocation;
    private CharacterFactory characterFactory = new CharacterFactory();

    // Constructeur de ClanLeader
    public ClanLeader(String name, char sex, int age, Space location) {
        super(name, sex, age);
        this.managedLocation = location;
    }

    @Override
    public void lead(Character character) {
        System.out.println(getName() + " leads " + character.getName());
    }

    // Affiche les informations concernant l'emplacement geré
    public void examineLocation() {
        managedLocation.showCharacteristics();
    }

    // Crée un nouveau personnage dans le village
    public void createNewCharacterInVillage(Faction faction, String role, String name) {
        Character newCharacter = characterFactory.createCharacter(faction, role, name);
        if (newCharacter != null) {
            managedLocation.addCharacter(newCharacter);
            System.out.println(getName() + " has created a new character : " + newCharacter.getName() + " in the village.");
        } else {
            System.out.println("Failure to create character.");
        }
    }

    // Soigne un personnage du village
    public void healCharacterInVillage(Character character, int healAmountToHeal) {
        // Si le personnage concerné est dans l'emplacement geré
        if (managedLocation.getCharacters().contains(character)) {
            character.beHealed(healAmountToHeal);
            System.out.println(getName() + " treat " + character.getName() + ". His health is now " + character.getHealth().get() + ".");
        } else {
            System.out.println(character.getName() + " is not in the village of " + getName() + ".");
        }
    }

    // Nourrit un personnage du village
    public void feedCharacterInVillage(Character character, Food food) {
        // Si le personnage concerné est dans l'emplacement géré et que la nourriture existe
        if (managedLocation.getCharacters().contains(character) && managedLocation.getFoods().contains(food)) {
            character.eat(food);
            managedLocation.removeFood(food);
            System.out.println(getName() + " feeds " + character.getName() + " with " + food.getName() + ".");
        }
        // Si le personnage concerné n'est pas dans l'emplacement geré
        else if (!managedLocation.getCharacters().contains(character)) {
            System.out.println(character.getName() + " is not in the village of " + getName() + ".");
        }
        // Si la nouriture est dusponible dans le village
        else {
            System.out.println(food.getName() + " is not available in the village of " + getName() + ".");
        }
    }

    // Demander au druide de faire une potion
    public void askDruidForMagicPotion(Druid druid) {
        if (managedLocation.getCharacters().contains(druid)) {
            druid.craftMagicPotion(potionType);
            System.out.println(getName() + " ask to " + druid.getName() + " to make a magic potion.");
        } else {
            System.out.println("The druid " + druid.getName() + " is not in the villag.");
        }
    }

    // Donner une potion magique à un personnage du village
    public void giveMagicPotionToCharacterInVillage(Character character, int amount) {
        if (managedLocation.getCharacters().contains(character)) {
            character.drinkMagicPotion(magicPotion, false);
            System.out.println(getName() + " give the magic potion to " + character.getName() + ".");
        } else {
            System.out.println(character.getName() + " is not in the village of " + getName() + ".");
        }
    }

    public void transferCharacter(Character character, Space destination) {
        if (managedLocation.getCharacters().contains(character)) {
            if (destination.authorized(character)) {
                managedLocation.removeCharacter(character);
                destination.addCharacter(character);
                System.out.println(getName() + " a transféré " + character.getName() + " vers " + destination.getName());
            } else {
                System.out.println("Transfert impossible : " + character.getName() + " n'est pas autorisé dans " + destination.getName());
            }
        } else {
            System.out.println(character.getName() + " n'est pas dans le lieu géré par " + getName());
        }
    }

    public void setLocation(Space location) {
        this.managedLocation = location;
    }
}