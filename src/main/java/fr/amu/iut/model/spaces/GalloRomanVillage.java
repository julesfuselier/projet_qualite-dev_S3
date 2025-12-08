package fr.amu.iut.model.spaces;

import fr.amu.iut.model.characters.jobs.*;
import fr.amu.iut.model.characters.Character;

public final class GalloRomanVillage extends Space {
    public GalloRomanVillage(String name, double surface, Character leader) {
        super(name, surface, leader);
    }

    @Override
    public boolean authorized(Character c) {
        return (c instanceof Innkeeper || c instanceof Blacksmith || c instanceof Druid || c instanceof Merchant ||c instanceof Legionary || c instanceof Prefect || c instanceof General);
    }
}
