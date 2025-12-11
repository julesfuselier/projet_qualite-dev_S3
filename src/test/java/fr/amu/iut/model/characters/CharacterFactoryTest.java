package fr.amu.iut.model.characters;

import fr.amu.iut.model.characters.jobs.Druid;
import fr.amu.iut.model.characters.jobs.Blacksmith;
import fr.amu.iut.model.characters.jobs.Legionary;
import fr.amu.iut.model.characters.jobs.General;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CharacterFactoryTest {

    private CharacterFactory factory;

    @BeforeEach
    public void setUp() {
        factory = new CharacterFactory();
    }

    @Test
    public void testCreateDruid() {
        Character character = factory.createCharacter(Faction.GAULOIS, JobType.DRUID, "Panoramix");
        assertTrue(character instanceof Druid);
        assertEquals("Panoramix", character.getName());
        assertEquals(Faction.GAULOIS, character.getFaction());
        assertTrue(character.getEndurance() >= 50 + 20 && character.getEndurance() < 100 + 20);
    }

    @Test
    public void testCreateBlacksmith() {
        Character character = factory.createCharacter(Faction.GAULOIS, JobType.BLACKSMITH, "Cetaumatix");
        assertTrue(character instanceof Blacksmith);
        assertEquals("Cetaumatix", character.getName());
        assertEquals(Faction.GAULOIS, character.getFaction());
        assertTrue(character.getStrength() >= 50 + 20 && character.getStrength() < 100 + 20);
    }

    @Test
    public void testCreateLegionary() {
        Character character = factory.createCharacter(Faction.ROMAIN, JobType.LEGIONARY, "Minus");
        assertTrue(character instanceof Legionary);
        assertEquals("Minus", character.getName());
        assertEquals(Faction.ROMAIN, character.getFaction());
        assertTrue(character.getStrength() >= 50 + 10 && character.getStrength() < 100 + 10);
        assertTrue(character.getEndurance() >= 50 + 15 && character.getEndurance() < 100 + 15);
    }

    @Test
    public void testCreateGeneral() {
        Character character = factory.createCharacter(Faction.ROMAIN, JobType.GENERAL, "Ceasar");
        assertTrue(character instanceof General);
        assertEquals("Ceasar", character.getName());
        assertEquals(Faction.ROMAIN, character.getFaction());
        assertTrue(character.getStrength() >= 50 + 15 && character.getStrength() < 100 + 15);
        assertTrue(character.getEndurance() >= 50 + 10 && character.getEndurance() < 100 + 10);
    }

    @Test
    public void testCreateUnknownRole() {
        assertThrows(IllegalArgumentException.class, () -> {
            factory.createCharacter(Faction.GAULOIS, JobType.UNKNOWN, "Nobody"); // Updated to use JobType.UNKNOWN
        });
    }
}
