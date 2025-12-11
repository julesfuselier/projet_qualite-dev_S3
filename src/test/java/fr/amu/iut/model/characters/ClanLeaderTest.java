package fr.amu.iut.model.characters;

import fr.amu.iut.model.characters.jobs.Druid;
import fr.amu.iut.model.characters.JobType;
import fr.amu.iut.model.exceptions.InsufficientIngredientsException;
import fr.amu.iut.model.items.foods.Food;
import fr.amu.iut.model.items.potion.MagicPotion;
import fr.amu.iut.model.items.potion.PotionType;
import fr.amu.iut.model.spaces.Battlefield;
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
        // Space est sealed/final, on utilise une vraie implémentation
        location = new Battlefield("Test Lieu", 100);
        leader = new ClanLeader("Chief", 'M', 40, location);
        character = mock(Character.class);
        characterFactory = mock(CharacterFactory.class);
        leader.characterFactory = characterFactory;

        location.addCharacter(character);
        location.addCharacter(leader);
    }

    @Test
    public void testReceiveMagicPotion() {
        MagicPotion potion = mock(MagicPotion.class);
        leader.receiveMagicPotion(potion);
        leader.giveMagicPotionToCharacterInVillage(character, 1);
        verify(character).drinkMagicPotion(potion, false);
    }

    @Test
    public void testSetLocation() {
        // On utilise aussi un vrai objet ici pour éviter l'erreur
        Space newLocation = spy(new Battlefield("Nouveau", 100));
        leader.setLocation(newLocation);
        leader.examineLocation();
        // Battlefield ne log rien de spécial, mais on vérifie l'appel
        // Ici examineLocation appelle showCharacteristics
        verify(newLocation).showCharacteristics();
    }

    @Test
    public void testCreateNewCharacterInVillage() {
        when(characterFactory.createCharacter(Faction.GAULOIS, JobType.WARRIOR, "Newbie")).thenReturn(character);
        leader.createNewCharacterInVillage(Faction.GAULOIS, JobType.WARRIOR, "Newbie");
        // Comme on utilise un vrai Space, on vérifie que le perso est bien dans la liste
        assert(location.getCharacters().contains(character));
    }

    @Test
    public void testFeedCharacterInVillage() {
        Food food = mock(Food.class);
        location.addFood(food);

        leader.feedCharacterInVillage(character, food);

        verify(character).eat(food);
        assert(!location.getFoods().contains(food));
    }

    @Test
    public void testAskDruidForMagicPotion() throws InsufficientIngredientsException {
        Druid druid = mock(Druid.class);
        location.addCharacter(druid);

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
        Space destination = new Battlefield("Dest", 100);
        leader.transferCharacter(character, destination);

        assert(!location.getCharacters().contains(character));
        assert(destination.getCharacters().contains(character));
    }
}