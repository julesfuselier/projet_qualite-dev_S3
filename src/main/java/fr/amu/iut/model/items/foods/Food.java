package fr.amu.iut.model.items.foods;

import fr.amu.iut.model.items.Item;

/**
 * Enumération représentant le statut de fraîcheur des aliments.
 */
public class Food extends Item {

    private int nutritionValue;
    private boolean isPerishable;
    private FreshnessStatus status;
    private FoodType type;

    /**
     * Constructeur de la classe Food.
     *
     * @param name            Le nom de l'aliment.
     * @param nutritionValue  La valeur nutritionnelle de l'aliment.
     * @param isPerishable    Indique si l'aliment est périssable.
     * @param status          Le statut de fraîcheur de l'aliment.
     * @param type            Le type d'aliment.
     */
    public Food(String name, int nutritionValue, boolean isPerishable, FreshnessStatus status, FoodType type) {
        super(name);
        this.nutritionValue = nutritionValue;
        this.isPerishable = isPerishable;
        this.status = status;
        this.type = type;
    }

    /** Obtient la valeur nutritionnelle de l'aliment.
     *
     * @return La valeur nutritionnelle de l'aliment.
     */
    public int getNutritionValue() { return nutritionValue; }

    /** Indique si l'aliment est périssable.
     *
     * @return true si l'aliment est périssable, false sinon.
     */
    public FreshnessStatus getStatus() { return status; }

    /** Obtient le type d'aliment.
     *
     * @return Le type d'aliment.
     */
    public FoodType getType() { return type; }

    /** Indique si l'aliment est frais.
     *
     * @return true si l'aliment est frais, false sinon.
     */
    public boolean isFresh() { return status == FreshnessStatus.FRESH; }

    /** Définit le statut de fraîcheur de l'aliment.
     *
     * @param status Le nouveau statut de fraîcheur.
     */
    public void setFreshnessStatus(FreshnessStatus status) {
        this.status = status;
    }
}