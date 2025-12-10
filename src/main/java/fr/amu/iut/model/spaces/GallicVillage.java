package fr.amu.iut.model.spaces;

import fr.amu.iut.model.characters.Faction;
import fr.amu.iut.model.characters.jobs.*;
import fr.amu.iut.model.characters.Character;

public final class GallicVillage extends Space{

    public GallicVillage(String name, double surface, Character leader) {
        super(name, surface, leader);
    }

    @Override
    public boolean authorized(Character c) {
        return c.getFaction() == Faction.GAULOIS || c instanceof Lycanthrope;
    }
}
