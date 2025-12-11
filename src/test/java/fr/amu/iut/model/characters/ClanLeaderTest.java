package fr.amu.iut.model.characters;

import fr.amu.iut.model.characters.jobs.Druid;
import fr.amu.iut.model.exceptions.InsufficientIngredientsException;
import fr.amu.iut.model.items.foods.Food;
import fr.amu.iut.model.items.potion.MagicPotion;
import fr.amu.iut.model.items.potion.PotionType;
import fr.amu.iut.model.spaces.Space;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.HashSet;
import static org.mockito.Mockito.*;

public class ClanLeaderTest {

    private ClanLeader leader;
    private Space location;
    private Character character;
    private CharacterFactory characterFactory;

    @BeforeEach
    public void setUp() {
        location = mock(Space.class);
        leader = new ClanLeader("Chief", 'M', 40, location);
        character = mock(Character.class);
        characterFactory = mock(CharacterFactory.class);
        leader.characterFactory = characterFactory;

        HashSet<Character> characters = new HashSet<>();
        characters.add(character);
        when(location.getCharacters()).thenReturn(characters);
    }

    @Test
    public void testReceiveMagicPotion() {
        MagicPotion potion = mock(MagicPotion.class);
        leader.receiveMagicPotion(potion);
        // No real getter to verify, but we can test if giving it works
        leader.giveMagicPotionToCharacterInVillage(character, 1);
        verify(character).drinkMagicPotion(potion, false);
    }

    @Test
    public void testSetLocation() {
        Space newLocation = mock(Space.class);
        leader.setLocation(newLocation);
        leader.examineLocation(); // This will call showCharacteristics on the new location
        verify(newLocation).showCharacteristics();
    }

    @Test
    public void testCreateNewCharacterInVillage() {
        when(characterFactory.createCharacter(Faction.GAULOIS, "Warrior", "Newbie")).thenReturn(character);
        leader.createNewCharacterInVillage(Faction.GAULOIS, "Warrior", "Newbie");
        verify(location).addCharacter(character);
    }

    @Test
    public void testHealCharacterInVillage() {
        leader.healCharacterInVillage(character, 20);
        verify(character).beHealed(20);
    }

    @Test
    public void testFeedCharacterInVillage() {
        Food food = mock(Food.class);
        HashSet<Food> foods = new HashSet<>();
        foods.add(food);
        when(location.getFoods()).thenReturn(new ArrayList<>(foods)); // Convert HashSet to List

        leader.feedCharacterInVillage(character, food);

        verify(character).eat(food);
        verify(location).removeFood(food);
    }

    @Test
    public void testAskDruidForMagicPotion() throws InsufficientIngredientsException {
        Druid druid = mock(Druid.class);
        HashSet<Character> characters = new HashSet<>();
        characters.add(druid);
        when(location.getCharacters()).thenReturn(characters);

        leader.askDruidForMagicPotion(druid, PotionType.BASIC);

        verify(druid).craftMagicPotion(PotionType.BASIC);
    }

    @Test
    public void testGiveMagicPotionToCharacterInVillage() {
        MagicPotion potion = mock(MagicPotion.class);
        leader.receiveMagicPotion(potion);
        leader.giveMagicPotionToCharacterInVillage(character, 1);
        verify(character).drinkMagicPotion(potion, false);
    }

    @Test
    public void testTransferCharacter() {
        Space destination = mock(Space.class);
        leader.transferCharacter(character, destination);
        verify(location).removeCharacter(character);
        verify(destination).addCharacter(character);
    }
}
