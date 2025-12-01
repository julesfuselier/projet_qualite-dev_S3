package fr.amu.iut.model.characters;

import fr.amu.iut.model.characters.jobs.*;


import java.util.Random;

public class CharacterFactory {
    private Random random = new Random();

    /**
     * Crée dynamiquement un personnage avec des stats aléatoires adaptées.
     * @param faction La faction (GAULOIS ou ROMAIN)
     * @param role Le métier ("Forgeron", "Druide", etc.)
     * @param name Le nom du personnage
     */
    public Character createCharacter(Faction faction, String role, String name) {
        int size = 160 + random.nextInt(40);
        int age = 18 + random.nextInt(60);
        int strength = 50 + random.nextInt(50);
        int endurance = 50 + random.nextInt(50);
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