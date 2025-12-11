package fr.amu.iut.model.spaces;

import fr.amu.iut.model.characters.Character;
import fr.amu.iut.model.characters.Faction;
import fr.amu.iut.model.characters.jobs.Legionary;
import fr.amu.iut.model.characters.jobs.Lycanthrope;
import fr.amu.iut.model.items.foods.Food;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SpaceTest {

    private Space space;
    private Character character;
    private Food food;

    @BeforeEach
    public void setUp() {
        // Battlefield accepte tout le monde
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
        // Battlefield autorise tout
        assertTrue(space.addCharacter(character));
        assertTrue(space.getCharacters().contains(character));
        verify(character).setCurrentSpace(space);
    }

    @Test
    public void testAddCharacterNotAuthorized() {
        // Utilisation d'un village Gaulois qui n'accepte pas les Romains
        Space restrictedSpace = new GallicVillage("Restricted Space", 100, null);
        Character roman = mock(Character.class);
        when(roman.getFaction()).thenReturn(Faction.ROMAN);

        assertFalse(restrictedSpace.addCharacter(roman));
        assertFalse(restrictedSpace.getCharacters().contains(roman));
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
        // Utilisation de Legionary (qui est Fighter ET Character)
        Legionary gaulois = mock(Legionary.class);
        when(gaulois.getFaction()).thenReturn(Faction.GALISH);
        Legionary romain = mock(Legionary.class);
        when(romain.getFaction()).thenReturn(Faction.ROMAN);

        Space battlefield = new Battlefield("Arena", 2000);
        battlefield.addCharacter(gaulois);
        battlefield.addCharacter(romain);

        battlefield.resolveCombat();

        verify(gaulois).fight(romain);
        verify(romain).fight(gaulois);
    }

    @Test
    public void testResolveCombatNoFighters() {
        Character civilian1 = mock(Character.class);
        Character civilian2 = mock(Character.class);
        Space battlefield = new Battlefield("Market", 100.0);
        battlefield.addCharacter(civilian1);
        battlefield.addCharacter(civilian2);

        battlefield.resolveCombat(); // Ne doit pas planter
    }

    @Test
    public void testNewPack() {
        Space forest = new Battlefield("Forest", 1000);
        Lycanthrope lycan1 = mock(Lycanthrope.class);
        when(lycan1.isLone()).thenReturn(true);
        Lycanthrope lycan2 = mock(Lycanthrope.class);
        when(lycan2.isLone()).thenReturn(true);

        forest.addCharacter(lycan1);
        forest.addCharacter(lycan2);

        forest.newPack();
    }
}