package fr.amu.iut.model.characters;

import fr.amu.iut.model.characters.jobs.*;
import fr.amu.iut.GameConfig;

public class CharacterBuilder {
    private String name = "Inconnu";
    private char sex = 'M';
    private int size = GameConfig.BASE_SIZE;
    private int age = 30;
    private int strength = 50;
    private int endurance = 50;
    private Faction faction = Faction.GAULOIS;

    public CharacterBuilder setName(String name) { this.name = name; return this; }
    public CharacterBuilder setSex(char sex) { this.sex = sex; return this; }
    public CharacterBuilder setSize(int size) { this.size = size; return this; }
    public CharacterBuilder setAge(int age) { this.age = age; return this; }
    public CharacterBuilder setStrength(int strength) { this.strength = strength; return this; }
    public CharacterBuilder setEndurance(int endurance) { this.endurance = endurance; return this; }
    public CharacterBuilder setFaction(Faction faction) { this.faction = faction; return this; }

    public Character build(String role) {
        return switch (role.toLowerCase()) {
            case "druide" -> new Druid(name, sex, size, age, strength, endurance, faction);
            case "forgeron" -> new Blacksmith(name, sex, size, age, strength, endurance, faction);
            case "legionnaire" -> new Legionary(name, sex, size, age, strength, endurance, faction);
            case "général" -> new General(name, sex, size, age, strength, endurance, faction);
            case "aubergiste" -> new Innkeeper(name, sex, size, age, strength, endurance, faction);
            case "marchand" -> new Merchant(name, sex, size, age, strength, endurance, faction);
            case "préfet" -> new Prefect(name, sex, size, age, strength, endurance, faction);
            default -> throw new IllegalArgumentException("Métier inconnu : " + role);
        };
    }
}