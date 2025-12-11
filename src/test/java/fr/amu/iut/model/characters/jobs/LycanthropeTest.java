package fr.amu.iut.model.characters.jobs;

import fr.amu.iut.model.characters.Character;
import fr.amu.iut.model.Statistics;
import fr.amu.iut.model.lycanthropes.Pack;
import fr.amu.iut.model.lycanthropes.Rank;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class LycanthropeTest {

    private Lycanthrope alpha;
    private Lycanthrope beta;
    private Lycanthrope omega;
    private Pack pack;

    @BeforeEach
    public void setUp() {
        pack = mock(Pack.class);
        alpha = new Lycanthrope("Alpha", 'M', 180, 35, 90, 80, "adulte", "alpha", 10, 0.8, pack, false);
        beta = new Lycanthrope("Beta", 'F', 170, 25, 70, 60, "adulte", "beta", 5, 0.5, pack, false);
        omega = new Lycanthrope("Omega", 'M', 160, 20, 50, 40, "jeune", "omega", -2, 0.2, pack, false);
    }

    @Test
    public void testFight() {
        beta.getHealth().set(100);
        alpha.fight(beta);
        // (90 * 2) - 60 = 120 damage. Health should go to 0, but not below for a
        // lycanthrope.
        assertEquals(1, beta.getHealth().get());
    }

    @Test
    public void testFightPreventsDeathBetweenLycanthropes() {
        beta.getHealth().set(10);
        alpha.setStrength(100);
        alpha.fight(beta);
        assertEquals(1, beta.getHealth().get());
    }

    @Test
    public void testDominateSuccess() {
        // Ensure alpha has a higher level than beta
        alpha.setStrength(100);
        beta.setStrength(50);
        Rank initialAlphaRank = alpha.getRank();
        Rank initialBetaRank = beta.getRank();

        alpha.dominate(beta);

        assertEquals(initialBetaRank, alpha.getRank());
        assertEquals(initialAlphaRank, beta.getRank());
        assertEquals(11, alpha.getDominationFactor());
        assertEquals(4, beta.getDominationFactor());
    }

    @Test
    public void testDominateFailure() {
        beta.setStrength(100);
        alpha.setStrength(50);
        Rank initialAlphaRank = alpha.getRank();
        Rank initialBetaRank = beta.getRank();

        alpha.dominate(beta);

        assertEquals(initialAlphaRank, alpha.getRank());
        assertEquals(initialBetaRank, beta.getRank());
    }

    @Test
    public void testOmegaCannotDominate() {
        Rank initialBetaRank = beta.getRank();
        omega.dominate(beta);
        assertEquals(initialBetaRank, beta.getRank());
    }

    @Test
    public void testCannotDominateAlphaFemale() {
        when(pack.getAlphaFemale()).thenReturn(beta);
        alpha.dominate(beta);
        assertEquals(Rank.ALPHA, alpha.getRank());
        assertEquals(Rank.BETA, beta.getRank());
    }

    @Test
    public void testLeavePack() {
        alpha.leavePack();
        verify(pack).removeMember(alpha);
        assertNull(alpha.getPack());
        assertTrue(alpha.isLone());
    }

    @Test
    public void testBecomeSolitary() {
        alpha.becomeSolitary();
        verify(pack).removeMember(alpha);
        assertNull(alpha.getPack());
        assertTrue(alpha.isLone());
        assertNull(alpha.getRank());
    }

    @Test
    public void testUpdateRankFromDominationFactor() {
        beta.setDominationFactor(-10);
        when(pack.isLastOfRank(Rank.BETA, 'F')).thenReturn(false);
        beta.updateRankFromDominationFactor();
        assertEquals(Rank.GAMMA, beta.getRank());
    }
}
