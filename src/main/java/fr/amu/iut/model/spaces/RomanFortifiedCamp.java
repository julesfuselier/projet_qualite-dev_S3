package fr.amu.iut.model.spaces;

import fr.amu.iut.model.characters.Character;
import fr.amu.iut.model.characters.Faction;
import fr.amu.iut.model.characters.Fighter;
import fr.amu.iut.model.characters.jobs.Lycanthrope;


public final class RomanFortifiedCamp extends Space {
    public RomanFortifiedCamp(String name, double surface, Character leader) {
        super(name, surface, leader);
    }

    @Override
    public boolean authorized(Character c) {
        return (c.getFaction() == Faction.ROMAIN && c instanceof Fighter) || c instanceof Lycanthrope;
    }

}
