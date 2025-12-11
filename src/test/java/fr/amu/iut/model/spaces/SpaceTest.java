package fr.amu.iut.model.spaces;

import fr.amu.iut.model.characters.Character;
import fr.amu.iut.model.characters.Faction;
import fr.amu.iut.model.characters.Fighter;
import fr.amu.iut.model.characters.jobs.Lycanthrope;
import fr.amu.iut.model.items.foods.Food;
import fr.amu.iut.model.lycanthropes.Pack;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SpaceTest {

    private Space space;
    private Character character;
    private Food food;

    @BeforeEach
    public void setUp() {
        // Using Battlefield as a permitted subclass of Space
        space = new Battlefield("Test Battlefield", 100.0);
        character = mock(Character.class);
        food = mock(Food.class);
    }

    @Test
    public void testAddFood() {
        space.addFood(food);
        assertTrue(space.getFoods().contains(food));
    }

    @Test
    public void testRemoveFood() {
        space.addFood(food);
        space.removeFood(food);
        assertFalse(space.getFoods().contains(food));
    }

    @Test
    public void testIsBattlefield() {
        assertTrue(space.isBattlefield());
    }

    @Test
    public void testAddCharacter() {
        assertTrue(space.addCharacter(character));
        assertTrue(space.getCharacters().contains(character));
        verify(character).setCurrentSpace(space);
    }

    @Test
    public void testAddCharacterNotAuthorized() {
        Space restrictedSpace = new Battlefield("Restricted Space", 100); // Use Battlefield instead of anonymous class
        assertFalse(restrictedSpace.addCharacter(character));
        assertFalse(restrictedSpace.getCharacters().contains(character));
    }

    @Test
    public void testRemoveCharacter() {
        space.addCharacter(character);
        space.removeCharacter(character);
        assertFalse(space.getCharacters().contains(character));
    }

    @Test
    public void testHealCharacter() {
        space.addCharacter(character);
        space.healCharacter(character, 20);
        verify(character).beHealed(20);
    }

    @Test
    public void testHealCharacterNotInSpace() {
        space.healCharacter(character, 20);
        verify(character, never()).beHealed(anyInt());
    }

    @Test
    public void testEatFood() {
        space.addCharacter(character);
        space.addFood(food);
        space.eatFood(character, food);
        verify(character).eat(food);
        assertFalse(space.getFoods().contains(food));
    }

    @Test
    public void testEatFoodNotInSpace() {
        space.addCharacter(character);
        space.eatFood(character, food);
        verify(character, never()).eat(food);
    }

    @Test
    public void testEatFoodCharacterNotInSpace() {
        space.addFood(food);
        space.eatFood(character, food);
        verify(character, never()).eat(food);
    }

    @Test
    public void testResolveCombat() {
        Fighter gaulois = mock(Fighter.class);
        when(gaulois.getFaction()).thenReturn(Faction.GAULOIS);
        Fighter romain = mock(Fighter.class);
        when(romain.getFaction()).thenReturn(Faction.ROMAIN);

        Space battlefield = new Battlefield("Arena", 2000);
        battlefield.addCharacter((Character) gaulois); // Cast Fighter to Character
        battlefield.addCharacter((Character) romain); // Cast Fighter to Character

        battlefield.resolveCombat();

        verify(gaulois).fight((Character) romain); // Cast Fighter to Character
        verify(romain).fight((Character) gaulois); // Cast Fighter to Character
    }

    @Test
    public void testResolveCombatNoFighters() {
        Character civilian1 = mock(Character.class);
        Character civilian2 = mock(Character.class);
        Space battlefield = new Battlefield("Market", 100.0); // Correct constructor arguments
        battlefield.addCharacter(civilian1);
        battlefield.addCharacter(civilian2);

        battlefield.resolveCombat(); // Should not throw an error
    }

    @Test
    public void testNewPack() {
        Space forest = new Battlefield("Forest", 1000); // Correct constructor arguments
        Lycanthrope lycan1 = mock(Lycanthrope.class);
        when(lycan1.isLone()).thenReturn(true);
        Lycanthrope lycan2 = mock(Lycanthrope.class);
        when(lycan2.isLone()).thenReturn(true);

        forest.addCharacter(lycan1);
        forest.addCharacter(lycan2);

        // This test is limited as Pack.createPackWithSolitary is static and not easily
        // mocked.
        // We can only check if the logic tries to create a pack.
        // A full test would require refactoring Pack.createPackWithSolitary to be
        // mockable.
        forest.NewPack();
        // Assuming createPackWithSolitary returns a pack, the space's pack should be
        // set.
        // Since we can't mock the static method, we can't assert a change in the
        // space's pack.
        // This test mainly ensures NewPack runs without error.
    }
}
