package fr.amu.iut.model.items.potion;

import fr.amu.iut.model.items.Item;

public class MagicPotion extends Item {
    private int doses;
    private boolean hasUnicornMilk;
    private boolean hasIdefixHair;
    private boolean isFresh;

    public MagicPotion(boolean hasUnicornMilk, boolean hasIdefixHair) {
        super(hasIdefixHair && hasUnicornMilk ? "Potion of Ultimate Magic" :
              hasUnicornMilk ? "Potion of Unicorn Magic" :
              hasIdefixHair ? "Potion of Lycanthrope Magic" :
              "Ordinary Magic Potion");
        this.doses = 5;
        this.hasUnicornMilk = hasUnicornMilk;
        this.hasIdefixHair = hasIdefixHair;
        this.isFresh = true;
    }

    public boolean takeDose() {
        if (doses > 0) {
            doses--;
            return true;
        }
        return false;
    }

    public boolean hasUnicornEffect() { return hasUnicornMilk; }
    public boolean hasLycanthropeEffect() { return hasIdefixHair; }
    public int getDoses() { return doses; }
}