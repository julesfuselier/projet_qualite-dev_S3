package fr.amu.iut.model;

/**
 * Classe représentant des statistiques avec une valeur, un minimum et un maximum.
 * La valeur ne peut pas descendre en dessous du minimum et ne peut pas dépasser le maximum.
 */
public class Statistics {
    private int value;
    private final int min; // ne peut pas descendre en dessous de min
    private int max;

    /**
     * Constructeur de la classe Statistics.
     *
     * @param value La valeur initiale des statistiques.
     * @param min   La valeur minimale des statistiques.
     * @param max   La valeur maximale des statistiques.
     */
    public Statistics(int value, int min, int max) {
        this.min = min;
        this.max = max;
        this.value = clamp(value);
    }

    /**
     * Méthode privée pour s'assurer que la valeur reste dans les limites définies.
     *
     * @param value La valeur à vérifier.
     * @return La valeur ajustée si elle dépasse les limites.
     */
    private int clamp(int value) {
        return Math.max(min, Math.min(value, max));
    }

    /**
     * Ajoute une quantité à la valeur des statistiques.
     *
     * @param amount La quantité à ajouter.
     */
    public void add(int amount) {
        value = clamp(value + amount);
    }

    /**
     * Augmente la valeur maximale des statistiques.
     *
     * @param amount La quantité à ajouter au maximum.
     */
    public void increaseMax(int amount) {
        max += amount;
        value = clamp(value);
    }

    /**
     * Définit une nouvelle valeur maximale pour les statistiques.
     *
     * @param max La nouvelle valeur maximale.
     */
    public void setMax(int max) {
        this.max = max;
        value = clamp(value);
    }

    /**
     * Getter pour la valeur actuelle des statistiques.
     *
     * @return La valeur actuelle.
     */
    public int get() { return value; }

    /**
     * Getter pour la valeur maximale des statistiques.
     *
     * @return La valeur maximale.
     */
    public int getMax() { return max; }

    public void set(int value) {
        this.value = clamp(value);
    }

    /**
     * Diminue la valeur des statistiques d'une quantité spécifiée.
     *
     * @param amount La quantité à soustraire.
     */
    public void decreaseStats(int amount) {
        value -= amount;
    }

    /**
     * Increases the value of the statistics by a specified amount.
     *
     * @param amount The amount to increase the value by.
     */
    public void increase(int amount) {
        add(amount);
    }
}
