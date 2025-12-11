package fr.amu.iut.model.spaces;

import fr.amu.iut.model.characters.Character;
import fr.amu.iut.model.characters.Faction;
import fr.amu.iut.model.characters.Fighter;
import fr.amu.iut.model.characters.jobs.Lycanthrope;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RomanFortifiedCampTest {

    private RomanFortifiedCamp camp;

    @BeforeEach
    public void setUp() {
        camp = new RomanFortifiedCamp("Test Camp", 1000, null);
    }

    @Test
    public void testAuthorizedRomanFighter() {
        Fighter romanFighter = mock(Fighter.class);
        when(romanFighter.getFaction()).thenReturn(Faction.ROMAIN);
        assertTrue(camp.authorized((Character) romanFighter)); // Cast Fighter to Character
    }

    @Test
    public void testUnauthorizedRomanCivilian() {
        Character romanCivilian = mock(Character.class);
        when(romanCivilian.getFaction()).thenReturn(Faction.ROMAIN);
        assertFalse(camp.authorized(romanCivilian));
    }

    @Test
    public void testUnauthorizedGauloisFighter() {
        Fighter gauloisFighter = mock(Fighter.class);
        when(gauloisFighter.getFaction()).thenReturn(Faction.GAULOIS);
        assertFalse(camp.authorized((Character) gauloisFighter)); // Cast Fighter to Character
    }

    @Test
    public void testAuthorizedLycanthrope() {
        Lycanthrope lycanthrope = mock(Lycanthrope.class);
        // Faction does not matter for Lycanthrope
        when(lycanthrope.getFaction()).thenReturn(Faction.GAULOIS);
        assertTrue(camp.authorized(lycanthrope));
    }
}
