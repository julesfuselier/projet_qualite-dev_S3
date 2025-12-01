package fr.amu.iut.model.items.potion;

import fr.amu.iut.model.items.Item;

public class MagicPotion extends Item {
    private int doses;
    private PotionType type;

    public MagicPotion(PotionType type) {
        super("Potion Magique (" + type + ")");
        this.doses = 5;
        this.type = type;
    }

    public boolean takeDose() {
        if (doses > 0) {
            doses--;
            return true;
        }
        return false;
    }

    public PotionType getType() { return type; }
    public int getDoses() { return doses; }
}