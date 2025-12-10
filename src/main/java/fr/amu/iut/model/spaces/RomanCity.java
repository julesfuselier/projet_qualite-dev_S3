package fr.amu.iut.model.spaces;

import fr.amu.iut.model.characters.Faction;
import fr.amu.iut.model.characters.jobs.Lycanthrope;
import fr.amu.iut.model.characters.Character;

public final class RomanCity extends Space {

    public RomanCity(String name, double surface, Character leader) {
        super(name, surface, leader);
    }

    @Override
    public boolean authorized(Character c) {
        return c.getFaction() == Faction.ROMAIN || c instanceof Lycanthrope;
    }
}
