package fr.amu.iut.model.spaces;

import fr.amu.iut.model.characters.Character;
import fr.amu.iut.model.characters.jobs.Lycanthrope;

public class Enclosure extends Space {
    public Enclosure(String name, double surface) {
        super(name, surface);
    }

    @Override
    public boolean authorized(Character c){
        return c instanceof Lycanthrope;
    }
}
