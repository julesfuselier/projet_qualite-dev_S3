package fr.amu.iut.model.lycanthropes;

import fr.amu.iut.model.characters.jobs.Lycanthrope;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PackTest {

    private Pack pack;
    private Lycanthrope lycanthrope1;
    private Lycanthrope lycanthrope2;

    @BeforeEach
    public void setUp() {
        pack = new Pack();
        lycanthrope1 = mock(Lycanthrope.class);
        lycanthrope2 = mock(Lycanthrope.class);
    }

    @Test
    public void testAddMember() {
        pack.addMember(lycanthrope1);
        assertTrue(pack.getMembers().contains(lycanthrope1));
        verify(lycanthrope1).setPack(pack);
        verify(lycanthrope1).setLone(false);
    }

    @Test
    public void testRemoveMember() {
        pack.addMember(lycanthrope1);
        pack.removeMember(lycanthrope1);
        assertFalse(pack.getMembers().contains(lycanthrope1));
        verify(lycanthrope1).setPack(null);
        verify(lycanthrope1).setLone(true);
    }

    @Test
    public void testCreatePackWithSolitary() {
        Lycanthrope male = mock(Lycanthrope.class);
        when(male.getSex()).thenReturn('M');
        Lycanthrope female = mock(Lycanthrope.class);
        when(female.getSex()).thenReturn('F');
        List<Lycanthrope> solitaries = new ArrayList<>();
        solitaries.add(male);
        solitaries.add(female);

        Pack newPack = Pack.createPackWithSolitary(solitaries);

        assertNotNull(newPack);
        assertTrue(newPack.getMembers().contains(male));
        assertTrue(newPack.getMembers().contains(female));
    }
    
    @Test
    public void testCreatePackWithSolitaryNoFemale() {
        Lycanthrope male = mock(Lycanthrope.class);
        when(male.getSex()).thenReturn('M');
        List<Lycanthrope> solitaries = new ArrayList<>();
        solitaries.add(male);

        Pack newPack = Pack.createPackWithSolitary(solitaries);

        assertNull(newPack);
    }

    @Test
    public void testGetAlphaMaleAndFemale() {
        Lycanthrope alphaMale = mock(Lycanthrope.class);
        when(alphaMale.getSex()).thenReturn('M');
        when(alphaMale.getAgeGroup()).thenReturn("adulte");
        when(alphaMale.getStrength()).thenReturn(100);

        Lycanthrope alphaFemale = mock(Lycanthrope.class);
        when(alphaFemale.getSex()).thenReturn('F');
        when(alphaFemale.getAgeGroup()).thenReturn("adulte");
        when(alphaFemale.getStrength()).thenReturn(90);

        pack.addMember(alphaMale);
        pack.addMember(alphaFemale);
        pack.setAlphaCouple();

        assertEquals(alphaMale, pack.getAlphaMale());
        assertEquals(alphaFemale, pack.getAlphaFemale());
    }

    @Test
    public void testIsLastOfRank() {
        when(lycanthrope1.getSex()).thenReturn('M');
        when(lycanthrope1.getRank()).thenReturn(Rank.BETA);
        pack.addMember(lycanthrope1);

        assertTrue(pack.isLastOfRank(Rank.BETA, 'M'));

        when(lycanthrope2.getSex()).thenReturn('M');
        when(lycanthrope2.getRank()).thenReturn(Rank.BETA);
        pack.addMember(lycanthrope2);

        assertFalse(pack.isLastOfRank(Rank.BETA, 'M'));
    }
    
    @Test
    public void testCreateHierarchy() {
        Lycanthrope weak = mock(Lycanthrope.class);
        when(weak.getStrength()).thenReturn(10);
        pack.addMember(weak);
        pack.createHierarchy();
        verify(weak).setRank(Rank.OMEGA);
    }
}
