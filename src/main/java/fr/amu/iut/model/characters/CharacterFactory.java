package fr.amu.iut.model.characters;

import fr.amu.iut.GameConfig;
import java.util.Random;

public class CharacterFactory {
    private final Random random = new Random();

    public Character createCharacter(Faction faction, String role, String name) {
        int size = GameConfig.BASE_SIZE + random.nextInt(GameConfig.SIZE_VARIATION);
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

        switch (role.toLowerCase()) {
            case "forgeron" -> builder.setStrength(baseStr + 20);
            case "druide" -> builder.setEndurance(baseEnd + 20);
            case "legionnaire" -> {
                builder.setStrength(baseStr + 10);
                builder.setEndurance(baseEnd + 15);
            }
            case "général" -> {
                builder.setStrength(baseStr + 15);
                builder.setEndurance(baseEnd + 10);
            }
            // TODO : Voir si on ajoute d'autre bonus
        }

        return builder.build(role);
    }
}