package fr.amu.iut.model.characters;

import fr.amu.iut.model.characters.jobs.*;


import java.util.Random;

public class CharacterFactory {
    private Random random = new Random();

    // Constantes pour les variations
    private static final int BASE_SIZE = 160;
    private static final int SIZE_VARIATION = 40; // Variation max (160 + 0 à 40)
    private static final int BASE_AGE = 18;
    private static final int AGE_VARIATION = 60;
    private static final int BASE_STAT = 50;      // Pour Force et Endurance
    private static final int STAT_VARIATION = 50;

    /**
     * Crée dynamiquement un personnage avec des stats aléatoires adaptées.
     * @param faction La faction (GAULOIS ou ROMAIN)
     * @param role Le métier ("Forgeron", "Druide", etc.)
     * @param name Le nom du personnage
     */
    public Character createCharacter(Faction faction, String role, String name) {
        int size = BASE_SIZE + random.nextInt(SIZE_VARIATION);
        int age = BASE_AGE + random.nextInt(AGE_VARIATION);
        int strength = BASE_STAT + random.nextInt(STAT_VARIATION);
        int endurance = BASE_STAT + random.nextInt(STAT_VARIATION);
        char sex = random.nextBoolean() ? 'M' : 'F';

        // Switch sur le RÔLE / METIER
        switch (role.toLowerCase()) {
            case "forgeron":
                strength += 20;
                return new Blacksmith(name, sex, size, age, strength, endurance, faction);
            case "druide":
                endurance += 20;
                return new Druid(name, sex, size, age, strength, endurance, faction);
            case "legionnaire":
                strength += 10;
                endurance += 15;
                return new Legionary(name, sex, size, age, strength, endurance, faction);
            case "général":
                strength += 15;
                endurance += 10;
                return new General(name, sex, size, age, strength, endurance, faction);
            case "aubergiste":
                endurance += 25;
                return new Innkeeper(name, sex, size, age, strength, endurance, faction);
            case "marchand":
                strength += 5;
                endurance += 5;
                return new Merchant(name, sex, size, age, strength, endurance, faction);
            case "préfet":
                strength += 10;
                endurance += 10;
                return new Prefect(name, sex, size, age, strength, endurance, faction);
            default:
                throw new IllegalArgumentException("Métier inconnu : " + role);
        }
    }
}