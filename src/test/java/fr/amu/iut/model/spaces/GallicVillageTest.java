package fr.amu.iut.model.spaces;

import fr.amu.iut.model.characters.Character;
import fr.amu.iut.model.characters.Faction;
import fr.amu.iut.model.characters.jobs.Lycanthrope;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GallicVillageTest {

    private GallicVillage village;

    @BeforeEach
    public void setUp() {
        village = new GallicVillage("Test Village", 1200, null);
    }

    @Test
    public void testAuthorizedGaulois() {
        Character gaulois = mock(Character.class);
        when(gaulois.getFaction()).thenReturn(Faction.GALISH);
        assertTrue(village.authorized(gaulois));
    }

    @Test
    public void testUnauthorizedRoman() {
        Character roman = mock(Character.class);
        when(roman.getFaction()).thenReturn(Faction.ROMAN);
        assertFalse(village.authorized(roman));
    }

    @Test
    public void testAuthorizedLycanthrope() {
        Lycanthrope lycanthrope = mock(Lycanthrope.class);
        when(lycanthrope.getFaction()).thenReturn(Faction.ROMAN); // Peu importe
        assertTrue(village.authorized(lycanthrope));
    }
}