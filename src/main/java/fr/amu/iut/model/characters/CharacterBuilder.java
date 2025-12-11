package fr.amu.iut.model.characters;

import fr.amu.iut.model.characters.jobs.*;
import fr.amu.iut.GameConfig;

/**
 * Builder pour créer des personnages avec des attributs personnalisés.
 * Utilise le pattern Builder pour faciliter la création de personnages.
 * Permet de définir des valeurs par défaut pour les attributs.
 * Exemple d'utilisation :
 * Character character = new CharacterBuilder()
 *     .setName("Asterix")
 *     .setSex('M')
 *     .setSize(170)
 *     .setAge(35)
 *     .setStrength(80)
 *     .setEndurance(70)
 *     .setFaction(Faction.GALISH)
 *     .build("druide");
 */
public class CharacterBuilder {
    private final GameConfig gameConfig = GameConfig.getInstance();

    private String name = "Inconnu";
    private char sex = 'M';
    private int size = gameConfig.getBaseSize();
    private int age = 30;
    private int strength = 50;
    private int endurance = 50;
    private Faction faction = Faction.GALISH;

    /**
     * Définit le nom du personnage.
     * @param name Le nom du personnage.
     * @return Le builder actuel pour le chaînage.
     */
    public CharacterBuilder setName(String name) { this.name = name; return this; }

    /**
     * Définit le sexe du personnage
     * @param sex Le sexe du personnage.
     * @return Le builder actuel pour le chaînage.
     */
    public CharacterBuilder setSex(char sex) { this.sex = sex; return this; }

    /**
     * Définit la taille du personnage.
     * @param size La taille du personnage.
     * @return Le builder actuel pour le chaînage.
     */
    public CharacterBuilder setSize(int size) { this.size = size; return this; }

    /**
     * Définit l'âge du personnage.
     * @param age L'âge du personnage.
     * @return Le builder actuel pour le chaînage.
     */
    public CharacterBuilder setAge(int age) { this.age = age; return this; }

    /**
     * Définit la force du personnage.
     * @param strength La force du personnage.
     * @return Le builder actuel pour le chaînage.
     */
    public CharacterBuilder setStrength(int strength) { this.strength = strength; return this; }

    /**
     * Définit l'endurance du personnage.
     * @param endurance L'endurance du personnage.
     * @return Le builder actuel pour le chaînage.
     */
    public CharacterBuilder setEndurance(int endurance) { this.endurance = endurance; return this; }

    /**
     * Définit la faction du personnage.
     * @param faction La faction du personnage.
     * @return Le builder actuel pour le chaînage.
     */
    public CharacterBuilder setFaction(Faction faction) { this.faction = faction; return this; }

    /**
     * Construit le personnage avec les attributs définis.
     * @param role Le rôle (métier) du personnage.
     * @return Le personnage construit.
     */
    public Character build(JobType role) {
        return switch (role) {
            case DRUID -> new Druid(name, sex, size, age, strength, endurance, faction);
            case BLACKSMITH -> new Blacksmith(name, sex, size, age, strength, endurance, faction);
            case LEGIONARY -> new Legionary(name, sex, size, age, strength, endurance, faction);
            case GENERAL -> new General(name, sex, size, age, strength, endurance, faction);
            case INKEEPER -> new Innkeeper(name, sex, size, age, strength, endurance, faction);
            case MERCHANT -> new Merchant(name, sex, size, age, strength, endurance, faction);
            case PREFECT -> new Prefect(name, sex, size, age, strength, endurance, faction);
            case WARRIOR -> new Legionary(name, sex, size, age, strength, endurance, faction);
            case UNKNOWN -> throw new IllegalArgumentException("Unknown job type: " + role);
        };
    }
}