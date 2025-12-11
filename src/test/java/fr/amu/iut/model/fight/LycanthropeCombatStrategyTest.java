package fr.amu.iut.model.fight;

import fr.amu.iut.model.characters.Character;
import fr.amu.iut.model.Statistics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        // (40 * 2) - 30 = 50
        strategy.executeAttack(attacker, defender);
        verify(defender.getHealth()).add(-50);
    }

    @Test
    public void testExecuteAttack_MinimumDamage() {
        attackerStrength.add(-20); // Strength becomes 20
        when(attacker.getStrength()).thenReturn(attackerStrength.get());
        // (20 * 2) - 30 = 10
        strategy.executeAttack(attacker, defender);
        verify(defender.getHealth()).add(-10);

        attackerStrength.add(-10); // Strength becomes 10
        when(attacker.getStrength()).thenReturn(attackerStrength.get());
        // (10 * 2) - 30 = -10, so damage should be 5
        strategy.executeAttack(attacker, defender);
        verify(defender.getHealth()).add(-5);
    }
}
