package fr.amu.iut.model.spaces;

import fr.amu.iut.model.characters.*;
import fr.amu.iut.model.characters.Character;

public class GalloRomanVillage extends Space {
    public GalloRomanVillage(String name, double surface, Character leader) {
        super(name, surface, leader);
    }

    @Override
    public boolean authorized(Character c) {
        return (c instanceof Innkeeper || c instanceof Blacksmith || c instanceof Druid || c instanceof Merchant ||c instanceof Legionnaire || c instanceof Prefect || c instanceof General);
    }
}
