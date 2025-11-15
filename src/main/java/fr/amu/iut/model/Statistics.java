package fr.amu.iut.model;

public class Statistics {
    private int value;
    private final int min; // ne peut pas descendre en dessous de min
    private int max;

    public Statistics(int value, int min, int max) {
        this.min = min;
        this.max = max;
        this.value = clamp(value);
    }

    private int clamp(int value) {
        return Math.max(min, Math.min(value, max));
    }

    public void add(int amount) {
        value = clamp(value + amount);
    }

    public void increaseMax(int amount) {
        max += amount;
        value = clamp(value);
    }

    public void setMax(int max) {
        this.max = max;
        value = clamp(value);
    }

    public int get() { return value; }
    public int getMax() { return max; }
}
