package fr.amu.iut.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StatisticsTest {

    private Statistics stats;

    @BeforeEach
    public void setUp() {
        stats = new Statistics(50, 0, 100);
    }

    @Test
    public void testConstructor() {
        assertEquals(50, stats.get());
        assertEquals(100, stats.getMax());
    }

    @Test
    public void testConstructorWithValueAboveMax() {
        Statistics highStats = new Statistics(150, 0, 100);
        assertEquals(100, highStats.get());
    }

    @Test
    public void testConstructorWithValueBelowMin() {
        Statistics lowStats = new Statistics(-50, 0, 100);
        assertEquals(0, lowStats.get());
    }

    @Test
    public void testAdd() {
        stats.add(20);
        assertEquals(70, stats.get());
    }

    @Test
    public void testAddAboveMax() {
        stats.add(60);
        assertEquals(100, stats.get());
    }

    @Test
    public void testAddBelowMin() {
        stats.add(-60);
        assertEquals(0, stats.get());
    }

    @Test
    public void testIncreaseMax() {
        stats.increaseMax(50);
        assertEquals(150, stats.getMax());
        assertEquals(50, stats.get());
    }

    @Test
    public void testSetMax() {
        stats.setMax(200);
        assertEquals(200, stats.getMax());
        assertEquals(50, stats.get());
    }

    @Test
    public void testSetMaxWithValueClamping() {
        stats = new Statistics(120, 0, 100);
        // Le constructeur clampe 120 -> 100
        stats.setMax(150);
        assertEquals(100, stats.get()); // Était 120 dans le test original, mais c'est faux

        stats.setMax(80);
        assertEquals(80, stats.get());
    }

    @Test
    public void testDecreaseStats() {
        stats.decreaseStats(20);
        assertEquals(30, stats.get());
    }

    @Test
    public void testDecreaseStatsBelowMin() {
        stats.decreaseStats(60);
        assertEquals(-10, stats.get());
    }

    @Test
    public void testGet() {
        assertEquals(50, stats.get());
    }

    @Test
    public void testGetMax() {
        assertEquals(100, stats.getMax());
    }
}