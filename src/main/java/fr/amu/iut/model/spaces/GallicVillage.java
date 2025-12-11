package fr.amu.iut.model.spaces;

import fr.amu.iut.model.characters.Faction;
import fr.amu.iut.model.characters.jobs.*;
import fr.amu.iut.model.characters.Character;

/**
 * Représente un village gaulois dans le jeu.
 * Les personnages autorisés à entrer dans ce village sont ceux de la faction Gaulois
 * ou les Lycanthropes.
 */
public final class GallicVillage extends Space{

    /**
     * Constructeur de la classe GallicVillage.
     *
     * @param name    Le nom du village gaulois.
     * @param surface La surface du village gaulois.
     * @param leader  Le personnage leader du village gaulois.
     */
    public GallicVillage(String name, double surface, Character leader) {
        super(name, surface, leader);
    }

    /**
     * Vérifie si un personnage est autorisé à entrer dans le village gaulois.
     *
     * @param c Le personnage à vérifier.
     * @return Vrai si le personnage appartient à la faction Gaulois ou est un Lycanthrope, sinon faux.
     */
    @Override
    public boolean authorized(Character c) {
        return c.getFaction() == Faction.GAULOIS || c instanceof Lycanthrope;
    }
}
