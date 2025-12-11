package fr.amu.iut.util;

import fr.amu.iut.model.characters.Character;
import fr.amu.iut.model.characters.Faction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CharacterSorterTest {

    private List<Character> characters;

    private static class TestCharacter extends Character {
        public TestCharacter(String name) {
            super(name, 'M', 30, 50, 10, 10, Faction.GAULOIS);
        }

        @Override
        public void fight(Character opponent) {
            // Simple implementation for testing purposes
            System.out.println(getName() + " fights " + opponent.getName());
        }
    }

    @BeforeEach
    public void setUp() {
        characters = new ArrayList<>();
        characters.add(new TestCharacter("C"));
        characters.add(new TestCharacter("A"));
        characters.add(new TestCharacter("B"));
        characters.add(new TestCharacter("E"));
        characters.add(new TestCharacter("D"));
    }

    @Test
    public void testQuickSortByName() {
        CharacterSorter.quickSortByName(characters);
        assertEquals("A", characters.get(0).getName());
        assertEquals("B", characters.get(1).getName());
        assertEquals("C", characters.get(2).getName());
        assertEquals("D", characters.get(3).getName());
        assertEquals("E", characters.get(4).getName());
    }

    @Test
    public void testQuickSortWithEmptyList() {
        List<Character> emptyList = new ArrayList<>();
        CharacterSorter.quickSortByName(emptyList);
        assertTrue(emptyList.isEmpty());
    }

    @Test
    public void testQuickSortWithOneElement() {
        List<Character> singleElementList = new ArrayList<>();
        singleElementList.add(new TestCharacter("Z"));
        CharacterSorter.quickSortByName(singleElementList);
        assertEquals(1, singleElementList.size());
        assertEquals("Z", singleElementList.get(0).getName());
    }

    @Test
    public void testQuickSortWithDuplicateNames() {
        characters.add(new TestCharacter("A"));
        CharacterSorter.quickSortByName(characters);
        assertEquals("A", characters.get(0).getName());
        assertEquals("A", characters.get(1).getName());
        assertEquals("B", characters.get(2).getName());
        assertEquals("C", characters.get(3).getName());
        assertEquals("D", characters.get(4).getName());
        assertEquals("E", characters.get(5).getName());
    }
}
