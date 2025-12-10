package fr.amu.iut.model.spaces;

import fr.amu.iut.model.characters.Faction;
import fr.amu.iut.model.characters.Character;

public final class GalloRomanVillage extends Space {
    public GalloRomanVillage(String name, double surface, Character leader) {
        super(name, surface, leader);
    }

    @Override
    public boolean authorized(Character c) {
        return c.getFaction() == Faction.GAULOIS || c.getFaction() == Faction.ROMAIN;
    }
}
