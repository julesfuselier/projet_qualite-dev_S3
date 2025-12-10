package fr.amu.iut;

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
}