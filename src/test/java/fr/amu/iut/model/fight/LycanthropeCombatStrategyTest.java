package fr.amu.iut.model.fight;

import fr.amu.iut.model.characters.Character;
import fr.amu.iut.model.Statistics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class LycanthropeCombatStrategyTest {

    private LycanthropeCombatStrategy strategy;
    private Character attacker;
    private Character defender;
    private Statistics attackerStrength;
    private Statistics defenderEndurance;
    private Statistics defenderHealth;

    @BeforeEach
    public void setUp() {
        strategy = new LycanthropeCombatStrategy();
        attacker = mock(Character.class);
        defender = mock(Character.class);

        attackerStrength = new Statistics(40, 0, 100);
        defenderEndurance = new Statistics(30, 0, 100);
        defenderHealth = new Statistics(100, 0, 100);

        when(attacker.getStrength()).thenReturn(attackerStrength.get());
        when(defender.getEndurance()).thenReturn(defenderEndurance.get());
        when(defender.getHealth()).thenReturn(defenderHealth);
    }

    @Test
    public void testExecuteAttack_HighDamage() {
        // (40 * 2) - 30 = 50 dégâts. PV restants = 50.
        strategy.executeAttack(attacker, defender);
        assertEquals(50, defender.getHealth().get());
    }

    @Test
    public void testExecuteAttack_MinimumDamage() {
        // Force 20 -> (20*2) - 30 = 10 dégâts. PV restants = 90.
        when(attacker.getStrength()).thenReturn(20);
        strategy.executeAttack(attacker, defender);
        assertEquals(90, defender.getHealth().get());

        // Reset health
        defenderHealth.set(100);

        // Force 10 -> (10*2) - 30 = -10 -> min 5 dégâts. PV restants = 95.
        when(attacker.getStrength()).thenReturn(10);
        strategy.executeAttack(attacker, defender);
        assertEquals(95, defender.getHealth().get());
    }
}