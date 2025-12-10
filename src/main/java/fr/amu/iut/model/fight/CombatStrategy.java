package fr.amu.iut.model.fight;

import fr.amu.iut.model.characters.Character;

public interface CombatStrategy {
    void executeAttack(Character attacker, Character defender);
}