package fr.amu.iut.model.characters;

import fr.amu.iut.model.Location;
import fr.amu.iut.model.characters.jobs.Druid;
import fr.amu.iut.model.items.foods.Food;

public class ClanLeader extends Character implements Leader {

    private final Location managedLocation;
    private CharacterFactory characterFactory = new CharacterFactory();

    // ClanLeader constructor
    public ClanLeader(String name, char sex, int age, Location location) {
        super(name, sex, age);
        this.managedLocation = location;
    }

    @Override
    public void lead(Character character) {
        System.out.println(getName() + " leads " + character.getName());
    }

    // Displays the characteristics of the managed location
    public void examineLocation() {
        managedLocation.displayCharacteristics();
    }

    // Create a new character in the village
    public void createNewCharacterInVillage(Faction faction, String role, String name) {
        Character newCharacter = characterFactory.createCharacter(faction, role, name);
        if (newCharacter != null) {
            managedLocation.addCharacter(newCharacter);
            System.out.println(getName() + " has created a new character : " + newCharacter.getName() + " in the village.");
        } else {
            System.out.println("Failure to create character.");
        }
    }

    // Heal a character from the village
    public void healCharacterInVillage(Character character, int healAmountToHeal) {
        // If the character concerned is in the managed location
        if (managedLocation.getCharacters().contains(character)) {
            character.beHealed(healAmountToHeal);
            System.out.println(getName() + " treat " + character.getName() + ". His health is now " + character.getHealth().get() + ".");
        } else {
            System.out.println(character.getName() + " is not in the village of " + getName() + ".");
        }
    }

    // Feel a character from the village
    public void feedCharacterInVillage(Character character, Food food) {
        // If the character concerned is in the managed location & that food exists
        if (managedLocation.getCharacters().contains(character) && managedLocation.getFoods().contains(food)) {
            character.eat(food);
            managedLocation.removeFood(food);
            System.out.println(getName() + " feeds " + character.getName() + " with " + food.getName() + ".");
        }
        // If the character concerned is not in the managed location
        else if (!managedLocation.getCharacters().contains(character)) {
            System.out.println(character.getName() + " is not in the village of " + getName() + ".");
        }
        // If food is not available in this village
        else {
            System.out.println(food.getName() + " is not available in the village of " + getName() + ".");
        }
    }

    // Ask the druid to make a potion
    public void askDruidForMagicPotion(Druid druid) {
        if (managedLocation.getCharacters().contains(druid)) {
            druid.makeMagicPotion();
            System.out.println(getName() + " ask to " + druid.getName() + " to make a magic potion.");
        } else {
            System.out.println("The druid " + druid.getName() + " is not in the villag.");
        }
    }

    // Give a magic potion to a character in the village
    public void giveMagicPotionToCharacterInVillage(Character character, int amount) {
        if (managedLocation.getCharacters().contains(character)) {
            character.drinkMagicPotion(amount);
            System.out.println(getName() + " give the magic potion to " + character.getName() + ".");
        } else {
            System.out.println(character.getName() + " is not in the village of " + getName() + ".");
        }
    }

    // Transfer a character from their location to a battlefield or enclosure
    public void transferCharacter(Character character) {

    }
}