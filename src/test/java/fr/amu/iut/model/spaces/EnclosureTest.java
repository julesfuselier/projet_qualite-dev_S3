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

public class EnclosureTest {

    private Enclosure enclosure;

    @BeforeEach
    public void setUp() {
        enclosure = new Enclosure("Test Enclosure", 500);
    }

    @Test
    public void testAuthorizedLycanthrope() {
        Lycanthrope lycanthrope = mock(Lycanthrope.class);
        assertTrue(enclosure.authorized(lycanthrope));
    }

    @Test
    public void testUnauthorizedNonLycanthrope() {
        Character character = mock(Character.class);
        assertFalse(enclosure.authorized(character));
    }
}
