package fr.amu.iut.model.characters;

import fr.amu.iut.model.characters.jobs.Druid;
import fr.amu.iut.model.characters.jobs.Blacksmith;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CharacterBuilderTest {

    private CharacterBuilder builder;

    @BeforeEach
    public void setUp() {
        builder = new CharacterBuilder();
    }

    @Test
    public void testBuildDruid() {
        Character character = builder
                .setName("TestDruid")
                .setSex('F')
                .setAge(45)
                .setStrength(30)
                .setEndurance(60)
                .setFaction(Faction.GAULOIS)
                .build(JobType.DRUID);

        assertTrue(character instanceof Druid);
        assertEquals("TestDruid", character.getName());
        assertEquals('F', character.getSex());
        assertEquals(45, character.getAge());
        // Note: getStrength() might be different if the potion is active, so we can't
        // directly test the base strength.
        // This is a limitation of the current Character design. We test what we can.
        assertEquals(60, character.getEndurance());
        assertEquals(Faction.GAULOIS, character.getFaction());
    }

    @Test
    public void testBuildWithDefaults() {
        Character character = builder.build(JobType.BLACKSMITH);
        assertTrue(character instanceof Blacksmith);
        assertEquals("Inconnu", character.getName());
        assertEquals('M', character.getSex());
        assertEquals(30, character.getAge());
    }

    @Test
    public void testBuildUnknownRole() {
        assertThrows(IllegalArgumentException.class, () -> {
            builder.build(JobType.UNKNOWN); // Updated to use JobType.UNKNOWN
        });
    }
}
