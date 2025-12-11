package fr.amu.iut.model.spaces;

import fr.amu.iut.model.characters.Character;
import fr.amu.iut.model.characters.Faction;
import fr.amu.iut.model.characters.jobs.Legionary;
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
        // Utiliser Legionary car c'est un Character ET un Fighter
        Legionary romanFighter = mock(Legionary.class);
        when(romanFighter.getFaction()).thenReturn(Faction.ROMAN);
        assertTrue(camp.authorized(romanFighter));
    }

    @Test
    public void testUnauthorizedRomanCivilian() {
        Character romanCivilian = mock(Character.class);
        when(romanCivilian.getFaction()).thenReturn(Faction.ROMAN);
        // Character n'est pas Fighter -> non autorisé
        assertFalse(camp.authorized(romanCivilian));
    }

    @Test
    public void testUnauthorizedGauloisFighter() {
        Legionary gauloisFighter = mock(Legionary.class);
        when(gauloisFighter.getFaction()).thenReturn(Faction.GALISH);
        assertFalse(camp.authorized(gauloisFighter));
    }

    @Test
    public void testAuthorizedLycanthrope() {
        Lycanthrope lycanthrope = mock(Lycanthrope.class);
        when(lycanthrope.getFaction()).thenReturn(Faction.GALISH);
        assertTrue(camp.authorized(lycanthrope));
    }
}