package fr.amu.iut.model.characters.jobs;

import fr.amu.iut.model.Inventory;
import fr.amu.iut.model.characters.Character;
import fr.amu.iut.model.characters.Faction;
import fr.amu.iut.model.characters.Fighter;
import fr.amu.iut.model.characters.Leader;
import fr.amu.iut.model.characters.Worker;
import fr.amu.iut.model.items.foods.Food;
import fr.amu.iut.model.items.foods.FoodType;
import fr.amu.iut.model.items.foods.FreshnessStatus;
import fr.amu.iut.model.items.potion.MagicPotion;

public class Druid extends fr.amu.iut.model.characters.Character implements Leader, Worker, Fighter {

    public Druid(String name, char sex, int size, int age, int strength, int endurance, Faction faction) {
        super(name, sex, size, age, strength, endurance, faction );
    }

    @Override
    public void lead(fr.amu.iut.model.characters.Character character) {
        System.out.println(this.getName() + " is leading " + character.getName());
    }

    @Override
    public void work() {

    }

    @Override
    public void fight(Character character) {
        System.out.println(this.getName() + " is fighting " + character.getName());
    }

    public MagicPotion craftPotion() {
        Inventory inv = this.getInventory();
        System.out.println(this.getName() + " tente de préparer une potion...");

        Food fish = inv.getFood(FoodType.FISH);
        Food clover = inv.getFood(FoodType.CLOVER);

        boolean hasBaseItems = inv.contains(FoodType.MISTLETOE)
                && inv.contains(FoodType.CARROT)
                && inv.contains(FoodType.SALT)
                && inv.contains(FoodType.HONEY)
                && inv.contains(FoodType.MEAD)
                && inv.contains(FoodType.SECRET_INGREDIENT)
                && (fish != null)
                && (clover != null);

        //Vérification de la fraîcheur des ingrédients de base
        boolean isFreshEnough = false;
        if (hasBaseItems) {
            boolean fishOk = (fish.getStatus() == FreshnessStatus.FRESH || fish.getStatus() == FreshnessStatus.STALE);
            boolean cloverOk = (clover.getStatus() == FreshnessStatus.FRESH);
            isFreshEnough = fishOk && cloverOk;

            if(!isFreshEnough) System.out.println("Les ingrédients ne sont pas assez frais !");
        }

        // Vérification de l'alternative Huile / Betterave
        boolean hasOilOrBeet = inv.contains(FoodType.ROCK_OIL) || inv.contains(FoodType.BEET_JUICE);

        if (hasBaseItems && isFreshEnough && hasOilOrBeet) {

            // Vérification des ingrédients BONUS avant consommation
            boolean addUnicorn = inv.contains(FoodType.TWO_HEADED_UNICORNN_MILK);
            boolean addIdefix = inv.contains(FoodType.IDEFIX_HAIR);

            // Consommation des ingrédients de base
            inv.consume(FoodType.MISTLETOE);
            inv.consume(FoodType.CARROT);
            inv.consume(FoodType.SALT);
            inv.consume(FoodType.CLOVER);
            inv.consume(FoodType.FISH);
            inv.consume(FoodType.HONEY);
            inv.consume(FoodType.MEAD);
            inv.consume(FoodType.SECRET_INGREDIENT);

            // Consommation alternative
            if (inv.contains(FoodType.ROCK_OIL)) inv.consume(FoodType.ROCK_OIL);
            else inv.consume(FoodType.BEET_JUICE);

            // Consommation des bonus
            if (addUnicorn) inv.consume(FoodType.TWO_HEADED_UNICORNN_MILK);
            if (addIdefix) inv.consume(FoodType.IDEFIX_HAIR);

            if (inv.contains(FoodType.LOBSTER)) inv.consume(FoodType.LOBSTER);
            if (inv.contains(FoodType.STRAWBERRY)) inv.consume(FoodType.STRAWBERRY);

            System.out.println(this.getName() + " a préparé une marmite de Potion Magique !");

            return new MagicPotion(addUnicorn, addIdefix);
        }
        else {
            System.out.println("Impossible de créer la potion (Ingrédients manquants ou avariés).");
            return null;
        }
    }
}