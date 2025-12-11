package fr.amu.iut.model.fight;

import fr.amu.iut.model.characters.Character;
import fr.amu.iut.model.Statistics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

public class StandardCombatStrategyTest {

    private StandardCombatStrategy strategy;
    private Character attacker;
    private Character defender;
    private Statistics attackerStrength;
    private Statistics defenderEndurance;
    private Statistics defenderHealth;

    @BeforeEach
    public void setUp() {
        strategy = new StandardCombatStrategy();
        attacker = mock(Character.class);
        defender = mock(Character.class);

        attackerStrength = new Statistics(50, 0, 100);
        defenderEndurance = new Statistics(30, 0, 100);
        defenderHealth = new Statistics(100, 0, 100);

        when(attacker.getStrength()).thenReturn(attackerStrength.get());
        when(defender.getEndurance()).thenReturn(defenderEndurance.get());
        when(defender.getHealth()).thenReturn(defenderHealth);
    }

    @Test
    public void testExecuteAttack_DamageDealt() {
        strategy.executeAttack(attacker, defender);
        verify(defender.getHealth()).add(-20);
    }

    @Test
    public void testExecuteAttack_MinimumDamage() {
        attackerStrength.add(-40); // Strength becomes 10
        when(attacker.getStrength()).thenReturn(attackerStrength.get());
        strategy.executeAttack(attacker, defender);
        verify(defender.getHealth()).add(-1);
    }

    @Test
    public void testExecuteAttack_DefenderHasPotion() {
        when(defender.isActivePotion()).thenReturn(true);
        strategy.executeAttack(attacker, defender);
        verify(defender.getHealth(), never()).add(anyInt());
    }
}
