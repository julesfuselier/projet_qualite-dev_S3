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

public class RomanCityTest {

    private RomanCity city;

    @BeforeEach
    public void setUp() {
        city = new RomanCity("Test City", 5000, null);
    }

    @Test
    public void testAuthorizedRoman() {
        Character roman = mock(Character.class);
        when(roman.getFaction()).thenReturn(Faction.ROMAN);
        assertTrue(city.authorized(roman));
    }

    @Test
    public void testUnauthorizedGaulois() {
        Character gaulois = mock(Character.class);
        when(gaulois.getFaction()).thenReturn(Faction.GALISH);
        assertFalse(city.authorized(gaulois));
    }

    @Test
    public void testAuthorizedLycanthrope() {
        Lycanthrope lycanthrope = mock(Lycanthrope.class);
        when(lycanthrope.getFaction()).thenReturn(Faction.GALISH);
        assertTrue(city.authorized(lycanthrope));
    }
}