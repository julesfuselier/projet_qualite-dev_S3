package fr.amu.iut;

/**
 * Classe contenant les constantes de configuration du jeu.
 */
public final class GameConfig {
    private GameConfig() {}

    public static final int PROBABILITY_HUNGER_EVENT = 10;
    public static final int PROBABILITY_FOOD_SPAWN = 20;
    public static final int PROBABILITY_DRUID_POTION = 33;

    public static final int MAX_HUNGER_INCREASE = 5;
    public static final int MAX_POTION_DECREASE = 2;
    public static final int TURN_DURATION_MS = 3000;

    public static final int BASE_SIZE = 160;
    public static final int SIZE_VARIATION = 40;

    public static final int BONUS_STRENGTH_POTION = 100 ;

    public static final int PROBABILITY_LYCAN_REPRODUCTION = 10;
    public static final int PROBABILITY_LYCAN_TRANSFORMATION = 50;
    public static final int PROBABILITY_HOWL = 20;
}