package fr.amu.iut.model.spaces;

import fr.amu.iut.model.characters.jobs.*;
import fr.amu.iut.model.characters.Character;

public final class RomanCity extends Space {

    public RomanCity(String name, double surface, Character leader) {
        super(name, surface, leader);
    }

    @Override
    public boolean authorized(Character c) {
        return (c instanceof Legionary || c instanceof Prefect || c instanceof General || c instanceof Lycanthrope);
    }
}
