package fr.amu.iut.model.spaces;

import fr.amu.iut.model.characters.Character;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

public class BattlefieldTest {

    private Battlefield battlefield;

    @BeforeEach
    public void setUp() {
        battlefield = new Battlefield("Test Battlefield", 10000);
    }

    @Test
    public void testAuthorizedIsAlwaysTrue() {
        Character anyCharacter = mock(Character.class);
        assertTrue(battlefield.authorized(anyCharacter));
    }
}
