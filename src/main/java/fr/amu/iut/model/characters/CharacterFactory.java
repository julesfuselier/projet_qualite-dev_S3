package fr.amu.iut.model.characters;

import fr.amu.iut.GameConfig;
import java.util.Random;

/**
 * Factory pour créer des personnages avec des attributs aléatoires basés sur leur faction et rôle.
 * Utilise le pattern Factory pour encapsuler la logique de création des personnages.
 * Exemple d'utilisation :
 * Character character = new CharacterFactory().createCharacter(Faction.GAULOIS, "druide", "Panoramix");
 */
public class CharacterFactory {
    private final Random random = new Random();
    private final GameConfig gameConfig = GameConfig.getInstance();

    /**
     * Crée un personnage avec des attributs aléatoires basés sur la faction et le rôle.
     * @param faction La faction du personnage.
     * @param role Le rôle du personnage (e.g., "forgeron", "druide", "légionnaire", "général").
     * @param name Le nom du personnage.
     * @return Le personnage créé.
     */
    public Character createCharacter(Faction faction, JobType role, String name) {
        int size = gameConfig.getBaseSize() + random.nextInt(gameConfig.getSizeVariation());
        int age = 18 + random.nextInt(60);
        int baseStr = 50 + random.nextInt(50);
        int baseEnd = 50 + random.nextInt(50);
        char sex = random.nextBoolean() ? 'M' : 'F';

        CharacterBuilder builder = new CharacterBuilder()
                .setName(name)
                .setFaction(faction)
                .setSex(sex)
                .setSize(size)
                .setAge(age)
                .setStrength(baseStr)
                .setEndurance(baseEnd);

        switch (role) {
            case FORGERON -> builder.setStrength(baseStr + 20);
            case DRUIDE -> builder.setEndurance(baseEnd + 20);
            case LEGIONNAIRE -> {
                builder.setStrength(baseStr + 10);
                builder.setEndurance(baseEnd + 15);
            }
            case GENERAL -> {
                builder.setStrength(baseStr + 15);
                builder.setEndurance(baseEnd + 10);
            }
        }

        return builder.build(role);
    }
}