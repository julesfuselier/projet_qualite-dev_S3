package fr.amu.iut.model.items.potion;

import fr.amu.iut.model.items.Item;

/**
 * Classe représentant une potion magique avec plusieurs doses.
 */
public class MagicPotion extends Item {
    private int doses;
    private PotionType type;

    /**
     * Constructeur de la classe MagicPotion.
     *
     * @param type Le type de la potion magique.
     */
    public MagicPotion(PotionType type) {
        super("Potion Magique (" + type + ")");
        this.doses = 5;
        this.type = type;
    }

    /**
     * Permet de prendre une dose de la potion magique.
     *
     * @return true si une dose a été prise, false si aucune dose n'est disponible.
     */
    public boolean takeDose() {
        if (doses > 0) {
            doses--;
            return true;
        }
        return false;
    }

    /**
     * Obtient le type de la potion magique.
     *
     * @return Le type de la potion magique.
     */
    public PotionType getType() { return type; }

    /**
     * Obtient le nombre de doses restantes dans la potion magique.
     *
     * @return Le nombre de doses restantes.
     */
    public int getDoses() { return doses; }
}