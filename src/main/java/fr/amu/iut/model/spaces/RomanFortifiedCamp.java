package fr.amu.iut.model.spaces;

import fr.amu.iut.model.characters.Character;
import fr.amu.iut.model.characters.General;
import fr.amu.iut.model.characters.Legionnaire;
import fr.amu.iut.model.characters.Lycanthrope;


public class RomanFortifiedCamp extends Space{
    public RomanFortifiedCamp(String name, double surface, Character leader) {
        super(name, surface, leader);
    }

    @Override
    public boolean authorized(Character c) {
        return (c instanceof Legionnaire || c instanceof General || c instanceof Lycanthrope);
    }
}
