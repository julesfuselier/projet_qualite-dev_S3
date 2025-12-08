package fr.amu.iut.model.spaces;

import fr.amu.iut.model.characters.Character;
import fr.amu.iut.model.characters.ClanLeader;
import fr.amu.iut.model.characters.jobs.General;
import fr.amu.iut.model.characters.jobs.Legionary;
import fr.amu.iut.model.characters.jobs.Lycanthrope;


public final class RomanFortifiedCamp extends Space{
    public RomanFortifiedCamp(String name, double surface, Character leader) {
        super(name, surface, leader);
    }

    @Override
    public boolean authorized(Character c) {
        return (c instanceof Legionary || c instanceof General || c instanceof Lycanthrope || c instanceof ClanLeader);    }
}
