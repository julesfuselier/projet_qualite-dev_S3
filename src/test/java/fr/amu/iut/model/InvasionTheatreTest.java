package fr.amu.iut.model;

import fr.amu.iut.model.characters.Character;
import fr.amu.iut.model.characters.Faction;
import fr.amu.iut.model.characters.jobs.Legionary;
import fr.amu.iut.model.items.foods.Food;
import fr.amu.iut.model.items.foods.FreshnessStatus;
import fr.amu.iut.model.spaces.Battlefield;
import fr.amu.iut.model.spaces.GallicVillage;
import fr.amu.iut.model.spaces.RomanFortifiedCamp;
import fr.amu.iut.model.spaces.Space;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class InvasionTheatreTest {

    private InvasionTheatre theatre;
    private Space battlefield;
    private Space village;

    @BeforeEach
    public void setUp() {
        theatre = new InvasionTheatre("Gaul", 10);
        battlefield = mock(Battlefield.class);
        village = mock(GallicVillage.class);
        when(battlefield.isBattlefield()).thenReturn(true);
        theatre.addLocation(battlefield);
        theatre.addLocation(village);
    }

    @Test
    public void testAddLocation() {
        assertEquals(2, theatre.getExistingLocations().size());
        assertTrue(theatre.getExistingLocations().contains(battlefield));
    }

    @Test
    public void testHandleBattles() {
        theatre.handleBattles();
        verify(battlefield).resolveCombat();
        verify(village, never()).resolveCombat();
    }

    @Test
    public void testUpdateFoodFreshness() {
        Food freshFood = mock(Food.class);
        when(freshFood.isFresh()).thenReturn(true);
        List<Food> foods = new ArrayList<>();
        foods.add(freshFood);
        when(village.getFoods()).thenReturn(foods);

        theatre.updateFoodFreshness();

        verify(freshFood).setFreshnessStatus(FreshnessStatus.valueOf("NOT_FRESH"));
    }

    @Test
    public void testHandleAutonomousMovements_FighterToBattlefield() {
        // Utiliser Legionary
        Legionary healthyFighter = mock(Legionary.class);
        when(healthyFighter.getHealth()).thenReturn(new Statistics(100, 0, 100));
        when(healthyFighter.getHunger()).thenReturn(new Statistics(100, 0, 100));

        HashSet<Character> characters = new HashSet<>();
        characters.add(healthyFighter);
        when(village.getCharacters()).thenReturn(characters);
        when(battlefield.authorized(healthyFighter)).thenReturn(true);

        theatre.handleAutonomousMovements();

        verify(village).removeCharacter(healthyFighter);
        verify(battlefield).addCharacter(healthyFighter);
    }

    @Test
    public void testHandleAutonomousMovements_InjuredFromBattlefield() {
        Character injuredCharacter = mock(Character.class);
        when(injuredCharacter.getHealth()).thenReturn(new Statistics(10, 0, 100));
        when(injuredCharacter.getHunger()).thenReturn(new Statistics(100, 0, 100));
        when(injuredCharacter.getFaction()).thenReturn(Faction.GALISH);

        HashSet<Character> characters = new HashSet<>();
        characters.add(injuredCharacter);
        when(battlefield.getCharacters()).thenReturn(characters);
        when(village.authorized(injuredCharacter)).thenReturn(true);

        theatre.handleAutonomousMovements();

        verify(battlefield).removeCharacter(injuredCharacter);
        verify(village).addCharacter(injuredCharacter);
    }
}