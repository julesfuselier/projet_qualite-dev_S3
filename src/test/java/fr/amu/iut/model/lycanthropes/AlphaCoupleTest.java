package fr.amu.iut.model.lycanthropes;

import fr.amu.iut.model.characters.jobs.Lycanthrope;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AlphaCoupleTest {

    private Lycanthrope male;
    private Lycanthrope female;
    private Pack pack;

    @BeforeEach
    public void setUp() {
        male = mock(Lycanthrope.class);
        when(male.getSex()).thenReturn('M');
        female = mock(Lycanthrope.class);
        when(female.getSex()).thenReturn('F');
        pack = mock(Pack.class);
    }

    @Test
    public void testConstructor_Success() {
        AlphaCouple couple = new AlphaCouple(male, female, pack);
        assertEquals(male, couple.getMale());
        assertEquals(female, couple.getFemale());
    }

    @Test
    public void testConstructor_InvalidMale() {
        when(male.getSex()).thenReturn('F');
        assertThrows(IllegalArgumentException.class, () -> {
            new AlphaCouple(male, female, pack);
        });
    }

    @Test
    public void testConstructor_InvalidFemale() {
        when(female.getSex()).thenReturn('M');
        assertThrows(IllegalArgumentException.class, () -> {
            new AlphaCouple(male, female, pack);
        });
    }

    @Test
    public void testReproduce() {
        AlphaCouple couple = new AlphaCouple(male, female, pack);
        couple.reproduce();

        ArgumentCaptor<Lycanthrope> captor = ArgumentCaptor.forClass(Lycanthrope.class);
        verify(pack, atLeastOnce()).addMember(captor.capture());

        Lycanthrope young = captor.getValue();
        assertEquals("jeune", young.getAgeGroup());
        assertEquals(pack, young.getPack());
        assertFalse(young.isLone());
    }
}
