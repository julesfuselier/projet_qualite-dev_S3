package fr.amu.iut.model.spaces;

import fr.amu.iut.model.characters.Character;

public final class Battlefield extends Space{
    public Battlefield(String name, double surface){
        super(name, surface);
    }

    @Override
    public boolean authorized(Character c){
        return true;
    }
}
