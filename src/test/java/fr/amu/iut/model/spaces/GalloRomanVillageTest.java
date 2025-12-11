package fr.amu.iut.model.spaces;

import fr.amu.iut.model.characters.Character;
import fr.amu.iut.model.characters.Faction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GalloRomanVillageTest {

    private GalloRomanVillage village;

    @BeforeEach
    public void setUp() {
        village = new GalloRomanVillage("Test Village", 1500, null);
    }

    @Test
    public void testAuthorizedGaulois() {
        Character gaulois = mock(Character.class);
        when(gaulois.getFaction()).thenReturn(Faction.GAULOIS);
        assertTrue(village.authorized(gaulois));
    }

    @Test
    public void testAuthorizedRoman() {
        Character roman = mock(Character.class);
        when(roman.getFaction()).thenReturn(Faction.ROMAIN);
        assertTrue(village.authorized(roman));
    }

    @Test
    public void testUnauthorizedOtherFaction() {
        Character other = mock(Character.class);
        // Assuming there might be other factions in the future
        // For now, let's imagine a neutral faction
        when(other.getFaction()).thenReturn(null);
        assertFalse(village.authorized(other));
    }
}
