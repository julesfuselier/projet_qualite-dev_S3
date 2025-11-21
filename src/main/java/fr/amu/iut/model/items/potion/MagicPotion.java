package fr.amu.iut.model.items.potion;

public class MagicPotion {
    private int doses; // Une marmite contient plusieurs doses [cite: 50]
    private boolean hasUnicornMilk; // Pouvoir de dédoublement
    private boolean hasIdefixHair;  // Pouvoir de métamorphosis
    private boolean isFresh;        // Pour gérer la qualité globale

    public MagicPotion(boolean hasUnicornMilk, boolean hasIdefixHair) {
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